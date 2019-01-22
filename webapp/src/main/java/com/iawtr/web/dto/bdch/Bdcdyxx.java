package com.iawtr.web.dto.bdch;

import java.io.Serializable;

/**
 * Ȩ֤��ѯ ������Ԫ��Ϣ
 * @author wuhy
 *
 */
public class Bdcdyxx implements Serializable{
	private static final long serialVersionUID = 1L;
	private String hbh;	//ͼ��ͳһ����
	private String bdcdyh;	//������Ԫ��
	private String zl;	//����
	private double jzmj;	//ʵ�⽨�����
	
	public String getHbh() {
		return hbh;
	}
	public void setHbh(String hbh) {
		this.hbh = hbh;
	}
	public String getBdcdyh() {
		return bdcdyh;
	}
	public void setBdcdyh(String bdcdyh) {
		this.bdcdyh = bdcdyh;
	}
	public String getZl() {
		return zl;
	}
	public void setZl(String zl) {
		this.zl = zl;
	}
	public double getJzmj() {
		return jzmj;
	}
	public void setJzmj(double jzmj) {
		this.jzmj = jzmj;
	}
	
	
}
