package com.dinkevin.xui.storage;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import com.dinkevin.xui.util.Debuger;
import com.dinkevin.xui.util.DecimalUtil;
import com.dinkevin.xui.util.FileUtil;
import com.dinkevin.xui.util.ThreadUtil;

/**
 * 文件写入任务类，调用此类前一定要对 {@link ThreadUtil} 进行初始化
 */
public class DownloadTask{
	
	protected URL url;
	protected String httpUrl;	// 待下载的网络路径
	protected String cachePath;	// 文件缓存路径

	/**
	 * 构造器
	 * @param httpUrl 网络路径
	 * @param dstFilePath 本地文件路径
	 * @throws MalformedURLException
	 */
	public DownloadTask(String httpUrl,String dstFilePath) throws MalformedURLException{
		
		url = new URL(httpUrl); 
		this.httpUrl = httpUrl;
		this.cachePath = dstFilePath;
	}
	
	/**
	 * 获取当前下载的网络路径
	 * @return
	 */
	public String getHttpUrl(){
		return httpUrl;
	}
	
	/**
	 * 打开网络连接异常
	 */
	public static final int ERROR_CODE_OPEN_CONNECTION = -2;
	
	/**
	 * 打开网络输入流异常
	 */
	public static final int ERROR_CODE_OPEN_INPUT_STREAM = -3;
	
	/**
	 * 创建本地缓存文件失败
	 */
	public static final int ERROR_CODE_CREATE_CACHE_FILE = -4;
	
	/**
	 * 打开本地缓存文件输出流异常
	 */
	public static final int ERROR_CODE_OPEN_CACHE_FILE_OUTPUT_STREAM = -5;
	
	/**
	 * 读取网络输出流错误
	 */
	public static final int ERROR_CODE_READ_INPUT_STREAM = -6;
	
	/**
	 * 启动下载
	 */
	public synchronized void start() {
		
		Runnable executor = new Runnable() {
			
			@Override
			public void run() {
				
				// 打开网线连接
				URLConnection connection = null;
				try {
					connection = url.openConnection();
					connection.setConnectTimeout(10000);
				} 
				catch (IOException e) {
					
					String message = "网络连接失败";
					Debuger.e(message,httpUrl);
					
					if(listener != null){
						listener.onDownloadError(DownloadTask.this,ERROR_CODE_OPEN_CONNECTION, message);
					}
					return;
				}
				
				// 添加浏览器头标识
				connection.setRequestProperty("Accept", "*/*");
				connection.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.8,en;q=0.6,zh-TW;q=0.4");
				connection.setRequestProperty("Connection", "Keep-Alive");
				connection.setRequestProperty("User-Agent", "Mozilla/5.0");
				
				// 打开网络输入流
				InputStream input = null;
				try {
					input = connection.getInputStream();
				} catch (IOException e) {
					
					String message = "获取输入流失败";
					Debuger.e(message,httpUrl);
					
					FileUtil.closeStream(input);
					
					if(listener != null){
						listener.onDownloadError(DownloadTask.this,ERROR_CODE_OPEN_INPUT_STREAM, message);
					}
					return;
				}
				
				// 创建本地缓存文件
				if(!FileUtil.recreate(cachePath)){
					String message = "创建本地缓存文件失败";
					Debuger.e(message,cachePath);
					
					if(listener != null){
						listener.onDownloadError(DownloadTask.this,ERROR_CODE_CREATE_CACHE_FILE, message);
					}
					return;
				}
				
				// 文件输出流
				OutputStream output = FileUtil.openFileOutput(cachePath);
				if(null == output){
					String message = "打开本地缓存文件流失败";
					Debuger.e(message,cachePath);
					
					if(listener != null){
						listener.onDownloadError(DownloadTask.this,ERROR_CODE_OPEN_CACHE_FILE_OUTPUT_STREAM, message);
					}
					return;
				}
				
				// 获取网络文件长度信息
				long length = connection.getContentLength();
				byte[] cache = new byte[1024];
				int read = 0;
				int percent = 0;		// 百分比
				int percentTemp = 0;
				long count = 0;
				
				try {
					while ((read = input.read(cache)) != -1) {
						output.write(cache, 0, read);
						count += read;
						
						// 计算数据下载百分比
						if(length > 0)
						{
							double temp = DecimalUtil.divide(count, length);
							percentTemp = (int) (temp * 100);
							if(percentTemp > percent){
								if(listener != null){
									listener.onDownloadProgressUpdate(DownloadTask.this,percent,read,length);
								}
								percent = percentTemp;
							}
						}
						
						if(cancel) break;
					}
					
					if(listener != null && !cancel){
						listener.onDownloadFinish(DownloadTask.this,cachePath);
					}
					
				} catch (IOException e) {
					
					String message = "读取网络数据异常";
					Debuger.e(message,httpUrl);
					
					if(listener != null){
						listener.onDownloadError(DownloadTask.this,ERROR_CODE_READ_INPUT_STREAM, message);
					}
					
					FileUtil.closeInputStream(input);
					FileUtil.closeOutputStream(output);
				}
			}
		};
		
		// 添加到全局线程池中执行
		ThreadUtil.runInThreadPool(executor);
	}

	/**
	 * 下载监听回调
	 */
	public interface DownloadListener{
		
		/**
		 * 下载结束
		 * @param task
		 * @param filePath 本地文件路径
		 */
		void onDownloadFinish(DownloadTask task,String filePath);
		
		/**
		 * 下载进度更新回调
		 * @param task
		 * @param progress 当前进度
		 * @param bufferSize 本次下载长度
		 * @param totalLength 总文件长度
		 */
		void onDownloadProgressUpdate(DownloadTask task,int progress, int bufferSize,long totalLength);
		
		/**
		 * 写入异常
		 * @param task
		 * @param errorCode
		 * @param errorMessage
		 * @return true -> 结束写入；false -> 继续写入
		 */
		void onDownloadError(DownloadTask task,int errorCode,String errorMessage);
	}
	
	protected DownloadListener listener;
	
	/**
	 * 设置下载监听
	 * @param listener
	 */
	public void setListener(DownloadListener listener){
		this.listener = listener;
	}
	
	/**
	 * 获取本地缓存路径
	 * @return cachePath
	 */
	public String getLocalCachePath(){
		return cachePath;
	}
	
	protected boolean cancel = false;
	
	/**
	 * 取消写入任务
	 */
	public void cancel(){
		this.cancel = true;
	}
}
