package com.dinkevin.xui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListAdapter;

/**
 * 图片表格控件，类似微信朋友圈图片展现方式的自定义控件
 * @author chengpengfei
 */
public class FeedImageGridView extends android.widget.GridView{
	
	private int length;

	public FeedImageGridView(Context context) {
		super(context);
	}

	public FeedImageGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public FeedImageGridView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,MeasureSpec.AT_MOST);
		
		length = MeasureSpec.getSize(widthMeasureSpec);
		int height = MeasureSpec.getSize(heightMeasureSpec);
		
		setMeasuredDimension(length, height);
		super.onMeasure(widthMeasureSpec, expandSpec);
		
		// 通知数据源当前容器宽度信息
		if(length > 0)
		{
			ListAdapter adapter = getAdapter();
			if(adapter != null)
			{
				if(adapter instanceof FeedImageAdapter)
				{
					FeedImageAdapter zoneAdapter = (FeedImageAdapter)adapter;
					
					if(adapter.getCount() == 4 && zoneAdapter.getWatchFour()){
						setNumColumns(2);
					}
					
					zoneAdapter.setContainer(this);
					zoneAdapter.notifyDataSetChanged();
				}
			}
		}
	}
	
	/**
	 * 获取容器的边长
	 * @return
	 */
	public int getLength(){
		return length; 
	}
}
