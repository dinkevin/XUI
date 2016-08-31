package com.dinkevin.xui.util;

import android.widget.Toast;

/**
 * Toast 工具类</br>
 * 在调用此方法中的接口前要一定要调用对 {@link ThreadUtil} 进行初始化</br>
 * @author chengpengfei
 */
public class ToastUtil {
	
	private ToastUtil(){}
	
	/**
	 * 调用系统的 Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
	 * @param stringId
	 */
	public static void showShort(int stringId){
		
		String message = ThreadUtil.getContext().getString(stringId);
		showShort(message);
	}
	
	/**
	 * 调用系统的 Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
	 * @param message
	 */
	public static void showShort(final String message){
		
		if(ThreadUtil.isOnMainThread()) 
			Toast.makeText(ThreadUtil.getContext(), message, Toast.LENGTH_SHORT).show();
		else
			ThreadUtil.runOnMainThread(new Runnable() {
				@Override
				public void run() {
					Toast.makeText(ThreadUtil.getContext(), message, Toast.LENGTH_SHORT).show();
				}
			});
	}
	
	/**
	 * 调用系统的 Toast.makeText(context, message, Toast.LENGTH_LONG).show();
	 * @param stringId
	 */
	public static void showLong(int stringId){
		String message = ThreadUtil.getContext().getString(stringId);
		showLong(message);
	}
	
	/**
	 * 调用系统的 Toast.makeText(context, message, Toast.LENGTH_LONG).show();
	 * @param message
	 */
	public static void showLong(final String message){
		if(ThreadUtil.isOnMainThread())
			Toast.makeText(ThreadUtil.getContext(), message, Toast.LENGTH_LONG).show();
		else
			ThreadUtil.runOnMainThread(new Runnable() {
				@Override
				public void run() {
					Toast.makeText(ThreadUtil.getContext(), message, Toast.LENGTH_LONG).show();
				}
			});
	}
}
