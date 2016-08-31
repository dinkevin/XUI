package com.dinkevin.xui.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * 网络相关工具类
 * @author chengpengfei
 */
public final class NetworkUtil {

	private NetworkUtil(){}
	
	/**
	 * 查网络连接状态
	 * @return true -> 当前网络可用；否则表示不可用
	 */
	public static boolean isNetworkAvailable() {
		NetworkInfo info = getNetworkInfo();
		return (info != null && info.isConnected());
	}
	
	/**
	 * 获取当前连接网络数据
	 * @return 网络信息
	 */
	public static NetworkInfo getNetworkInfo() {
		
		Context context = ThreadUtil.getContext();
		if (null == context) {
			Debuger.e(ThreadUtil.class.getName(),"need initial");
			return null;
		}

		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = cm.getActiveNetworkInfo();
		return info;
	}
}
