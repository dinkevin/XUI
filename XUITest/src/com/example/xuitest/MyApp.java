package com.example.xuitest;

import com.dinkevin.xui.storage.CacheStorage;
import com.dinkevin.xui.storage.DataStorage;
import com.dinkevin.xui.util.ImageLoader;
import com.dinkevin.xui.util.ThreadUtil;
import android.app.Application;

public class MyApp extends Application{

	@Override
	public void onCreate() {
		super.onCreate();
		
		// 初始化
		ThreadUtil.initial(this);
		ImageLoader.initial(this);
		CacheStorage.getInstance().initial(Constants.CACHE_DIR);
		DataStorage.getInstance().initial(Constants.DATA_DIR);
	}
}
