package com.face.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;


/**
 * 日期操作类
 * @author Jason Xie 2015-11-9 18:22:26
 *
 */
public class DateUtil {

	private static final TimeZone timeZone = TimeZone.getTimeZone("GMT+8");
	public static final String PATTERN_YYYY = "yyyy";
	public static final String PATTERN_MM = "MM";
	public static final String PATTERN_DD = "dd";
	public static final String PATTERN_HH_MM_SS = "HH:mm:ss";
	public static final String PATTERN_HH_MM = "HH:mm";
	public static final String PATTERN_HH = "HH";
	public static final String PATTERN_mm = "mm";
	public static final String PATTERN_SS = "ss";
	public static final String PATTERN_YYYY_MM_DD = "yyyy-MM-dd";
	public static final String PATTERN_YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
	public static final String PATTERN_YYYY_MM_DD_HHMMSS = "yyyy-MM-dd HHmmss";
	public static final String ZH_PATTERN_YYYY_MM_DD_HH_MM = "yyyy年MM月dd日 HH时mm分";
	public static final String ZH_PATTERN_YYYY_MM_DD = "yyyy年MM月dd日";

	/**
	 * 获取当前年
	 * 
	 * @return Integer yyyy
	 */
	public static Integer getCurrentYear() {
		Calendar calendar = Calendar.getInstance(timeZone);
		return calendar.get(Calendar.YEAR);
	}

	/**
	 * 获取当前月，月份从0开始
	 * 
	 * @return Integer MM
	 */
	public static Integer getCurrentMonth() {
		Calendar calendar = Calendar.getInstance(timeZone);
		return calendar.get(Calendar.MONTH) + 1;
	}

	/**
	 * 获取当天时间
	 * 
	 * @return Date java.util.Date
	 */
	public static Date getNowDateTime() {
		Calendar calendar = Calendar.getInstance(timeZone);
		return calendar.getTime();
	}
	
	/**
	 * 获取当天时间
	 * 
	 * @param dateformat String日期格式
	 * @return String 
	 */
	public static String getNowDateTime(String dateformat) {
		SimpleDateFormat format = new SimpleDateFormat(dateformat);
		return format.format(getNowDateTime().getTime());
	}

	
	/**
	 * 获取当天时间
	 * 
	 * @return java.sql.Timestamp
	 */
	public static Timestamp getNowTimestamp() {
		Calendar calendar = Calendar.getInstance(timeZone);
		return new Timestamp(calendar.getTimeInMillis());
	}

	/**
	 * 加减当前时间
	 * 
	 * @param year
	 *            int
	 * @param month
	 *            int
	 * @param day
	 *            int
	 * @param isAdd
	 *            boolean
	 * @return Date
	 */
	public static Date addNowDateTime(int yearNum, int monthNum, int dayNum,
			boolean isAdd) {
		Calendar calendar = Calendar.getInstance(timeZone);
		if (isAdd) {
			if (yearNum > 0) {
				calendar.add(Calendar.YEAR, yearNum);
			}
			if (monthNum > 0) {
				calendar.add(Calendar.MONTH, monthNum);
			}
			if (dayNum > 0) {
				calendar.add(Calendar.DATE, dayNum);
			}
		} else {
			if (yearNum > 0) {
				calendar.add(Calendar.YEAR, -yearNum);
			}
			if (monthNum > 0) {
				calendar.add(Calendar.MONTH, -monthNum);
			}
			if (dayNum > 0) {
				calendar.add(Calendar.DATE, -dayNum);
			}
		}
		return calendar.getTime();
	}

	/**
	 * 获取当前天
	 * 
	 * @return int
	 */
	public static int getCurrentDay() {
		Calendar calendar = Calendar.getInstance(timeZone);
		return calendar.get(Calendar.DATE);
	}

	/**
	 * 获取当前小时
	 * 
	 * @return int
	 */
	public static int getCurrentHour() {
		Calendar calendar = Calendar.getInstance(timeZone);
		return calendar.get(Calendar.HOUR_OF_DAY);
	}

	/**
	 * 获取当前分钟
	 * 
	 * @return int
	 */
	public static int getCurrentMinute() {
		Calendar calendar = Calendar.getInstance(timeZone);
		return calendar.get(Calendar.MINUTE);
	}

