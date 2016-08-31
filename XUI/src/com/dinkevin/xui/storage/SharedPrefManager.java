package com.dinkevin.xui.storage;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * SharedPreferences 统一管理类
 * @author chengpengfei
 *
 */
public class SharedPrefManager {
	
	private static String CONFIG_FILE_NAME = "shared.config";
	
	private volatile static SharedPrefManager singleton = new SharedPrefManager();
	
	private static SharedPreferences sharedPref;

	private SharedPrefManager(){}
	
	/**
	 * 此接口在 Application 中的 onCreate 中调用
	 * @param context
	 */
	public static void initial(Context context){
		sharedPref = context.getSharedPreferences(CONFIG_FILE_NAME,Context.MODE_PRIVATE);
	}
	
	public static SharedPrefManager getInstance(){
		return singleton;
	}
	
	public String getString(String key,String defValue){
		if(sharedPref != null){
			return sharedPref.getString(key, defValue);
		}
		return defValue;
	}
	
	public void putString(String key,String value){
		if(sharedPref != null){
			sharedPref.edit().putString(key, value).commit();
		}
	}
	
	public int getInt(String key,int defValue){
		if(sharedPref != null){
			return sharedPref.getInt(key, defValue);
		}
		return defValue;
	}
	
	public void putInt(String key,int value){
		if(sharedPref != null)
			sharedPref.edit().putInt(key, value);
	}
	
	public boolean getBoolean(String key,boolean defValue){
		if(sharedPref != null)
			return sharedPref.getBoolean(key, defValue);
		return defValue;
	}
	
	public void putBoolean(String key,boolean value){
		if(sharedPref != null)
			sharedPref.edit().putBoolean(key, value);
	}
	
	public SharedPreferences getSharedPref(){
		return sharedPref;
	}
}
