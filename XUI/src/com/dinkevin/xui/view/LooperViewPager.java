package com.dinkevin.xui.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

/**
 * 基于 android.support.v4.ViewPager 的循环切换 view 的控件。</br>
 * 除 ViewPager 类提供的手动切换，本类还提供自动切换接口
 * @author chengpengfei
 *
 */
public class LooperViewPager extends ViewPager{
	
	/**
	 * 循环切换时间间隔
	 */
	protected int looperInterval = 3000;
	
	public static final int ACTION_AUTO_SCROLL = 1;
	
	/**
	 * View 切换处理器
	 */
	@SuppressLint("HandlerLeak")
	protected Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			int position = getNextPosition();
			setCurrentItem(position, position != 0);
			
			Message message = obtainMessage();
			handler.sendMessageDelayed(message, looperInterval);
		}
	};

	public LooperViewPager(Context context) {
		super(context);
	}
	
	public LooperViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	/**
	 * 获取当前 View 的下一个 View 索引位置
	 * @return
	 */
	protected int getNextPosition(){
		int count = getChildCount();
		int position = getCurrentItem();
		position++;
		if(position > count - 1)
			position = 0;
		return position;
	}
	
	/**
	 * 开启自动滑动
	 */
	public void startAutoScroll(){
		stopAutoScroll();
		handler.sendEmptyMessage(ACTION_AUTO_SCROLL);
	}
	
	/**
	 * 停止自动滑动
	 */
	public void stopAutoScroll(){
		handler.removeCallbacksAndMessages(null);
	}
	
	/**
	 * 设置自动滑动时间间隔，单位为毫秒，默认为 3000 （3秒）
	 * @param interval 大于 0，且为 100 的整数倍的值
	 */
	public void setLooperInterval(int interval){
		if(interval > 0)
		{
			int t_temp = interval / 100;
			looperInterval = t_temp * 100;
			
			handler.sendEmptyMessage(ACTION_AUTO_SCROLL);
		}
	}
}
