package com.iawtr.web.dto;

import java.io.Serializable;
import java.util.List;

/**
 * 该类封装了前后台互相传递的数据,比如表格数据,页面信息数据等.
 * @author Administrator
 *
 */
public class CollectionData implements Serializable{
	private static final long serialVersionUID = 1L;
	/** 
	 * 当前要获得数据的页
	 */
	protected int currentPage=1;
	/** 
	 * 默认的分页大小
	 */
	protected int pageSize = 20;
	/**
	 * 数据分页时开始的记录数索引,以0开始
	 */
	protected int start=0;
	/**
	 * 数据总记录数
	 */
	protected long totalCount=0;
	/**
	 * 集合数据list (适用于后台集合数据到前台,目前用于前台store请求数据返回)
	 */
	protected List dataList;
	///currentPage  pageSize  start  totalCount  dataList主要适用于前台表格和后台的交互
	//下面的属于自定义,直接适用于前台ajax方式直接和后台交互
	//特别注意:前后台的交互,请使用对象进行交互
	/**
	 * 单个数据对象	(适用于后台自定义对象到前台数据的封装,到前台需要相应的转化后使用)
	 */
	protected Object data;
	/**
	 * 单个数据对象的id字符串 (适用于前台一个字符串到后台的传输,后台直接使用不需要反序列化对象的操作.不推荐使用'直接的字符串'传输到后台,请使用'对象序列化后的字符串')
	 */
	@Deprecated
	protected String dataId;
	/**
	 * json字符串,默认情况下指的是一个对象(javascript/java对象)转化成的json字符串 (适用于前台对象转化为string后到后台的传输,后台使用需要反序列化对象)
	 */
	protected String jsonData;
	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}

	public List getDataList() {
		return dataList;
	}

	public void setDataList(List dataList) {
		this.dataList = dataList;
	}

	public String getJsonData() {
		return jsonData;
	}

	public void setJsonData(String jsonData) {
		this.jsonData = jsonData;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getDataId() {
		return dataId;
	}

	public void setDataId(String dataId) {
		this.dataId = dataId;
	}
	
}
