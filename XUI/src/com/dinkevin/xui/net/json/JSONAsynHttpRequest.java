package com.dinkevin.xui.net.json;

import com.dinkevin.xui.net.Headers;
import com.dinkevin.xui.net.IHttpRequestErrorListener;
import com.dinkevin.xui.net.Params;
import com.dinkevin.xui.net.exception.HttpRequestException;
import com.dinkevin.xui.util.ThreadUtil;

/**
 * JSON 数据格式的 HTTP 异步数据请求封装类</br>
 * 此类中使用到了 ThreadUtil 类，详见 {@link ThreadUtil}
 * 
 * @author chengpengfei
 */
public class JSONAsynHttpRequest {

	private JSONAsynHttpRequest() {}

	/**
	 * 创建一个 JSONAsynHttpClient 对象
	 * @return
	 */
	public static JSONAsynHttpRequest create() {
		return new JSONAsynHttpRequest();
	}

	/**
	 * JSON 数据格式 HTTP 请求回调
	 */
	public interface IJSONHttpClientRequestCallback extends IHttpRequestErrorListener {

		/**
		 * 接收到服务器返回的 JSON 对象实体类
		 * @param json
		 */
		void onReceiveJSON(JSON json);
	}

	/**
	 * 执行 HTTP GET 异步请求
	 * 
	 * @param action 请求地址
	 * @param callback 数据请求回调
	 */
	public void get(final String action, final IJSONHttpClientRequestCallback callback) {
		get(action, null, callback);
	}

	/**
	 * 在线程池中执行此 HTTP GET 请求
	 * 
	 * @param action
	 * @param headers
	 * @param callback
	 */
	public void getInThreadPool(final String action, final Headers headers, final IJSONHttpClientRequestCallback callback) {
		JSONSyncHttpRequest client = JSONSyncHttpRequest.create();
		try {
			JSON result = client.get(action, headers);
			if (callback != null) {
				callback.onReceiveJSON(result);
			}

		} catch (final HttpRequestException e) {
			if (callback != null) {
				callback.onHttpRequestError(e.getCode(), e.getMessage());
			}
		}
	}

	/**
	 * 执行 HTTP GET 异步请求
	 * 
	 * @param action 请求地址
	 * @param headers 请求是要添加的头数据
	 * @param callback 数据请求回调
	 */
	public void get(final String action, final Headers headers, final IJSONHttpClientRequestCallback callback) {

		// 添加到线程池
		ThreadUtil.runInThreadPool(new Runnable() {

			@Override
			public void run() {

				JSONSyncHttpRequest client = JSONSyncHttpRequest.create();
				try {
					final JSON result = client.get(action, headers);
					// 为了 UI 更新方便，将回调放在主线程中
					ThreadUtil.runOnMainThread(new Runnable() {

						@Override
						public void run() {
							if (callback != null) {
								callback.onReceiveJSON(result);
							}
						}
					});

				} catch (final HttpRequestException e) {

					// 为了更新 UI 方便，将回调放在主线程中
					ThreadUtil.runOnMainThread(new Runnable() {
						@Override
						public void run() {
							if (callback != null) {
								callback.onHttpRequestError(e.getCode(), e.getMessage());
							}
						}
					});
				}
			}
		});
	}

	/**
	 * 执行 HTTP POST 异步请求
	 * 
	 * @param action 请求地址
	 * @param params 待提交参数
	 * @param callback 数据请求回调
	 */
	public void post(final String action, final Params params, final IJSONHttpClientRequestCallback callback) {
		post(action, params, null, callback);
	}

	/**
	 * 执行 HTTP POST 异步请求
	 * 
	 * @param action 请求地址
	 * @param params 待提交参数
	 * @param callback 数据请求回调
	 */
	public void postInThreadPool(final String action, final Params params, final IJSONHttpClientRequestCallback callback) {
		postInThreadPool(action, params, null, callback);
	}

	/**
	 * 在线程池中执行 HTTP POST 异步请求
	 * 
	 * @param action 请求地址
	 * @param params 待提交参数
	 * @param callback 数据请求回调
	 */
	public void postInThreadPool(final String action, final Params params, final Headers headers,
			final IJSONHttpClientRequestCallback callback) {
		// 添加到线程池
		ThreadUtil.runInThreadPool(new Runnable() {

			@Override
			public void run() {

				JSONSyncHttpRequest client = JSONSyncHttpRequest.create();
				try {

					JSON result = client.post(action, params, headers);
					if (callback != null) {
						callback.onReceiveJSON(result);
					}

				} catch (final HttpRequestException e) {

					if (callback != null) {
						callback.onHttpRequestError(e.getCode(), e.getMessage());
					}
				}
			}
		});
	}

	/**
	 * 执行 HTTP POST 异步请求
	 * 
	 * @param action 请求地址
	 * @param params 待提交参数
	 * @param headers 请求是要添加的头数据
	 * @param callback 数据请求回调
	 */
	public void post(final String action, final Params params, final Headers headers,
			final IJSONHttpClientRequestCallback callback) {

		// 添加到线程池
		ThreadUtil.runInThreadPool(new Runnable() {

			@Override
			public void run() {

				JSONSyncHttpRequest client = JSONSyncHttpRequest.create();
				try {

					final JSON result = client.post(action, params, headers);
					// 为了 UI 更新方便，将回调放在主线程中
					ThreadUtil.runOnMainThread(new Runnable() {
						@Override
						public void run() {
							if (callback != null) {
								callback.onReceiveJSON(result);
							}
						}
					});

				} catch (final HttpRequestException e) {

					// 为了更新 UI 方便，将回调放在主线程中
					ThreadUtil.runOnMainThread(new Runnable() {
						@Override
						public void run() {
							if (callback != null) {
								callback.onHttpRequestError(e.getCode(), e.getMessage());
							}
						}
					});
				}
			}
		});
	}
}
