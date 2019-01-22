package com.iawtr.web.dto.bdch;

import java.io.Serializable;
import java.util.ArrayList;

public class OwnershipInfoResponseData implements Serializable{
	private static final long serialVersionUID = 1L;
	private ArrayList<OwnershipInfoResponseInfo> arrayList;	//���ز�ѯ�б�
	private int totalCount;	//��ѯ�ܼ�¼��
	private int limit;	//��ѯ����
	
	public ArrayList<OwnershipInfoResponseInfo> getArrayList() {
		return arrayList;
	}
	public void setArrayList(ArrayList<OwnershipInfoResponseInfo> arrayList) {
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
