package com.iawtr.database.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.LockOptions;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Session.LockRequest;
import org.hibernate.SessionFactory;
import com.iawtr.commons.util.BeanUtils;
import com.iawtr.commons.util.StrHelper;
import com.iawtr.commons.util.TimeHelper;
import com.iawtr.database.dto.DtoInterface;

public class HibernateDao {
	private final Log log = LogFactory.getLog(getClass());
	private SessionFactory sessionFactory;
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	// *****************数据的查询******
	/**
	 * 以默认(没有锁)的锁模式根据id得到数据
	 * 
	 * @param entityClass
	 * @param id
	 * @return
	 */
	public <T> T get(Class<T> entityClass, Serializable id) {
		return get(new LockOptions().setLockMode(LockMode.NONE), entityClass, id);
	}
	/**
	 * 根据id得到数据
	 * 
	 * @param lockOptions 锁模式
	 * @param entityClass 要操作的dto对象类型
	 * @param id
	 * @return 泛型所表示的类的对象
	 */
	public <T> T get(LockOptions lockOptions, Class<T> entityClass, Serializable id) {
		if (id == null) {
			return null;
		}
		long t1 = Calendar.getInstance().getTimeInMillis();
		Session session = sessionFactory.getCurrentSession();
		T o = (T) session.get(entityClass, id, lockOptions);
		long t2 = Calendar.getInstance().getTimeInMillis();
		long t3 = t2 - t1;
		log.info("^^^^^^^^根据主键" + id + "得到数据,用时:" + t1 + "-" + t2 + "=" + t3);
		return o;
	}
	/**
	 * 根据PagesearchEntity对象以默认(无锁)的锁模式得到总记录数
	 * 
	 * @param entityClass
	 * @param entity
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public <T> List findPageListByEntity(Class<T> entityClass, PageSearchEntity entity, int pageNo, int pageSize) {
		return findPageListByEntity(new LockOptions().setLockMode(LockMode.NONE), entityClass, entity, pageNo, pageSize);
	}
	/**
	 * 根据PagesearchEntity对象得到总记录数
	 * 
	 * @param entityClass 要操作的dto对象类型
	 * @param entity sql语句封装类
	 * @param pageNo
	 * @param pageSize 为0不分页
	 * @return
	 */
	public <T> List findPageListByEntity(LockOptions lockOptions, Class<T> entityClass, PageSearchEntity entity, int pageNo, int pageSize) {
		log.debug("根据查询实体类进行分页查询");
		StringBuffer hql = new StringBuffer();
		if (entity != null) {
			if (entity.getSelectField().toString().trim().length() > 0) {
				// hql.append(" select  new "+entityClass.getName()+"("+entity.getSelectField()+" )"
				// );
				hql.append(" select  " + entity.getSelectField() + " ");
			}
		}
		hql.append(" from ");
		hql.append(entityClass.getName());
		hql.append("  t ");
		Object objs[] = new Object[] {};
		if (entity != null) {
			hql.append(entity.getConditionSql());
			if (StringUtils.isNotBlank(entity.getOrderSql())) {
				hql.append(entity.getOrderSql());
			}
			objs = entity.getParaValue().toArray();
		}
		return findListByHql(lockOptions, hql.toString(), pageNo, pageSize, objs);
	}
	/**
	 * 查询总记录数
	 * 
	 * @param entityClass dto对象的类型
	 * @param entity hql封装类
	 * @return
	 */
	public <T> long findTotalCountByEntity(Class<T> entityClass, PageSearchEntity entity) {
		StringBuffer hql = new StringBuffer();
		hql.append(" select count(*) from ");
		hql.append(entityClass.getName());
		hql.append("  t ");
		Object objs[] = new Object[] {};
		if (entity != null) {
			hql.append(entity.getConditionSql());
			objs = entity.getParaValue().toArray();
		}
		return findTotalCountByHql(hql.toString(), objs);
	}
	/**
	 * 查询总数
	 * 
	 * @param hql hibernate的sql语句
	 * @param values 占位符的参数
	 * @return
	 * @deprecatd
	 */
	public long findTotalCountByHql(String hql, Object... values) {
		long totalCount = 0;
		List countlist = this.findListByHql(hql, 0, 0, values);
		if (countlist != null && countlist.size() > 0) {
			totalCount = Long.parseLong(countlist.get(0) + "");
		}
		return totalCount;
	}
	/**
	 * hibernate查询, 以默认(没有锁)锁模式使用hql
	 * 
	 * @param hql
	 * @param pageNo
	 * @param pageSize
	 * @param values
	 * @return
	 * @deprecated 使用':xxxx'型的占位符取代'?'
	 */
	public List findListByHql(String hql, int pageNo, int pageSize, Object... values) {
		return findListByHql(new LockOptions().setLockMode(LockMode.NONE), hql, pageNo, pageSize, values);
	}
	/**
	 * hibernate查询，使用hql.
	 * 
	 * @param lockOptions 锁模式 
	 * @param hql hibernate的sql语句
	 * @param pageNo 要请求的页面序号, 从1开始(0为1)
	 * @param pageSize 为0不分页
	 * @param values 占位符的参数
	 * @return
	 * @deprecated 使用':xxxx'型的占位符取代'?'
	 */
	public List findListByHql(LockOptions lockOptions, String hql, int pageNo, int pageSize, Object... values) {
		if (pageNo == 0) {
			pageNo = 1;
		}
		long t1 = Calendar.getInstance().getTimeInMillis();
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(hql);
		for (int i = 0; i < values.length; i++) {
			query.setParameter(i, values[i]);
		}
		List list = null;
		if (pageSize == 0) {// 不分页
			query.setLockOptions(lockOptions);
			list = query.list();
		} else {
			int startIndex = (pageNo - 1) * pageSize;
			list = query.setFirstResult(startIndex).setMaxResults(pageSize).list();
//			LockRequest lr=session.buildLockRequest(lockOptions);
			for(int i=0;list!=null&&i<list.size();i++){
//				lr.lock(list.get(i));
				session.refresh(list.get(i), lockOptions);
			}
		}
		long t2 = Calendar.getInstance().getTimeInMillis();
		long t3 = t2 - t1;
		log.info("^^^^^^^^根据分页实体,进行查询,语句" + hql + ",用时:" + t1 + "-" + t2 + "=" + t3);
		return list;
	}
	/**
	 * 原生sql查询
	 * 
	 * @param sql hibernate的sql语句
	 * @param pageNo 要请求的页面序号, 从1开始(0为1)
	 * @param pageSize 为0不分页
	 * @param args 占位符的参数
	 * @return
	 * @deprecated 使用':xxxx'型的占位符取代'?'
	 */
	public List findListBySql(String sql, int pageNo, int pageSize, Object... args) {
		if (pageNo == 0) {
			pageNo = 1;
		}
		long t1 = Calendar.getInstance().getTimeInMillis();
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createSQLQuery(sql);
		// query.setLockOptions(lockOptions); //原生sql不支持锁
		for (int i = 0; i < args.length; i++) {
			query.setParameter(i, args[i]);
		}
		List list = null;
		if (pageSize == 0) {// 不分页
			list = query.list();
		} else {
			int startIndex = (pageNo - 1) * pageSize;
			list = query.setFirstResult(startIndex).setMaxResults(pageSize).list();
		}
		long t2 = Calendar.getInstance().getTimeInMillis();
		long t3 = t2 - t1;
		log.info("^^^^^^^^根据分页实体,进行查询,语句" + sql + ",用时:" + t1 + "-" + t2 + "=" + t3);
		return list;
	}
	// *************** 下面为增改删的操作******
	/**
	 * 新增对象本身
	 * 
	 * @param dto
	 * @return 主键
	 * @throws IllegalAccessException
	 * @throws NoSuchFieldException
	 */
	public Serializable insert(DtoInterface dto) throws IllegalAccessException, NoSuchFieldException {
		// 新增时写入版本信息: 14位的时间字符串("yyyyMMddHHmmSS")
		BeanUtils.setPrivateProperty(dto, dto.getDataVersionName(), TimeHelper.getCurrentTime14());
		long t1 = Calendar.getInstance().getTimeInMillis();
		Session session = sessionFactory.getCurrentSession();
		Serializable key = session.save(dto);
		long t2 = Calendar.getInstance().getTimeInMillis();
		long t3 = t2 - t1;
		log.info("^^^^^^^^新增对象,用时:" + t1 + "-" + t2 + "=" + t3);
		return key;
	}
	public Serializable insertNoVersion(Object dto) throws IllegalAccessException, NoSuchFieldException {
		long t1 = Calendar.getInstance().getTimeInMillis();
		Session session = sessionFactory.getCurrentSession();
		Serializable key = session.save(dto);
		long t2 = Calendar.getInstance().getTimeInMillis();
		long t3 = t2 - t1;
		log.info("^^^^^^^^新增对象,用时:" + t1 + "-" + t2 + "=" + t3);
		return key;
	}
	/**
	 * 更新对象本身
	 * 
	 * @param dto
	 * @throws IllegalAccessException
	 * @throws NoSuchFieldException
	 */
	public void update(DtoInterface dto) throws IllegalAccessException, NoSuchFieldException {
		// 更新时写入版本信息: 14位的时间字符串("yyyyMMddHHmmSS")
		BeanUtils.setPrivateProperty(dto, dto.getDataVersionName(), TimeHelper.getCurrentTime14());
		long t1 = Calendar.getInstance().getTimeInMillis();
		Session session = sessionFactory.getCurrentSession();
		session.update(dto);
		long t2 = Calendar.getInstance().getTimeInMillis();
		long t3 = t2 - t1;
		log.info("^^^^^^^^更新对象,用时:" + t1 + "-" + t2 + "=" + t3);
	}
	/**
	 * 原始的更新数据
	 * @param dto
	 * @throws IllegalAccessException
	 * @throws NoSuchFieldException
	 */
	public void updateNoVersion(Object dto) throws IllegalAccessException, NoSuchFieldException {
		// 更新时写入版本信息: 14位的时间字符串("yyyyMMddHHmmSS")
//		BeanUtils.setPrivateProperty(dto, dto.getDataVersionName(), TimeHelper.getCurrentTime14());
		long t1 = Calendar.getInstance().getTimeInMillis();
		Session session = sessionFactory.getCurrentSession();
		session.update(dto);
		long t2 = Calendar.getInstance().getTimeInMillis();
		long t3 = t2 - t1;
		log.info("^^^^^^^^更新对象,用时:" + t1 + "-" + t2 + "=" + t3);
	}
	/**
	 * 新增对象拷贝
	 * 
	 * @param dto
	 * @return 更新过的持久化对象
	 * @throws IllegalAccessException
	 * @throws NoSuchFieldException
	 */
	public Object merge(DtoInterface dto) throws IllegalAccessException, NoSuchFieldException {
		// 拷贝新增时写入版本信息: 14位的时间字符串("yyyyMMddHHmmSS")
		BeanUtils.setPrivateProperty(dto, dto.getDataVersionName(), TimeHelper.getCurrentTime14());
		long t1 = Calendar.getInstance().getTimeInMillis();
		Session session = sessionFactory.getCurrentSession();
		Object key = session.merge(dto);
		long t2 = Calendar.getInstance().getTimeInMillis();
		long t3 = t2 - t1;
		log.info("^^^^^^^^新增对象,用时:" + t1 + "-" + t2 + "=" + t3);
		return key;
	}
	public Object mergeNoVersion(Object dto) throws IllegalAccessException, NoSuchFieldException {
		long t1 = Calendar.getInstance().getTimeInMillis();
		Session session = sessionFactory.getCurrentSession();
		Object key = session.merge(dto);
		long t2 = Calendar.getInstance().getTimeInMillis();
		long t3 = t2 - t1;
		log.info("^^^^^^^^新增对象,用时:" + t1 + "-" + t2 + "=" + t3);
		return key;
	}
	/**
	 * 新增或更新对象
	 * 
	 * @param o
	 * @throws IllegalAccessException
	 * @throws NoSuchFieldException
	 */
	public void saveOrUpdate(DtoInterface o) throws IllegalAccessException, NoSuchFieldException {
		// 新增或更新时写入版本信息: 14位的时间字符串("yyyyMMddHHmmSS")
		BeanUtils.setPrivateProperty(o, o.getDataVersionName(), TimeHelper.getCurrentTime14());
		long t1 = Calendar.getInstance().getTimeInMillis();
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(o);
		long t2 = Calendar.getInstance().getTimeInMillis();
		long t3 = t2 - t1;
		log.info("^^^^^^^^新增或更新单个对象,用时:" + t1 + "-" + t2 + "=" + t3);
	}
	/**
	 * 删除对象
	 * 
	 * @param dto
	 */
	public void delete(DtoInterface dto) {
		long t1 = Calendar.getInstance().getTimeInMillis();
		Session session = sessionFactory.getCurrentSession();
		session.delete(dto);
		long t2 = Calendar.getInstance().getTimeInMillis();
		long t3 = t2 - t1;
		log.info("^^^^^^^^删除单个对象,用时:" + t1 + "-" + t2 + "=" + t3);
	}
	/**
	 * <pre>
	 * 根据hibernate的sql批量更新或者删除
	 * 注意: 
	 * 	<b>使用该方法时请注意 数据版本的更新
	 * 	在熟悉hql的情况下推荐使用该方法.有利于将hq1以很直观的方式写出来
	 * 		如:" update tableclass t set t.field1=? ,t.field2=? where t.id=?"
	 * 		或 " delete from  tableclass where t.id=?"
	 * </pre>
	 * 
	 * @param hql hibernate的sql
	 * @param values 占位符的值
	 * @return 受影响的行数
	 * @deprecated 使用':xxxx'型的占位符取代'?'
	 */
	public int updateOrDeleteByHql(String hql, Object... values) {
		long t1 = Calendar.getInstance().getTimeInMillis();
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(hql);
		for (int i = 0; i < values.length; i++) {
			query.setParameter(i, values[i]);
		}
		int size = query.executeUpdate();
		long t2 = Calendar.getInstance().getTimeInMillis();
		long t3 = t2 - t1;
		log.info("^^^^^^^^执行批量操作语句>>" + hql + ",用时:" + t1 + "-" + t2 + "=" + t3);
		return size;
	}
	/**
	 * <pre>
	 * 根据原生的sql批量更新或者删除
	 * 注意: 
	 * 	推荐使用updateOrDeleteByHql方法 
	 * 	<b>如果使用该方法时请注意 数据版本的更新
	 * </pre>
	 * 
	 * @see updateOrDeleteByHql
	 * @param sql 原生sql
	 * @param values 占位符的值
	 * @return 更新或删除的行数
	 * @deprecated 使用':xxxx'型的占位符取代'?'
	 */
	public int updateOrDeleteBySql(String sql, Object... values) {
		long t1 = Calendar.getInstance().getTimeInMillis();
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createSQLQuery(sql);
		for (int i = 0; i < values.length; i++) {
			query.setParameter(i, values[i]);
		}
		int size = query.executeUpdate();
		long t2 = Calendar.getInstance().getTimeInMillis();
		long t3 = t2 - t1;
		log.info("^^^^^^^^执行批量操作语句>>" + sql + ",用时:" + t1 + "-" + t2 + "=" + t3);
		return size;
	}
	/**
	 * 根据PagesearchEntity对象批量更新数据
	 * 
	 * @param entityClass 要操作的dto对象类型
	 * @param pEntity hql的封装类
	 * @return 更新的行数
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public int updateByEntity(Class<? extends DtoInterface> entityClass, PageSearchEntity pEntity) throws InstantiationException, IllegalAccessException {
		DtoInterface ih = entityClass.newInstance();
		if (pEntity.getUpdateSql().toString().indexOf(ih.getDataVersionName()) == -1) {
			pEntity.constructUpdateSqlByArray(new String[] { ih.getDataVersionName() }, new String[] { TimeHelper.getCurrentTime14() });
		}
		StringBuffer hql = new StringBuffer();
		hql.append(" update  ");
		hql.append(entityClass.getName());
		Object objs[] = new Object[] {};
		if (pEntity != null) {
			hql.append(pEntity.getUpdateSql());
			hql.append(pEntity.getConditionSql());
			List<Object> lis = new ArrayList<Object>();
			lis.addAll(pEntity.getUpdateValue());
			lis.addAll(pEntity.getParaValue());
			objs = lis.toArray();
		}
		log.debug("根据sql语句执行批量更新数据:" + hql.toString());
		int size = updateOrDeleteByHql(hql.toString(), objs);
		log.debug("批量更新了" + size + "条数据");
		return size;
	}
	/**
	 * 根据PagesearchEntity对象批量删除数据
	 * 
	 * @param entityClass 要操作的dto对象类型
	 * @param pEntity hql封装类
	 * @return 删除的行数
	 */
	public int deleteByEntity(Class<? extends DtoInterface> entityClass, PageSearchEntity pEntity) {
		StringBuffer hql = new StringBuffer();
		hql.append(" delete from  ");
		hql.append(entityClass.getName());
		hql.append(" t ");
		Object objs[] = new Object[] {};
		if (pEntity != null) {
			hql.append(pEntity.getConditionSql());
			objs = pEntity.getParaValue().toArray();
		}
		log.debug("根据sql语句执行批量删除数据:" + hql.toString());
		int size = updateOrDeleteByHql(hql.toString(), objs);
		log.debug("批量删除了" + size + "条数据");
		return size;
	}
	// *****下面为系统级的操作,请慎用******
	/**
	 * 给hibernate管理的对象加锁
	 * @param obj
	 * @param lockOptions
	 */
	public void lock(Object obj,LockOptions lockOptions){
		long t1 = Calendar.getInstance().getTimeInMillis();
		Session session = sessionFactory.getCurrentSession();
		if(lockOptions.getTimeOut()==-1){
			lockOptions.setTimeOut(3000);
		}
		if(obj instanceof List){
			LockRequest lr=session.buildLockRequest(lockOptions);
			for(Object dto:(List)obj ){
				lr.lock(dto);
			}
		}else{
			session.buildLockRequest(lockOptions).lock(obj);
		}
		long t2 = Calendar.getInstance().getTimeInMillis();
		long t3 = t2 - t1;
		log.info("^^^^^^^^数据加锁,用时:" + t1 + "-" + t2 + "=" + t3);
	}
	/**
	 * 刷新hibernate管理的对象并且加锁
	 * @param obj
	 * @param lockOptions
	 */
	public void refresh(Object obj,LockOptions lockOptions){
		long t1 = Calendar.getInstance().getTimeInMillis();
		Session session = sessionFactory.getCurrentSession();
		if(lockOptions.getTimeOut()==-1){
			lockOptions.setTimeOut(3000);
		}
		if(obj instanceof List){
			for(Object dto:(List)obj ){
				session.refresh(dto, lockOptions);
			}
		}else{
			session.refresh(obj, lockOptions);
		}
		long t2 = Calendar.getInstance().getTimeInMillis();
		long t3 = t2 - t1;
		log.info("^^^^^^^^刷新数据并加锁,用时:" + t1 + "-" + t2 + "=" + t3);
	}
	/**
	 * <pre>
	 *  强制刷新当前session中的数据
	 *    如下:
	 *     	class serviceimp{
	 *     		Dao dao ..
	 *    		List<TableClass> dtos ..
	 *    		for(int i=0;i<dtos.size();i++){
	 *    			dao.insert  插入的操作...
	 *    			if(i%100==0){//插入了100条数据时进行强制的flush clear操作
	 *    				dao.flush();
	 *    				dao.clear();
	 *    			}
	 *      	}
	 *      }
	 *      在大批量插入时调用可以防止出现内存不足
	 * </pre>
	 * 
	 * @see clear
	 */
	public void flush() {
		Session session = sessionFactory.getCurrentSession();
		session.flush();
	}
	/**
	 * <pre>
	 *  强制清除当前session中的数据
	 *    如下:
	 *     	class serviceimp{
	 *     		Dao dao ..
	 *    		List<TableClass> dtos ..
	 *    		for(int i=0;i<dtos.size();i++){
	 *    			dao.insert  插入的操作...
	 *    			if(i%100==0){//插入了100条数据时进行强制的flush clear操作
	 *    				dao.flush();
	 *    				dao.clear();
	 *    			}
	 *      	}
	 *      }
	 *      在大批量插入时调用可以防止出现内存不足
	 * </pre>
	 * 
	 * @see flush
	 */
	public void clear() {
		Session session = sessionFactory.getCurrentSession();
		session.clear();
	}
	// /*******************************使用':xxx'的占位符 ,取代'?'占位符start*****************************
	// 查询语句
	/**
	 * 查询总数
	 * 
	 * @param hql hibernate的sql语句
	 * @param values 占位符的参数
	 * @return
	 */
	public long findTotalCountByHql(String hql, HashMap<String, Object> mapValues) {
		long totalCount = 0;
		List countlist = this.findListByHql(new LockOptions().setLockMode(LockMode.NONE), hql, 0, 0, mapValues);
		if (countlist != null && countlist.size() > 0) {
			totalCount = Long.parseLong(countlist.get(0) + "");
		}
		return totalCount;
	}
	/**
	 * hibernate查询，使用hql.使用LockMode.NONE的锁模式
	 * 
	 * @param hql hibernate的sql语句
	 * @param pageNo 要请求的页面序号, 从1开始(0为1)
	 * @param pageSize 为0不分页
	 * @param values 占位符的参数
	 * @return
	 */
	public List findListByHql(String hql, int pageNo, int pageSize, HashMap<String, Object> mapValues) {
		return findListByHql(new LockOptions().setLockMode(LockMode.NONE), hql, pageNo, pageSize, mapValues);
	}
	/**
	 * hibernate查询，使用hql.
	 * 
	 * @param lockOptions 锁模式 
	 * @param hql hibernate的sql语句
	 * @param pageNo 要请求的页面序号, 从1开始(0为1)
	 * @param pageSize 为0不分页
	 * @param values HashMap<String, Object>型,key为占位符,val为值
	 * @return
	 */
	public List findListByHql(LockOptions lockOptions, String hql, int pageNo, int pageSize, HashMap<String, Object> mapValues) {
		if (pageNo == 0) {
			pageNo = 1;
		}
		long t1 = Calendar.getInstance().getTimeInMillis();
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(hql);
		Iterator<String> it = mapValues.keySet().iterator();
		while (it.hasNext()) {
			String key = it.next();
			Object val = mapValues.get(key);
			if (val instanceof List ) {
				query.setParameterList(key, (List) val);
			}else if ( val instanceof Object[]) {
				query.setParameterList(key, (Object[]) val);
			} else {
				query.setParameter(key, val);
			}
		}
		List list = null;
		if (pageSize == 0) {// 不分页
			query.setLockOptions(lockOptions);
			list = query.list();
		} else {
			int startIndex = (pageNo - 1) * pageSize;
			list = query.setFirstResult(startIndex).setMaxResults(pageSize).list();
//			LockRequest lr=session.buildLockRequest(lockOptions);
//			for(int i=0;list!=null&&i<list.size();i++){//联合查询不适用
////				lr.lock(list.get(i));
//				session.refresh(list.get(i), lockOptions);
//			}
		}
		long t2 = Calendar.getInstance().getTimeInMillis();
		long t3 = t2 - t1;
		log.info("^^^^^^^^根据分页实体,进行查询,语句" + hql + ",用时:" + t1 + "-" + t2 + "=" + t3);
		return list;
	}
	/**
	 * 原生sql查询
	 * 
	 * @param sql hibernate的sql语句
	 * @param pageNo 要请求的页面序号, 从1开始(0为1)
	 * @param pageSize 为0不分页
	 * @param args 占位符的参数
	 * @return
	 */
	public List findListBySql(String sql, int pageNo, int pageSize, HashMap<String, Object> mapValues) {
		if (pageNo == 0) {
			pageNo = 1;
		}
		long t1 = Calendar.getInstance().getTimeInMillis();
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createSQLQuery(sql);
		Iterator<String> it = mapValues.keySet().iterator();
		while (it.hasNext()) {
			String key = it.next();
			Object val = mapValues.get(key);
			if (val instanceof List ) {
				query.setParameterList(key, (List) val);
			}else if ( val instanceof Object[]) {
				query.setParameterList(key, (Object[]) val);
			} else {
				query.setParameter(key, val);
			}
		}
		List list = null;
		if (pageSize == 0) {// 不分页
			list = query.list();
		} else {
			int startIndex = (pageNo - 1) * pageSize;
			list = query.setFirstResult(startIndex).setMaxResults(pageSize).list();
		}
		long t2 = Calendar.getInstance().getTimeInMillis();
		long t3 = t2 - t1;
		log.info("^^^^^^^^根据分页实体,进行查询,语句" + sql + ",用时:" + t1 + "-" + t2 + "=" + t3);
		return list;
	}
	// ********更新语句**********
	/**
	 * <pre>
	 * 根据hibernate的sql批量更新或者删除
	 * 注意: 
	 * 	<b>使用该方法时请注意 数据版本的更新
	 * 	在熟悉hql的情况下推荐使用该方法.有利于将hq1以已很直观的方式写出来
	 * 		如:" update tableclass t set t.field1=? ,t.field2=? where t.id=?"
	 * 		或 " delete from  tableclass where t.id=?"
	 * <b> 2 注意该方法中会进行clear的操作,为了使更新或删除的结果即时入库(因为hibernate缓存的原因,hql语句会批量执行且执行时查询会在更新前面,防止更新后的查询得不到事务中的最新数据)
	 * </pre>
	 * 
	 * @param hql hibernate的sql
	 * @param values 占位符的值
	 * @return 受影响的行数
	 */
	public int updateOrDeleteByHql(String hql, HashMap<String, Object> mapValues) {
		long t1 = Calendar.getInstance().getTimeInMillis();
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(hql);
		Iterator<String> it = mapValues.keySet().iterator();
		while (it.hasNext()) {
			String key = it.next();
			Object val = mapValues.get(key);
			if (val instanceof List ) {
				query.setParameterList(key, (List) val);
			}else if ( val instanceof Object[]) {
				query.setParameterList(key, (Object[]) val);
			} else {
				query.setParameter(key, val);
			}
		}
		int size = query.executeUpdate();
		session.clear();//将更新的结果即时更新进去
		long t2 = Calendar.getInstance().getTimeInMillis();
		long t3 = t2 - t1;
		log.info("^^^^^^^^执行批量操作语句>>" + hql + ",用时:" + t1 + "-" + t2 + "=" + t3);
		return size;
	}
	/**
	 * <pre>
	 * 根据原生的sql批量更新或者删除
	 * 注意: 
	 * 	推荐使用updateOrDeleteByHql方法 
	 * 	<b>如果使用该方法时请注意 数据版本的更新
	 * </pre>
	 * 
	 * @see updateOrDeleteByHql
	 * @param sql 原生sql
	 * @param values 占位符的值
	 * @return 更新或删除的行数
	 */
	public int updateOrDeleteBySql(String sql, HashMap<String, Object> mapValues) {
		long t1 = Calendar.getInstance().getTimeInMillis();
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createSQLQuery(sql);
		Iterator<String> it = mapValues.keySet().iterator();
		while (it.hasNext()) {
			String key = it.next();
			Object val = mapValues.get(key);
			if (val instanceof List ) {
				query.setParameterList(key, (List) val);
			}else if ( val instanceof Object[]) {
				query.setParameterList(key, (Object[]) val);
			} else {
				query.setParameter(key, val);
			}
		}
		int size = query.executeUpdate();
		long t2 = Calendar.getInstance().getTimeInMillis();
		long t3 = t2 - t1;
		log.info("^^^^^^^^执行批量操作语句>>" + sql + ",用时:" + t1 + "-" + t2 + "=" + t3);
		return size;
	}
	// /*******************************使用':xxxx'的占位符 ,取代'?'占位符end*****************************
	// //////********************业务相关start***********************
	/**
	 * 获取回执编号
	 * 
	 * @param qz 回执编号前缀
	 * @return
	 */
	public String getHzbh(String qz) {
		return qz + StrHelper.strBuwei(findListBySql("select hzbh_xuhao.nextval from dual", 1, 0).get(0) + "", 14);
	}
	// //////*********************业务相关end*************************
	
	/**
	 * 获取序列号
	 * 
	 * @param xlmc 序列名称
	 * @return
	 */
	public String getXlh(String xlmc) {
		return findListBySql("select " + xlmc + ".nextval from dual", 1, 0).get(0) + "";
	}
	/**
	 * 获取包号,定长11位.得到一个8位日期+有序数字(3位)的一个值,
	 * 
	 * @return
	 */
	public String getBh() {
		String dt = TimeHelper.getCurrentDate();
		String xh = getXlh("BAGNO");
		return dt + StrHelper.strBuwei(xh, 3);
	}
	
	
}
