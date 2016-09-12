package com.example.xuitest;

import java.util.List;

import com.dinkevin.xui.activity.AbstractActivity;
import com.dinkevin.xui.activity.WebViewActivity;
import com.dinkevin.xui.net.AsynHttpRequest;
import com.dinkevin.xui.net.AsynHttpRequest.IHttpClientRequestCallback;
import com.dinkevin.xui.net.exception.HttpRequestException;
import com.dinkevin.xui.util.Debuger;
import com.dinkevin.xui.util.JSONUtil;
import com.dinkevin.xui.util.StringUtil;
import com.dinkevin.xui.util.ThreadUtil;
import com.dinkevin.xui.util.ToastUtil;
import com.dinkevin.xui.view.PullRefreshListView;
import com.example.xuitest.adapter.NewsAdapter;
import com.example.xuitest.model.News;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

/**
 * 百度新闻列表页面
 * @author chengpengfei
 *
 */
public class NewsActivity extends AbstractActivity implements PullRefreshListView.OnRefreshListener,
		PullRefreshListView.OnLoadListener,
		IHttpClientRequestCallback{
	
	private PullRefreshListView lsv_news;
	private String newsBasePath = "http://m.news.baidu.com/news?tn=bdapibaiyue&t=newchosenlist&";
	private boolean refreshFlag = false;
	private boolean loadFlag = false;
	private NewsAdapter newsAdapter;
	private List<News> lastNewsCache;

	@Override
	protected void onCreate(Bundle savedInstance) {
		super.onCreate(savedInstance);
		
		// 初始化新闻列表，设置刷新与载入更多监听
		lsv_news = findViewInRootView(R.id.lsv_news);
		lsv_news.setOnRefreshListener(this);
		lsv_news.setOnLoadListener(this);
		
		newsAdapter = new NewsAdapter(this);
		lsv_news.setAdapter(newsAdapter);
		lsv_news.setPageSize(20);
		
		onRefresh();
		setTitle("新闻列表");
		
		// 新闻详情
		lsv_news.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				
				// 过滤处理头与尾的Item点击情况
				int location = position - 1;
				if(newsAdapter.getCount() < 1 || location == newsAdapter.getCount()) return;
				
				News news = newsAdapter.getItem(location);
				Intent intent = new Intent(NewsActivity.this,WebViewActivity.class);
				intent.putExtra(WebViewActivity.WEB_SITE, news.getUrl());
				startActivity(intent);
			}
		});
	}

	@Override
	protected int getContentLayout() {
		return R.layout.activity_baidu_news_list;
	}

	@Override
	public void onLoad() {
		loadFlag = true;
		requestNewsList();
	}

	@Override
	public void onRefresh() {
		refreshFlag = true;
		requestNewsList();
	}
	
	/**
	 * 请求新闻列表
	 */
	private void requestNewsList(){
		String action = newsBasePath + "&display_time=" + System.currentTimeMillis();
		AsynHttpRequest httpRequest = AsynHttpRequest.create();
		httpRequest.get(action,this);
	}
	
	/**
	 * 新闻请求结束回调
	 */
	private void onNewsRequestComplete(){
		
		// UI线程刷新界面显示
		ThreadUtil.runOnMainThread(new Runnable() {
			
			@Override
			public void run() {
				
				if(refreshFlag){
					lsv_news.onRefreshComplete();
					refreshFlag = false;
				}
				
				if(loadFlag){
					lsv_news.onLoadComplete();
					loadFlag = false;
				}
				
				newsAdapter.notifyDataSetChanged();
				lsv_news.setResultSize(lastNewsCache.size());
			}
		});
	}

	@Override
	public void onReceiveData(String data) {
		
		// 处理新闻 JSON 数据
		int errno = JSONUtil.getInt(data, "errno");
		if(errno != 0){
			ToastUtil.showShort("获取新闻列表失败");
			return;
		}
		
		String dataString = JSONUtil.getString(data, "data");
		if(StringUtil.isEmpty(dataString)) {
			Debuger.e("response no key(data)");
			return;
		}
		
		if(refreshFlag){
			newsAdapter.clearViewCache();
			newsAdapter.getDataSource().clear();
		}
		
		String newsString = JSONUtil.getString(dataString, "news");
		lastNewsCache = JSONUtil.parseToList(newsString, News.class);
		
		// PageSize 大小检测更新
		if(lastNewsCache.size() > lsv_news.getPageSize())
			lsv_news.setPageSize(lastNewsCache.size());
		
		newsAdapter.getDataSource().addAll(lastNewsCache);
		onNewsRequestComplete();
	}

	@Override
	public void onHttpClientRequestError(HttpRequestException e) {
		onNewsRequestComplete();
		ToastUtil.showShort(e.getMessage());
	}
}
