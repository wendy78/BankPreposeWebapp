package com.iawtr.web.dto.bdch;

import java.io.Serializable;

public class OwnershipInfoResponse implements Serializable{
	private static final long serialVersionUID = 1L;
	private String code;	//���ش���
	private String msg;	//������Ϣ
	private OwnershipInfoResponseData data;	//���ز�ѯ���
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public OwnershipInfoResponseData getData() {
		return data;
	}
	public void setData(OwnershipInfoResponseData data) {
		this.data = data;
	}
	
	
}
