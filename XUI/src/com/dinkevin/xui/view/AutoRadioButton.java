package com.dinkevin.xui.view;

import com.dinkevin.xui.R;
import com.dinkevin.xui.util.ResoureFinder;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.RadioButton;
/**
 * 自定义的 RadioButton 按纽
 * @author chengpengfei
 */
public class AutoRadioButton extends RadioButton{
	
	private static int DEFAULT_CHECKED_TEXT_COLOR = 0XFFFC00D1;
	private static int DEFAULT_UNCHECKED_TEXT_COLOR = 0xFFd3d6da;
	private static int DEFAULT_DRAWABLE_NOTICE_ID;
	private static int DEFAULT_DRAWABLE_POSITION = 0;
	
	private int mCheckedTextColor;				// Checked 状态下显示文字颜色
	private int mUncheckedTextColor;			// UnChecked 状态下显示文字颜色
	
	private Drawable mUnCheckedDrawable;		// UnChecked 状态下显示图片资源
	private Drawable mCheckedDrawable;			// Checked 状态下显示图片资源
	private int mNoticeDrawableId;				// 通知图片资源
	
	private boolean mHasNotice = false;				// 是否有通知状态值
	private Drawable mCompoundDrawableCache = null;
	private Bitmap mCacheBitmap;
	private int mDrawablePosition;				// 图片位置。例如：0 -> top;1 -> right;2 -> bottom;3 -> left

	public AutoRadioButton(Context context) {
		this(context,null);
	}
	
	public AutoRadioButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		ResoureFinder.initial(context);
		DEFAULT_DRAWABLE_NOTICE_ID = R.drawable.xui_radio_button_notice_icon;
		
		final TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.xui_radio_button_style);
		mCheckedTextColor = attributes.getColor(
				R.styleable.xui_radio_button_style_checked_text_color,
				DEFAULT_CHECKED_TEXT_COLOR);
		
		mUncheckedTextColor = attributes.getColor(
				R.styleable.xui_radio_button_style_unchecked_text_color,
				DEFAULT_UNCHECKED_TEXT_COLOR);
		
		int id = attributes.getResourceId(
				R.styleable.xui_radio_button_style_unchecked_drawable,
				0);
		if(id != 0) mUnCheckedDrawable = getResources().getDrawable(id);
		
		id = attributes.getResourceId(
				R.styleable.xui_radio_button_style_checked_drawable,
				0);
		if(id != 0) mCheckedDrawable = getResources().getDrawable(id);
		
		mNoticeDrawableId = attributes.getResourceId(
				R.styleable.xui_radio_button_style_notice_drawable,
				DEFAULT_DRAWABLE_NOTICE_ID);

		mDrawablePosition = attributes.getInt(R.styleable.xui_radio_button_style_drawable_position, DEFAULT_DRAWABLE_POSITION);
		
		attributes.recycle();
		
		setButtonDrawable(null);	// 清除单选按钮前小圆圈
		
		requestInvalideLayout();
	}
	
	/**
	 * 请求刷新当前页面
	 */
	protected void requestInvalideLayout()
	{
		setTextColor(isChecked() ? mCheckedTextColor : mUncheckedTextColor);
		
		if(mCheckedDrawable == null || mUnCheckedDrawable == null) return;
		
		// 释放图片资源
		if(mCompoundDrawableCache != null)
		{
			mCompoundDrawableCache.setCallback(null);
			mCompoundDrawableCache = null;
			mCacheBitmap.recycle();
		}
		
		// 绘制原 RadioButton 状态图
		Drawable temp = isChecked() ? mCheckedDrawable : mUnCheckedDrawable;
		int width =  temp.getIntrinsicHeight();
		int height = temp.getIntrinsicHeight();
		
		mCacheBitmap = Bitmap.createBitmap(width, height, Config.ARGB_8888);
		Canvas canvas = new Canvas(mCacheBitmap);
		temp.setBounds(0,0,width,height);
		temp.draw(canvas);
		
		// 通知状态检测
		if(mHasNotice && mNoticeDrawableId != 0)
		{
			Bitmap noticeBitmap = BitmapFactory.decodeResource(getResources(), mNoticeDrawableId);
			Paint paint = new Paint();
			canvas.drawBitmap(noticeBitmap, width - noticeBitmap.getWidth(), 0, paint);
			noticeBitmap.recycle();
		}
		
		// 设置待显示的图片位置
		mCompoundDrawableCache = new BitmapDrawable(getResources(),mCacheBitmap);
		setCompoundDrawablesWithIntrinsicBounds(mDrawablePosition == 3 ? mCompoundDrawableCache : null,
						mDrawablePosition == 0? mCompoundDrawableCache:null,
						mDrawablePosition == 1? mCompoundDrawableCache:null,
						mDrawablePosition == 2? mCompoundDrawableCache:null);
	}
	
	/**
	 * 重写 checked 的处理方法
	 */
	@Override
	public void setChecked(boolean checked) 
	{
		super.setChecked(checked);
		requestInvalideLayout();
	}
	
	/**
	 * 显示更新图标
	 * @param state
	 */
	public void setNotice(boolean state)
	{
		mHasNotice = state;
		requestInvalideLayout();
	}
}
