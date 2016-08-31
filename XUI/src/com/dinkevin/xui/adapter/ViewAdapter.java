package com.dinkevin.xui.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;

/**
 * 通用的数据列表数据源适配器类
 * 
 * @author chengpengfei
 * @param <T> 对象类
 * @param <V> 对象类对应的显示类
 */
public abstract class ViewAdapter<T, V extends ViewHolder<T>> extends AbstractAdapter<T> {

	/**
	 * 构造器
	 * @param context
	 */
	public ViewAdapter(Context context) {
		super(context);
	}
	
	/**
	 * 构造器，传入数据
	 * @param context
	 * @param data
	 */
	public ViewAdapter(Context context,List<T> data) {
		super(context,data);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected View createItemView(int position) {
		View view = createViewHolder().inflate(context, null, false);
		V holder = (V) view.getTag();
		holder.set(getItem(position));
		return view;
	}
	
	/**
	 * 获取列表 Item 对应的 ViewHolder
	 * @param position
	 * @return ViewHolder 的实现类
	 */
	@SuppressWarnings("unchecked")
	public V getViewHolder(int position){
		View view = getView(position, null, null);
		if(null == view) return null;
		return (V)view.getTag();
	}

	protected abstract V createViewHolder();
}
