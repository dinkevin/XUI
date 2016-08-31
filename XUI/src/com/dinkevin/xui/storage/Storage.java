package com.dinkevin.xui.storage;

import java.io.File;

import com.dinkevin.xui.storage.DownloadTask.DownloadListener;
import com.dinkevin.xui.util.Debuger;
import com.dinkevin.xui.util.FileUtil;
import com.dinkevin.xui.util.MD5Util;
import com.dinkevin.xui.util.StringUtil;

import android.os.Environment;

/**
 * 存储管理基类
 * @author chengpengfei
 *
 */
public abstract class Storage{

	/**
	 * SD卡根目录
	 */
	protected String SDCardRoot;
	
	protected Storage(){
		SDCardRoot = Environment.getExternalStorageDirectory().getPath() + File.separator;
	}
	
	/**
	 * 获取SD卡根目录路径
	 * @return
	 */
	public String getSDCardRoot(){
		return SDCardRoot;
	}
	
	/**
	 * 本地文件存储根路径
	 */
	protected String storageRootPath;
	
	/**
	 * 初始化，文件系统目录结构
	 * @param rootDir 根目录路径，不包含 SD 卡路径
	 * @return true -> 初始化成功，否则失败
	 */
	public boolean initial(String rootDir){
		
		if(StringUtil.isEmpty(rootDir)) return false;
		
		if(!rootDir.endsWith(File.separator)){
			rootDir += File.separator;
		}
		
		storageRootPath = SDCardRoot + rootDir;
		return FileUtil.create(storageRootPath);
	}
	
	/**
	 * 获取 Storage 的根目录路径
	 * @return
	 */
	public String getDirRoot(){
		return storageRootPath;
	}
	
	/**
	 * 计算指定的网络路径对应的 storageId 值
	 * @param url
	 * @return null 表示计算失败；否则返回对应的 storageId 值
	 */
	public static String parseUrlToStorageId(String url){
		if(StringUtil.isEmpty(url)) return null;
		return MD5Util.calculate(url);
	}
	
	/**
	 * 删除指定的数据
	 * @param uid 数据唯一标识
	 */
	public abstract void remove(String uid);
	
	/**
	 * 删除指定的文件
	 * @param fileDirPath 目录路径
	 * @param fileName 文件名称，无后缀格式
	 * @param suffix 文件格式后缀
	 */
	protected void remove(String fileDirPath,String fileName,String suffix){
		if(!fileDirPath.endsWith(File.separator)) fileDirPath += File.separator;
		String localFilePath = fileDirPath + fileName + suffix;
		FileUtil.delete(localFilePath);
	}
	
	/**
	 * 读取存储数据的大小
	 * @return
	 */
	public long getStorageLength(){
		return FileUtil.getDirFileLength(storageRootPath);
	}
	
	/**
	 * 删除此根目录下所有的文件
	 */
	public void clear(){
		FileUtil.delete(storageRootPath);
	}
	
	/**
	 * 获取 Storage  中的文件列表
	 * @return null 或者 文件、文件夹列表
	 */
	public File[] list(){
		return FileUtil.list(storageRootPath);
	}
	
	/**
	 * 下载监听实现
	 */
	public class SimpleDownloadListener implements DownloadListener{
		
		IStorageWriteListener listener;
		
		public SimpleDownloadListener(IStorageWriteListener listener) {
			this.listener = listener;
		}

		@Override
		public void onDownloadFinish(DownloadTask task, String filePath) {
			Debuger.d(task.getHttpUrl(),"complete",filePath);
			if(null != listener){
				listener.onWriteComplete(filePath);
			}
		}

		@Override
		public void onDownloadProgressUpdate(DownloadTask task, int progress, int bufferSize, long totalLength) {
			Debuger.d(task.getHttpUrl(),progress);
		}

		@Override
		public void onDownloadError(DownloadTask task, int errorCode, String errorMessage) {
			Debuger.e(task.getHttpUrl(),"error",errorMessage);
			if(null != listener){
				listener.onWriteError(errorCode, errorMessage);
			}
			FileUtil.delete(task.cachePath);
		}
	}
}
