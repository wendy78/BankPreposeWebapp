package com.iawtr.database.dto.bizoperate;

/**
 * Bizoperate entity. @author MyEclipse Persistence Tools
 */

public class Bizoperate implements java.io.Serializable,Cloneable {

	private static final long serialVersionUID = 1L;
	private String opeId;
	private String opetypeno;
	private String opedevdes;
	private String opecusdes;
	/**
	 * 保存动态值的字段,替换opecusdes中的"#"
	 */
	private String dynamicstr;
	private String opeflag;
	// Constructors

	/** default constructor */
	public Bizoperate() {
	}

	/** minimal constructor */
	public Bizoperate(String opeid) {
		this.opeId = opeid;
	}

	/** full constructor */
	public Bizoperate(String opeid, String opetypeno, String opedevdes, String opecusdes, String opeflag, String dynamicstr) {
		this.opeId = opeid;
		this.opetypeno = opetypeno;
		this.opedevdes = opedevdes;
		this.opecusdes = opecusdes;
		this.opeflag = opeflag;
		this.dynamicstr = dynamicstr;
	}

	public String getOpeId() {
		return this.opeId;
	}

	public void setOpeId(String opeid) {
		this.opeId = opeid;
	}

	public String getOpetypeno() {
		return this.opetypeno;
	}

	public void setOpetypeno(String opetypeno) {
		this.opetypeno = opetypeno;
	}

	public String getOpedevdes() {
		return this.opedevdes;
	}

	public void setOpedevdes(String opedevdes) {
		this.opedevdes = opedevdes;
	}

	public String getOpecusdes() {
		return this.opecusdes;
	}

	public void setOpecusdes(String opecusdes) {
		this.opecusdes = opecusdes;
	}

	public String getDynamicstr() {
		return dynamicstr;
	}

	public void setDynamicstr(String dynamicstr) {
		this.dynamicstr = dynamicstr;
	}

	public String getOpeflag() {
		return opeflag;
	}

	public void setOpeflag(String opeflag) {
		this.opeflag = opeflag;
	}

	public Bizoperate clone() {		
		try{
			return (Bizoperate) super.clone();
		}catch(CloneNotSupportedException ce){
			
		}
		return null;
	}
	
}