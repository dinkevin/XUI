package com.dinkevin.xui.util;

import java.math.BigDecimal;

/**
 * 数据大小计算工具类,例如 byte 单位换算到 MB 单位
 * @author chengpengfei
 *
 */
public final class SizeUtil {

	private SizeUtil(){}
	
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
