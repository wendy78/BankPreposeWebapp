package com.iawtr.exception;

import com.iawtr.database.dto.bizoperate.Bizoperate;

/**
 * 业务层异常类
 * 
 * @author
 */
public class BizException extends Exception {
	private static transient final long serialVersionUID = 114945248902044624L;
	protected Bizoperate bizoperate = new Bizoperate("cwl201010231151", "01", "success", "操作完成", "success", "");
	protected StringBuffer traceMessage=new StringBuffer();//跟踪信息
	/**
	 * @param exId 异常的id,对应数据库中的id
	 */
	public BizException(String exId) {
		this.bizoperate.setOpeId(exId);
		Thread t=Thread.currentThread();
		StackTraceElement[] stes=t.getStackTrace();
		for(int i=0;stes!=null&&i<stes.length;i++){
			StackTraceElement ste=stes[i];
//			sb.append("第一个堆栈>>");
//			sb.append(i);
			traceMessage.append(" 类名:");
			traceMessage.append(ste.getClassName());
			traceMessage.append(" 方法名:");
			traceMessage.append(ste.getMethodName());
			traceMessage.append(" 行数:");
			traceMessage.append(ste.getLineNumber());
			traceMessage.append("\r\n");
		}
	}
	/**
	 * @param exId 异常的id,对应数据库中的id
	 * @param dynamicStr 要动态显示的值,成为该异常类的message描述
	 */
	public BizException(String exId, String dynamicStr) {
		this(exId);
		this.bizoperate.setDynamicstr(dynamicStr);
	}
	public Bizoperate getBizoperate() {
		return bizoperate;
	}
	@Override
	public String getMessage(){
		StringBuffer sb=new StringBuffer();
		sb.append(bizoperate.getOpeId());
		sb.append(" ");
		sb.append(bizoperate.getDynamicstr());
		sb.append("\r\n");
		sb.append(traceMessage);
		return sb.toString();
	}
	@Override
	public Throwable fillInStackTrace() {
		return this;
	}
}