package com.dinkevin.xui.view;

import java.util.List;

import com.dinkevin.xui.R;
import com.dinkevin.xui.adapter.AbstractAdapter;
import com.ztt.afinal.FinalBitmap;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * 图片表格控件数据源
 * @author chengpengfei
 */
@SuppressLint("InflateParams")
public class FeedImageAdapter extends AbstractAdapter<String> {

	private FinalBitmap bitmapLoader; 				// 网络图片加载器
	private Context context;
	private LayoutInflater inflater;
	private FeedImageGridView gridView;				 // 表格容器
	private final int DEFAULT_LENGTH = 800; 		 // 默认表格宽度
	private final int DEFAULT_NUMBER_COLUMN = 3;	 // 默认表格列娄
	private final int DEFAULT_MAX_PICTURE = 9;		 // 默认最多显示图片个数
	
	private boolean watchFour = true;				 // 如果是四张图片则分两行且每行两张显示

	public FeedImageAdapter(Context context, List<String> data) {
		super(context,data);
		this.bitmapLoader = FinalBitmap.create(context);
		this.context = context;
		this.inflater = LayoutInflater.from(this.context);
	}
	
	/**
	 * 设置是否关注四张图片的情况
	 * @param watch
	 */
	public void setWatchFour(boolean watch){
		watchFour  = watch;
	}
	
	public boolean getWatchFour(){
		return watchFour;
	}

	@Override
	public int getCount() {
		int count = data.size();
		
		// 如果不喜欢4张图片时分两行且每行2张显示的方式，可以把这里处理程序注释掉
		if(count == 4)
			count++;
		
		if(count > DEFAULT_MAX_PICTURE)
			count = DEFAULT_MAX_PICTURE;
		return  count;
	}

	@Override
	public String getItem(int position) {
		// 如果不喜欢4张图片时分两行且每行2张显示的方式，可以把这里处理程序注释掉
		if(data.size() == 4)
		{
			if(position == 2) return null;
			if(position > 2) position--;
		}
		
		if(position > DEFAULT_MAX_PICTURE - 1)
			position = DEFAULT_MAX_PICTURE - 1;
		
		return super.getItem(position);
	}

	/**
	 * 更新容器
	 * 
	 * @param gridView
	 */
	protected void setContainer(FeedImageGridView gridView) {
		this.gridView = gridView;
	}

	class ViewHolder {
		ImageView image;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder holder;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.xui_item_image_grid_view, null);
			holder = new ViewHolder();
			holder.image = (ImageView) convertView.findViewById(R.id.xui_img_zone_image);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		int length = DEFAULT_LENGTH;
		if(gridView != null && gridView.getLength() > 0)
			length = gridView.getLength();

		int columnLength = length / DEFAULT_NUMBER_COLUMN;
		holder.image.getLayoutParams().width = columnLength;
		holder.image.getLayoutParams().height = columnLength;

		// 如果不喜欢4张图片时分两行且每行2张显示的方式，可以把这里处理程序注释掉
		if(watchFour)
		{
			if(data.size() == 4){
				if(position == 2)
					return convertView;
			
				if(position > 2)
					position--;
			}
		}
		
		bitmapLoader.display(holder.image, data.get(position));
		return convertView;
	}

	@Override
	protected View createItemView(int position) {
		return null;
	}
}
