package com.example.xuitest;

import java.io.File;

/**
 * 定义常量
 * @author chengpengfei
 *
 */
public final class Constants {

	private Constants(){}
	
	/**
	 * 应用本地文件根目录名称
	 */
	public static final String APP_DIR = "XUI";
	
	/**
	 * 缓存根目录
	 */
	public static final String CACHE_DIR =  APP_DIR + File.separator + "cache" + File.separator;
	
	/**
	 * 数据根目录
	 */
	public static final String DATA_DIR = APP_DIR + File.separator + "data"  + File.separator;
}
