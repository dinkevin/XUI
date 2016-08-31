package com.dinkevin.xui.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.annotation.SuppressLint;

/**
 * 时间处理辅助工具类
 * @author chengpengfei
 */
@SuppressLint("SimpleDateFormat")
public class TimeUtil {

	private TimeUtil(){}
	
	/**
	 * 标准的时间格式：yyyy-MM-dd HH:mm:ss
	 */
	public static final SimpleDateFormat FORMAT_STANDARD = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	/**
	 * 时间戳 格式：yyyyMMddHHmmsss
	 */
	public static final SimpleDateFormat FORMAT_STAMP = new SimpleDateFormat("yyyyMMddHHmmsss");
	
	/**
	 * 日期格式 格式：yyyy-MM-dd
	 */
	public static final SimpleDateFormat FORMAT_DATE = new SimpleDateFormat("yyyy-MM-dd");
	
	/**
	 * 日期格式 格式：MM-dd
	 */
	public static final SimpleDateFormat FORMAT_1 = new SimpleDateFormat("MM-dd");
	
	/**
	 * 日期格式 格式：MM-dd HH:mm
	 */
	public static final SimpleDateFormat FORMAT_2 = new SimpleDateFormat("MM-dd HH:mm");
	
	/**
	 * 格式化时间
	 * @param time
	 * @param format
	 * @return
	 */
	public static String formatTime(long time,SimpleDateFormat format){
		return format.format(time);
	}
	
	/**
	 * 格式到社区时间方式显示
	 * @param time
	 * @return
	 */
	public static String formatToCommunityTime(long time){
		Date now = new Date();
		Calendar calNow = Calendar.getInstance();
		int dayNow = calNow.get(Calendar.DAY_OF_YEAR);
		
		Calendar calTime = Calendar.getInstance();
		calTime.setTimeInMillis(time);
		int dayTime = calTime.get(Calendar.DAY_OF_YEAR);
		
		long diff = now.getTime() - time;
		if(diff < 0) return "时间转换错误：传入参数比现在还以后呢？";
		long minute = diff / (1000 * 60);
		if(minute == 0) return "刚刚";
		if(minute < 60) return minute + "分钟前";
		
		long hour = minute / 60;
		if(Math.abs(dayNow - dayTime) == 1 && hour > 10) return "昨天";
		if(hour < 24)
		{
			return hour + "小时前";
		}
		
		long day = hour / 24;
		
		if(Math.abs(dayNow - dayTime) == day + 1)
			day++;
		return day + "天前";
	}
	
	/**
	 * 格式化新闻时间格式
	 * @param time
	 * @return
	 */
	public static String formatToNewsTime(long time){
		if(time < 0) return "传入参数不正确";
		
		Date now = new Date();
		Calendar calNow = Calendar.getInstance();
		int dayNow = calNow.get(Calendar.DAY_OF_YEAR);
		
		Calendar calTime = Calendar.getInstance();
		calTime.setTimeInMillis(time);
		int dayTime = calTime.get(Calendar.DAY_OF_YEAR);
		
		long diff = now.getTime() - time;
		if(diff < 0) return "时间转换错误：传入参数比现在还以后呢？";
		long minute = diff / (1000 * 60);
		if(minute == 0) return "刚刚";
		if(minute < 60) return minute + "分钟前";
		
		long hour = minute / 60;
		if(Math.abs(dayNow - dayTime) == 1 && hour > 10) return "昨天";
		if(hour < 24){
			return hour + "小时前";
		}
		
		int month = calTime.get(Calendar.MONTH) + 1;
		int day = calTime.get(Calendar.DAY_OF_MONTH);
		
		return ((month < 10) ? "0" + month:Integer.toString(month)) + "-" + (day < 10 ? "0" + day:Integer.toString(day));
	}
	
	/**
	 * 将字符串格式时间转化到毫秒格式
	 * @param time
	 * @return
	 */
	public static long getTime(String time){
		try {
			return FORMAT_STANDARD.parse(time).getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	public static String getCurrentTime(){
		return FORMAT_STANDARD.format(new Date());
	}
	
	/**
	 * 获取当前日期 格式为 yyyy-MM-dd
	 * @return
	 */
	public static String getCurrentDate(){
		return FORMAT_DATE.format(new Date());
	}
	
	/**
	 * 获取唯一标识时间戳
	 * @return 例如：2017070512253536
	 */
	public static String getCurrentTimeStamp(){
		return FORMAT_STAMP.format(new Date());
	}
	
	/**
	 * 转化到日期
	 * @param dateString
	 * @return
	 */
	public static Date parseToDate(String dateString){
		try {
			return FORMAT_DATE.parse(dateString);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 日期对比
	 * @param start
	 * @param end
	 * @return
	 */
	public static int DateCompare(Date start,Date end){
		Calendar calStart = Calendar.getInstance();
		calStart.setTime(start);
		calStart.set(Calendar.HOUR_OF_DAY,0);
		calStart.set(Calendar.MINUTE,0);
		calStart.set(Calendar.SECOND,0);
		
		Calendar calEnd = Calendar.getInstance();
		calEnd.setTime(end);
		calEnd.set(Calendar.HOUR_OF_DAY,0);
		calEnd.set(Calendar.MINUTE,0);
		calEnd.set(Calendar.SECOND,0);
		return calStart.compareTo(calEnd);
	}
}
