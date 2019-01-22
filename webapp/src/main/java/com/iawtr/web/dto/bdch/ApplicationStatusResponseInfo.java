package com.iawtr.web.dto.bdch;

import java.io.Serializable;
import java.util.Date;

public class ApplicationStatusResponseInfo implements Serializable{
	private static final long serialVersionUID = 1L;
	private String uid;
	private String status;
	private Date dtime;
	private String bz;
	
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getDtime() {
		return dtime;
	}
	public void setDtime(Date dtime) {
		this.dtime = dtime;
	}
	public String getBz() {
		return bz;
	}
	public void setBz(String bz) {
		this.bz = bz;
	}
	
	
}
