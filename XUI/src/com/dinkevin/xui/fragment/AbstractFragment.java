package com.dinkevin.xui.fragment;

import com.dinkevin.xui.R;
import com.dinkevin.xui.util.ViewFinder;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

/**
 * Fragment 抽象基类
 * @author chengpengfei
 *
 */
public abstract class AbstractFragment extends Fragment{

	/**
	 * 根 View
	 */
	protected View view_root;
	
	/**
	 * 标题栏
	 */
	protected View view_head;
	
	/**
	 * 标题
	 */
	protected TextView txt_headTitle;
	
	/**
	 * 标题栏左边按钮
	 */
	protected ImageView img_headLeft;
	
	/**
	 * 标题栏右边按钮
	 */
	protected ImageView img_headRight;
	
	/**
	 * 标题栏右按钮
	 */
	protected Button btn_headRight;
	
	/**
	 * 资源映射
	 */
	protected LayoutInflater layoutInflater;
	
	/**
	 * 控件资源初始化标识
	 */
	protected boolean widgetInitial = false;
	
	/**
	 * View 查询器
	 */
	protected ViewFinder viewFinder = null;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		
		// 如果需要保存之前 Fragment 的显示状态，则返回之前状态的 View
		if(view_root != null && needSaveInstanceState()) {
			ViewParent parent = view_root.getParent();
			if(parent != null) ((ViewGroup)parent).removeView(view_root);
			return view_root;
		}
		
		int fragmetnLayout = getFragmetLayout();
		if(fragmetnLayout < 1) return null;
		view_root = inflater.inflate(fragmetnLayout, null);
		
		// 重置 Fragment 布局，点满屏幕内容区
		view_root.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		
		// 初始化标题标题栏控件
		view_head = view_root.findViewById(R.id.view_head);
		if(view_head != null)
		{
			img_headLeft = (ImageView)view_head.findViewById(R.id.img_head_left);
			img_headRight = (ImageView)view_head.findViewById(R.id.img_head_right);
			txt_headTitle = (TextView)view_head.findViewById(R.id.txt_head_title);
			btn_headRight = (Button)view_head.findViewById(R.id.btn_head_right);
		}
		
		layoutInflater = inflater;
		viewFinder = new ViewFinder(view_root);
		return view_root;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		if(widgetInitial && needSaveInstanceState()) {
			return;
		}
		
		initialWidget();
		widgetInitial = true;
	}


	/**
	 * 获取布局文件资源Id
	 * @return
	 */
	protected abstract int getFragmetLayout();
	
	/**
	 * 初始化控件资源，此方法默认会在 onActivityCreated 后被调用
	 */
	protected void initialWidget(){}
	
	/**
	 * 是否保存当前页面状态，由子类决定。
	 * @return true -> 需要保存当前 Fragment 显示状态；false->不需要保存当前 Fragment 显示状态
	 */
	protected boolean needSaveInstanceState(){
		return false;
	}
}
