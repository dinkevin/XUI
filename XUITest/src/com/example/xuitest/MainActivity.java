package com.example.xuitest;

import com.dinkevin.xui.activity.AbstractActivity;
import com.dinkevin.xui.activity.ImageViewerActivity;
import com.dinkevin.xui.activity.WebViewActivity;
import com.dinkevin.xui.util.ToastUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AbstractActivity {
	
	Button btn_webView,btn_imageView,btn_dataStorage,btn_cacheStorage;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// 设置标题
		setTitle("首页");
		
		// 设置右上角菜单
		btn_headRight.setText("设置");
		btn_headRight.setOnClickListener(this);
		
		// 获取控件有两种方案可以选择，下面展示两种方案
		btn_webView = viewFinder.findViewById(R.id.btn_web_view);
		btn_imageView = viewFinder.findViewById(R.id.btn_image_view);
		
		btn_dataStorage = findViewInRootView(R.id.btn_data_storage);
		btn_cacheStorage = findViewInRootView(R.id.btn_cache_storage);
		
		// AbstractActivity 已经实现 View.OnClickListener
		btn_webView.setOnClickListener(this);
		btn_imageView.setOnClickListener(this);
		btn_dataStorage.setOnClickListener(this);
		btn_cacheStorage.setOnClickListener(this);
		
		viewFinder.findViewById(R.id.btn_baidu_news).setOnClickListener(this);
	}

	@Override
	protected int getContentLayout() {
		return R.layout.activity_main;
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);		// 如果去掉这个，需要自己处理默认的返回事件
		
		String url;
		Intent intent;
		switch (v.getId()) {
		// 右上角菜单
		case R.id.btn_head_right:
			ToastUtil.showShort("你点击了设置……");
			break;
			
		// 打开网页
		case R.id.btn_web_view:
			url = "http://www.baidu.com";
			intent = new Intent(this,WebViewActivity.class);
//			intent.putExtra(WebViewActivity.HEADER_TITLE, "网页");	// 如果不传标题,则显示网页标题
			intent.putExtra(WebViewActivity.WEB_SITE, url);
			startActivity(intent);
			break;
			
		// 显示网络图片
		case R.id.btn_image_view:
			// 网络图片
			String[] urls = new String[]{
					"http://tupian.enterdesk.com/2013/mxy/12/10/15/3.jpg",
					"http://img.taopic.com/uploads/allimg/140326/235113-1403260I33562.jpg",
					"http://www.wallcoo.com/nature/Summer_Fantasy_Landscapes/wallpapers/1680x1050/Summer_Fantasy_landscape_by_photo_manipulation_19315669.jpg"
			};
			intent = new Intent(this,ImageViewerActivity.class);
			intent.putExtra(ImageViewerActivity.PICTURE_SOURCE, urls);
//			intent.putExtra(ImageViewerActivity.SOURCE_INDEX, 1);	// 默认显示第几张，urls 中的索引值
			startActivity(intent);
			break;
			
		// 缓存数据
		case R.id.btn_cache_storage:
			intent = new Intent(this,CacheStorageActivity.class);
			startActivity(intent);
			break;
			
		// 加解密数据
		case R.id.btn_data_storage:
			
			break;
			
		// 新闻列表
		case R.id.btn_baidu_news:
			startActivity(new Intent(this,NewsActivity.class));
			break;
		}
	}
}
