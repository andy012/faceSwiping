package com.face.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/**
 * 系统工具类，整个系统共有的方法
 * 
 * @author Jason Xie 2015-11-9 18:22:13
 * 
 */
public class Util {

//	private static Logger log = Logger.getLogger(Util.class);
	/**
	 * 获取UUID
	 * 
	 * @return String
	 */
	public final static String getUUID() {
		return UUID.randomUUID().toString();
	}

	/**
	 * 转换为Integer类型，返回值可能为null,操作异常则返回null
	 * 
	 * @param obj 转换对象
	 * @return Integer
	 */
	public final static Integer parseInteger(Object obj) {
		try {
			if (isEmpty(obj)) {
				return null;
			}
			return Integer.valueOf(obj.toString());
		} catch (NumberFormatException e) {
			return null;
		}
	}

	/**
	 * 转换为int类型，返回值不能为null，操作异常则返回0
	 * 
	 * @param obj 转换对象
	 * @return int
	 */
	public final static int parseInt(Object obj) {
		try {
			return Integer.valueOf(parseDouble(obj).intValue());
		} catch (Exception e) {
			return 0;
		}
	}

	/**
	 * 转换为Double类型，返回值可能为null,操作异常则返回null
	 * 
	 * @param obj 转换对象
	 * @return Double
	 */
	public final static Double parseDouble(Object obj) {
		try {
			if (isEmpty(obj)) {
				return null;
			}
			return Double.valueOf(obj.toString());
		} catch (NumberFormatException e) {
			return null;
		}
	}

	/**
	 * 转换为double类型，返回值不能为null，操作异常则返回0
	 * 
	 * @param obj 转换对象
	 * @return double
	 */
	public final static double parsedouble(Object obj) {
		try {
			if (isEmpty(obj)) {
				return 0d;
			}
			return Double.valueOf(obj.toString());
		} catch (NumberFormatException e) {
			return 0d;
		}
	}
	
	/**
	 * 转换为long类型，返回值不能为null，异常则返回0
	 * 
	 * @param obj 转换对象
	 * @return long
	 */
	public final static long parselong(Object obj) {
		try {
			return Long.valueOf(parseDouble(obj.toString()).longValue());
		} catch (Exception e) {
			return 0l;
		}
	}
	
