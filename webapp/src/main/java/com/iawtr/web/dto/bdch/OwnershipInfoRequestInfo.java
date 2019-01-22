package com.iawtr.web.dto.bdch;

import java.io.Serializable;

/**
 * �������
 * @author wuhy
 *
 */
public class OwnershipInfoRequestInfo implements Serializable{
	private static final long serialVersionUID = 1L;
	private String bdczh;	//������֤��
	private String bdcdyh;	//������Ԫ��
	private String qlr;	//������
	private String qlrzjh;	//������֤����
	
	public String getBdczh() {
		return bdczh;
	}
	public void setBdczh(String bdczh) {
		this.bdczh = bdczh;
	}
	public String getBdcdyh() {
		return bdcdyh;
	}
	public void setBdcdyh(String bdcdyh) {
		this.bdcdyh = bdcdyh;
	}
	public String getQlr() {
		return qlr;
	}
	public void setQlr(String qlr) {
		this.qlr = qlr;
	}
	public String getQlrzjh() {
		return qlrzjh;
	}
	public void setQlrzjh(String qlrzjh) {
		this.qlrzjh = qlrzjh;
	}
}
