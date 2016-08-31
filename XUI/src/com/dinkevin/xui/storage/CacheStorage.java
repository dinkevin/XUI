package com.dinkevin.xui.storage;

import java.io.File;
import java.io.FileOutputStream;
import java.net.MalformedURLException;
import com.dinkevin.xui.util.Debuger;
import com.dinkevin.xui.util.FileUtil;
import android.graphics.Bitmap;

/**
 * 缓存存储管理
 * @author chengpengfei
 */
public class CacheStorage extends Storage{
	
	protected CacheStorage(){
		super();
	}
	
	private static CacheStorage singleton = new CacheStorage();
	
	public static final String SUFFIX = ".cache";
	
	public static CacheStorage getInstance(){
		return singleton;
	}
	
	/**
	 * 将网络路径对应的文件缓存到本地，此操作为异步操作
	 * @param url
	 * @param listener
	 * @return true -> 添加成功；false -> 添加失败
	 */
	public boolean put(String url,IStorageWriteListener listener){
		
		String fileKey = parseUrlToStorageId(url);
		if(null == fileKey) return false;
		
		String localFilePath = storageRootPath + fileKey + SUFFIX;
		if(FileUtil.exist(localFilePath)) {
			if(null != listener){
				listener.onWriteComplete(localFilePath);
			}
			return true;
		}
		
		DownloadTask task = null;
		try {
			task = new DownloadTask(url, localFilePath);
		} catch (MalformedURLException e) {
			Debuger.e("添加缓存",url,"网络路径格式不正确！");
			return false;
		}
		
		task.setListener(new SimpleDownloadListener(listener));
		task.start();
		return true;
	}
	
	/**
	 * 读取本地缓存文件路径
	 * @param url 网络路径
	 * @return null -> 本地不存在缓存数据；否则返回本地缓存数据对应的路径
	 */
	public String get(String url){
		
		String fileKey = parseUrlToStorageId(url);
		if(null == fileKey) return null;
		
		String localFilePath = storageRootPath + fileKey + SUFFIX;
		if(FileUtil.exist(localFilePath)) {
			return localFilePath;
		} 
		return null;
	}
	
	/**
	 * 将 Bitmap 添加到缓存区
	 * @param uid 缓存唯一标识
	 * @param data 数据
	 * @return true -> 存储成功；false -> 存储失败
	 */
	public String put(String uid,Bitmap data){
		
		String cacheFilePath = storageRootPath + uid + SUFFIX;
		FileUtil.delete(cacheFilePath);
		if(FileUtil.create(cacheFilePath))
		{
			File f = new File(cacheFilePath);
			
			try {
				FileOutputStream out = new FileOutputStream(f);
				data.compress(Bitmap.CompressFormat.PNG, 100, out);
				out.flush();
				out.close();
				return f.getAbsolutePath();
			} catch (Exception e) {
				Debuger.e("写入 cache 异常 ",uid,e.getMessage());
				FileUtil.delete(cacheFilePath);
			}
		}
		return null;
	}

	@Override
	public void remove(String uid) {
		remove(storageRootPath,uid, SUFFIX);
	}
}
