package com.iawtr.web.interceptor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public abstract class MainInterceptor extends AbstractInterceptor{
	private static final long serialVersionUID = 3605513694464408159L;
	protected final Log log = LogFactory.getLog(getClass());
}
