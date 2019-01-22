package com.iawtr.database.dao;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.Assert;

/**
 * 简单hql的封装类. 不支持数据库表join的操作
 * @author Administrator
 *
 */
public class PageSearchEntity {
	private transient final Log log = LogFactory.getLog(getClass());
	private String orderSql = "";//构建的排序sql,  如:  order by desj  desc
	private StringBuffer conditionSql;//查询条件--构建的带占位符的sql条件语句,如:  and zwxm=?;
	private List<Object> paraValue; //查询条件--sql条件语句中占位符对应参数
	private StringBuffer updateSql;//更新--构建的带占位符的set 语句,如: set  zwxm=?,sfzh=?
	private List<Object> updateValue;//更新--set语句中占位符对应的参数
	private StringBuffer selectField;//查询字段--要查询用的字段
	private boolean ignoreFlag=false;//是否忽略空值的标志
	public PageSearchEntity() {
		paraValue = new ArrayList<Object>();
		conditionSql=new StringBuffer();
		updateSql=new StringBuffer();
		updateValue=new ArrayList<Object>();
		selectField=new StringBuffer();
	}	
	public boolean isIgnoreFlag() {
		return ignoreFlag;
	}
	public void setIgnoreFlag(boolean ignoreFlag) {
		this.ignoreFlag = ignoreFlag;
	}
	public StringBuffer getSelectField() {
		return selectField;
	}
	public String getOrderSql() {
		return orderSql;
	}
	public void setOrderSql(String orderSql) {
		this.orderSql = orderSql;
	}
	public List<Object> getParaValue() {
		return paraValue;
	}
	public StringBuffer getUpdateSql() {
		return updateSql;
	}	
	public StringBuffer getConditionSql() {
		return conditionSql;
	}
	public List<Object> getUpdateValue() {
		return updateValue;
	}
	/**
	 * 根据那么字段名数组,构建select语句  
	 * 此方法不完整,还需抽象. 需要将selectField返回的数组转为对象
	 * @param nameArray
	 */
	public  void constructSelectFieldSqlByArray(String[] nameArray){
		if(nameArray==null||nameArray.length<=0){
			return;
		}
		if(this.selectField.toString().trim().length()>0){
			this.selectField.append(" , ");
		}
		int count=0;
		for(int i=0;i<nameArray.length;i++){
			if(count>0){
				this.selectField.append(" , ");
			}
			this.selectField.append(nameArray[i]);		
			count++;
		}
	}
	/**
	 * 将数据库的一个字段名和多个值对应起来)
	 * @param joinType	
	 * @param name
	 * @param pType
	 * @param arrayValue
	 */
	 public void constructSqlByArray(String joinType,String name, String pType, Object[] arrayValue) {
		 constructSqlByArray(joinType,new String[]{name},pType,arrayValue);
	 }
	/**
	 * 将数据库的多个字段名和一个值对应起来)
	 * @param joinType	
	 * @param nameArray
	 * @param pType
	 * @param value
	 */
	 public void constructSqlByArray(String joinType,String[] nameArray, String pType, Object value) {
		 constructSqlByArray(joinType,nameArray,pType,new Object[]{value});
	 }
	/**
	 * 将数据库的一个字段名和一个值对应起来)
	 * @param joinType	
	 * @param name
	 * @param pType
	 * @param value
	 */
	 public void constructSqlByArray(String joinType,String name, String pType, Object value) {
		 constructSqlByArray(joinType,new String[]{name},pType,new Object[]{value});
	 }
	/**
     * 将多个数据库字段名和 多个值对应   其间是or的关系
     * @param joinType   逻辑连接运算符  and或者or
     * @param nameArray  数据库字段名数组
     * @param pType  运算符 “=”
     * @param arrayValue  值
     * @return  组织好的 sql语句,如：" joinType (  ( nameArray[0]= arrayValue[0] or nameArray[1]= arrayValue[0] or nameArray[2]= arrayValue[0] ) or (nameArray[0]= arrayValue[1] or nameArray[1]= arrayValue[1] or nameArray[2]= arrayValue[1]  ) or (... ) )"
     * 如果是第一次调用此方法 那么上面的and就是where了,实际组织出来的语句中arrayValue[0]等是由?占位的,上面语句为实际运行将占位符换成了相应的值后,最终要进行执行的sql语句
     */
    public void constructSqlByArray(String joinType,String[] nameArray, String pType, Object[] arrayValue) {
        Assert.notEmpty(nameArray,"组织conditionSql时数据库字段名集合中没有元素");
        Assert.notEmpty(arrayValue,"组织conditonSql时值集合中没有元素");
        Assert.hasText(joinType,"组织conditionSql时,逻辑连接运算符必须是个非空格字符串");
        Assert.hasText(pType,"组织conditionSql时,匹配连接运算符必须是个非空格字符串");
        pType=pType.trim();
        
        int returnCount = 0;
        StringBuffer conditionSqltmp=new StringBuffer();
        List<Object> paraValuetmp=new ArrayList<Object>();
        conditionSqltmp.append(" (");
        for (int k = 0; k < arrayValue.length; k++) {
        	Object pValue=arrayValue[k];
        	if(this.ignoreFlag){
	        	if(pValue==null){
	        		continue;
	        	}else{
	        		if(pValue instanceof String){
	        			String str=((String)pValue).trim();
	        			if(str.equals("")){
	        				continue;
	        			}
	        		}else{
	        			//web项目全部以String来进行操作,不考虑其他类型的数据情况,
	        		}
	        	}
        	}else{
        		Assert.notNull(pValue,"组织conditionSql时ignoreFlag为false,不允许放入空值");
        		Assert.hasText(((String)pValue).trim(),"组织conditionSql时ignoreFlag为false,不允许放入空值");
        	}
        	if(pValue!=null&&(pValue.toString().indexOf("%")>0||pValue.toString().indexOf("_")>0)){
        		pType="like";
        	}
            int count = 0;
            if (returnCount > 0) {
            	conditionSqltmp.append(" or (");
            } else {
            	conditionSqltmp.append("  ( ");
            }
            for (int i = 0; i < nameArray.length; i++) {
            	Assert.hasText(nameArray[i],"组织sql时字段名不合法");
                if (count > 0) {
                	conditionSqltmp.append( " or ");
                }                    
                if (StringUtils.isBlank(pType)) {
                	conditionSqltmp.append( nameArray[i]);
                	conditionSqltmp.append( " = ? ");
                	paraValuetmp.add(pValue);
    			} else if (pType.equals("like")) {//匹配value值,如:_3_3,_3%5
    				conditionSqltmp.append(nameArray[i]);
    				conditionSqltmp.append(" like ? ");
    				paraValuetmp.add( pValue );
    			}else if (pType.equals("sublike")) {
    				conditionSqltmp.append(nameArray[i]);
    				conditionSqltmp.append(" like ? ");
    				paraValuetmp.add("%" + pValue + "%");
    			} else if (pType.equals("startlike")) {
    				conditionSqltmp.append(nameArray[i]);
    				conditionSqltmp.append( " like ? ");
    				paraValuetmp.add("%" + pValue);
    			} else if (pType.equals("endlike")) {
    				conditionSqltmp.append( nameArray[i]);
    				conditionSqltmp.append(" like ? ");
    				paraValuetmp.add(pValue + "%");
    			} else if (pType.equals("in")) {
    				conditionSqltmp.append( nameArray[i]);
    				conditionSqltmp.append( " = ? ");
    				paraValuetmp.add(pValue);
    			} else if (pType.equals("=")) {
    				conditionSqltmp.append(nameArray[i]);
    				conditionSqltmp.append(" = ? ");
    				paraValuetmp.add(pValue);
    			} else if (pType.equals(">")) {
    				conditionSqltmp.append(nameArray[i]);
    				conditionSqltmp.append(" > ? ");
    				paraValuetmp.add(pValue);
    			} else if (pType.equals("<")) {
    				conditionSqltmp.append(nameArray[i]);
    				conditionSqltmp.append(" < ? ");
    				paraValuetmp.add(pValue);
    			}
                else if (pType.equals("<>")||pType.equals("!=")) {
    				conditionSqltmp.append(nameArray[i]);
    				conditionSqltmp.append(" != ? ");
    				paraValuetmp.add(pValue);
    			}
    			else {
    				if (pType.indexOf("?") == -1)
    					pType += " ? ";
    				conditionSqltmp.append(nameArray[i]);
    				conditionSqltmp.append(" ");
    				conditionSqltmp.append( pType);
    				paraValuetmp.add(pValue);
    			}
                count++;
                returnCount++;
            }
            conditionSqltmp.append( " )");            
        }
        conditionSqltmp.append(" )");
        if(returnCount==0){
        	return;
        }else{
            if(conditionSql.toString().indexOf("where")==-1){
            	conditionSql.append(" where ");
            	conditionSql.append(conditionSqltmp);
            }else{
            	conditionSql.append(" "+joinType+" ");
            	conditionSql.append(conditionSqltmp);
            }
            paraValue.addAll(paraValuetmp);
        }
    }
    /**
     * 根据传入的bean对象构建sql条件语句,过虑code2(代码释义)字段
     * 
     * @param obj
     * @param joinType  bean对象的属性之间的逻辑连接关系
     * @throws IllegalAccessException 
     * @throws IllegalArgumentException 
     */
    public void constructSqlByEntity(Object obj,String joinType){
    	Assert.notNull(obj,"要构建条件的分页实体对象不能为空");
    	//obj的属性至少有一个合法的值
    	Field[] fields = obj.getClass().getDeclaredFields(); 
    	int count=0;
    	boolean flag=true;
    	StringBuffer conditionSqltmp=new StringBuffer();
    	List<Object> paraValuetmp=new ArrayList<Object>();
    	conditionSqltmp.append("   ( ");
    	for(int i=0;i<fields.length;i++){
    		fields[i].setAccessible(true);
    		String fielName=fields[i].getName();
    		Object fieldValue=null;
			try {
				fieldValue = fields[i].get(obj);
			} catch (IllegalArgumentException e) {
				log.warn("根据传入的bean实体构建sql语句时传入的对象对象不是声明底层字段（或者其子类或实现者）的类或接口的实例!");
				log.warn(this,e);
				flag=false;
			} catch (IllegalAccessException e) {
				log.warn("根据传入的bean实体构建sql语句时字段不可访问!");
				log.warn(this,e);
				flag=false;
			}
			Assert.isTrue(flag,"根据传入的bean实体构建sql语句时异常");
			if(this.ignoreFlag){
				if(fieldValue!=null&& fieldValue.toString().trim().length()>0){	    			
	    			if(count>0&&!(fielName.equals("serialVersionUID")||fielName.startsWith("code2"))){
	    				conditionSqltmp.append("  ");
	    				conditionSqltmp.append(joinType);
	    				conditionSqltmp.append(" ");
	    			}
	    			if(fielName.equals("serialVersionUID")||fielName.startsWith("code2")){
	    				continue;
	    			}else{
	    				conditionSqltmp.append(fielName);
	    				conditionSqltmp.append(" =? ");
	    			}
	    			paraValuetmp.add(fieldValue.toString().trim());
	    			count++;
	    		}    	    		
			}else{
				if(count>0&&!(fielName.equals("serialVersionUID")||fielName.startsWith("code2"))){
    				conditionSqltmp.append("  ");
    				conditionSqltmp.append(joinType);
    				conditionSqltmp.append(" ");
    			}
    			if(fielName.equals("serialVersionUID")||fielName.startsWith("code2")){
    				continue;
    			}else{
    				conditionSqltmp.append(fielName);
    				conditionSqltmp.append(" =? ");
    			}
    			if(fieldValue!=null){
    				paraValuetmp.add(fieldValue.toString().trim());
    			}else{
    				paraValuetmp.add("");
    			}
    			count++;
			}
    		
    	}    	
    	conditionSqltmp.append(" )");
    	if(count==0){
    		return;
    	}else{
        	if(conditionSql.toString().indexOf("where")==-1){
            	conditionSql.append(" where ");
            	conditionSql.append(conditionSqltmp);
            }else{
            	conditionSql.append(" and ");
            	conditionSql.append(conditionSqltmp);
            }
        	paraValue.addAll(paraValuetmp);
    		
    	}
    }
    /**
     * 根据传入的bean对象构建sql条件语句,
     * 
     * @param obj
     * @param joinType  bean对象的属性之间的逻辑连接关系
     * @throws IllegalAccessException 
     * @throws IllegalArgumentException 
     */
    public void constructSqlByEntityAll(Object obj,String joinType){
    	Assert.notNull(obj,"要构建条件的分页实体对象不能为空");
    	//obj的属性至少有一个合法的值
    	Field[] fields = obj.getClass().getDeclaredFields(); 
    	int count=0;
    	boolean flag=true;
    	StringBuffer conditionSqltmp=new StringBuffer();
    	List<Object> paraValuetmp=new ArrayList<Object>();
    	conditionSqltmp.append("   ( ");
    	for(int i=0;i<fields.length;i++){
    		fields[i].setAccessible(true);
    		String fielName=fields[i].getName();
    		Object fieldValue=null;
			try {
				fieldValue = fields[i].get(obj);
			} catch (IllegalArgumentException e) {
				log.warn("根据传入的bean实体构建sql语句时传入的对象对象不是声明底层字段（或者其子类或实现者）的类或接口的实例!");
				log.warn(this,e);
				flag=false;
			} catch (IllegalAccessException e) {
				log.warn("根据传入的bean实体构建sql语句时字段不可访问!");
				log.warn(this,e);
				flag=false;
			}
			Assert.isTrue(flag,"根据传入的bean实体构建sql语句时异常");
			if(this.ignoreFlag){
				if(fieldValue!=null&& fieldValue.toString().trim().length()>0){    			
	    			if(count>0){
	    				conditionSqltmp.append("  ");
	    				conditionSqltmp.append(joinType);
	    				conditionSqltmp.append(" ");
	    			}
	    			if(fielName.equals("serialVersionUID")){
	    				continue;
	    			}else{
	    				conditionSqltmp.append(fielName);
	    				conditionSqltmp.append(" =? ");
	    			}
	    			paraValuetmp.add(fieldValue.toString().trim());
	    			count++;
	    		}  
			}else{
				if(count>0){
    				conditionSqltmp.append("  ");
    				conditionSqltmp.append(joinType);
    				conditionSqltmp.append(" ");
    			}
    			if(fielName.equals("serialVersionUID")){
    				continue;
    			}else{
    				conditionSqltmp.append(fielName);
    				conditionSqltmp.append(" =? ");
    			}
    			paraValuetmp.add(fieldValue.toString().trim());
    			count++;
			}    		  	    		
    	}    	
    	conditionSqltmp.append(" )");
    	if(count==0){
    		return;
    	}else{
        	if(conditionSql.toString().indexOf("where")==-1){
            	conditionSql.append(" where ");
            	conditionSql.append(conditionSqltmp);
            }else{
            	conditionSql.append(" and ");
            	conditionSql.append(conditionSqltmp);
            }
        	paraValue.addAll(paraValuetmp);
    		
    	}
    }
    /**
     * 将原生sql添加进条件语句
     * @param jointype	sql逻辑运算符'and'或者'or'
     * @param obj 原生sql  比如:  zwxm='哈哈哈'
     */
    public void constructOriSql(String jointype,String obj){
    	//Assert.notNull(obj,"要构建条件的分页实体对象不能为空");
    	if(obj==null||obj.trim().equals("")){
    		return;
    	}
    	if(conditionSql.toString().indexOf("where")==-1){
        	conditionSql.append(" where ");
        	conditionSql.append(" ( ");
        	conditionSql.append(obj);
        	conditionSql.append(" ) ");
        }else{
        	conditionSql.append(" "+jointype+" ");
        	conditionSql.append(" ( ");
        	conditionSql.append(obj);
        	conditionSql.append(" ) ");
        }
    }
    /**
     * 在当前对象的条件中插入进来子对象的条件
     * @param pe 子对象
     */
    public void addSubSearEntity(PageSearchEntity pe){
    	if(pe==null){
    		return;
    	}
    	if(conditionSql.toString().indexOf("where")==-1){
        	conditionSql.append(" where ");
        	conditionSql.append(" ( ");
        	conditionSql.append(pe.getConditionSql().toString().replace("where", ""));
        	conditionSql.append(" ) ");
        }else{
        	conditionSql.append(" and ");
        	conditionSql.append(" ( ");
        	conditionSql.append(pe.getConditionSql().toString().replace("where", ""));
        	conditionSql.append(" ) ");
        }
    	for(int i=0;i<pe.getParaValue().size();i++){
			this.paraValue.add(pe.getParaValue().get(i));
		}
    }
    /**
     * 根据字段名和字段值,组织带占位符的updateSql语句,如:  set  zwx=?, zwm=?
     * @param nameArray
     * @param valueArray
     */
    public void constructUpdateSqlByArray(String[] nameArray,Object[] valueArray){
    	Assert.notEmpty(nameArray,"构建updateSql语句时,字段名集合不能为空");
    	Assert.notEmpty(valueArray,"构建updateSql语句时,字段值集合不能为空");
    	Assert.isTrue(nameArray.length==valueArray.length,"构建updateSql语句时,要更新的字段名集合的长度和字段值集合的长度必须相等");
    	if(updateSql.toString().indexOf("set")==-1){
    		updateSql.append(" set ");
    	}
    	for(int i=0;i<nameArray.length;i++){
    		if(updateSql.toString().indexOf("=")!=-1){
    			updateSql.append(" , ");
    		}
    		updateSql.append(nameArray[i]);
    		updateSql.append(" =? ");
    		this.updateValue.add(valueArray[i]);
    	}
    }
    /**
     * 根据字段名和字段值,组织带占位符的updateSql语句,如:  set  zwx=?, zwm=?
     * @param nameArray
     * @param valueArray
     */
    public void constructUpdateOriSql(String usql){
    	if(updateSql.toString().indexOf("set")==-1){
    		updateSql.append(" set ");
    	}
		if(updateSql.toString().indexOf("=")!=-1){
			updateSql.append(" , ");
		}
		updateSql.append(" "+usql+" ");
    }
    public static void  main(String df[]){
    	PageSearchEntity ps=new PageSearchEntity();
    	ps.constructSqlByArray("and", new String[]{"ss"}, " != ", new String[]{"11"});
//    	ps.constructSqlByArray("and", new String[]{"ere"}, " != ", new String[]{""});
    	ps.constructSqlByArray("and", new String[]{"qq"}, " = ", new String[]{"88"});
    	ps.constructSqlByArray("or", new String[]{"sd"}, " != ", new String[]{"22"});
//    	ps.constructSqlByArray("and", new String[]{"ere"}, " != ", new String[]{""});
    	ps.constructSqlByArray("and", new String[]{"qq"}, " = ", new String[]{"88"});
    	System.out.println(ps.getConditionSql());
    	for(int i=0;i<ps.getParaValue().size();i++){
    		System.out.println(ps.getParaValue().get(i));
    	}
    	String dd="(  ( ss > ? or ss < ?  or sd > ? or sd < ?  ) or (ss > ? or ss < ?  or sd > ? or sd < ?  ) ) and  (  ( qq = ?  or ff = ?  or sfzh11 = ?  ) or (qq = ?  or ff = ?  or sfzh11 = ?  ) or (qq = ?  or ff = ?  or sfzh11 = ?  ) )";
    }
}
