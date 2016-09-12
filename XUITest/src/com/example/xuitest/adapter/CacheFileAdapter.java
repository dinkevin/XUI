package com.example.xuitest.adapter;

import java.io.File;

import com.dinkevin.xui.adapter.AbstractAdapter;
import com.dinkevin.xui.util.ViewFinder;
import com.example.xuitest.R;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

/**
 * 缓存文件列表数据源适配器
 * @author chengpengfei
 */
public class CacheFileAdapter extends AbstractAdapter<File>{

	public CacheFileAdapter(Context context) {
		super(context);
	}

	@Override
	protected View createItemView(int position) {
		View view = inflateLayout(R.layout.item_cache_storage);
		ViewFinder finder = new ViewFinder(view);
		TextView txt_title = finder.findViewById(R.id.txt_cache_title);
		TextView txt_size = finder.findViewById(R.id.txt_cache_size);
		File file = getItem(position);
		txt_title.setText(file.getName());
		txt_size.setText(file.length() + "byte");
		return view;
	}
}
