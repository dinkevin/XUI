package com.dinkevin.xui.view;

import java.util.ArrayList;
import java.util.List;

import com.dinkevin.xui.R;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

/**
 * 底部弹出菜单控件
 * @author chengpengfei
 *
 */
@SuppressLint("InflateParams")
public class BottomPopupMenu extends PopupMenu{

	protected LayoutInflater layoutInflater;
	
	/**
	 * 构造器
	 * @param locationView
	 */
	public BottomPopupMenu(View locationView) {
		layoutInflater = LayoutInflater.from(locationView.getContext());
		this.contentView = layoutInflater.inflate(R.layout.xui_view_popup_menu, null);
		this.locationView = locationView;
		this.direction = Direction.BUTTOM;
		initial();
	}
	
	protected List<String> menuList = new ArrayList<String>();
	
	/**
	 * 设置菜单列表，取消不需要放入列表默认会在列表最后显示取消
	 * @param menu
	 */
	public void setMenu(List<String> menu){
		
		LinearLayout parent = (LinearLayout)contentView;
		parent.removeAllViews();
		
		menuList = menu;
		
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		params.height = 115;
		params.topMargin = 30;
		
		for(String m : menu){
			Button btn = (Button)layoutInflater.inflate(R.layout.xui_view_popup_menu_item, null);
			btn.setText(m);
			parent.addView(btn,params);
			btn.setOnClickListener(listener);
		}
		
		LinearLayout.LayoutParams cancelParams = new LinearLayout.LayoutParams(params);
		cancelParams.topMargin = 60;
		Button cancel = (Button)layoutInflater.inflate(R.layout.xui_view_popup_menu_item, null);
		cancel.setText(R.string.xui_picture_menu_cancel);
		parent.addView(cancel,cancelParams);
		cancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				window.dismiss();
			}
		});
	}
	
	/**
	 * 菜单选择监听
	 */
	public interface OnMenuSelectedListner{
		
		/**
		 * 选中菜单回调
		 * @param index 索引
		 * @param title 标题
		 */
		void onSelectedMenu(int index,String title);
	}
	
	protected OnMenuSelectedListner selectedListener;
	
	/**
	 * 设置菜单选中监听
	 * @param listener
	 */
	public void setOnMenuSelectedListener(OnMenuSelectedListner listener){
		selectedListener = listener;
	}
	
	/**
	 * 菜单单击事件监听
	 */
	protected View.OnClickListener listener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			Button btn = (Button)v;
			String menu = btn.getText().toString().trim();
			for(int i = 0;i < menuList.size();i++){
				if(menu.equals(menuList.get(i))){
					window.dismiss();
					if(selectedListener != null){
						selectedListener.onSelectedMenu(i, menu);
					}
				}
			}
		}
	};
}
