package com.iawtr.web.dto.bdch;

import java.io.Serializable;
import java.util.ArrayList;

public class ApplicationStatusResponseData implements Serializable{
	private static final long serialVersionUID = 1L;
	private ArrayList<ApplicationStatusResponseInfo> arrayList;
	private int totalCount;
	private int limit;
	
	public ArrayList<ApplicationStatusResponseInfo> getArrayList() {
		return arrayList;
	}
	public void setArrayList(ArrayList<ApplicationStatusResponseInfo> arrayList) {
		this.arrayList = arrayList;
	}
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	public int getLimit() {
		return limit;
	}
	public void setLimit(int limit) {
		this.limit = limit;
	}
	
	
}
