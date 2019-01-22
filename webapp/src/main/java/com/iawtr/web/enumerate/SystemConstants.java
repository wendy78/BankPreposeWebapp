package com.iawtr.web.enumerate;
/**
 * 此类定义一些web系统用到的全局常量值
 */
public class SystemConstants {
	
//	public static final String getVersion() {
//		return "2.0 20100313";
//	}
	/**
	 * 应用服务器根目录的绝对路径
	 */
	public static String APPLICATIONREALPATH="D:/apache-tomcat-7.0.16/webapps/webapp";
	/**
	 * 缓存临时文件的文件夹,相对于web根目录
	 */
	public static String BUFFERSAVEPATH="/tempFolder";//服务器缓存文件夹
	/**
	 * 标示项目是正式发布版或者测试版本,默认为测试
	 */
//	public static ObjectKind OBJECTKIND=ObjectKind.RELEASE; //正式库
	public static ObjectKind OBJECTKIND=ObjectKind.TEST; //测试库
	/**
	 * 项目的web [contextPath], 共支付回调通知地址使用
	 */
	public static String OBJECTWEBROOT="http://www.redtree0.com:8888/webapp";
	/**
	 * 搜索引擎中选用的分词插件类型
	 */
//	public static String ESANALYSIS="ik_max_word";
	/**
	 * <pre>
	 * 多网卡的情况下,应用服务器的ip.(和指定ip通信的,如:与数据库通信)
	 * 	 注意:
	 *   	该变量可能要变化,因为应用服务器在多网卡的时候,有可能要和别的电脑通信需要
	 *   通过不同的网卡,而这里只用了一个字符串变量来记录
	 * 		
	 * </pre>
	 */
	public static String APPLICATIONIP="192.168.50.5";
	/**
	 * 用户登录超时时间
	 * 单位是 分钟
	 */
	public static int SESSIONTIMEOUT=30;
	
	/**
	 * 是否检查附件
	 */
	public static boolean IfCheckFj=true;
	
	/**
	 * ftp存放文件件夹名字 
	 */
	public static String PreRemote="";
	
	/**
	 * 附件下载地址
	 */
    public static String FjHttpAddress="http://192.168.50.2/public/index.php/bank/DownloadFujian";
}