	/**
	 * 获得下周星期一的日期
	 * 
	 * @return java.util.Date
	 */
	public static Date getNextMonday() {
		Calendar calendar = Calendar.getInstance(timeZone);
		System.out.println(calendar.get(Calendar.DAY_OF_WEEK));
		// 获得今天是一周的第几天,因为按中国礼拜一作为第一天所以这里减1，每星期7天，获得周一日期需再加1
		int dayOfWeek = 7 - (calendar.get(Calendar.DAY_OF_WEEK) - 1) + 1;
		return addNowDateTime(0, 0, dayOfWeek, true);
	}

	/**
	 * 获取当天时间
	 * 
	 * @return String yyyy-MM-dd HH:mm:ss
	 */
	public static String getNowTime() {
		return getNowDateTime(PATTERN_YYYY_MM_DD_HH_MM_SS);
	}

	/**
	 * 获取当天星期几
	 * 
	 * @return int
	 */
	public static int getWeek() {
		Calendar calendar = Calendar.getInstance(timeZone);
		return calendar.get(Calendar.DAY_OF_WEEK) - 1;
	}

//	/**
//	 * 比较时间（参数为java.util.Date）
//	 * 
//	 * @param startDate
//	 *            开始时间
//	 * @param endDate
//	 *            结束时间
//	 * @return int 
//	 */
//	public static int compareDate(Date startDate, Date endDate) {
//		if (startDate == null && endDate == null) {
//			return Time.TIME_EQUES;
//		} else if (startDate == null) {
//			return Time.TIME_GREAT;
//		} else if (endDate == null) {
//			return Time.TIME_LESS;
//		} else {
//			long timeStart = startDate.getTime();
//			long timeEnd = endDate.getTime();
//
//			if (timeStart == timeEnd) {
//				return Time.TIME_EQUES;
//			} else {
//				return (timeEnd > timeStart) ? Time.TIME_GREAT : Time.TIME_LESS;
//			}
//		}
//	}
//	/**
//	 * 比较时间（参数为java.sql.Timestamp）
//	 * 
//	 * @param startDate
//	 *            开始时间
//	 * @param endDate
//	 *            结束时间
//	 * @return int
//	 */
//	public static int compareDate(Timestamp startDate, Timestamp endDate) {
//		if (startDate == null && endDate == null) {
//			return Time.TIME_EQUES;
//		} else if (startDate == null) {
//			return Time.TIME_GREAT;
//		} else if (endDate == null) {
//			return Time.TIME_LESS;
//		} else {
//			long timeStart = startDate.getTime();
//			long timeEnd = endDate.getTime();
//
//			if (timeStart == timeEnd) {
//				return Time.TIME_EQUES;
//			} else {
//				return (timeEnd > timeStart) ? Time.TIME_GREAT : Time.TIME_LESS;
//			}
//		}
//	}
//	
//	/**
//	 * 比较时间（参数为java.util.String 默认格式为yyyy-MM-dd HH:mm:ss）
//	 * 
//	 * @param startDate
//	 *            开始时间
//	 * @param endDate
//	 *            结束时间
//	 * @return int
//	 */
//	public static int compareDate(String startDate, String endDate) {
//		return compareDate(startDate, endDate, null);
//	}
//
//	/**
//	 * 比较时间（参数为java.util.String 默认格式为yyyy-MM-dd HH:mm:ss）
//	 * 
//	 * @param startDate
//	 *            开始时间
//	 * @param endDate
//	 *            结束时间
//	 * @return int
//	 */
//	public static int compareDate(String startDate, String endDate, String pattern) {
//
//		if (pattern == null) {
//			pattern = pattern_yyyy_MM_dd_HH_mm_ss;
//		}
//		SimpleDateFormat format = new SimpleDateFormat(pattern);
//		if (startDate == null || endDate == null) {
//			return 0;
//		}
//		try {
//			Date start = format.parse(startDate);
//			Date end = format.parse(endDate);
//			return compareDate(start, end);
//		} catch (Exception e) {
//			return 0;
//		}
//	}

	/**
	 * 将日期字符串转换为Timestamp
	 * @param date String, 格式必须为yyyy-MM-dd
	 * @return java.sql.Timestamp
	 */
	public static Timestamp parseDateToTimestemp(String date) {
		return parseDateToTimestemp(date, null);
	}
	
