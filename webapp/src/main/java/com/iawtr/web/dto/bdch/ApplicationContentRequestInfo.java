package com.iawtr.web.dto.bdch;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class ApplicationContentRequestInfo implements Serializable{
	private static final long serialVersionUID = 1L;
	private String uid;
	private ArrayList<String> qzslbh;
	private Date sqsj; //to1do: ��֪ �޸��� created_at
	private ArrayList<Xgrxx> dyqr;
	private ArrayList<Xgrxx> dyqrdlr;
	private ArrayList<Xgrxx> dyrdlr;
	private String ywlx;
	private String dyfs;
	private String dylx;
	private double dyje;
	private Date stime; //to1do: ��֪ �޸��� �ظ�
	private Date etime;
	private String dysw;
	private String htbh; //todo:���
	private double dymj; //todo:���
	private String zjjzwzl; //todo:���
	private String zjjzwdyfw; //todo:���
	private String dbfw; //todo:���
	private double pgje; //todo:���
	private ArrayList<Xgrxx> jkr;
	private ArrayList<Fj> links;
	private String slry;
	private String tzr;
	private String tzdh;
	private String tzrdz;
	private String tzbz;
	
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public ArrayList<String> getQzslbh() {
		return qzslbh;
	}
	public void setQzslbh(ArrayList<String> qzslbh) {
		this.qzslbh = qzslbh;
	}
	public Date getSqsj() {
		return sqsj;
	}
	public void setSqsj(Date sqsj) {
		this.sqsj = sqsj;
	}
	public ArrayList<Xgrxx> getDyqr() {
		return dyqr;
	}
	public void setDyqr(ArrayList<Xgrxx> dyqr) {
		this.dyqr = dyqr;
	}
	public ArrayList<Xgrxx> getDyqrdlr() {
		return dyqrdlr;
	}
	public void setDyqrdlr(ArrayList<Xgrxx> dyqrdlr) {
		this.dyqrdlr = dyqrdlr;
	}
	public ArrayList<Xgrxx> getDyrdlr() {
		return dyrdlr;
	}
	public void setDyrdlr(ArrayList<Xgrxx> dyrdlr) {
		this.dyrdlr = dyrdlr;
	}
	public String getYwlx() {
		return ywlx;
	}
	public void setYwlx(String ywlx) {
		this.ywlx = ywlx;
	}
	public String getDyfs() {
		return dyfs;
	}
	public void setDyfs(String dyfs) {
		this.dyfs = dyfs;
	}
	public String getDylx() {
		return dylx;
	}
	public void setDylx(String dylx) {
		this.dylx = dylx;
	}
	public double getDyje() {
		return dyje;
	}
	public void setDyje(double dyje) {
		this.dyje = dyje;
	}
	public Date getStime() {
		return stime;
	}
	public void setStime(Date stime) {
		this.stime = stime;
	}
	public Date getEtime() {
		return etime;
	}
	public void setEtime(Date etime) {
		this.etime = etime;
	}
	public String getDysw() {
		return dysw;
	}
	public void setDysw(String dysw) {
		this.dysw = dysw;
	}
	public String getHtbh() {
		return htbh;
	}
	public void setHtbh(String htbh) {
		this.htbh = htbh;
	}
	public double getDymj() {
		return dymj;
	}
	public void setDymj(double dymj) {
		this.dymj = dymj;
	}
	public String getZjjzwzl() {
		return zjjzwzl;
	}
	public void setZjjzwzl(String zjjzwzl) {
		this.zjjzwzl = zjjzwzl;
	}
	public String getZjjzwdyfw() {
		return zjjzwdyfw;
	}
	public void setZjjzwdyfw(String zjjzwdyfw) {
		this.zjjzwdyfw = zjjzwdyfw;
	}
	public String getDbfw() {
		return dbfw;
	}
	public void setDbfw(String dbfw) {
		this.dbfw = dbfw;
	}
	public double getPgje() {
		return pgje;
	}
	public void setPgje(double pgje) {
		this.pgje = pgje;
	}
	public ArrayList<Xgrxx> getJkr() {
		return jkr;
	}
	public void setJkr(ArrayList<Xgrxx> jkr) {
		this.jkr = jkr;
	}
	public ArrayList<Fj> getLinks() {
		return links;
	}
	public void setLinks(ArrayList<Fj> links) {
		this.links = links;
	}
	public String getSlry() {
		return slry;
	}
	public void setSlry(String slry) {
		this.slry = slry;
	}
	public String getTzr() {
		return tzr;
	}
	public void setTzr(String tzr) {
		this.tzr = tzr;
	}
	public String getTzdh() {
		return tzdh;
	}
	public void setTzdh(String tzdh) {
		this.tzdh = tzdh;
	}
	public String getTzrdz() {
		return tzrdz;
	}
	public void setTzrdz(String tzrdz) {
		this.tzrdz = tzrdz;
	}
	public String getTzbz() {
		return tzbz;
	}
	public void setTzbz(String tzbz) {
		this.tzbz = tzbz;
	}
	
}
