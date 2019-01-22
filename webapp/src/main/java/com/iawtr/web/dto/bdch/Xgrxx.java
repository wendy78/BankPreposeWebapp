package com.iawtr.web.dto.bdch;

import java.io.Serializable;

/**
 * �������Ϣ
 * @author wuhy
 *
 */
public class Xgrxx implements Serializable{
	private static final long serialVersionUID = 1L;
	private String name; //姓名
	private int xh;	//序号
	private String zjlx;	//֤证件类型
	private String zjh;	//֤证件号
	private String tel;	//电话
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getXh() {
		return xh;
	}
	public void setXh(int xh) {
		this.xh = xh;
	}
	public String getZjlx() {
		return zjlx;
	}
	public void setZjlx(String zjlx) {
		this.zjlx = zjlx;
	}
	public String getZjh() {
		return zjh;
	}
	public void setZjh(String zjh) {
		this.zjh = zjh;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	
}
