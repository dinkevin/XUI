package com.dinkevin.xui.util;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import android.content.Context;
import android.text.TextUtils;

/**
 * 资源 Finder 工具类</br>
 * 在调用此方法中的接口前要一定要调用 {@link #initial(Context)} 进行初始化</br>
 * 建议在 Application 实现类中的 onCreate 方法中调用 {@link #initial(Context)}，{@link #initial(Context)} 调用一次即可。
 * @author chengpengfei
 */
public class ResoureFinder {
	
	private ResoureFinder(){}
	
	private static String mClassName;
	private static Map<String, Map<String, Object>> mResourceMap = new HashMap<String, Map<String, Object>>();

	/**
	 * 在 Application 实现类中的 onCreate 方法中调用
	 * @param context
	 */
	public static void initial(Context context) {
		if (TextUtils.isEmpty(mClassName))
			mClassName = context.getPackageName() + ".R";
		
		if (mClassName != null) {
			if (mResourceMap.size() == 0) {
				Class<?>[] classArray = null;
				try {
					classArray = Class.forName(mClassName).getClasses();

					if (classArray != null) {
						for (Class<?> cls : classArray) {
							String typeName = cls.getSimpleName().toLowerCase(Locale.getDefault());

							Map<String, Object> fieldMap = mResourceMap.get(typeName);
							if(null == fieldMap){
								fieldMap = new HashMap<String, Object>();
								mResourceMap.put(typeName, fieldMap);
							}
							
							Field[] fields = cls.getFields();
							for (Field f : fields) {
								fieldMap.put(f.getName().toLowerCase(Locale.getDefault()), f.get(null));
							}
						}
					}
				} catch (Exception e) {
					Debuger.e("ResourceFinder initial Error",e.getMessage());
				}
			}
		}
	}

	private static Object getResource(String type, String name) {
		
		if (mClassName != null) {
			if (mResourceMap.containsKey(type)) {
				if (mResourceMap.get(type).containsKey(name)) {
					return mResourceMap.get(type).get(name);
				}
			}
		}
		return null;
	}

	/**
	 * 获取资源Id
	 * @param resType 资源类型 {@link ResType}
	 * @param name 资源名称
	 * @return
	 */
	public static int getResourceId(ResType resType, String name) {
		Object obj = getResource(resType.toString(), name);
		if (obj == null)
			throw new RuntimeException("获取资源ID失败:(packageName=" + mClassName + " type=" + resType + " name=" + name);

		return (Integer) obj;
	}

	/**
	 * 读取字符串
	 * @param context
	 * @param name
	 * @return
	 */
	public static String getString(Context context, String name) {
		int i = getResourceId(ResType.STRING, name);
		return context.getString(i);
	}

	/**
	 * 读取属性数组
	 * @param name
	 * @return
	 */
	public static int[] getStyleableAttributes(String name) {
		Object obj = getResource(ResType.STYLEABLE.toString(), name);
		if (obj != null) {
			return (int[]) obj;
		}
		return null;
	}

	/**
	 * 资源类型
	 */
	public static enum ResType {
		LAYOUT, ID, DRAWABLE, STYLE, STRING, COLOR, DIMEN, RAW, ANIM, STYLEABLE;

		@Override
		public String toString() {
			return this.name().toLowerCase(Locale.getDefault());
		}
	}
}
