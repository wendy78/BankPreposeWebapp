package com.iawtr.service.main.impl;

import java.io.File;
import java.util.HashMap;
import java.util.UUID;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.iawtr.database.dao.HibernateDao;
import com.iawtr.database.dao.PageSearchEntity;
import com.iawtr.database.dto.bizoperate.Bizoperate;
import com.iawtr.exception.BizException;
import com.iawtr.service.main.MainService;
import com.iawtr.web.enumerate.SystemConstants;


/**
 * 业务类逻辑实现的基类，每个业务逻辑实现类必须继承此类
 * <p/>
 */
public class MainServiceImpl implements MainService {
	/**
	 * 在线用户组 非游客用户以desktopId和userId作为主键的两条相同记录 分布式的情况下,利用elasticsearch(搜索引擎)或数据库,在其中建立UserStatus对应的表,将onlineUsers放入表中,实现应用服务器多台部署且用户唯一登录 在这种情况下,该表中的最大记录为所有用户的2倍
	 */
//	protected static HashMap<String, UserStatus> onlineUsers = new HashMap<String, UserStatus>();
//	private static HashMap<String, List<SysDmWeb>> XTZDMAP;// 静态储存代码类别HashMap<序列号, List<对象>>
	protected transient final Log log = LogFactory.getLog(getClass());
	/**
	 * 所有的桌面图标的集合
	 */
//	protected static List<DesktopShortcut> allShortcuts = null;
	/**
	 * bizoperate表的所有记录集合 记录id为主键,记录为值
	 */
	protected static HashMap<String, Bizoperate> allBizoperates = null;
	protected HibernateDao hibernateDao;
	public HibernateDao getHibernateDao() {
		return hibernateDao;
	}
	public void setHibernateDao(HibernateDao hibernateDao) {
		this.hibernateDao = hibernateDao;
	}

	@Override
	public String buildFilePath(String desktopId, String extName) throws BizException {
		return buildFilePath(desktopId, "", "", extName);
	}
	@Override
	public String buildFilePath(String desktopId, String prefixName, String currentFileName, String extName)  {
		String fileName = "";
		if (prefixName != null && !prefixName.trim().equals("")) {
			fileName = prefixName + ")_]_}_";
		}
		if (currentFileName != null && !currentFileName.trim().isEmpty()) {
			fileName = fileName + currentFileName;
		} else {
			fileName = fileName + UUID.randomUUID().toString();
		}
		fileName = fileName + "." + extName;
		String filePath = "";
		if (desktopId == null || desktopId.trim().isEmpty()) {
			filePath = SystemConstants.BUFFERSAVEPATH + "/" + fileName;
			return filePath;
		}
//		UserStatus us = fetchUserStatus(desktopId);
		File f = new File(SystemConstants.APPLICATIONREALPATH + "/" + SystemConstants.BUFFERSAVEPATH);
		if (!f.exists()) {
			f.mkdir();
		}
//		if (us.isVisitor()) {
//			File visitorFile = new File(SystemConstants.APPLICATIONREALPATH + "/" + SystemConstants.BUFFERSAVEPATH + "/vistorTmp");
//			if (!visitorFile.exists()) {
//				visitorFile.mkdir();
//			}
//			filePath = SystemConstants.BUFFERSAVEPATH + "/vistorTmp" + "/" + fileName;
//		} else {
//			File userFile = new File(SystemConstants.APPLICATIONREALPATH + "/" + SystemConstants.BUFFERSAVEPATH + "/" + us.getUserId());
//			if (!userFile.exists()) {
//				userFile.mkdir();
//			}
//			filePath = SystemConstants.BUFFERSAVEPATH + "/" + us.getUserId() + "/" + fileName;
//		}
		return filePath;
	}
	@Override
	public void loadAllBizObj() {
		if (allBizoperates == null) {
			allBizoperates = new HashMap<String, Bizoperate>();
//			PageSearchEntity pe = new PageSearchEntity();
//			List<TblredtreeXtxx> ltx = hibernateDao.findPageListByEntity(TblredtreeXtxx.class, pe, 0, 0);
			// List<Bizoperate> ls = hibernateDao.findPageListByEntity(Bizoperate.class, pe, 0, 0);
			// for (Bizoperate bizo : ls) {
//			for (TblredtreeXtxx trt : ltx) {
				Bizoperate bizo = new Bizoperate();
				bizo.setOpeId("");// private String opeId;
				bizo.setOpetypeno("");// private String opetypeno;
				bizo.setOpedevdes("");// private String opedevdes;
				bizo.setOpecusdes("");// private String opecusdes;
				/**
				 * 保存动态值的字段,替换opecusdes中的"#"
				 */
				bizo.setDynamicstr("");// private String dynamicstr;
				bizo.setOpeflag("");// private String opeflag;
				allBizoperates.put(bizo.getOpeId(), bizo);
//			}
		}
	}
	
	public void getAllDmlb() {
		
	}
}
