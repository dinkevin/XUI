package com.dinkevin.xui.util;

import android.support.annotation.NonNull;

/**
 * 字符串处理工具类
 * @author chengpengfei
 *
 */
public final class StringUtil {

	private StringUtil(){}
	
	/**
	 * 截取字符串
	 * @param source 
	 * @param MaxLength 最大长度
	 * @param suffix 如果给定的字符长度大于指定的最大长度，零取后所加的后缀
	 * @return
	 */
	public static String cutString(@NonNull String source,@NonNull int MaxLength,String suffix){
		if(source != null)
		{
			if(source.length() > MaxLength){
				int index = MaxLength - (suffix != null ? suffix.length():0);
				if(index > 0){
					return source.substring(0,index);
				}
			}
		}
		return source;
	}
	
	/**
	 * 判断该字段是否为空，
	 * @param str 如果 str 为 null或者长度为 0 则返回 null
	 */
	public static boolean isEmpty(String str){
		if(str == null || str.trim().length() < 1){
			return true;
		}
		return false;
	}
}
