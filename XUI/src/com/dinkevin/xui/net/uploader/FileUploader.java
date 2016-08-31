package com.dinkevin.xui.net.uploader;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;

import org.apache.http.Header;

import com.dinkevin.xui.net.Params;
import com.dinkevin.xui.util.Debuger;
import com.dinkevin.xui.util.ThreadUtil;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * 文件上传类
 * @author chengpengfei
 */
public class FileUploader {
	
	protected Params params;
	protected String localFilePath,fileKey,action;
	
	/**
	 * 构造器
	 * @param action 提交服务器地址
	 * @param params 参数
	 * @param localFilePath 本地文件路径
	 * @param fileKey 文件对象的键值
	 */
	public FileUploader(String action,Params params,String localFilePath,String fileKey) {
		this.action = action;
		this.params = params;
		this.fileKey = fileKey;
		this.localFilePath = localFilePath;
	}
	
	/**
	 * 文件上传回调
	 */
	public interface UploadFileCallback{
		
		/**
		 * 上传成功服务器返回
		 * @param data 服务器返回数据
		 */
		void onUploadSucceed(String data);
		
		/**
		 * 上传失败
		 * @param code 错误码
		 * @param message 错误信息
		 */
		void onUploadError(int code,String message);
	}
	
	/**
	 * 开始 Post 方式提交
	 * @param callback 提交回调
	 * @return true->启动成功
	 */
	public boolean post(final UploadFileCallback  callback){
		
		final RequestParams param = new RequestParams();
		final File f = new File(localFilePath);
		try {
			param.put(fileKey, f);
		} catch (FileNotFoundException e) {
			Debuger.e("文件未找到 "+localFilePath);
			return false;
		}
		
		if(null != params){
			Iterator<String> it = this.params.container.keySet().iterator();
			while (it.hasNext()) {
				String key = it.next();
				String value = this.params.container.get(key);
				param.put(key, value);
			}
		}
		
		// AsyncHttpClient 需要在 UI 线程上调用
		if(ThreadUtil.isOnMainThread()){
			post(param, callback);
		}
		else
		{
			ThreadUtil.runOnMainThread(new Runnable() {
				
				@Override
				public void run() {
					post(param, callback);
				}
			});
		}
		return true;
	}
	
	/**
	 * 执行 HTTP POST 网络请求
	 * @param params 请求参数
	 * @param callback 上传回调监听
	 */
	private void post(RequestParams params,final UploadFileCallback  callback){
		
		AsyncHttpClient client = new AsyncHttpClient();
		client.setTimeout(2*60000);
		client.post(action, params, new AsyncHttpResponseHandler(){

			@Override
			public void onFailure(int code, Header[] header, byte[] response, Throwable t) {
				if(callback != null){
					if(response != null)
						callback.onUploadError(code, new String(response));
				}
			}

			@Override
			public void onSuccess(int code, Header[] header, byte[] response) {
				if(callback != null){
					if(response != null)
						callback.onUploadSucceed(new String(response));
				}
			}
		});
	}
}
