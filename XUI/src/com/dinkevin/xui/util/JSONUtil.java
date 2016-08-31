package com.dinkevin.xui.util;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import android.text.TextUtils;

/**
 * JSON 工具类
 * @author chengpengfei
 */
public class JSONUtil {
	
	/**
	 * 谷歌 JSON 解析原生对象
	 */
	public static final Gson gson = new Gson();

	/**
	 * 从 JSON 中读取到指定类型数组对象并转化到指定类型数组
	 * @param json
	 * @param type
	 * @return
	 */
	public static <T> List<T> parseToList(String json, Class<T> type) {
		JsonParser parser = new JsonParser();
		JsonArray Jarray = parser.parse(json).getAsJsonArray();
		ArrayList<T> lcs = new ArrayList<T>();
		for (JsonElement obj : Jarray) {
			lcs.add((T) gson.fromJson(obj, type));
		}
		return lcs;
	}

	/**
	 * 将 JSON 转换到指定对象
	 * @param json
	 * @param type
	 * @return
	 */
	public static <T> Object parseToObject(String json, Class<T> type) {
		return gson.fromJson(json, type);
	}
	
	/**
	 * 将字符串转换到 JSONObject 对象
	 * @param json
	 * @return
	 */
	public static JSONObject parse(String json)
	{
		JSONObject result = null;
		try {
			result = new JSONObject(json);
		} catch (JSONException e) {
			Debuger.e("json get error",e.getMessage());
		}
		return result;
	}
	
	/**
	 * 从 json 中读取到 key 对应的对象
	 * @param json
	 * @param key
	 * @return
	 */
	public static JSONObject getJSONObject(String json,String key)
	{
		JSONObject result = null;
		try {
			result = new JSONObject(json);
			return result.getJSONObject(key);
		} catch (JSONException e) {
			Debuger.e("json get JSONOjbect for key:",key);
		}
		return result;
	}
	
	/**
	 * 从 JSON 中读取到指定 key 对应的字符串
	 * @param json
	 * @param key
	 * @return
	 */
	public static String getString(String json,String key)
	{
		JSONObject result = null;
		try {
			result = new JSONObject(json);
			return result.getString(key);
		} catch (JSONException e) {
			Debuger.e("json get String for key:",key);
		}
		return null;
	}
	
	/**
	 * 从 JSON 中读取到指定 key 对应的字符串
	 * @param json
	 * @param key
	 * @return
	 */
	public static int getInt(String json,String key)
	{
		String num = getString(json, key);
		if(TextUtils.isEmpty(num)){
			return 0;
		}
		
		return Integer.parseInt(num);
	}
	
	/**
	 * 从 JSON 中读取到指定 key 对应的字符串
	 * @param json
	 * @param key
	 * @return
	 */
	public static String getString(JSONObject json,String key)
	{
		 try {
			return json.getString(key);
		} catch (JSONException e) {
			Debuger.e("get value for key:",key);
		}
		return null;
	}
	
	/**
	 * 从 JSON 中读取到指定 key 对应的字符串
	 * @param json
	 * @param key
	 * @return
	 */
	public static int getInt(JSONObject json,String key)
	{
		String num = getString(json, key);
		if(TextUtils.isEmpty(num)){
			return 0;
		}
		
		return Integer.parseInt(num);
	}
	
	/**
	 * 从 JSON 中读取到指定 key 对应的 JSON 对象
	 * @param json
	 * @param key
	 * @return
	 */
	public static JSONObject getJSONObject(JSONObject json,String key)
	{
		try {
			return json.getJSONObject(key);
		} catch (JSONException e) {
			Debuger.e("get jsonObject for key:",key);
		}
		return null;
	}
	
	/**
	 * 从 JSON 中读取到指定 key 对应的 JSON 对象数组
	 * @param json
	 * @param key
	 * @return
	 */
	public static JSONArray getJSONArray(String json,String key){
		JSONObject obj = parse(json);
		if(obj != null)
		{
			try {
				return obj.getJSONArray(key);
			} catch (JSONException e) {
				Debuger.e("get jsonArray for key:"+key);
			}
		}
		return null;
	}
}
