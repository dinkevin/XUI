package com.dinkevin.xui.util;

import java.math.BigDecimal;

/**
 * 精确运算辅助类
 * @author chengpengfei
 */
public final class DecimalUtil {

	private DecimalUtil(){}
	
	/**
	 * 除
	 * @param a 被除数
	 * @param b 除数
	 * @return
	 */
	public static double divide(long a,long b){
		BigDecimal numberA = new BigDecimal(a);
		BigDecimal numberB = new BigDecimal(b);
		BigDecimal result = numberA.divide(numberB,2,BigDecimal.ROUND_HALF_UP);
		return result.doubleValue();
	}
}
