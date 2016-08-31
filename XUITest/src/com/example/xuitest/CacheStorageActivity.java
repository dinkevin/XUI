package com.example.xuitest;

import java.io.File;
import com.dinkevin.xui.activity.AbstractActivity;
import com.dinkevin.xui.fragment.AbstractListViewFragment;
import com.dinkevin.xui.storage.CacheStorage;
import com.example.xuitest.adapter.CacheFileAdapter;
import android.os.Bundle;
import android.view.View;

/**
 * 缓存存储页面
 * @author chengpengfei
 */
public class CacheStorageActivity extends AbstractActivity{
	
	private CacheFileAdapter fileAdapter;
	
	/**
	 * 缓存文件列表 Fragment
	 */
	class ListViewFragment extends AbstractListViewFragment<File>{
		
		@Override
		protected void initialWidget() {
			super.initialWidget();
			
			view_head.setVisibility(View.GONE);		// 隐藏 Fragment 默认的标题
		}

		@Override
		protected void initialAdapter() {
			setAdapter(fileAdapter);
		}
	}
	private ListViewFragment fragment_cacheFileList;

	@Override
	protected void onCreate(Bundle savedInstance) {
		super.onCreate(savedInstance);
		
		setTitle("缓存存储");
		
		// 初始化标题化右上角按钮菜单
		btn_headRight.setText("清空");
		btn_headRight.setOnClickListener(this);
		
		fileAdapter = new CacheFileAdapter(this);
		initialCacheFileList();
		
		fragment_cacheFileList = new ListViewFragment();
		getFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment_cacheFileList).commit();
	}
	
	/**
	 * 初始化缓存文件列表显示
	 */
	private void initialCacheFileList(){
		
		fileAdapter.getDataSource().clear();
		File[] fileList = CacheStorage.getInstance().list();
		if(null != fileList){
			for(File file : fileList){
				fileAdapter.getDataSource().add(file);
			}
		}
		fileAdapter.notifyDataSetChanged();
	}

	@Override
	protected int getContentLayout() {
		return R.layout.activity_cache_storage;
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		
		if(v.getId() == R.id.btn_head_right){
			CacheStorage.getInstance().clear();
			initialCacheFileList();
		}
	}
}
