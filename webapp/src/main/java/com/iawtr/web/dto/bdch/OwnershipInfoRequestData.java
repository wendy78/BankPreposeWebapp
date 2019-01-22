package com.iawtr.web.dto.bdch;

import java.io.Serializable;
import java.util.ArrayList;

public class OwnershipInfoRequestData implements Serializable{
	private static final long serialVersionUID = 1L;
	private ArrayList<OwnershipInfoRequestInfo> arrayList;	//�����ѯ����
	private int totalCount;
	private int limit;
	
	public ArrayList<OwnershipInfoRequestInfo> getArrayList() {
		return arrayList;
	}
	public void setArrayList(ArrayList<OwnershipInfoRequestInfo> arrayList) {
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
