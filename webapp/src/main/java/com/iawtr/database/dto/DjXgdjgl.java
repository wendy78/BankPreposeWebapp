package com.iawtr.database.dto;
// Generated 2019-1-7 14:12:28 by Hibernate Tools 3.2.1.GA


import java.math.BigDecimal;
import java.util.Date;

/**
 * DjXgdjgl generated by hbm2java
 */
public class DjXgdjgl  implements java.io.Serializable ,DtoInterface {
	private static final long serialVersionUID = 1L;

     private String bgbm;
     private String zslbh;
     private String fslbh;
     private Date bgrq;
     private String bglx;
     private String xgzh;
     private String xgzlx;
     private String yxgzh;
     private String pid;
     private String yslbh;
     private String qlr;
     private BigDecimal lifecycle;
     private String tstybm;
     private String xgzh1;
     private String fslbh1;
     private String bs;
     private String pregid;

    public DjXgdjgl() {
    }

	
    public DjXgdjgl(String bgbm) {
        this.bgbm = bgbm;
    }
    public DjXgdjgl(String bgbm, String zslbh, String fslbh, Date bgrq, String bglx, String xgzh, String xgzlx, String yxgzh, String pid, String yslbh, String qlr, BigDecimal lifecycle, String tstybm, String xgzh1, String fslbh1, String bs, String pregid) {
       this.bgbm = bgbm;
       this.zslbh = zslbh;
       this.fslbh = fslbh;
       this.bgrq = bgrq;
       this.bglx = bglx;
       this.xgzh = xgzh;
       this.xgzlx = xgzlx;
       this.yxgzh = yxgzh;
       this.pid = pid;
       this.yslbh = yslbh;
       this.qlr = qlr;
       this.lifecycle = lifecycle;
       this.tstybm = tstybm;
       this.xgzh1 = xgzh1;
       this.fslbh1 = fslbh1;
       this.bs = bs;
       this.pregid = pregid;
    }
   
    public String getBgbm() {
        return this.bgbm;
    }
    
    public void setBgbm(String bgbm) {
        this.bgbm = bgbm;
    }
    public String getZslbh() {
        return this.zslbh;
    }
    
    public void setZslbh(String zslbh) {
        this.zslbh = zslbh;
    }
    public String getFslbh() {
        return this.fslbh;
    }
    
    public void setFslbh(String fslbh) {
        this.fslbh = fslbh;
    }
    public Date getBgrq() {
        return this.bgrq;
    }
    
    public void setBgrq(Date bgrq) {
        this.bgrq = bgrq;
    }
    public String getBglx() {
        return this.bglx;
    }
    
    public void setBglx(String bglx) {
        this.bglx = bglx;
    }
    public String getXgzh() {
        return this.xgzh;
    }
    
    public void setXgzh(String xgzh) {
        this.xgzh = xgzh;
    }
    public String getXgzlx() {
        return this.xgzlx;
    }
    
    public void setXgzlx(String xgzlx) {
        this.xgzlx = xgzlx;
    }
    public String getYxgzh() {
        return this.yxgzh;
    }
    
    public void setYxgzh(String yxgzh) {
        this.yxgzh = yxgzh;
    }
    public String getPid() {
        return this.pid;
    }
    
    public void setPid(String pid) {
        this.pid = pid;
    }
    public String getYslbh() {
        return this.yslbh;
    }
    
    public void setYslbh(String yslbh) {
        this.yslbh = yslbh;
    }
    public String getQlr() {
        return this.qlr;
    }
    
    public void setQlr(String qlr) {
        this.qlr = qlr;
    }
    public BigDecimal getLifecycle() {
        return this.lifecycle;
    }
    
    public void setLifecycle(BigDecimal lifecycle) {
        this.lifecycle = lifecycle;
    }
    public String getTstybm() {
        return this.tstybm;
    }
    
    public void setTstybm(String tstybm) {
        this.tstybm = tstybm;
    }
    public String getXgzh1() {
        return this.xgzh1;
    }
    
    public void setXgzh1(String xgzh1) {
        this.xgzh1 = xgzh1;
    }
    public String getFslbh1() {
        return this.fslbh1;
    }
    
    public void setFslbh1(String fslbh1) {
        this.fslbh1 = fslbh1;
    }
    public String getBs() {
        return this.bs;
    }
    
    public void setBs(String bs) {
        this.bs = bs;
    }
    public String getPregid() {
        return this.pregid;
    }
    
    public void setPregid(String pregid) {
        this.pregid = pregid;
    }


	@Override
	public String getIdName() {
		// TODO Auto-generated method stub
		return "bgbm";
	}


	@Override
	public String getDataVersionName() {
		// TODO Auto-generated method stub
		return null;
	}




}


