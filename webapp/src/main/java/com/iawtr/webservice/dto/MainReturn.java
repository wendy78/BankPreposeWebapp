package com.iawtr.webservice.dto;

import java.io.Serializable;

import com.iawtr.database.dto.bizoperate.Bizoperate;
/**
 * webservice访问返回信息的封装,
 * webservice中所有的方法返回类必须继承该类
 * @author Administrator
 *
 */
public class MainReturn  implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 业务操作成功与否的描述类 默认提示用户的信息为"success"
	 * 方法抛出异常时应该将异常转为该类对应的描述
	 */
	protected Bizoperate bizoperate = new Bizoperate("extcwl201010231151", "01", "success", "操作成功", "success","");
	public Bizoperate getBizoperate() {
		return bizoperate;
	}
	public void setBizoperate(Bizoperate bizoperate) {
		this.bizoperate = bizoperate;
	}
}
