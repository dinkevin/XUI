package com.dinkevin.xui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

/**
 * 可动态添加子控件且控件容器高度动态变化的 LinearLayout，子控件可以自动换行。
 * @author chengpengfei
 */
public class AutoLinearLayout extends LinearLayout {
	private int linearLayoutWidth; 		// 宽
	private int linearLayoutHeight; 	// 高
	private int childCount;				// 子控件数量
	
	private final int HORIZONTAIL_MARGIN = 10;  // 子控件左右距离
	private final int VERTICAL_MARGIN = 10; 	// 子控件上下距离

	public AutoLinearLayout(Context context) {
		super(context);
	}

	public AutoLinearLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		
		childCount = getChildCount(); 

		linearLayoutWidth = MeasureSpec.getSize(widthMeasureSpec);
		linearLayoutHeight = childCount > 0 ? getChildAt(0).getMeasuredHeight() : 0;

		int length = 0;		// 控件排布水平方向的长度
		for (int i = 0; i < childCount; i++) {
			View child = getChildAt(i);
			child.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);

			int childWidth = child.getMeasuredWidth();
			int childHeight = child.getMeasuredHeight();
			
			// 换行判断，控件高度更新
			if (length + childWidth >= linearLayoutWidth) {
				length = 0;
				linearLayoutHeight += childHeight + VERTICAL_MARGIN;
			}

			length += (childWidth + HORIZONTAIL_MARGIN);
		}

		// 设置自身宽度
		setMeasuredDimension(linearLayoutWidth, linearLayoutHeight);
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {

		int x = 0; 							// 子控件横坐标
		int y = 0; 							// 子控件纵坐标
		int childLengthTotal = r - l; 		// 子控件总有效长度

		for (int i = 0; i < childCount; i++) {
			View childView = getChildAt(i);
			int width = childView.getMeasuredWidth();
			int height = childView.getMeasuredHeight();

			// 换行控制
			if (x + width > childLengthTotal) {
				x = 0;
				y += height + VERTICAL_MARGIN;
			}

			childView.layout(x, y, x + width, y + height);
			x += width + HORIZONTAIL_MARGIN;
		}
	}
}