package com.iawtr.commons.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import sun.net.www.protocol.http.HttpURLConnection;

public class HttpHelper {
	
	/**
	 * 下载远程文件
	 * @param remoteFilePath 远程文件名
	 * @param localFilePath	本地文件名
	 */
    public static void downloadFile(String remoteFilePath, String localFilePath){
        URL urlfile = null;
        HttpURLConnection httpUrl = null;
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        
        
        try{
            urlfile = new URL(remoteFilePath);
            httpUrl = (HttpURLConnection)urlfile.openConnection();
            httpUrl.connect();
            bis = new BufferedInputStream(httpUrl.getInputStream());
            if (bis == null) {
                System.out.println("下载文件不存在！");
                return;
            }
            
            //如何本地文件夹不存在，则创建
            File file = new File(localFilePath);
        	if(!file.exists()){
        		file.mkdirs();
            }
        	String name = new File(remoteFilePath).getName(); //获取远程需要下载的文件名
            File filename = new File(localFilePath+"\\"+name);
        	if(!filename.exists()){
        		filename.createNewFile();
        	}
        	
            bos = new BufferedOutputStream(new FileOutputStream(filename));
            int len = 2048;
            byte[] b = new byte[len];
            while ((len = bis.read(b)) != -1){
                bos.write(b, 0, len);
            }

            System.out.println("上传成功！");
            bos.flush();
            bis.close();
            httpUrl.disconnect();
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("上传失败！");
        }finally{
            try{
                bis.close();
                bos.close();
            }catch (IOException e){
                e.printStackTrace();
                System.out.println("上传失败！");
            }
        }
    }



	public static void main(String[] args) {
		new HttpHelper().downloadFile("http://192.168.50.2/public/index.php/bank/DownloadFujian/DownloadFujian/201901031546507345/dyht/20180320_all.sql", "e:\\test");
	}
	
}
