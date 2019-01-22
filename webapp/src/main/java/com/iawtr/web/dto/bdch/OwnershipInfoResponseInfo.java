package com.iawtr.web.dto.bdch;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Ȩ֤��ѯ ������Ϣ
 * ������Ϣ
 * @author wuhy
 *
 */
public class OwnershipInfoResponseInfo implements Serializable{
	private static final long serialVersionUID = 1L;
	private String qzslbh;	//Ȩ֤������
	private String bdczh;	//������֤��
	private String bdczlx;	//������֤����
	private ArrayList<Xgrxx> qlr;	//�������Ϣ
	private ArrayList<Bdcdyxx> bdcdyxx;	//������Ԫ��Ϣ
	private ArrayList<Dyqk> dyqk;	//��Ѻ���
	private String xzxx;	//����Ϣ
	
	public String getQzslbh() {
		return qzslbh;
	}
	public void setQzslbh(String qzslbh) {
		this.qzslbh = qzslbh;
	}
	public String getBdczh() {
		return bdczh;
	}
	public void setBdczh(String bdczh) {
		this.bdczh = bdczh;
	}
	public String getBdczlx() {
		return bdczlx;
	}
	public void setBdczlx(String bdczlx) {
		this.bdczlx = bdczlx;
	}
	public ArrayList<Xgrxx> getQlr() {
		return qlr;
	}
	public void setQlr(ArrayList<Xgrxx> qlr) {
		this.qlr = qlr;
	}
	public ArrayList<Bdcdyxx> getBdcdyxx() {
		return bdcdyxx;
	}
	public void setBdcdyxx(ArrayList<Bdcdyxx> bdcdyxx) {
		this.bdcdyxx = bdcdyxx;
	}
	public ArrayList<Dyqk> getDyqk() {
		return dyqk;
	}
	public void setDyqk(ArrayList<Dyqk> dyqk) {
		this.dyqk = dyqk;
	}
	public String getXzxx() {
		return xzxx;
	}
	public void setXzxx(String xzxx) {
		this.xzxx = xzxx;
	}
	
}
