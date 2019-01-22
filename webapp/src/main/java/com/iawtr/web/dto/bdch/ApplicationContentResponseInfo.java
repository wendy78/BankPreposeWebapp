package com.iawtr.web.dto.bdch;

import java.io.Serializable;

public class ApplicationContentResponseInfo implements Serializable{
	private static final long serialVersionUID = 1L;
	private String uid;
	private String errinfo;
	
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getErrinfo() {
		return errinfo;
	}
	public void setErrinfo(String errinfo) {
		this.errinfo = errinfo;
	}
}
