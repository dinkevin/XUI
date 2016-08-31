package com.dinkevin.xui.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.dinkevin.xui.module.BaseModel;
import com.dinkevin.xui.module.ModelComparator;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;

/**
 * 综合的工具类
 * @author chengpengfei
 *
 */
public final class XUIUtil {

	private XUIUtil() {}

	/***
	 * 手机号码验证（严格）
	 * @param mobiles
	 * @return
	 */
	public static boolean isMobileNumber(String mobiles) {
		if(TextUtils.isEmpty(mobiles)) return false;
		if(mobiles.length() != 11) return false;
		
		Pattern p = Pattern.compile("^1[3|4|5|6|7|8|9][0-9]\\d{4,8}$");
		Matcher m = p.matcher(mobiles);
		return m.matches();
	}
	
	/**
	 * md5加密 返回32位加密的结果
	 * 
	 * @param src
	 * @return
	 * @see 
	 */
	public static String md5Encode(String src) {
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		try {
			md.update(src.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		byte b[] = md.digest();
		int i;
		StringBuffer buf = new StringBuffer("");
		for (int offset = 0; offset < b.length; offset++) {
			i = b[offset];
			if (i < 0)
				i += 256;
			if (i < 16)
				buf.append("0");
			buf.append(Integer.toHexString(i));
		}
		return buf.toString();
	}

	/**
	 * 查网络连接状态
	 * @param context
	 * @return
	 */
	public static boolean isNetworkAvailable(Context context) {
		NetworkInfo info = getNetworkInfo(context);
		return (info != null && info.isConnected());
	}
	
	/**
	 * 
	 * @param context
	 * @return
	 */
	public static NetworkInfo getNetworkInfo(Context context) {
		if (null == context) {
			return null;
		}

		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = cm.getActiveNetworkInfo();
		return info;
	}
	
	/**
	 * 签名生成算法
	 * @param HashMap<String,String> params 请求参数集，所有参数必须已转换为字符串类型
	 * @param String secret 签名密钥
	 * @return 签名
	 * @throws IOException
	 */
	@SuppressLint("NewApi") 
	public static String getSignature(HashMap<String,String> params, String secret) throws IOException
	{
		// 先将参数以其参数名的字典序升序进行排序
		Map<String, String> sortedParams = new TreeMap<String, String>(params);
		Set<Entry<String, String>> entrys = sortedParams.entrySet();
	 
		// 遍历排序后的字典，将所有参数按"key=value"格式拼接在一起
		StringBuilder basestring = new StringBuilder();
		for (Entry<String, String> param : entrys) {
			basestring.append(param.getKey()).append("=").append(param.getValue());
		}
		basestring.append(secret);
		System.out.println("加密后的字符串"+basestring);
		// 使用MD5对待签名串求签
		byte[] bytes = null;
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			bytes = md5.digest(basestring.toString().getBytes("UTF-8"));
		} catch (GeneralSecurityException ex) {
			throw new IOException(ex);
		}
	 
		// 将MD5输出的二进制结果转换为小写的十六进制
		StringBuilder sign = new StringBuilder();
		for (int i = 0; i < bytes.length; i++) {
			String hex = Integer.toHexString(bytes[i] & 0xFF);
			if (hex.length() == 1) {
				sign.append("0");
			}
			sign.append(hex);
		}
		return sign.toString();
	}
	
	/**
	 * 计算页索引
	 * @param numb 已有数量
	 * @return
	 */
	public static int getPageIndex(int numb){
		if (numb < 0) return 1;
		int pageSize = 10;
		int remainder = numb % pageSize;
		int index = numb / pageSize;
		int pageIndex = remainder > 0 ? index + 2 : index + 1;
		return pageIndex;
	}
	
	/**
	 * 排序
	 * @param data
	 * @return
	 */
	public static void sort(List<? extends BaseModel> data){
		if(data != null)
		{
			ModelComparator comparator = new ModelComparator();
			Collections.sort(data,comparator);
		}
	}
	
	/**
	 * 将 byte 转化到 MB 显示
	 * @param size 单位为byte表示的大小
	 * @return
	 */
	public static String byteSizetoMBSize(long size){
		if(size < 0){
			Debuger.e("byteSize 大小需大于0",size);
			return null;
		}
		
		BigDecimal unit = new BigDecimal(1024);
		BigDecimal byteSize = new BigDecimal(size);
		BigDecimal KBSize = byteSize.divide(unit);
		BigDecimal MBSize = KBSize.divide(unit);
		return Double.toString(MBSize.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
	}
}
