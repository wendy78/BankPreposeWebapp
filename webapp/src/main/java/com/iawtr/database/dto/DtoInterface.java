package com.iawtr.database.dto;
/**
 * 所有的数据库dto类要实现该接口
 * @author Administrator
 *
 */
public interface DtoInterface {
	/**
	 * @return 实体类的主键字段名
	 */
	public String getIdName();
	/**
	 * 标示实体类数据版本的字段名
	 * @return
	 */
	public String getDataVersionName();
}
