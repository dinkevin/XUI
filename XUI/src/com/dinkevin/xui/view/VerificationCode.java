package com.dinkevin.xui.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.Button;

/**
 * 自定义验证码控件</br>
 * 点击控件后，启动计时器，计时结束后才可以再次触发点击事件</br>
 * 只有调用 {@link #setOnCountListener(OnCountListener)} 设置计时监听后，点击控件才可以启动计时
 * @author chengpengfei
 *
 */
@SuppressLint("HandlerLeak")
public class VerificationCode extends Button {
	
	/**
	 * 最大计时数值，单位为秒
	 */
	protected int maxCount = 30;
	
	/**
	 * 最小计时数值，单位为秒
	 */
	protected int minCount = 1;
	
	/**
	 * 单位时间间隔
	 */
	protected int interval = 1000;
	
	/**
	 * 当前计时
	 */
	protected int count = 0;
	
	/**
	 * 原始控件文字
	 */
	protected String text;
	
	/**
	 * 界面更新处理
	 */
	protected Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			
			if(count > 0)
			{
				setText(count+" S");
				count--;
				Message message = handler.obtainMessage();
				handler.sendMessageDelayed(message, interval);
			}
			else
			{
				setText(text);
				if(countListener != null)
					countListener.onCountComplete();
			}
		}
	};

	public VerificationCode(Context context) {
		super(context, null);
	}

	public VerificationCode(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public VerificationCode(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}
	
	/**
	 * 设置最大计时数值，单位为秒，默认为 30
	 * @param max
	 */
	public void setMaxCount(int max){
		maxCount = max > 0 ? max : maxCount;
	}
	
	/**
	 * 获取当前最大计时值
	 * @return
	 */
	public int getMaxCount(){
		return maxCount;
	}
	
	/**
	 * 设置最小计时数值，单位为秒，默认为 0
	 * @param min
	 */
	public void setMinCount(int min){
		min = min > 0 && min < maxCount ? min:min;
	}
	
	/**
	 * 获取当前最小计时值
	 * @return
	 */
	public int getMinCount(){
		return minCount;
	}
	
	/**
	 * 开始计时
	 */
	protected void startCount(){
		if(count == 0 && countListener != null && countListener.onCountStart())
		{
			text = getText().toString();
			count = maxCount;
			handler.sendEmptyMessage(1);
		}
	}
	
	public boolean performClick(){
		return super.performClick();
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		performClick();
		if(event.getAction() == MotionEvent.ACTION_UP){
			startCount();
		}
		return super.onTouchEvent(event);
	}

	/**
	 * 停止计时
	 */
	public void stopCount(){
		count = 0;
		handler.removeCallbacksAndMessages(null);
		setText(text);
	}
	
	/**
	 * 计时监听
	 * @author chengpengfei
	 */
	public interface OnCountListener{
		
		/**
		 * 计时开始
		 * @return true -> 表示计时开始, false -> 表示不处理计时
		 */
		boolean onCountStart();
		
		void onCountComplete();
	}
	
	protected OnCountListener countListener;
	
	/**
	 * 设置计时监听
	 * @param listener
	 */
	public void setOnCountListener(OnCountListener listener){
		countListener = listener;
	}
}
