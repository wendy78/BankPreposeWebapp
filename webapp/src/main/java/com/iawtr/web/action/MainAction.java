package com.iawtr.web.action;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.json.annotations.JSON;

import com.iawtr.database.dto.bizoperate.Bizoperate;
import com.iawtr.exception.BizException;
import com.iawtr.service.main.MainService;
import com.iawtr.web.dto.CollectionData;
import com.iawtr.web.enumerate.SystemConstants;

/**
 * 业务用action主类,所有业务action应该继承该类
 * 
 * @author Administrator
 */
public class MainAction extends BaseAction {
	private static final long serialVersionUID = 1L;
	protected MainService mainService;
	/**
	 * 前台桌面id,每次的http请求都会有这个参数.
	 */
	protected String desktopId;
	/**
	 * 是否允许游客登录,该值从前台得到,适应不同桌面的个性设置,子类action应该复写get方法,返回到前台使用
	 */
	protected boolean allowVisitor;
	/**
	 * 用户状态信息.子类action应该复写get方法,返回到前台使用
	 */
//	protected UserStatus userStatus;
	/**
	 * 业务操作成功与否的描述类 默认提示用户的信息为"success",子类action应该复写get方法,返回到前台使用
	 */
	protected Bizoperate bizoperate = new Bizoperate("extcwl201010231151", "01", "success", "操作成功", "success", "");
	/**
	 * 封装前后台异步传递的数据,子类action应该复写get方法,返回到前台使用
	 */
	protected CollectionData collectionData;
	/**
	 * 上传的文件实体
	 */
	protected File[] file;
	/**
	 * 上传的文件名
	 */
	protected String[] fileFileName;
	// protected String[] fileContentType;//获取上传文件类型 如:image\jpeg
	/**
	 * 下载时的文件流
	 */
	protected InputStream downloadFileStream;
	/**
	 * 下载时预设的文件名
	 */
	protected String downloadFileName;
	@JSON(serialize = false)
	public InputStream getDownloadFileStream() {
		return downloadFileStream;
	}
	public void setDownloadFileStream(InputStream downloadFileStream) {
		this.downloadFileStream = downloadFileStream;
	}
	@JSON(serialize = false)
	public String getDownloadFileName() {
		return downloadFileName;
	}
	public void setDownloadFileName(String downloadFileName) {
		this.downloadFileName = downloadFileName;
	}
	@JSON(serialize = false)
	public File[] getFile() {
		return file;
	}
	public void setFile(File[] file) {
		this.file = file;
	}
	@JSON(serialize = false)
	public String[] getFileFileName() {
		return fileFileName;
	}
	public void setFileFileName(String[] fileFileName) {
		this.fileFileName = fileFileName;
	}
	@JSON(serialize=true)
	public boolean isAllowVisitor() {
		return allowVisitor;
	}
	public void setAllowVisitor(boolean allowVisitor) {
		this.allowVisitor = allowVisitor;
	}
	@JSON(serialize = true)
	public String getDesktopId() {
		return desktopId;
	}
	public void setDesktopId(String desktopId) {
		this.desktopId = desktopId;
	}
	@JSON(serialize = true)
	public Bizoperate getBizoperate() {
		return bizoperate;
	}
	public void setBizoperate(Bizoperate bizoperate) {
		this.bizoperate = bizoperate;
	}
	@JSON(serialize = true)
	public CollectionData getCollectionData() {
		return collectionData;
	}
	public void setCollectionData(CollectionData collectionData) {
		this.collectionData = collectionData;
	}
	@JSON(serialize = false)
	public MainService getMainService() {
		return mainService;
	}
	public void setMainService(MainService mainService) {
		this.mainService = mainService;
	}
}
