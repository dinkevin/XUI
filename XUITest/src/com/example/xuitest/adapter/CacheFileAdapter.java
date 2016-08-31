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
		View view = inflateLayout(R.layout.xui_item_text_view);
		ViewFinder finder = new ViewFinder(view);
		TextView txt_title = finder.findViewById(R.id.txt_item_title);
		File file = getItem(position);
		txt_title.setText(file.getName());
		return view;
	}
}
