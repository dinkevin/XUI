package com.dinkevin.xui.util;

/**
 * 映射工具类
 * 
 * @author chengpengfei
 *
 */
public final class ReflactUtil {

	private ReflactUtil() {}

	/**
	 * 获取指定类的属性
	 * @param className
	 * @param filedName
	 * @return
	 */
	public static Object reflactFiled(String className, String filedName) {
		Object result = null;
		try {
			result = Class.forName(className).getField(filedName).get(null);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return result;
	}
}
