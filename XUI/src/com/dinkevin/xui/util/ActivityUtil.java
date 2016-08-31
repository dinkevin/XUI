package com.dinkevin.xui.util;

import android.app.Activity;
import android.view.View;

/**
 * Activity 相关工具类
 * @author chengpengfei
 *
 */
public class ActivityUtil {
	
	private ActivityUtil(){}

	/**
	 * 获取 Activity 的标题栏
	 * @param activity
	 * @return 标题栏
	 */
	public static View getHeadView(Activity activity){
		
		// 获取标题栏资源Id
		Object t_obj = ReflactUtil.reflactFiled("com.android.internal.R$id", "title_container");
		if(t_obj != null)
		{
			int t_id = (Integer)t_obj;
			View t_container = activity.getWindow().findViewById(t_id);
			if(t_container != null){
				return t_container;
			}
		}
		return null;
	}
	
	/**
	 * 隐藏 Activity 的标题栏
	 * @param activity
	 */
	public static void hiddenHeadView(Activity activity)
	{
		View t_container = getHeadView(activity);
		if(t_container != null)
			t_container.setVisibility(View.GONE);
	}
	
	/**
	 * 显示 Activity 的标题栏
	 * @param activity
	 */
	public static void showHeadView(Activity activity)
	{
		View t_container = getHeadView(activity);
		if(t_container != null && t_container.getVisibility() == View.GONE)
			t_container.setVisibility(View.VISIBLE);
	}
}
