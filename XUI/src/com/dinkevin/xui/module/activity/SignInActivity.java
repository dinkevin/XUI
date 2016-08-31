package com.dinkevin.xui.module.activity;

import com.dinkevin.xui.R;
import com.dinkevin.xui.activity.AbstractActivity;
import com.dinkevin.xui.module.CommonConstant;
import com.dinkevin.xui.util.ToastUtil;
import com.dinkevin.xui.util.XUIUtil;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * 登录页面
 * @author chengpengfei
 *
 */
public class SignInActivity extends AbstractActivity{
	
	/**
	 * 手机号输入框
	 */
	protected EditText edt_phoneNumber;
	
	/**
	 * 密码输入框
	 */
	protected EditText edt_password;
	
	/**
	 * 忘记密码按钮
	 */
	protected Button btn_forgetPassowrd;
	
	/**
	 * 提交按钮
	 */
	protected Button btn_submit;

	@Override
	protected void onCreate(Bundle savedInstance) {
		super.onCreate(savedInstance);
		
		View viewPhoneNumber = findViewById(R.id.xui_phone_number);
		edt_phoneNumber = (EditText)viewPhoneNumber.findViewById(R.id.xui_edt_input);
		edt_phoneNumber.setHint(R.string.xui_input_phone_number_hint);
		// 设置最大输入长度
		setMaxInputLength(edt_phoneNumber, CommonConstant.PHONE_NUMBER_LENGTH);
		
		View viewPassword  = findViewById(R.id.xui_password);
		edt_password = (EditText)viewPassword.findViewById(R.id.xui_edt_input);
		edt_password.setHint(R.string.xui_input_password_hint);
		// 设置最大输入长度
		setMaxInputLength(edt_password, CommonConstant.PASSWORD_MAX_LENGTH);
		
		btn_forgetPassowrd = (Button)viewPassword.findViewById(R.id.xui_btn_input_right);
		btn_forgetPassowrd.setOnClickListener(this);
		btn_forgetPassowrd.setText(R.string.xui_forget_password);
		
		btn_submit = (Button)findViewById(R.id.xui_submit);
		btn_submit.setOnClickListener(this);
		btn_submit.setText(R.string.xui_sign_in);
		
		// 设置 Activity 的标题与标题栏图片左按钮
		setTitle(R.string.xui_header_title_sign_in);
		img_headLeft.setImageResource(R.drawable.xui_action_back_white);
		
		// 隐藏标题栏右图片按钮
		img_headRight.setVisibility(View.GONE);
		
		// 设置标题栏右按钮文字
		btn_headRight.setText(R.string.xui_sign_up);	// 注册
		btn_headRight.setOnClickListener(this);
	}

	@Override
	protected int getContentLayout() {
		return R.layout.xui_activity_sign_in;
	}
	
	/**
	 * 用户输入检查
	 * @return true -> 输入正确
	 */
	protected boolean inputCheck(){
		String phoneNumber = edt_phoneNumber.getText().toString().trim();
		if(TextUtils.isEmpty(phoneNumber)){
			ToastUtil.showShort("手机号为空！");
			return false;
		}
		
		if(!XUIUtil.isMobileNumber(phoneNumber)){
			ToastUtil.showShort("手机号码格式不对!");
			return false;
		}
		
		String password = edt_password.getText().toString().trim();
		if(TextUtils.isEmpty(password)){
			ToastUtil.showShort("密码为空！");
			return false;
		}
		
		int passwordLength = password.length();
		if(passwordLength > CommonConstant.PASSWORD_MAX_LENGTH || passwordLength < CommonConstant.PASSWORD_MIN_LENGTH){
			ToastUtil.showShort("密码长度为 "+CommonConstant.PASSWORD_MIN_LENGTH + "~"+CommonConstant.PASSWORD_MAX_LENGTH+" 位！");
			return false;
		}
		
		return true;
	}
}
