package com.iawtr.service.main;

import com.iawtr.exception.BizException;

/**
 * 业务接口基类,每个业务接口继承该接口
 * 
 * @author Administrator
 */
public interface MainService {
	/**
	 * 得到所有代码类,会缓存到服务器
	 */
	public void getAllDmlb();
	/**
	 * 根据desktopId和扩展名,生成临时目录下的文件路径.
	 * 
	 * @param desktopId 桌面id	该值不为空,那么生成路径时,和desktopid相关的用户必须在线.为空时,生成的路径就和用户在线不在线就没有关系了.
	 * @param prefixName 前缀
	 * @param customFileName	自定义的文件名,如果为空,则生成个uuid作为文件名
	 * @param extName 文件扩展名
	 * @return 形如:有效登陆用户:"/tempFolder/{用户id}/{uuid文件名}.{extName}"; 游客:"/tempFolder/vistorTmp/{uuid文件名}.{extName}"
	 * @throws BizException
	 */
	public String buildFilePath(String desktopId, String prefixName,String customFileName, String extName) ;
	/**
	 * 根据desktopId和扩展名,生成临时目录下的文件路径.
	 * 
	 * @param desktopId 桌面id	该值不为空,那么生成路径时,和desktopid相关的用户必须在线.为空时,生成的路径就和用户在线不在线就没有关系了.
	 * @param extName 文件扩展名
	 * @return 形如:有效登陆用户:"/tempFolder/{用户id}/{uuid文件名}.{extName}"; 游客:"/tempFolder/vistorTmp/{uuid文件名}.{extName}"
	 * @throws BizException
	 */
	public String buildFilePath(String desktopId, String extName) throws BizException ;
	/**
	/**
	 * 加载所有的biz对象
	 */
	public void loadAllBizObj();
}

