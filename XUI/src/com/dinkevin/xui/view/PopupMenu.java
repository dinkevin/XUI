package com.dinkevin.xui.view;

import com.dinkevin.xui.R;
import com.dinkevin.xui.util.ViewFinder;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;

/**
 * 弹出菜单
 * 
 * @author chengpengfei
 */
public class PopupMenu {

	protected PopupWindow window;
	protected View contentView;
	protected Direction direction = Direction.BUTTOM; // 默认从底部弹出
	protected ViewFinder viewFinder;

	protected PopupMenu() {}

	/**
	 * 构造器
	 * 
	 * @param contentView
	 *            菜单内容
	 */
	public PopupMenu(View contentView) {
		this(contentView, Direction.BUTTOM);
	}

	/**
	 * 构造器
	 * 
	 * @param contentView
	 *            菜单内容
	 * @param direction
	 *            菜单弹出方向
	 */
	public PopupMenu(View contentView, Direction direction) {
		this.contentView = contentView;
		this.direction = direction;
		initial();
	}

	protected void initial() {
		window = new PopupWindow(contentView, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);

		if (Direction.LEFT == direction) {
			window.setAnimationStyle(R.style.animation_left_fade);
		} else if (Direction.RIGHT == direction) {
			window.setAnimationStyle(R.style.animation_right_fade);
		} else if(Direction.BUTTOM == direction){
			window.setAnimationStyle(R.style.animation_bottom_fade);
		}

		// 菜单背景色
		ColorDrawable drawable = new ColorDrawable(Color.TRANSPARENT);
		window.setBackgroundDrawable(drawable);
		window.setOutsideTouchable(true);
		window.setOnDismissListener(new PopupWindow.OnDismissListener() {
			@Override
			public void onDismiss() {
				setBackgroundAlpha(1f);
			}
		});
	}
	
	/**
	 * 设置外部是否可点击
	 * @param touchable
	 */
	public void setOutsideTouchable(boolean touchable){
		window.setOutsideTouchable(touchable);
	}
	
	/**
	 * 设置是否可以获取焦点
	 * @param focusable
	 */
	public void setFocusable(boolean focusable){
		window.setFocusable(focusable);
	}

	/**
	 * 设置背景透明度
	 * 
	 * @param alpha
	 */
	private void setBackgroundAlpha(float alpha) {
		// 设置背景半透明
		Activity activity = (Activity) contentView.getContext();
		WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
		lp.alpha = alpha;
		activity.getWindow().setAttributes(lp);
	}

	protected View locationView;

	/**
	 * 设置容器 View
	 * 
	 * @param locationView
	 */
	public void setLocationView(View locationView) {
		this.locationView = locationView;
	}

	/**
	 * 弹出菜单
	 */
	public void show() {
		if (window.isShowing())
			return;

		if (this.locationView != null) {
			switch (direction) {
			case LEFT:
				window.showAtLocation(this.locationView, Gravity.LEFT, 0, 0);
				break;

			case RIGHT:
				window.showAtLocation(this.locationView, Gravity.RIGHT, 0, 0);

			case BUTTOM:
				window.showAtLocation(this.locationView, Gravity.BOTTOM, 0, 0);
				break;
				
			case CENTER:
				window.showAtLocation(this.locationView, Gravity.CENTER, 0, 0);
			default:
				break;
			}

			setBackgroundAlpha(0.5f);
		}
	}

	/**
	 * 关闭菜单
	 */
	public void dismiss() {
		window.dismiss();
	}

	/**
	 * 菜单弹出方向
	 */
	public enum Direction {

		/**
		 * 从底部
		 */
		BUTTOM,

		/**
		 * 从左边
		 */
		LEFT,

		/**
		 * 从右边
		 */
		RIGHT,
		
		/**
		 * 居中显示
		 */
		CENTER
	}
}
