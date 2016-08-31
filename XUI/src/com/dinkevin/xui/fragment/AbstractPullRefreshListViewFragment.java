package com.dinkevin.xui.fragment;

import com.dinkevin.xui.R;
import com.dinkevin.xui.module.CommonConstant;
import com.dinkevin.xui.view.PullRefreshListView;
import com.dinkevin.xui.view.PullRefreshListView.OnLoadListener;
import com.dinkevin.xui.view.PullRefreshListView.OnRefreshListener;

import android.annotation.SuppressLint;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;

/**
 * 滑动刷新列表 Fragment 抽象基类
 * @author chengpengfei
 */
@SuppressLint("InflateParams")
public abstract class AbstractPullRefreshListViewFragment extends AbstractFragment implements OnItemClickListener{
	
	/**
	 * 上下拉滑动刷新列表控件
	 */
	protected PullRefreshListView lsv_content;
	
	/**
	 * 当前列表对应的数据源
	 */
	private BaseAdapter adapter;

	protected boolean isPullUpRefresh = false;		// 下拉刷新操作标识
	protected boolean isLoadMoreRefresh = false;	// 上拉刷新操作标识
	
	/**
	 * 设置列表数据数据源
	 * @param adapter
	 */
	public void setAdapter(BaseAdapter adapter){
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
	protected int getFragmetLayout(){
		return R.layout.xui_view_pull_to_refresh;
	}
	
	@Override
	protected boolean needSaveInstanceState(){
		return true;	// 保存当前列表显示状态
	}
	
	@Override
	protected void initialWidget(){
		
		lsv_content = viewFinder.findViewById(R.id.lsv_content);
		lsv_content.setResultSize(CommonConstant.PAGE_SIZE);
		lsv_content.setOnItemClickListener(this);
		
		// 设置下拉刷新监听
		lsv_content.setOnRefreshListener(new OnRefreshListener() {
			
			@Override
			public void onRefresh() {
				isPullUpRefresh = true;
				onRefresh();
			}
		});
		
		// 设置上拉刷新监听
		lsv_content.setOnLoadListener(new OnLoadListener() {
			
			@Override
			public void onLoad() {
				isLoadMoreRefresh = true;
				onLoad();
			}
		});
	}
	
	/**
	 * 下拉刷新回调，列表刷新</br>
	 * 注：调用此方法刷新列表数据后，从服务器获取数据添加到当前列表后，需要调用 {@link #onRefreshOrLoadComplete(int)} 来更新列表头部显示状态
	 */
	protected abstract void onRefresh();
	
	/**
	 * 上拉刷新回调，载入更多数据</br>
	 * 注：调用此方法刷新列表数据后，从服务器获取数据添加到当前列表后，需要调用 {@link #onRefreshOrLoadComplete(int)} 来更新列表尾部显示状态
	 */
	protected abstract void onLoad();
	
	/**
	 * 初始化列表数据源，初始化数据源成功后，调用 {@link #setAdapter(BaseAdapter)} 绑定列表数据源
	 */
	protected abstract void initialAdapter();
	
	/**
	 * 刷新结束，更新列表头与尾显示状态
	 * @param resultSize 此次刷新数量
	 */
	protected void onRefreshOrLoadComplete(int resultSize){
		
		if(isPullUpRefresh)
		{
			lsv_content.onRefreshComplete();
			if(resultSize > -1)
				lsv_content.setResultSize(resultSize);
			isPullUpRefresh = false;
		}
		
		if(isLoadMoreRefresh)
		{
			lsv_content.onLoadComplete();
			if(resultSize > -1)
				lsv_content.setResultSize(resultSize);
			isLoadMoreRefresh = false;
		}
	}
}
