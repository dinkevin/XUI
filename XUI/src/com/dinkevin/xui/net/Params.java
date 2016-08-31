package com.dinkevin.xui.net;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.http.message.BasicNameValuePair;

/**
 * 网络请求参数实体对象类
 * @author chengpengfei
 */
public class Params extends Headers{
	
	/**
	 * 将当前的参数信息输出
	 * @return
	 */
	public List<BasicNameValuePair> output(){
		List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
		for (Map.Entry<String, String> entry : container.entrySet()) {
			params.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
		}
		return params;
	}

	@Override
	public String toString() {
		String result = "";
		Iterator<String> it = container.keySet().iterator();
		while (it.hasNext()) {
			String key = it.next();
			result += key;
			result += "=";
			result += container.get(key);
			result += "&";
		}
		int index = result.lastIndexOf("&");
		return result.substring(0,index);
	}
}
