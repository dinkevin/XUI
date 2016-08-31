package com.dinkevin.xui.activity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.dinkevin.xui.R;
import com.dinkevin.xui.adapter.ViewPagerAdapter;
import com.dinkevin.xui.storage.CacheStorage;
import com.dinkevin.xui.storage.IStorageWriteListener;
import com.dinkevin.xui.util.ThreadUtil;
import com.dinkevin.xui.util.ViewFinder;
import com.dinkevin.xui.view.LooperViewPager;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

/**
 * 图片浏览页面，在 Intent 中需要将图片路径数据源装进 {@value #PICTURE_SOURCE} 格式为 String[],</br>
 * 缩略图（可选参数）装进 {@link #PICTURE_THUMBNAIL} 格式为 Bitmap，</br>
 * 当前显示的索引位置 {@link #SOURCE_INDEX} int
 * @author chengpengfei
 *
 */
@SuppressLint("InflateParams")
public class ImageViewerActivity extends AbstractActivity{
	
	private String[] source;
	
	/**
	 * 图片数据源传递关键字
	 */
	public static final String PICTURE_SOURCE = "source";
	
	private int index = 0;
	
	/**
	 * 图片数据源索引
	 */
	public static final String SOURCE_INDEX = "index";
	
	/**
	 * 缩略图关键字
	 */
	public static final String  PICTURE_THUMBNAIL = "thumbnail";
	
	private LooperViewPager view_pager;
	
	/**
	 * 缩略图
	 */
	private Bitmap thumbnail;
	
	private List<View> view_source = new ArrayList<View>();
	private ViewPagerAdapter view_adapter;
	private CacheStorage storage = CacheStorage.getInstance();

	@Override
	protected void onCreate(Bundle savedInstance) {
		super.onCreate(savedInstance);
		
		setTitle("");
		
		view_pager = viewFinder.findViewById(R.id.xui_pager);
		
		thumbnail = getIntent().getParcelableExtra(PICTURE_THUMBNAIL);
		source = getIntent().getStringArrayExtra(PICTURE_SOURCE);
		index = getIntent().getIntExtra(SOURCE_INDEX, 0);
		if(source != null && source.length > 0){
			if(index > source.length - 1) index = source.length - 1;
			
			for(int i = 0;i < source.length;i++)
			{
				View view = layoutInflater.inflate(R.layout.xui_view_image_viewer_image, null);
				ViewFinder finder = new ViewFinder(view);
				final ImageView img_picture = finder.findViewById(R.id.img_picture);
				final ProgressBar prb_loading = finder.findViewById(R.id.prb_load);
				final View view_error = finder.findViewById(R.id.view_error);
				view_source.add(view);
				
				if(thumbnail != null){
					img_picture.setImageBitmap(thumbnail);
				}
				
				// 缓存网络图片到本地
				storage.put(source[i], new IStorageWriteListener() {
					
					@Override
					public void onWriteError(int errorCode, String errorMessage) {
						
						ThreadUtil.runOnMainThread(new Runnable() {
							
							@Override
							public void run() {
								prb_loading.setVisibility(View.GONE);
								view_error.setVisibility(View.VISIBLE);
							}
						});
					}
					
					@Override
					public void onWriteComplete(final String localFilePath) {
						
						ThreadUtil.runOnMainThread(new Runnable() {
							
							@Override
							public void run() {
								prb_loading.setVisibility(View.GONE);
								img_picture.setImageURI(Uri.fromFile(new File(localFilePath)));
							}
						});
					}
				});
			}
			
			view_adapter = new ViewPagerAdapter(view_source);
			view_pager.setAdapter(view_adapter);
			view_pager.setCurrentItem(index);
		}
	}

	@Override
	protected int getContentLayout() {
		return R.layout.xui_activity_image_viewer;
	}
}
