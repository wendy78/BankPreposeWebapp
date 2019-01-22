package com.iawtr.web.dto.bdch;

import java.io.Serializable;

public class ISxkYwb implements Serializable{
	private static final long serialVersionUID = 1L;
	private String slbh;
	private int lifecycle;
	private String bdczh;
	private String zsxlh;
	
	public String getSlbh() {
		return slbh;
	}
	public void setSlbh(String slbh) {
		this.slbh = slbh;
	}
	public int getLifecycle() {
		return lifecycle;
	}
	public void setLifecycle(int lifecycle) {
		this.lifecycle = lifecycle;
	}
	public String getBdczh() {
		return bdczh;
	}
	public void setBdczh(String bdczh) {
		this.bdczh = bdczh;
	}
	public String getZsxlh() {
		return zsxlh;
	}
	public void setZsxlh(String zsxlh) {
		this.zsxlh = zsxlh;
	}
	
	
}
