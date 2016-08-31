package com.dinkevin.xui.net.json;

import com.dinkevin.xui.net.Headers;
import com.dinkevin.xui.net.Params;
import com.dinkevin.xui.net.SyncHttpRequest;
import com.dinkevin.xui.net.exception.HttpRequestException;
import com.dinkevin.xui.util.JSONUtil;

/**
 * JSON 数据格式的 HTTP 同步数据请求封装类
 * @author chengpengfei
 */
public class JSONSyncHttpRequest {

	private JSONSyncHttpRequest(){}

	/**
	 * 创建一个 JSONAsynHttpClient 对象
	 * @return
	 */
	public static JSONSyncHttpRequest create() {
		return new JSONSyncHttpRequest();
	}

	/**
	 * 执行 HTTP GET 同步请求
	 * @param action 请求地址
	 * @return JSON 对象
	 * @throws HttpRequestException
	 */
	public JSON get(String action) throws HttpRequestException{
		return get(action, null);
	}

	/**
	 * 执行 HTTP GET 同步请求
	 * @param action 请求地址
	 * @param headers 请求是要添加的头数据
	 * @return JSON 对象
	 * @throws HttpRequestException
	 */
	public JSON get(String action,Headers headers) throws HttpRequestException{
		SyncHttpRequest client = SyncHttpRequest.create();
		String data = client.get(action);
		
		JSON json = new JSON();
		json.setCode(JSONUtil.getInt(data, "code"));
		json.setMsg(JSONUtil.getString(data, "msg"));
		json.setData(JSONUtil.getString(data, "data"));
		return json;
	}
	
	/**
	 * 执行 HTTP POST 请求
	 * @param action 请求地址
	 * @param params 待提交参数
	 * @return 服务器返回 JSON 对象
	 * @throws HttpRequestException
	 */
	public JSON post(String action,Params params) throws HttpRequestException{
		return post(action, params, null);
	}

	/**
	 * 执行 HTTP POST 请求
	 * @param action 请求地址
	 * @param params 待提交参数
	 * @param headers 待添加的请求头数据
	 * @return 服务器返回 JSON 对象
	 * @throws HttpRequestException
	 */
	public JSON post(String action,Params params,Headers headers) throws HttpRequestException{
		SyncHttpRequest client = SyncHttpRequest.create();
		String data = client.post(action, params);
		
		JSON json = new JSON();
		json.setCode(JSONUtil.getInt(data, "code"));
		json.setMsg(JSONUtil.getString(data, "msg"));
		json.setData(JSONUtil.getString(data, "data"));
		return json;
	}
}