	/**
	 * 转换为Boolean类型，返回值可以为null
	 * 
	 * @param obj 转换对象
	 * @return Boolean
	 */
	public final static Boolean parseBoolean(Object obj) {
		try {
			if (isEmpty(obj)) {
				return null;
			}
			return Boolean.valueOf(obj.toString());
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * 转换为boolean类型，返回值不能为null
	 * 
	 * @param obj 转换对象
	 * @return boolean
	 */
	public final static boolean parseboolean(Object obj) {
		Boolean value = parseBoolean(obj);
		if (isEmpty(value)) {
			return false;
		}
		return value.booleanValue();
	}

	/**
	 * 将Object 转换为String，""转换为null
	 * 
	 * @param obj 转换对象
	 * @return String
	 */
	public final static String parseString(Object obj) {
		if (obj == null) {
			return null;
		}
		return obj.toString().length() <= 0 ? null : obj.toString();
	}

	/**
	 * 验证Integer类型数据是否大于0
	 * 
	 * @param value Integer对象
	 * @return true大于0，false小于等于0
	 */
	public final static boolean checkInt(Integer value) {
		if (value == null) {
			return false;
		}
		if (value.intValue() <= 0) {
			return false;
		}
		return true;
	}
	
	/**
	 * 验证Double类型数据是否大于0
	 * 
	 * @param value Double对象
	 * @return true大于0，false小于等于0
	 */
	public final static boolean checkdouble(Double value) {
		if (value == null) {
			return false;
		}
		if (value.doubleValue() <= 0d) {
			return false;
		}
		return true;
	}
	
	/**
	 * 验证Long类型数据是否大于0
	 * 
	 * @param value Long对象
	 * @return true大于0，false小于等于0
	 */
	public final static boolean checkLong(Long value) {
		if (value == null) {
			return false;
		}
		if (value <= 0l) {
			return false;
		}
		return true;
	}

	/**
	 * 将空字符串替换为null
	 * @param value String对象
	 * @return String 对象
	 */
	public final static String replaceSpace(String value) {
		if (value != null && value.trim().length() <= 0) {
			return null;
		}
		return value;
	}

	/**
	 * 将null替换为空格
	 * @param value String对象
	 * @return String对象
	 */
	public final static String replaceNull(String value) {
		if (value == null || "null".equals(value)) {
			return "";
		}
		return value;
	}

	/**
	 * 计算分页总页数
	 * 
	 * @param pageSize 每页数量
	 * @param count 总行数
	 * @return int 总页数
	 */
	public final static int getPageCount(int pageSize, int count) {
		if (checkInt(count)) {
			return (count % pageSize == 0) ? (count / pageSize) : (count / pageSize + 1);
		} else {
			return 1;
		}
	}
	
	/**
	 * MD5加密
	 * 
	 * @param source 加密字符串
	 * @return String 密文字符串 
	 */
	public static String toMD5(String source) {
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			md5.update(source.getBytes("UTF8"));
			byte s[] = md5.digest();
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < s.length; ++i) {
				sb.append(Integer.toHexString((s[i] & 0xFF) | 0x100).substring(
						1, 3));
			}
			return sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 判断列表是否为空
	 * @param list 集合
	 * @return boolean true空 false非空
	 */
	public static boolean isEmpty(List<?> lists){
		return null == lists || lists.size() == 0;
	}
	/**
	 * 判断列表是否非空
	 * @param list 集合
	 * @return boolean true非空 false空
	 */
	public static boolean isNotEmpty(List<?> lists){
		return !isEmpty(lists);
	}
	/**
	 * 判断列表是否为空
	 * @param list 集合
	 * @return boolean true空 false非空
	 */
	public static boolean isEmpty(Set<?> lists){
		return null == lists || lists.size() == 0;
	}
	/**
	 * 判断列表是否非空
	 * @param list 集合
	 * @return boolean true非空 false空
	 */
	public static boolean isNotEmpty(Set<?> lists){
		return !isEmpty(lists);
	}
	/**
	 * 判断Map列表是否为空
	 * @param list 集合
	 * @return boolean true空 false非空
	 */
	public static boolean isEmpty(Map<?,?> map){
		return null == map || map.size() == 0;
	}
	/**
	 * 判断Map列表是否非空
	 * @param list 集合
	 * @return boolean true非空 false空
	 */
	public static boolean isNotEmpty(Map<?,?> map){
		return !isEmpty(map);
	}
	/**
	 * 判断数组是否为空
	 * @param array Object[] 数组
	 * @return boolean true空 false非空
	 */
	public static boolean isEmpty(Object[] array){
		return null == array || array.length == 0;
	}
	/**
	 * 判断数组是否非空
	 * @param array Object[] 数组
	 * @return boolean true非空 false空
	 */
	public static boolean isNotEmpty(Object[] array){
		return !isEmpty(array);
	}
	/**
	 * 判断数组是否为空
	 * @param array Object[] 数组
	 * @return boolean true空 false非空
	 */
	public static boolean isEmpty(String[] array){
		return null == array || array.length == 0;
	}
	/**
	 * 判断数组是否非空
	 * @param array Object[] 数组
	 * @return boolean true非空 false空
	 */
	public static boolean isNotEmpty(String[] array){
		return !isEmpty(array);
	}
	/**
	 * 判断数组是否为空
	 * @param array Object[] 数组
	 * @return boolean true空 false非空
	 */
	public static boolean isEmpty(Long[] array){
		return null == array || array.length == 0;
	}
	/**
	 * 判断数组是否非空
	 * @param array Object[] 数组
	 * @return boolean true非空 false空
	 */
	public static boolean isNotEmpty(Long[] array){
		return !isEmpty(array);
	}
	/**
	 * 判断数组是否为空
	 * @param array Object[] 数组
	 * @return boolean true空 false非空
	 */
	public static boolean isEmpty(long[] array){
		return null == array || array.length == 0;
	}
	/**
	 * 判断数组是否非空
	 * @param array Object[] 数组
	 * @return boolean true非空 false空
	 */
	public static boolean isNotEmpty(long[] array){
		return !isEmpty(array);
	}
	/**
	 * 判断数组是否为空
	 * @param array Object[] 数组
	 * @return boolean true空 false非空
	 */
	public static boolean isEmpty(int[] array){
		return null == array || array.length == 0;
	}
	/**
	 * 判断数组是否非空
	 * @param array Object[] 数组
	 * @return boolean true非空 false空
	 */
	public static boolean isNotEmpty(int[] array){
		return !isEmpty(array);
	}
	/**
	 * 判断对象是否为空
	 * @param obj Object 对象
	 * @return boolean true空 false非空
	 */
	public static boolean isEmpty(Object obj){
		return null == obj;
	}
	/**
	 * 判断对象是否为非空
	 * @param obj Object 对象
	 * @return boolean true非空 false空
	 */
	public static boolean isNotEmpty(Object obj){
		return !isEmpty(obj);
	}
	/**
	 * 判断字符串是否为空
	 * @param obj String 对象
	 * @return boolean true空 false非空
	 */
	public static boolean isEmpty(String obj){
		return null == obj || obj.trim().length() == 0;
	}
	/**
	 * 判断字符串是否为非空
	 * @param obj String 对象
	 * @return boolean true非空 false空
	 */
	public static boolean isNotEmpty(String obj){
		return !isEmpty(obj);
	}

	
	/**
	 * value in (args)
	 * 判断是否字符串等于给定字符数组中的一个.
	 * @param value 给定的值
	 * @param args 匹配值
	 * @return bollean 返回比对状态 
	 */
	public static boolean equals(String value, String... args){
		if (value == null || args == null)
			return false;
		
		for (int i = 0; i < args.length; i++){
			if (value.equals(args[i])) return true;
		}
		return false;
	}

	/**
	 * 将Object数组转换为String数组
	 * @param array Object[] 
	 * @return String[]
	 */
	public static String[] toStringArray(Object[] array){
		if (isEmpty(array)) {
			return new String[]{};
		}
		String[] result = new String[array.length];
		for (int i = 0; i < array.length; i++) {
			result[i] = array[i].toString();
		}
		return result;
	}
	
	/**
	 * 获取默认的分页
	 * @return Pageable 分页对象
	 */
	public static Pageable getDefaultPageable(){
		return getPageable(1);
	}
	
	/**
	 * 获取分页对象
	 * @param pageIndex 当前页
	 * @return Pageable 分页对象
	 */
	public static Pageable getPageable(int pageIndex){
		return getPageable(pageIndex, 0);
	}

	/**
	 * 获取分页对象
	 * @param pageIndex 当前页
	 * @param pageSize 当前显示条数
	 * @return Pageable 分页对象
	 */
	public static Pageable getPageable(int pageIndex, int pageSize){
		return getPageable(pageIndex, pageSize, null);
	}

	/**
	 * 获取分页对象
	 * @param sort Sort排序对象 
	 * @return Pageable 分页对象
	 */
	public static Pageable getPageable(Sort sort){
		return getPageable(1, 0, sort);
	}
	
	/**
	 * 获取分页对象
	 * @param pageIndex 当前页
	 * @param pageSize 当前显示条数
	 * @param sort Sort排序对象 
	 * @return Pageable 分页对象
	 */
	public static Pageable getPageable(int pageIndex, int pageSize , Sort sort){
		if (pageSize <= 0) {
			pageSize = 20;
		}
		return new PageRequest(pageIndex-1, pageSize, sort);
	}

	public static void main(String[] args) {
		System.out.println(getUUID().length());
	}	
}
