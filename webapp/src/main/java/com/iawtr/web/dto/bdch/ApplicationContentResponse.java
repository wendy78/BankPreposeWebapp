package com.iawtr.web.dto.bdch;

import java.io.Serializable;

public class ApplicationContentResponse implements Serializable{
	private static final long serialVersionUID = 1L;
	private String code;
	private String msg;
	private ApplicationContentResponseData data; //t�޸ĸ�֪odo:
	
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
	public ApplicationContentResponseData getData() {
		return data;
	}
	public void setData(ApplicationContentResponseData data) {
		this.data = data;
	}
	
}
