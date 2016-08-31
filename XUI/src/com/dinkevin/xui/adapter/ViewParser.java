package com.dinkevin.xui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

/**
 * 视图解析器,用户加载、解析xml中的布局
 */
public interface ViewParser {
	
	/**
	 * 载入布局文件
	 * @param context 上下文
	 * @param parent 父容器
	 * @param attachToRoot 是否添加到根 View
	 * @return 
	 */
    View inflate(Context context, ViewGroup parent, boolean attachToRoot);
}
