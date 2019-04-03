package com.huonilaifu.commons;


import com.huonilaifu.commons.string.StringUtils;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;




public class DateUtil {
	public static final String FULL_FORMAT_STR = "yyyy-MM-dd HH:mm:ss";
	public static final String SHORT_FORMAT_STR = "yyyy-MM-dd";
	/**
	 * <summary>
	 * 	判断年份是否是闰年，true为闰年，false为平年
	 * </summary>
	 * @author Xiongyan
	 * @return
	 * @Date 2015年8月6日
	 */
	public static boolean isLeapYear(Serializable year){
		Integer _year = Integer.parseInt(year.toString());
		if((_year % 400 == 0)||(_year % 4 == 0)&&(_year % 100 != 0 )){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * <summary>
	 * 	格式化日期到字符串。
	 * </summary>
	 * @param
	 * 		date   ：指定要格式转换的日期<br>
	 * 		format ：指定要转换的日期格式，如果指定为null，默认格式为：yyyy-MM-dd HH:mm:ss
	 * @author Xiongyan
	 * @return
	 * @Date 2015年8月6日
	 */
	public static String formatDateToString(Date date ,String format) {
		if (StringUtils.isEmpty(format))
			return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
		else
			return new SimpleDateFormat(format).format(date);
	}
	
	/**
	 * <summary>
	 * 	解析字符串到日期。
	 * </summary>
	 * @param dateStr
	 * @param format
	 * @return
	 * @throws ParseException 
	 */
	public static Date parseStringToDate(String dateStr ,String format) throws ParseException {
		if (StringUtils.isEmpty(format))
			return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateStr);
		else
			return new SimpleDateFormat(format).parse(dateStr);
	}
	
	/**
	 * <summary>
	 * 	解析字符串到日期。
	 * </summary>
	 * @param dateStr
	 * @param format
	 * @return
	 * @throws ParseException 
	 */
	public static Date parseString2Date(String dateStr ,String ...formats) throws ParseException {
		if (formats != null && formats.length > 0) {
			for (String format:formats) {
				try {
					return parseStringToDate(dateStr,format);
				}catch (ParseException ex) {
				}
			}
		} else {
			return parseStringToDate(dateStr,null);
		}
		throw new ParseException(org.apache.commons.lang3.StringUtils.join(formats," ") + "中没有符合的转换格式",1);
	}
	
	/**
	 * 获取日期当天开始时间   如:(2015-09-10 00:00:00)
	 * 
	 * @author peihuhu
	 * @date 2015年9月10日下午4:10:43
	 * @param 
	 * 		date   ：指定要格式转换的日期<br>
	 * 		format ：指定要转换的日期格式，如果指定为null，默认格式为：yyyy-MM-dd HH:mm:ss
	 * @return 
	 * @throws
	 */
	public static String getStartTime(Date date, String format) {
		date = getOperationTime(date, 0, 0, 0);
		String strDate = formatDateToString(date, format);
		return strDate;
	}
	
	/**
	 * 获取日期当天结束时间   如:(2015-09-10 23:59:59)
	 * 
	 * @author peihuhu
	 * @date 2015年9月10日下午3:59:47
	 * @param 
	 * 		date   ：指定要格式转换的日期<br>
	 * 		format ：指定要转换的日期格式，如果指定为null，默认格式为：yyyy-MM-dd HH:mm:ss
	 * @return 
	 * @return 
	 * @throws
	 */
	public static String getEndTime(Date date, String format) {
		date = getOperationTime(date, 23, 59, 59);
		String strDate = formatDateToString(date, format);
		return strDate;
	}
	
	/**
	 * 获取操作后的日期, 对小时、分、秒操作
	 * 
	 * @author peihuhu
	 * @date 2015年9月10日下午4:03:04
	 * @param 
	 * 		date	: 指定要操作的日期<br>
	 * 		hour	: 小时
	 * 		minute	: 分
	 * 		second	: 秒
	 * @return 
	 * @throws
	 */
	public static Date getOperationTime(Date date, int hour, int minute, int second) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, hour);
		calendar.set(Calendar.MINUTE, minute);
		calendar.set(Calendar.SECOND, second);
		date = calendar.getTime();
		return date;
	}
	/**
	 * 获取传入日期的周几 
	 * @param date 传入日期
	 * @param week 传入日期的周数(1-7)
	 * @return
	 */
	public static Date getWeek(Date date, int week) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int dayWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
		if (dayWeek == 0) {
			dayWeek = 7;
		}
		calendar.add(Calendar.DATE, -dayWeek + week);
		date = calendar.getTime();
		return date;
	}
	
	/**
	 * 得到本月的第一天   
	 * @param date
	 * @return
	 */
	public static Date getMonthFirstDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
		date = calendar.getTime();
		return date;
	}
	
	/**
	 * 得到本月的最后一天    
	 * @param date
	 * @return
	 */
	public static Date getMonthLastDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		date = calendar.getTime();
		return date;
	}
	
	/**
	 * 获取传入日期的所属年份的第一天
	 * @param date
	 * @return
	 */
	public static Date getYearFirstDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_YEAR, calendar.getActualMinimum(Calendar.DAY_OF_YEAR));
		date = calendar.getTime();
		return date;
	}
	
	/**
	 * 获取传入日期的所属年份的最一天
	 * @param date
	 * @return
	 */
	public static Date getYearLastDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_YEAR, calendar.getMaximum(Calendar.DAY_OF_YEAR) - 1);
		date = calendar.getTime();
		return date;
	}
	
	/**
	 * 计算两个日期之间相差的天数 
	 * 秒不在计算范围,只计算天数
	 * @author peihuhu
	 * @date 2015年9月26日下午5:36:43
	 * @param d1 开始时间
	 * @param d2 结束日期
	 * @return 
	 * @throws
	 */
	public static int getDayBetween(Date d1, Date d2) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(d1);
		int time1 = calendar.get(Calendar.DATE);
		calendar.setTime(d2);
		int time2 = calendar.get(Calendar.DATE);
		return time2 - time1;
	}
	
	/**
	 * 计算两个日期之间总天数 
	 * 
	 * @author peihuhu
	 * @date 2015年9月26日下午5:48:03
 	 * @param d1 开始时间
	 * @param d2 结束日期
	 * @return 
	 * @throws
	 */
	public static int getDaysBetween(Date d1, Date d2) {
		return getDayBetween(d1, d2) + 1;
	}
	
	public static Date str2Date(String strdate) {
		try {
			return new SimpleDateFormat("yyyy-MM-dd").parse(strdate);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
	public static void main(String[] args) {
		System.out.println(str2Date("1992-12-12"));
	}
	
	/**
	 * 对当前时间做加减计算
	 * 
	 * @author xiongyan
	 * @date 2015年9月26日下午5:48:03
 	 * @param type 时间类型  时、 分 、 秒
	 * @param time 加减的数值
	 * @return Date
	 * @throws
	 */
	public static Date timeCalendar(int type, int time) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(type, time);
		return calendar.getTime();
	}
	
	/**
	 * <div style="color:green;font-weight: bolder;">
	 * 	<h3>时间比较</h3>
	 * 	&emsp;&emsp;描述：比较当前时间与传递的时间参数的大小。若当前时间小于，返回false，否则返回true。
	 * </div>
	 * @author xiongyan
	 * @date 2015年12月2日下午1:29:58
	 * @param 
	 * @throws
	 * @return
	 */
	public static boolean comparedTime(Date date) {
		Date currentDate = new Date();
		if(currentDate.getTime() > date.getTime()){
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * <div style="color:green;font-weight: bolder;">
	 * 	<h3></h3>
	 * 	&emsp;&emsp;描述：将毫秒转换为秒
	 * </div>
	 * @date 2016年4月6日下午2:47:37
	 * @param 
	 * @throws
	 * @return
	 */
	public static Double ms2second(Serializable time) {
		return Double.parseDouble(String.valueOf(time)) / 1000;
	}
	
	/**
	 * <div style="color:green;font-weight: bolder;">
	 * 	<h3></h3>
	 * 	&emsp;&emsp;描述：将时期按照指定的格式转换为字符串形式。
	 * </div>
	 * @author xiongyan
	 * @date 2016年3月18日下午8:22:23
	 * @param 
	 * @throws
	 * @return
	 */
	public static String formatDate2String(Date date ,String format) {
		return new SimpleDateFormat(format).format(date);
	}

	/**
		计算现在的前day天开始时间
	 * @return
	 */
	public static Date recedeTime(int day){

			Date now = new Date();
			Calendar cal = Calendar.getInstance();
			cal.setTime(now);
			cal.add(Calendar.DAY_OF_MONTH,-day);
			Date time = cal.getTime();
			if(time!=null){
                time = getOperationTime(time, 0, 0, 0);
			}
        return time;
	}
}