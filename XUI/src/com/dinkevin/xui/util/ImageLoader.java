package com.dinkevin.xui.util;

import java.io.File;

import com.dinkevin.xui.storage.CacheStorage;
import com.dinkevin.xui.storage.IStorageWriteListener;
import com.ztt.afinal.FinalBitmap;
import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

/**
 * 网络图片加载器 </br>
 * 在调用此方法中的接口前要一定要调用 {@link #initial(Context)} 进行初始化</br>
 * 建议在 Application 实现类中的 onCreate 方法中调用 {@link #initial(Context)}，{@link #initial(Context)} 调用一次即可。
 * @author chengpengfei
 */
public final class ImageLoader {

	private static FinalBitmap finalBitmap;

	private ImageLoader() {}

	/**
	 * 初始化，建议在 Application 的实现类中的 onCreate 中调用
	 * @param context
	 */
	public static void initial(Context context) {
		finalBitmap = FinalBitmap.create(context);
	}

	/**
	 * 获取 FinalBitmap 实例
	 * @return
	 */
	public static FinalBitmap getFinalBitmap() {
		return finalBitmap;
	}

	protected static CacheStorage storage = CacheStorage.getInstance();

	/**
	 * 显示图片
	 * @param img
	 * @param url 路径可以为网络路径，也可以为本地路径
	 */
	public static void display(final ImageView img, String url) {
		storage.put(url, new IStorageWriteListener() {
			
			@Override
			public void onWriteError(int errorCode, String errorMessage) {
				
			}
			
			@Override
			public void onWriteComplete(final String localFilePath) {
				
				// 在 UI 线程上更新图片显示
				ThreadUtil.runOnMainThread(new Runnable() {
					
					@Override
					public void run() {
						img.setImageURI(Uri.fromFile(new File(localFilePath)));
					}
				});
			}
		});
	}
}
