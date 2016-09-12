package com.example.xuitest.adapter.holder;

import java.util.List;

import com.dinkevin.xui.adapter.ViewHolder;
import com.dinkevin.xui.util.ImageLoader;
import com.dinkevin.xui.util.StringUtil;
import com.dinkevin.xui.util.TimeUtil;
import com.example.xuitest.R;
import com.example.xuitest.adapter.NewsAdapter;
import com.example.xuitest.model.ImageUrls;
import com.example.xuitest.model.News;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class NewsViewHolder extends ViewHolder<News> {

	private TextView txt_title, txt_site, txt_time;
	private ImageView img_delete;
	private ImageView img_picture;

	private NewsAdapter adapter;

	public NewsViewHolder(NewsAdapter adapter) {
		this.adapter = adapter;
	}

	@Override
	protected int getItemLayout() {
		return R.layout.item_baidu_news;
	}

	@Override
	protected void initWidgets() {
		txt_title = viewFinder.findViewById(R.id.txt_title);
		txt_site = viewFinder.findViewById(R.id.txt_site);
		txt_time = viewFinder.findViewById(R.id.txt_time);
		img_delete = viewFinder.findViewById(R.id.img_delete);
		img_picture = viewFinder.findViewById(R.id.img_picture);
	}

	@Override
	public void invalid() {

		News news = get();
		if(null == news) return;
		txt_title.setText(news.getTitle());
		txt_site.setText(news.getSite());

		// 格式化时间显示
		if(!StringUtil.isEmpty(news.getSourcets())){
			long pullTime = Long.parseLong(news.getSourcets());
			txt_time.setText(TimeUtil.formatToCommunityTime(pullTime));
		}

		// 绑定删除事件
		img_delete.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				// 根据唯一标识查询到指定的新闻并删除
				for (int i = 0; i < adapter.getDataSource().size(); i++) {
					News n = adapter.getDataSource().get(i);
					if (n.getNid().equals(get().getNid())) {
						adapter.getDataSource().remove(i);
						adapter.clearViewCache();
						adapter.notifyDataSetChanged();
						break;
					}
				}
			}
		});

		// 新闻图片
		List<ImageUrls> imageUrls = news.getImageurls();
		if (null != imageUrls && !StringUtil.isEmpty(imageUrls.get(0).getUrl())) {
			ImageLoader.display(img_picture, imageUrls.get(0).getUrl());
		}else{
			img_picture.setVisibility(View.GONE);
		}
	}
}
