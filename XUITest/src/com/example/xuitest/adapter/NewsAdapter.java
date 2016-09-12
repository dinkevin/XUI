package com.example.xuitest.adapter;

import com.dinkevin.xui.adapter.ViewHolderAdapter;
import com.example.xuitest.adapter.holder.NewsViewHolder;
import com.example.xuitest.model.News;
import android.content.Context;

public class NewsAdapter extends ViewHolderAdapter<News, NewsViewHolder>{

	public NewsAdapter(Context context) {
		super(context);
	}

	@Override
	protected NewsViewHolder createViewHolder() {
		return new NewsViewHolder(this);
	}
}
