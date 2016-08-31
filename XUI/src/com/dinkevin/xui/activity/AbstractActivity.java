package com.dinkevin.xui.activity;

import com.dinkevin.xui.R;
import com.dinkevin.xui.util.ViewFinder;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

/**
 * 抽象 Activity 基类
 * @author chengpengfei
 */
public abstract class AbstractActivity extends Activity implements OnClickListener{
	
	/**
	 * 标题栏
	 */
	protected View view_head;
	
	/**
	 * 标题栏标题
	 */
	protected TextView txt_headTitle;						
	
	/**
	 * 标题栏左右按钮
	 */
	protected ImageView img_headLeft,img_headRight;	
	
	/**
	 * 标题栏右按钮
	 */
	protected Button btn_headRight;
	
	/**
	 * 根 view
	 */
	protected LinearLayout view_root;
	
	protected ViewFinder viewFinder;
	
	protected LayoutInflater layoutInflater;

	@Override
	protected void onCreate(Bundle savedInstance) {
		super.onCreate(savedInstance);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.xui_activity_container); 		// 设置界面布局，所有子类界面布局的容器
		layoutInflater = LayoutInflater.from(this);	
		view_root = (LinearLayout)findViewById(R.id.xui_container);		// 根 view
		viewFinder = new ViewFinder(view_root);
		
		// 读取当前页面内容 ViewId
		int t_contentViewId = getContentLayout();
		if(t_contentViewId > 0)
		{
			// 将内容 view 添加到根 view
			View t_contentView = layoutInflater.inflate(t_contentViewId, null);
			LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
			view_root.addView(t_contentView,params);
			
			// 初始化标题栏
			view_head = viewFinder.findViewById(R.id.view_head);
			txt_headTitle = viewFinder.findViewById(R.id.txt_head_title);
			img_headLeft = viewFinder.findViewById(R.id.img_head_left);
			img_headRight = viewFinder.findViewById(R.id.img_head_right);
			btn_headRight = viewFinder.findViewById(R.id.btn_head_right);
			
			// 清空标题
			if(txt_headTitle != null)
				txt_headTitle.setText("");
			
			// 默认的返回
			if(img_headLeft != null)
				img_headLeft.setOnClickListener(this);
		}
	}
	
	/**
	 * 获取当前内容页面资源Id
	 * @return
	 */
	protected abstract int getContentLayout();
	
	/**
	 * 在 {@link #view_root} 中查找资源 Id 对应的 View
	 * @param viewResId 资源 Id
	 * @return 
	 */
	protected <T extends View> T  findViewInRootView(int viewResId){
		return viewFinder.findViewById(viewResId);
	}

	/**
	 * 设置当前页面标题
	 */
	@Override
	public void setTitle(CharSequence title) {
		if(txt_headTitle != null)
			txt_headTitle.setText(title);
	}

	/**
	 * 设置当前页面标题
	 */
	@Override
	public void setTitle(int titleId) {
		if(txt_headTitle != null)
			txt_headTitle.setText(titleId);
	}
	
	/**
	 * 设置输入框的最大输入长度
	 * @param edt
	 * @param maxLength
	 */
	protected void setMaxInputLength(EditText edt,int maxLength){
		if(edt == null || maxLength < 1) return;
		edt.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});
	}

	@Override
	public void onClick(View v) {
		if(v == img_headLeft){
			finish();
		}
	}
}
