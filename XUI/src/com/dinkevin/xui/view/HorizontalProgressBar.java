package com.dinkevin.xui.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * Created by chengpengfei on 15/11/24.
 * 自定义横向进度条控件 </br>
 */
@SuppressLint("DrawAllocation")
public class HorizontalProgressBar extends LinearLayout{

    private int mWidth;
    private int mHeight;
    private static final int DEFAULT_WIDTH = 400;
    private static final int DEFAULT_HEIGHT = 200;

    private int mMaxValue = 100;
    private int mMinValue = 0;
    private int mProgressValue = 1;

    private int mSelectedColor = 0xFF53C4FC;
    private int mNormalColor = 0xFFFFFFFF;

    public HorizontalProgressBar(Context context) {
        this(context, null);
    }

    public HorizontalProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        mWidth = getDefaultSize(DEFAULT_WIDTH, widthMeasureSpec);
        mHeight = getDefaultSize(DEFAULT_HEIGHT,heightMeasureSpec);
    }

    /**
     * 设置正常进度条颜色
     * @param color
     */
    public void setNormalCircleColor(int color){
        mNormalColor = color;
    }

    /**
     * 设置选中进度条颜色
     * @param color
     */
    public void setSelectedCircleColor(int color){
        mSelectedColor = color;
    }

    /**
     * 设置进度条最大值
     * @param max_progress 大于 1 的值
     */
    public void setMaxProgress(int max_progress){
        if(max_progress > 1) mMaxValue = max_progress;
    }
    
    /**
     * 设置进度值
     * @param value
     */
    public void setProgressValue(int value)
    {
    	if(value >= mMinValue && value <= mMaxValue)
    	{
    		mProgressValue = value;
    		invalidate();
    	}
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if(mWidth > 0 && mHeight > 0)
        {
            Paint paint = new Paint();
            paint.setColor(mSelectedColor);
            paint.setStyle(Paint.Style.FILL);

            canvas.drawColor(mNormalColor);
            float progress = (float)mProgressValue / (float)mMaxValue * mWidth;
            canvas.drawRect(0, 0, progress, mHeight, paint);
        }
    }
}