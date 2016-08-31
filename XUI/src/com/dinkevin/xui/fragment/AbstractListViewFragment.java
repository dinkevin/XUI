package com.dinkevin.xui.fragment;

import com.dinkevin.xui.R;
import com.dinkevin.xui.adapter.AbstractAdapter;

import android.widget.BaseAdapter;
import android.widget.ListView;

/**
 * 通用的列表 Fragment 基类
 * @author chengpengfei
 */
public abstract class AbstractListViewFragment<T> extends AbstractFragment{
	
	/**
	 * 列表对应的数据源
	 */
	private AbstractAdapter<T> adapter;
	
	/**
	 * 列表控件
	 */
	protected ListView lsv_content;
	
	/**
	 * 设置列表数据数据源
	 * @param adapter
	 */
	public void setAdapter(AbstractAdapter<T> adapter){
		this.adapter = adapter;
		if(lsv_content != null){
			lsv_content.setAdapter(adapter);
		}
	}
	
	/**
	 * 获取当前列表数据源
	 * @return
	 */
	public BaseAdapter getAdapter(){
		return adapter;
	}

	@Override
	protected int getFragmetLayout() {
		return R.layout.xui_common_list_view;
	}

	@Override
	protected void initialWidget() {
		super.initialWidget();
		
		initialAdapter();
		
		// 绑定列表数据源
		lsv_content = (ListView)view_root.findViewById(R.id.xui_list_view);
		if(adapter != null) lsv_content.setAdapter(adapter);
	}
	
	/**
	 * 子类实现初始化数据源，本接口由父类调用，调用 {@link #setAdapter(AbstractAdapter)} 来进行绑定列表数据源
	 */
	protected abstract void initialAdapter();

	@Override
	protected boolean needSaveInstanceState() {
		return true;		// 保存当前 Fragment 显示状态
	}
}
