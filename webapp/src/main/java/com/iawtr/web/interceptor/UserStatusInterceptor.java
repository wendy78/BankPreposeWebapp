package com.iawtr.web.interceptor;

import com.iawtr.commons.util.BeanUtils;
import com.iawtr.database.dto.bizoperate.Bizoperate;
import com.iawtr.exception.BizException;
import com.iawtr.service.main.MainService;
import com.iawtr.web.init.SpringFactoryHelp;
import com.opensymphony.xwork2.ActionInvocation;
/**
 * 该拦截器针对所有的action,实现用户登录状态的验证
 * @author Administrator
 *
 */
public class UserStatusInterceptor extends MainInterceptor {
	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		log.debug("用户登录状态维护拦截器运行");
		String result = "success";
		Object action = invocation.getAction();
		MainService ms=(MainService) SpringFactoryHelp.getApplicationContext().getBean("mainService");
		try {
//			ms.validateUserStatus(invocation);
//		}catch(BizException bizex){
//        	log.info("调用类" + action.getClass().getName() + "中的方法,抛出业务异常", bizex);
//        	Bizoperate bizoperate = ms.getObjById(bizex.getBizoperate().getOpeId());
//        	if(bizoperate!=null){
//        		bizoperate.setDynamicstr(bizex.getBizoperate().getDynamicstr());
//        	}else{
//        		bizoperate = new Bizoperate("extcwl201309181822", "01", "struts2拦截器中抛出的错误,没有根据bizoperate的id得到对象", "没有从es中查询到bizoperate对象记录", "unsuccess","");
//        		bizoperate.setDynamicstr("bizid["+bizex.getBizoperate().getOpeId()+"]对应的记录没有");
//        	}
//        	try {
////				BeanUtils.invokeProtectedMethod(action, "setBizoperate", bizoperate);
//			} catch (Exception ex) {
//				log.error("类" + action.getClass().getName() + "中不存在方法setBizoperate(参数的类型也要匹配),或者该方法不是公共方法或继承的方法", ex);
//				throw ex;
//			}
//        	return result;
        } catch (Exception e) {
        	log.error("调用类" + action.getClass().getName() + "中的方法出现错误", e);
        	Bizoperate bizo=new Bizoperate("extcwl201302271518","01","服务器应用程序的业务逻辑中出现的RuntimeException,该异常也就是业务代码级的bug","服务器应用程序业务代码内部异常,请联系管代码理员","bizappex","异常号为:");
//        	Bizoperate bizo = ms.getObjById("extcwl201302271518");
        	if(bizo!=null){
        		bizo.setDynamicstr(e.toString());
        	}else{
        		bizo = new Bizoperate("extcwl201309181822", "01", "struts2拦截器中抛出的错误,没有根据bizoperate的id得到对象", "没有查询到bizoperate对象记录", "unsuccess","");
        		bizo.setDynamicstr("bizid[extcwl201302271518]对应的记录没有");
        	}
        	try {
				BeanUtils.invokeProtectedMethod(action, "setBizoperate", bizo);
			} catch (Exception ex) {
				log.error("类" + action.getClass().getName() + "中不存在方法setBizoperate(参数的类型也要匹配),或者该方法不是公共方法或继承的方法", ex);
				throw ex;
			}
        	return result;
        }
		result = invocation.invoke();
		return result;
	}
}