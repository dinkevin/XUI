package com.dinkevin.xui.adapter;

import com.dinkevin.xui.util.ViewFinder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Adapter Item 对应的 ViewHolder 基类
 * @author chengpengfei
 * @param <T>
 */
public abstract class ViewHolder<T> implements IViewParser {

	protected View rootView;
	protected LayoutInflater layoutInflater;
	protected Context context;
	protected ViewFinder viewFinder;

	@Override
	public final View inflate(Context context, ViewGroup parent, boolean attachToRoot) {
		this.context = context;
		layoutInflater = LayoutInflater.from(context);
		rootView = layoutInflater.inflate(getItemLayout(), parent, attachToRoot);
		
		// 设置tag
		rootView.setTag(this);
		viewFinder = new ViewFinder(rootView);
		initWidgets();
		
		return rootView;
	}

	/**
	 * 获取 ItemView 的布局Id
	 * @return Item View布局
	 */
	protected abstract int getItemLayout();

	/**
	 * 初始化各个子视图、控件
	 */
	protected abstract void initWidgets();
	
	/**
	 * 填充数据
	 * @param t
	 */
	public abstract void set(T t);
}
