package com.iawtr.web.dto.bdch;

import java.io.Serializable;

public class Fj implements Serializable{
	private static final long serialVersionUID = 1L;
	private String name;
	private String url;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	
}
