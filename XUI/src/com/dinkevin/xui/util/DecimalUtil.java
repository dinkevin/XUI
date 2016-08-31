package com.dinkevin.xui.util;

import java.math.BigDecimal;

/**
 * 精确运算辅助类，精确到小数点后两位
 * @author chengpengfei
 */
public final class DecimalUtil {

	private DecimalUtil(){}
	
	/**
	 * 加，默认是精确到小数后两位
	 * @param a 被除数
	 * @param b 除数
	 * @return 精确到小数点后两位的 double 数
	 */
	public static double add(double a,double b){
		BigDecimal numberA = new BigDecimal(a);
		BigDecimal numberB = new BigDecimal(b);
		BigDecimal result = numberA.add(numberB).setScale(2);
		return result.doubleValue();
	}
	
	/**
	 * 加，默认是精确到小数后两位
	 * @param a 被除数
	 * @param b 除数
	 * @return 精确到小数点后两位的 double 数
	 */
	public static double add(long a,double b){
		BigDecimal numberA = new BigDecimal(a);
		BigDecimal numberB = new BigDecimal(b);
		BigDecimal result = numberA.add(numberB).setScale(2);
		return result.doubleValue();
	}
	
	/**
	 * 减，默认是精确到小数后两位
	 * @param a 被除数
	 * @param b 除数
	 * @return 精确到小数点后两位的 double 数
	 */
	public static double subtract(double a,double b){
		BigDecimal numberA = new BigDecimal(a);
		BigDecimal numberB = new BigDecimal(b);
		BigDecimal result = numberA.subtract(numberB).setScale(2);
		return result.doubleValue();
	}
	
	/**
	 * 减，默认是精确到小数后两位
	 * @param a 被除数
	 * @param b 除数
	 * @return 精确到小数点后两位的 double 数
	 */
	public static double subtract(double a,long b){
		BigDecimal numberA = new BigDecimal(a);
		BigDecimal numberB = new BigDecimal(b);
		BigDecimal result = numberA.subtract(numberB).setScale(2);
		return result.doubleValue();
	}
	
	/**
	 * 减，默认是精确到小数后两位
	 * @param a 被除数
	 * @param b 除数
	 * @return 精确到小数点后两位的 double 数
	 */
	public static double subtract(long a,double b){
		BigDecimal numberA = new BigDecimal(a);
		BigDecimal numberB = new BigDecimal(b);
		BigDecimal result = numberA.subtract(numberB).setScale(2);
		return result.doubleValue();
	}
	
	/**
	 * 除，默认是精确到小数后两位
	 * @param a 被除数
	 * @param b 除数
	 * @return 精确到小数点后两位的 double 数
	 */
	public static double divide(long a,long b){
		BigDecimal numberA = new BigDecimal(a);
		BigDecimal numberB = new BigDecimal(b);
		BigDecimal result = numberA.divide(numberB,2,BigDecimal.ROUND_HALF_UP);
		return result.doubleValue();
	}
	
	/**
	 * 除，默认是精确到小数后两位
	 * @param a 被除数
	 * @param b 除数
	 * @return 精确到小数点后两位的 double 数
	 */
	public static double divide(long a,double b){
		BigDecimal numberA = new BigDecimal(a);
		BigDecimal numberB = new BigDecimal(b);
		BigDecimal result = numberA.divide(numberB,2,BigDecimal.ROUND_HALF_UP);
		return result.doubleValue();
	}
	
	/**
	 * 除，默认是精确到小数后两位
	 * @param a 被除数
	 * @param b 除数
	 * @return 精确到小数点后两位的 double 数
	 */
	public static double divide(double a,long b){
		BigDecimal numberA = new BigDecimal(a);
		BigDecimal numberB = new BigDecimal(b);
		BigDecimal result = numberA.divide(numberB,2,BigDecimal.ROUND_HALF_UP);
		return result.doubleValue();
	}
	
	/**
	 * 除，默认是精确到小数后两位
	 * @param a 被除数
	 * @param b 除数
	 * @return 精确到小数点后两位的 double 数
	 */
	public static double divide(double a,double b){
		BigDecimal numberA = new BigDecimal(a);
		BigDecimal numberB = new BigDecimal(b);
		BigDecimal result = numberA.divide(numberB,2,BigDecimal.ROUND_HALF_UP);
		return result.doubleValue();
	}
	
	/**
	 * 乘，默认是精确到小数后两位
	 * @param a
	 * @param b
	 * @return 精确到小数点后两位的 double 数
	 */
	public static double multiply(long a,double b){
		BigDecimal numberA = new BigDecimal(a);
		BigDecimal numberB = new BigDecimal(b);
		BigDecimal result = numberA.multiply(numberB).setScale(2);
		return result.doubleValue();
	}
	
	/**
	 * 乘，默认是精确到小数后两位
	 * @param a
	 * @param b
	 * @return 精确到小数点后两位的 double 数
	 */
	public static double multiply(double a,double b){
		BigDecimal numberA = new BigDecimal(a);
		BigDecimal numberB = new BigDecimal(b);
		BigDecimal result = numberA.multiply(numberB).setScale(2);
		return result.doubleValue();
	}
}
