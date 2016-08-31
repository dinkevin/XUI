package com.dinkevin.xui.module.activity;

import com.dinkevin.xui.R;
import com.dinkevin.xui.activity.AbstractActivity;
import com.dinkevin.xui.module.CommonConstant;
import com.dinkevin.xui.util.ToastUtil;
import com.dinkevin.xui.util.XUIUtil;
import com.dinkevin.xui.view.VerificationCode;
import com.dinkevin.xui.view.VerificationCode.OnCountListener;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * 重置密码页面
 * @author chengpengfei
 *
 */
public class ResetPasswordActivity extends AbstractActivity implements OnCountListener{
	
	/**
	 * 手机号输入框
	 */
	protected EditText edt_phoneNumber;
	
	/**
	 * 验证码输入框
	 */
	protected EditText edt_verificationCode;
	
	/**
	 * 新密码输入框
	 */
	protected EditText edt_newPassword;
	
	/**
	 * 确认密码输入框
	 */
	protected EditText edt_confirmPassword;
	
	/**
	 * 验证码按钮
	 */
	protected VerificationCode btn_verificationCode;
	
	/**
	 * 提交按钮
	 */
	protected Button btn_submit;
	
	@Override
	protected void onCreate(Bundle savedInstance) {
		super.onCreate(savedInstance);
		
		setTitle(R.string.xui_header_title_reset_password);
		img_headLeft.setImageResource(R.drawable.xui_action_back_white);
		
		View viewPhoneNumber = findViewById(R.id.xui_phone_number);
		edt_phoneNumber = (EditText)viewPhoneNumber.findViewById(R.id.xui_edt_input);
		edt_phoneNumber.setHint(R.string.xui_input_phone_number_hint);
		setMaxInputLength(edt_phoneNumber, CommonConstant.PHONE_NUMBER_LENGTH);
		
		View viewVerificationCode = findViewById(R.id.xui_verification_code);
		edt_verificationCode = (EditText)viewVerificationCode.findViewById(R.id.xui_edt_input);
		edt_verificationCode.setHint(R.string.xui_input_verification_code_hint);
		setMaxInputLength(edt_verificationCode, CommonConstant.VERIFICATION_CODE_LENGTH);
		
		btn_verificationCode = (VerificationCode)viewVerificationCode.findViewById(R.id.xui_btn_input_right);
		btn_verificationCode.setText(R.string.xui_request_verification_code);
		btn_verificationCode.setOnCountListener(this);
		
		View viewNewPassword = findViewById(R.id.xui_password);
		edt_newPassword = (EditText)viewNewPassword.findViewById(R.id.xui_edt_input);
		edt_newPassword.setHint(R.string.xui_input_new_password_hint);
		
		View viewConfirmPassword = findViewById(R.id.xui_confirm_password);
		edt_confirmPassword = (EditText)viewConfirmPassword.findViewById(R.id.xui_edt_input);
		edt_confirmPassword.setHint(R.string.xui_input_new_password_confirm_hint);
		
		btn_submit = (Button)findViewById(R.id.xui_submit);
		btn_submit.setText(R.string.xui_submit);
		btn_submit.setOnClickListener(this);
	}
	
	/**
	 * 用户输入检查
	 * @return true -> 输入正确
	 */
	protected boolean inputCheck(){
		String phoneNumber = edt_phoneNumber.getText().toString().trim();
		if(!TextUtils.isEmpty(phoneNumber)){
			ToastUtil.showShort("手机号为空！");
			return false;
		}
		
		if(!XUIUtil.isMobileNumber(phoneNumber)){
			ToastUtil.showShort("手机号格式不正确！");
			return false;
		}
		
		String verificationCode = edt_verificationCode.getText().toString().trim();
		if(TextUtils.isEmpty(verificationCode)){
			ToastUtil.showShort("验证码为空！");
			return false;
		}
		
		if(verificationCode.length() != CommonConstant.VERIFICATION_CODE_LENGTH){
			ToastUtil.showShort("验证码是 "+ CommonConstant.VERIFICATION_CODE_LENGTH + " 位！");
			return false;
		}
		
		String password = edt_newPassword.getText().toString().trim();
		if(TextUtils.isEmpty(password)){
			ToastUtil.showShort("密码为空！");
			return false;
		}
		
		int passwordLength = password.length();
		if(passwordLength > CommonConstant.PASSWORD_MAX_LENGTH || passwordLength < CommonConstant.PASSWORD_MIN_LENGTH){
			ToastUtil.showShort("密码长度为 "+CommonConstant.PASSWORD_MIN_LENGTH+"~"+CommonConstant.PASSWORD_MAX_LENGTH+" 位！");
			return false;
		}
		
		if(!password.equals(edt_confirmPassword.getText().toString().trim())){
			ToastUtil.showShort("两次密码不一致！");
			return false;
		}
		
		return true;
	}

	@Override
	protected int getContentLayout() {
		return R.layout.xui_activity_reset_password;
	}

	@Override
	public boolean onCountStart() {
		return false;
	}

	@Override
	public void onCountComplete() {
		
	}
}
