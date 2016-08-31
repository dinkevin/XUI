package com.dinkevin.xui.net;

import com.dinkevin.xui.net.exception.HttpRequestException;
import com.dinkevin.xui.util.ThreadUtil;

/**
 * 异步方式 HTTP 网络请求封装类</br>
 * 此类中使用到了 ThreadUtil 类，详见 {@link ThreadUtil}
 * @author chengpengfei
 *
 */
public class AsynHttpRequest {

	private AsynHttpRequest() {}

	/**
	 * 生成一个网络请求封装类
	 * @return
	 */
	public static AsynHttpRequest create() {
		return new AsynHttpRequest();
	}
	
	/**
	 * 异步请求服务器数据返回回调
	 */
	public interface IHttpClientRequestCallback{
		
		/**
		 * 接收到服务器返回数据
		 * @param data
		 */
		void onReceiveData(String data);
		
		/**
		 * 请求服务器时发生错误
		 * @param e
		 */
		void onHttpClientRequestError(HttpRequestException e);
	}
	
	/**
	 * 执行 HTTP GET 异步请求
	 * @param action 请求地址
	 * @param callback 数据请求回调
	 */
	public void get(final String action,final IHttpClientRequestCallback callback){
		get(action, null, callback);
	}
	
	/**
	 * 执行 HTTP GET 异步请求
	 * @param action 请求地址
	 * @param headers 请求是要添加的头数据
	 * @param callback 数据请求回调
	 */
	public void get(final String action,final Headers headers,final IHttpClientRequestCallback callback){
		
		// 添加到线程池
		ThreadUtil.runInThreadPool(new Runnable() {
			
			@Override
			public void run() {
				SyncHttpRequest client = SyncHttpRequest.create();
				try{
					String result = client.get(action, headers);
					if(callback != null){
						callback.onReceiveData(result);
					}
				} catch (HttpRequestException e){
					if(callback != null){
						callback.onHttpClientRequestError(e);
					}
				}
			}
		});
	}
	
	/**
	 * 执行 HTTP POST 异步请求
	 * @param action 请求地址
	 * @param params 待提交参数
	 * @param callback 数据请求回调
	 */
	public void post(final String action,final Params params,final IHttpClientRequestCallback callback){
		post(action, params, null, callback);
	}
	
	/**
	 * 执行 HTTP POST 异步请求
	 * @param action 请求地址
	 * @param params 待提交参数
	 * @param headers 请求是要添加的头数据
	 * @param callback 数据请求回调
	 */
	public void post(final String action,final Params params,final Headers headers,final IHttpClientRequestCallback callback){
		
		// 添加到线程池
		ThreadUtil.runInThreadPool(new Runnable() {
			
			@Override
			public void run() {
				SyncHttpRequest client = SyncHttpRequest.create();
				try{
					String result = client.post(action, params ,headers);
					if(callback != null){
						callback.onReceiveData(result);
					}
				} catch (HttpRequestException e){
					if(callback != null){
						callback.onHttpClientRequestError(e);
					}
				}
			}
		});
	}
}
