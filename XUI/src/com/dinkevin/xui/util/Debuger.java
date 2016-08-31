package com.dinkevin.xui.util;

import java.io.File;
import java.util.Date;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

/**
 * Debug 调用打印输出工具类
 * @author chengpengfei
 */
public class Debuger {

	public static final String LOG_NAME = "debugger_log.txt";
	private static String LOG_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + LOG_NAME;
	
	private static boolean g_enable = true;	 		// 调试输出开关
	public static final String TAG = "dinkevin";

	/**
	 * 设置调用输出开关
	 * @param enable true -> 开启打印调试信息;false -> 关闭打印调试信息
	 */
	public static void setEnable(boolean enable){
		g_enable = enable;
	}
	
	/**
	 * 获取当前打印输出是否开启
	 * @return true -> 开启打印调试信息;false -> 关闭打印调试信息
	 */
	public static boolean isEnable(){
		return g_enable;
	}
	
	/**
	 * Debug 打印并记录到日志文件
	 * @param params
	 */
	public static void df(Object... params)
	{
		String content = d(params);
		if(!TextUtils.isEmpty(content))
			write(TimeUtil.FORMAT_STANDARD.format(new Date()) + content);
	}
	
	/**
	 * Debug 打印异常信息并记录到日志文件
	 * @param params
	 */
	public static void ef(Object... params)
	{
		String content = e(params);
		if(!TextUtils.isEmpty(content))
			write(TimeUtil.FORMAT_STANDARD.format(new Date()) + content);
	}
	
	/**
	 * 打印debug信息
	 * @param params
	 * @return 已经打印的字符串
	 */
	public static String d(Object... params)
	{
		if(!g_enable) return null;
		if(params == null) return null;
		StringBuffer buffer = new StringBuffer();
		buffer.append(" [D]:");
		for(Object obj : params)
		{
			buffer.append(obj);
			buffer.append(" ");
		}
		buffer.append("\n");
		Log.d(TAG, buffer.toString());
		System.out.println(buffer.toString());
		return buffer.toString();
	}
	
	/**
	 * 打印异常debug信息
	 * @param params
	 * @return 已打印的字符串
	 */
	public static String e(Object... params)
	{
		if(!g_enable) return null;
		if(params == null) return null;
		StringBuffer buffer = new StringBuffer();
		buffer.append(" [E]:");
		for(Object obj : params)
		{
			buffer.append(obj);
			buffer.append(" ");
		}
		buffer.append("\n");
		Log.e(TAG, buffer.toString());
		System.out.print(buffer.toString());
		return buffer.toString();
	}
	
	/**
	 * 调用系统的System.out.println接口
	 * @param params
	 */
	public static void p(Object...params)
	{
		if(!g_enable) return;
		if(params == null) return;
		StringBuffer buffer = new StringBuffer(TAG +" [P]:");
		for(Object obj : params)
		{
			buffer.append(obj);
			buffer.append(" ");
		}
		System.out.println(buffer.toString());
	}
	
	/**
	 * 将日志写入到指定的文件中
	 * @param content 日志内容
	 */
	private static synchronized void write(String content)
	{
		byte[] data = content.getBytes();
		FileUtil.appendToFile(data, 0, data.length, LOG_PATH);
	}
}
