package com.dinkevin.xui.net;

/**
 * HTTPClient 请求异常统一接口声明
 * @author chengpengfei
 *
 */
public interface IHttpRequestErrorListener {

	/**
	 * HTTP 请求异常回调
	 * @param errorCode 错误码
	 * @param errorMessage 错误描述信息
	 */
	void onHttpRequestError(int errorCode,String errorMessage);
}
