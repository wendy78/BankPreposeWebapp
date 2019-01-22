package com.iawtr.web.dto.bdch;

import java.io.Serializable;

/**
 * Ȩ֤��ѯ ��Ѻ���
 * @author wuhy
 *
 */
public class Dyqk implements Serializable{
	private static final long serialVersionUID = 1L;
	private String id;
	private double je;
	private int dysw;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public double getJe() {
		return je;
	}
	public void setJe(double je) {
		this.je = je;
	}
	public int getDysw() {
		return dysw;
	}
	public void setDysw(int dysw) {
		this.dysw = dysw;
	}
	
	
}
