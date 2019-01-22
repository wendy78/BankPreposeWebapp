package com.iawtr.webservice.service;

import java.io.File;
import java.util.UUID;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.iawtr.service.main.MainService;
import com.iawtr.web.enumerate.SystemConstants;

/**
 * 所有的webservice继承该类. 注意,webservice的方法不能抛出异常,异常应该封装到方法的返回类中(MainReturn).
 * 
 * @author Administrator
 */
public class WSMain {
	protected transient final Log log = LogFactory.getLog(getClass());
	protected MainService mainService;
	public MainService getMainService() {
		return mainService;
	}
	public void setMainService(MainService mainService) {
		this.mainService = mainService;
	}
}
