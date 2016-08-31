package com.dinkevin.xui.util;

import java.util.List;

/**
 * 数组对应的工具类
 * @author chengpengfei
 */
public final class ArrayUtil {

	private ArrayUtil(){}
	
	/**
	 * 将数组连接成一个字符串
	 * @param array
	 * @param connector 连接符号，如果连接符号为 null 则表示不用添加连接符
	 * @return
	 */
	public static String toString(int[] array,String connector){
		String result = "";
		if(array != null){
			for(int num : array){
				result += Integer.toString(num);
				result += connector != null ? connector : "";
			}
			
			if(result.length() > 0){
				result = result.substring(0,result.length() - 1);
			}
		}
		return result;
	}
	
	/**
	 * 将数组连接成一个字符串
	 * @param array
	 * @param connector 连接符号，如果连接符号为 null 则表示不用添加连接符
	 * @return
	 */
	public static String toString(String[] array,String connector){
		String result = "";
		if(array != null){
			for(String num : array){
				result += num;
				result += connector != null ? connector : "";
			}
			
			if(result.length() > 0){
				result = result.substring(0,result.length() - 1);
			}
		}
		return result;
	}
	
	/**
	 * 将数组连接成一个字符串
	 * @param array
	 * @param connector 连接符号，如果连接符号为 null 则表示不用添加连接符
	 * @return
	 */
	public static String toString(List<? extends Object> array,String connector){
		String result = "";
		if(array != null){
			for(Object num : array){
				result += num;
				result += connector != null ? connector : "";
			}
			
			if(result.length() > 0){
				result = result.substring(0,result.length() - 1);
			}
		}
		return result;
	}
}
