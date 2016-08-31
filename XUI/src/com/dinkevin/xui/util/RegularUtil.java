package com.dinkevin.xui.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.text.TextUtils;

/**
 * 正则相关工具类</br>
 * 手机号码验证、
 * @author chengpengfei
 *
 */
public final class RegularUtil {

	private RegularUtil(){}
	
	/***
	 * 手机号码验证（严格）
	 * @param mobiles
	 * @return true 表示是手机号码；否则验证不通过
	 */
	public static boolean isMobileNumber(String mobiles) {
		if(TextUtils.isEmpty(mobiles)) return false;
		if(mobiles.length() != 11) return false;
		
		Pattern p = Pattern.compile("^1[3|4|5|6|7|8|9][0-9]\\d{4,8}$");
		Matcher m = p.matcher(mobiles);
		return m.matches();
	}
}
