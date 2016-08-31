package com.dinkevin.xui.activity;

import com.dinkevin.xui.R;
import com.dinkevin.xui.util.Debuger;
import com.dinkevin.xui.view.HorizontalProgressBar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * 通用的用来展示网页的页面
 * @author chengpengfei
 *
 */
@SuppressLint("SetJavaScriptEnabled")
public class WebViewActivity extends AbstractActivity{
	
	/**
	 * 网页控件
	 */
	protected WebView web_view;
	
	/**
	 * 进度条控件
	 */
	protected HorizontalProgressBar progress_bar;
	
	/**
	 * 页面标题栏标题
	 */
	protected String headTitle;
	
	/**
	 * 网页地址
	 */
	protected String webSite;
	
	/**
	 * 标题栏标题
	 */
	public static final String HEADER_TITLE = "head_title";
	
	/**
	 * 网页地址
	 */
	public static final String WEB_SITE = "web_site";

	@Override
	protected void onCreate(Bundle savedInstance) {
		super.onCreate(savedInstance);
		
		img_headLeft.setImageResource(R.drawable.xui_action_back_white);
		web_view = (WebView)findViewById(R.id.xui_web_view);
		
		// 进度条
		progress_bar = (HorizontalProgressBar)findViewById(R.id.xui_progress_bar);
		progress_bar.setMaxProgress(100);
		progress_bar.setSelectedCircleColor(getResources().getColor(R.color.xui_text_blue_81ccea));
		progress_bar.setNormalCircleColor(getResources().getColor(R.color.xui_white));
		
		// 设置WebView
		WebSettings setting = web_view.getSettings();
		setting.setJavaScriptCanOpenWindowsAutomatically(true);
		setting.setJavaScriptEnabled(true);
		setting.setAppCacheEnabled(true);
		setting.setDatabaseEnabled(true);
		setting.setDomStorageEnabled(true);
		
		web_view.setWebViewClient(new MyWebViewClient());
		web_view.setWebChromeClient(new XUIWebChromeClient());
		
		web_view.setWebChromeClient(new XUIWebChromeClient());
		
		Intent data = getIntent();
		headTitle = data.getStringExtra(HEADER_TITLE);
		if(!TextUtils.isEmpty(headTitle)){
			setTitle(headTitle);
		}
		
		webSite = data.getStringExtra(WEB_SITE);
		loadUrl(webSite);
	}
	
	/**
	 * 载入指定网址
	 * @param site
	 */
	protected void loadUrl(String site){
		if(!TextUtils.isEmpty(site)){
			Debuger.d("load url:"+site);
			web_view.loadUrl(site);
		}
	}

	@Override
	protected int getContentLayout() {
		return R.layout.xui_web_view;
	}
	
	@Override
	public void onClick(View v) {
		if(v == img_headLeft){
			if(web_view.canGoBack()){
				web_view.goBack();
			}else{
				finish();
			}
		}
	}

	/**
	 * 网页地址过滤
	 * @param view
	 * @param url
	 * @return
	 */
	protected boolean shouldOverrideUrlLoading(WebView view,String url){
		return false;
	}
	
	protected class MyWebViewClient extends WebViewClient{

		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			Debuger.d("webview load url:"+url);
			return WebViewActivity.this.shouldOverrideUrlLoading(view, url);
		}
	}
	
	protected class XUIWebChromeClient extends WebChromeClient{

		@Override
		public void onReceivedTitle(WebView view, String title) {
			super.onReceivedTitle(view, title);
			if(TextUtils.isEmpty(WebViewActivity.this.headTitle)){
				WebViewActivity.this.setTitle(title);
			}
		}

		@Override
		public void onProgressChanged(WebView view, int newProgress) {
			super.onProgressChanged(view, newProgress);
			
			progress_bar.setProgressValue(newProgress);
			if(newProgress > 99)
				progress_bar.setVisibility(View.GONE);
		}
	}
}
