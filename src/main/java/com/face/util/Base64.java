package com.face.util;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Base64Utils;

import com.alibaba.fastjson.JSON;
import com.face.execption.Base64ParseExecption;
/**
 * Base64 编码解码工具类
 * @author Jason Xie 2015-5-22 13:24:28 
 *
 */
public class Base64 {
	public static final String CODE_UTF8 = "UTF-8";
	public static final String CODE_UTF16 = "UTF-16";
	public static final String CODE_GBK = "GBK";
	/**
	 * 将指定编码的字符串转换为Base64编码字符串
	 * @param source 需要转换的字符串
	 * @return String 返回Base64编码字符串
	 * @exception Base64ParseExecption 转换失败抛出异常
	 */
	public static String encode(String source) throws Base64ParseExecption {
		return encode(source, null);
	}
	/**
	 * 将指定编码的字符串转换为Base64编码字符串
	 * @param source 需要转换的字符串
	 * @param code 字符串指定编码
	 * @return String 返回Base64编码字符串
	 * @exception Base64ParseExecption 转换失败抛出异常
	 */
	public static String encode(String source, String code) throws Base64ParseExecption {
		return encode(source, code, false);
	}
	/**
	 * 将指定编码的字符串转换为Base64编码字符串
	 * @param source 需要转换的字符串
	 * @param isUrl 是否为url，true表示url，将替换"/"和"="
	 * @return String 返回Base64编码字符串
	 * @exception Base64ParseExecption 转换失败抛出异常
	 */
	public static String encode(String source, boolean isUrl) throws Base64ParseExecption {
		return encode(source, null, isUrl);
	}
	
	/**
	 * 将指定编码的字符串转换为Base64编码字符串
	 * @param source 需要转换的字符串
	 * @param code 字符串指定编码
	 * @param isUrl 是否为url，true表示url，将替换"/"和"="
	 * @return String 返回Base64编码字符串
	 * @exception Base64ParseExecption 转换失败抛出异常
	 */
	public static String encode(String source, String code, boolean isUrl) throws Base64ParseExecption {
		String result = null;
		try {
			if (StringUtils.isEmpty(source)) {
				return result;
			}
			if (StringUtils.isEmpty(code)) {
				code = CODE_UTF8;
			}
				
			result = Base64Utils.encodeToString(source.getBytes(code));
			if (isUrl) {
				result = result.replaceAll("\\/", "-");
				result = result.replaceAll("\\=", "_");
			}
		} catch (UnsupportedEncodingException e) {
			throw new Base64ParseExecption("Base64解码失败,"+e.getMessage());
		} catch (Exception e) {
			throw new Base64ParseExecption("Base64解码失败,"+e.getMessage());
		}
		return result;
	}
	
	/**
	 * 将Base64编码字符串转换为指定编码的字符串
	 * @param source 需要转换的字符串
	 * @return String 返回指定编码字符串
	 * @exception Base64ParseExecption 转换失败抛出异常
	 */
	public static String decode(String source) throws Base64ParseExecption {
		return decode(source, null);
	}
	/**
	 * 将Base64编码字符串转换为指定编码的字符串
	 * @param source 需要转换的字符串
	 * @param code 字符串指定编码
	 * @return String 返回指定编码字符串
	 * @exception Base64ParseExecption 转换失败抛出异常
	 */
	public static String decode(String source, String code) throws Base64ParseExecption {
		return decode(source, code, false);
	}
	/**
	 * 将Base64编码字符串转换为指定编码的字符串
	 * @param source 需要转换的字符串
	 * @param isUrl 是否为url，true表示url，将替换后的"/"和"="反转
	 * @return String 返回指定编码字符串
	 * @exception Base64ParseExecption 转换失败抛出异常
	 */
	public static String decode(String source, boolean isUrl) throws Base64ParseExecption {
		return decode(source, null, isUrl);
	}
	/**
	 * 将Base64编码字符串转换为指定编码的字符串
	 * @param source 需要转换的字符串
	 * @param code 字符串指定编码
	 * @param isUrl 是否为url，true表示url，将替换后的"/"和"="反转
	 * @return String 返回指定编码字符串
	 * @exception Base64ParseExecption 转换失败抛出异常
	 */
	public static String decode(String source, String code, boolean isUrl) throws Base64ParseExecption {
		String result = null;

		try {
			if (StringUtils.isEmpty(source)) {
				return result;
			}
			if (StringUtils.isEmpty(code)) {
				code = CODE_UTF8;
			}
			if (isUrl) {
				source = source.replaceAll("\\-", "/");
				source = source.replaceAll("\\_", "=");
			}
			result = new String(Base64Utils.decodeFromString(source), code);
		} catch (UnsupportedEncodingException e) {
			throw new Base64ParseExecption("Base64解码失败,"+e.getMessage());
		} catch (Exception e) {
			throw new Base64ParseExecption("Base64解码失败,"+e.getMessage());
		}
		return result;
	}	

	/**
	 * 将Base64编码字符串转换为指定编码的字符串
	 * @param source 需要转换的字符串
	 * @return String 返回指定编码字符串
	 */
	public static byte[] decoder(String source) {
		byte[] result = null;
		if (StringUtils.isEmpty(source)) {
			return result;
		}
		return Base64Utils.decodeFromString(source);
	}
	
	public static void main(String[] args) {

		System.out.println("----------");

		System.out.println("\n\r");
		System.out.println("-----------");
		try {

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("typeF", 1);
//			map.put("contentF", "新产品介绍");
			System.out.println(JSON.toJSONString(map));
			System.out.println(encode(JSON.toJSONString(map)));
			System.out.println(encode("{\"name\":\"Jason\"} "));
			System.out.println(encode("测试", true));
			System.out.println(decode("5rWL6K+V", true));
			System.out.println(encode(",./<>?<[]-=)_())(",true));
			System.out.println(decode("LC4vPD4-PFtdLT0pXygpKSg_", true));
			System.out.println(encode("{1:\"1\"}",true));
			System.out.println(decode("ezE6IjEifQ__", true));
//
			System.out.println(encode("{role_name:管理员,description:测试描述}",true));
			System.out.println(decode("eyJjb25kaXRpb25zIjp7ImlkIjoiMSJ9fQ__",true));
		} catch (Base64ParseExecption e) {
			e.printStackTrace();
		}
		
	}
}
