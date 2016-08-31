package com.dinkevin.xui.net;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 网络请求头数据实体对象类
 * @author chengpengfei
 */
public class Headers{
	
	/**
	 * 参数容器
	 */
	public Map<String,String> container = new HashMap<String,String>();

	/**
	 * 添加参数
	 * @param key
	 * @param vlaue
	 */
	public void put(String key,String value){
		container.put(key, value);
	}
	
	/**
	 * 添加参数
	 * @param key
	 * @param vlaue
	 */
	public void put(String key,int value){
		container.put(key, Integer.toString(value));
	}
	
	/**
	 * 添加参数
	 * @param key
	 * @param vlaue
	 */
	public void put(String key,long value){
		container.put(key, Long.toString(value));
	}
	
	/**
	 * 添加参数
	 * @param key
	 * @param value
	 */
	public void put(String key,double value){
		container.put(key, Double.toString(value));
	}
	
	/**
	 * 添加参数
	 * @param key
	 * @param value
	 */
	public void put(String key,float value){
		container.put(key, Float.toString(value));
	}
	
	/**
	 * 添加参数
	 * @param key
	 * @param value
	 */
	public void put(String key,Object value){
		if(null == value) 
			container.put(key, "");
		else
			container.put(key, value.toString());
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
			result += ";";
		}
		int index = result.lastIndexOf(";");
		return result.substring(0,index);
	}
}
