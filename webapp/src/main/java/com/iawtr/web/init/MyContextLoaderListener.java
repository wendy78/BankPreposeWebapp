package com.iawtr.web.init;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.util.ObjectUtils;
import org.springframework.web.context.ConfigurableWebApplicationContext;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.ContextLoaderListener;

import com.iawtr.commons.util.NetworkInfo;
import com.iawtr.service.main.MainService;
import com.iawtr.web.enumerate.ObjectKind;
import com.iawtr.web.enumerate.SystemConstants;

/**
 * Bootstrap listener to start up Spring's root WebApplicationContext. Simply delegates to ContextLoader.
 * <p>
 * This listener should be registered after Log4jConfigListener in web.xml, if the latter is used.
 * <p>
 * For Servlet 2.2 containers and Servlet 2.3 ones that do not initalize listeners before servlets, use ContextLoaderServlet. See the latter's Javadoc for details.
 * 
 * @author Juergen Hoeller
 * @since 17.02.2003
 * @see ContextLoader
 * @see ContextLoaderServlet
 * @see org.springframework.web.util.Log4jConfigListener
 */
public class MyContextLoaderListener extends ContextLoaderListener {
	protected transient final Log log = LogFactory.getLog(getClass());
	// private ContextLoader contextLoader;
	/**
	 * Initialize the root web application context.
	 */
	public void contextInitialized(ServletContextEvent event) {
		log.debug("从web.xml的参数中判断是正式还是测试项目");
		String intparam = event.getServletContext().getInitParameter("objectKind");
		log.debug("objectKind value-->" + intparam);
		if (intparam != null && intparam.equalsIgnoreCase("test")) {
			SystemConstants.OBJECTKIND = ObjectKind.TEST;
			try {
				List<String> iplis = new ArrayList<String>();
				iplis.add("115.28.170.83");
				String appIp = NetworkInfo.getAppIp(iplis);
				log.debug("应用服务器连接zookeeper的ip是: " + appIp);
				SystemConstants.APPLICATIONIP = appIp;
			} catch (IOException e) {
				log.error("应用服务器初始化没有得到连接zookeeper的ip", e);
			}
		} else if (intparam != null && intparam.equalsIgnoreCase("release")) {
			SystemConstants.OBJECTKIND = ObjectKind.RELEASE;
			try {
				List<String> iplis = new ArrayList<String>();
				iplis.add("10.144.162.247");
				iplis.add("10.144.230.231");
//				iplis.add("127.0.0.1");
				String appIp = NetworkInfo.getAppIp(iplis);
				log.debug("应用服务器连接zookeeper的ip是: " + appIp);
				SystemConstants.APPLICATIONIP = appIp;
			} catch (IOException e) {
				log.error("应用服务器初始化没有得到连接zookeeper的ip", e);
			}
		}
		SystemConstants.OBJECTWEBROOT = event.getServletContext().getInitParameter("objectWebRoot");
		log.debug("项目的web[contextPath]为-->>"+SystemConstants.OBJECTWEBROOT);
		log.debug("设置应用服务器的真实绝对路径");
		String tmpPath = event.getServletContext().getRealPath(SystemConstants.BUFFERSAVEPATH);
		tmpPath = tmpPath.replace("\\", "/");// windows下,转换路径分隔符
		SystemConstants.APPLICATIONREALPATH = tmpPath.replace(SystemConstants.BUFFERSAVEPATH, "");
		log.debug("应用服务器的真实绝对路径设置完成,路径是:" + SystemConstants.APPLICATIONREALPATH);
		log.debug("初始化spring开始");
		SpringFactoryHelp.setApplicationContext(this.initWebApplicationContext(event.getServletContext()));
		log.debug("初始化spring完成");
		log.debug("构建代码表缓存");
//		MainService mainService=(MainService) SpringFactoryHelp.getApplicationContext().getBean("mainService");
//		mainService.getAllDmlb();
//		log.debug("开始启动注册集群的线程");
//		try {
//			AppClusterThread.init();
//			log.debug("注册集群的线程启动完成");
//		} catch (Exception e) {
//			log.error("注册集群的线程启动失败", e);
//		}
	}
	/**
	 * 覆写该方法,不适用web.xml中的contextConfigLocation参数配置信息了
	 */
	protected void configureAndRefreshWebApplicationContext(ConfigurableWebApplicationContext wac, ServletContext sc) {
		if (ObjectUtils.identityToString(wac).equals(wac.getId())) {
			// The application context id is still set to its original default
			// value
			// -> assign a more useful id based on available information
			String idParam = sc.getInitParameter(CONTEXT_ID_PARAM);
			if (idParam != null) {
				wac.setId(idParam);
			} else {
				// Generate default id...
				if (sc.getMajorVersion() == 2 && sc.getMinorVersion() < 5) {
					// Servlet <= 2.4: resort to name specified in web.xml, if
					// any.
					wac.setId(ConfigurableWebApplicationContext.APPLICATION_CONTEXT_ID_PREFIX + ObjectUtils.getDisplayString(sc.getServletContextName()));
				} else {
					wac.setId(ConfigurableWebApplicationContext.APPLICATION_CONTEXT_ID_PREFIX + ObjectUtils.getDisplayString(sc.getContextPath()));
				}
			}
		}
		// Determine parent for root web application context, if any.
		ApplicationContext parent = loadParentContext(sc);
		wac.setParent(parent);
		wac.setServletContext(sc);
//		 String initParameter = sc.getInitParameter(CONFIG_LOCATION_PARAM);
		// if (initParameter != null) {
		// wac.setConfigLocation(initParameter);
		// }
		String[] springConfigrations = null;
		if (SystemConstants.OBJECTKIND.equals(ObjectKind.RELEASE)) {
			springConfigrations = new String[] { "classpath:spring/dao.xml", "classpath:spring/icdao_config_release.xml", "classpath:spring/action.xml", "classpath:spring/applicationContext.xml","classpath:spring/concurrence.xml", "classpath:spring/service.xml", "classpath:spring/task.xml", "classpath:spring/webservice.xml" };
		} else if (SystemConstants.OBJECTKIND.equals(ObjectKind.TEST)) {
			springConfigrations = new String[] { "classpath:spring/dao.xml", "classpath:spring/icdao_config_test.xml", "classpath:spring/action.xml", "classpath:spring/applicationContext.xml","classpath:spring/concurrence.xml", "classpath:spring/service.xml", "classpath:spring/task.xml" , "classpath:spring/webservice.xml"};
		}
		wac.setConfigLocations(springConfigrations);
		customizeContext(sc, wac);
		wac.refresh();
	}
	/**
	 * Create the ContextLoader to use. Can be overridden in subclasses.
	 * 
	 * @return the new ContextLoader
	 */
	@Deprecated
	protected ContextLoader createContextLoader() {
		return null;
	}
	/**
	 * Return the ContextLoader used by this listener.
	 */
	@Deprecated
	public ContextLoader getContextLoader() {
		return null;
	}
	/**
	 * Close the root web application context.
	 */
	public void contextDestroyed(ServletContextEvent event) {
		if (SpringFactoryHelp.getApplicationContext() instanceof ConfigurableWebApplicationContext) {
			((ConfigurableWebApplicationContext) SpringFactoryHelp.getApplicationContext()).close();
		}
		SpringFactoryHelp.setApplicationContext(null);
	}
}