	/**
	 * 将指定格式的日期字符串转换为Timestamp
	 * @param date String
	 * @param pattern 默认为yyyy-MM-dd
	 * @return java.sql.Timestamp
	 */
	public static Timestamp parseDateToTimestemp(String date, String pattern) {
		try {
			if (date == null) {
				return null;
			}
			if (pattern == null) {
				pattern = PATTERN_YYYY_MM_DD;
			}
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			return new Timestamp(sdf.parse(date).getTime());
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 将Timestamp转换为字符串
	 * @param date 格式为yyyy-MM-dd
	 * @return String yyyy-MM-dd HH:mm:ss
	 */
	public static String parseDateToString(Timestamp date) {
		return parseDateToString(date, null);
	}
	
	/**
	 * 将Timestamp转换为指定格式的日期字符串
	 * @param date Timestamp
	 * @param pattern 格式默认为yyyy-MM-dd
	 * @return String yyyy-MM-dd HH:mm:ss
	 */
	public static String parseDateToString(Timestamp date, String pattern) {
		if (date == null) {
			return null;
		}
		if (pattern == null) {
			pattern = PATTERN_YYYY_MM_DD_HH_MM_SS;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(date);
	}
	
	/**
	 * 将Date转换为字符串，默认格式为yyyy-MM-dd
	 * @param date Date
	 * @param format String 默认格式为yyyy-MM-dd
	 * @return String yyyy-MM-dd HH:mm:ss
	 */
	public static String parseDateToString(Date date, String format) {
		if (date == null) {
			return null;
		}
		if (format == null || format.trim().length() <= 0) {
			format = PATTERN_YYYY_MM_DD_HH_MM_SS;
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		return dateFormat.format(date);
	}

	/**
	 * 将Date转换为字符串，默认格式为yyyy-MM-dd
	 * @param date java.util.Date
	 * @return String yyyy-MM-dd
	 */
	public static String parseDateToString(Date date) {
		return parseDateToString(date, PATTERN_YYYY_MM_DD);
	}
	/**
	 * 格式化日期字符串
	 * @param datetime java.sql.Timestamp
	 * @return String yyyy-MM-dd HH:mm:ss
	 */
	public static String format(Timestamp datetime) {
		return format(datetime, null);
	}
	/**
	 * 格式化日期字符串
	 * @param datetime java.sql.Timestamp
	 * @param toPattern 转换后的日期格式
	 * @return String yyyy-MM-dd HH:mm:ss
	 */
	public static String format(Timestamp datetime, String toPattern) {
		try {
			if (datetime == null) {
				return null;
			}
			if (Util.isEmpty(toPattern)) {
				toPattern = PATTERN_YYYY_MM_DD_HH_MM_SS;
			}
			SimpleDateFormat toFormat = new SimpleDateFormat(toPattern);
			return toFormat.format(new Date(datetime.getTime()));
		} catch (Exception e) {
			return null;
		}
	}
	/**
	 * 格式化日期字符串
	 * @param date java.util.Date
	 * @return String yyyy-MM-dd
	 */
	public static String format(Date datetime) {
		return format(datetime, null);
	}
	/**
	 * 格式化日期字符串
	 * @param date java.util.Date
	 * @param toPattern 转换后的日期格式
	 * @return String yyyy-MM-dd
	 */
	public static String format(Date date, String toPattern) {
		try {
			if (date == null) {
				return null;
			}
			if (Util.isEmpty(toPattern)) {
				toPattern = PATTERN_YYYY_MM_DD;
			}
			SimpleDateFormat toFormat = new SimpleDateFormat(toPattern);
			return toFormat.format(date);
		} catch (Exception e) {
			return null;
		}
	}
	/**
	 * 格式化日期字符串
	 * @param date String
	 * @param fromPattern 传入日期格式
	 * @param toPattern 转换后的日期格式
	 * @return String 
	 */
	public static String format(String date, String fromPattern, String toPattern) {
		try {
			if (date == null || fromPattern == null || toPattern == null) {
				return null;
			}
			SimpleDateFormat fromFormat = new SimpleDateFormat(fromPattern);
			SimpleDateFormat toFormat = new SimpleDateFormat(toPattern);
			return toFormat.format(fromFormat.parse(date));
		} catch (Exception e) {
			return null;
		}
	}

	public static void main(String[] args) {
	}

}