package com.iawtr.database.dto;
// Generated 2019-1-7 14:12:28 by Hibernate Tools 3.2.1.GA


import java.math.BigDecimal;

/**
 * WwSqrxxTmp generated by hbm2java
 */
public class WwSqrxxTmp  implements java.io.Serializable ,DtoInterface {
	private static final long serialVersionUID = 1L;

     private String glbh;
     private String sqbh;
     private String sqrmc;
     private String zjlb;
     private String zjhm;
     private String dh;
     private String yb;
     private String dz;
     private String sqrlx;
     private BigDecimal sxh;
     private String gyfe;

    public WwSqrxxTmp() {
    }

	
    public WwSqrxxTmp(String glbh) {
        this.glbh = glbh;
    }
    public WwSqrxxTmp(String glbh, String sqbh, String sqrmc, String zjlb, String zjhm, String dh, String yb, String dz, String sqrlx, BigDecimal sxh, String gyfe) {
       this.glbh = glbh;
       this.sqbh = sqbh;
       this.sqrmc = sqrmc;
       this.zjlb = zjlb;
       this.zjhm = zjhm;
       this.dh = dh;
       this.yb = yb;
       this.dz = dz;
       this.sqrlx = sqrlx;
       this.sxh = sxh;
       this.gyfe = gyfe;
    }
   
    public String getGlbh() {
        return this.glbh;
    }
    
    public void setGlbh(String glbh) {
        this.glbh = glbh;
    }
    public String getSqbh() {
        return this.sqbh;
    }
    
    public void setSqbh(String sqbh) {
        this.sqbh = sqbh;
    }
    public String getSqrmc() {
        return this.sqrmc;
    }
    
    public void setSqrmc(String sqrmc) {
        this.sqrmc = sqrmc;
    }
    public String getZjlb() {
        return this.zjlb;
    }
    
    public void setZjlb(String zjlb) {
        this.zjlb = zjlb;
    }
    public String getZjhm() {
        return this.zjhm;
    }
    
    public void setZjhm(String zjhm) {
        this.zjhm = zjhm;
    }
    public String getDh() {
        return this.dh;
    }
    
    public void setDh(String dh) {
        this.dh = dh;
    }
    public String getYb() {
        return this.yb;
    }
    
    public void setYb(String yb) {
        this.yb = yb;
    }
    public String getDz() {
        return this.dz;
    }
    
    public void setDz(String dz) {
        this.dz = dz;
    }
    public String getSqrlx() {
        return this.sqrlx;
    }
    
    public void setSqrlx(String sqrlx) {
        this.sqrlx = sqrlx;
    }
    public BigDecimal getSxh() {
        return this.sxh;
    }
    
    public void setSxh(BigDecimal sxh) {
        this.sxh = sxh;
    }
    public String getGyfe() {
        return this.gyfe;
    }
    
    public void setGyfe(String gyfe) {
        this.gyfe = gyfe;
    }


	@Override
	public String getIdName() {
		// TODO Auto-generated method stub
		return "glbh";
	}


	@Override
	public String getDataVersionName() {
		// TODO Auto-generated method stub
		return null;
	}




}

