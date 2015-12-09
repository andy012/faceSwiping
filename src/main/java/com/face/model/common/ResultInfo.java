package com.face.model.common;

import com.face.util.Util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 返回结果集对象
 * @author Jason Xie 2015-11-10 12:03:14
 *
 */
@SuppressWarnings("unchecked")
public class ResultInfo<T> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8776609979439009156L;

	/**
	 * 返回信息代码表
	 * 
	 * 当请求查询列表时，>0表示成功返回数据条数 =0表示无数据 <0表示异常代码表
	 * 
	 * 当请求操作（增、删、改）时，=1表示操作成功 =0表示失败 <0表示异常代码表
	 * 
	 * @author Jason Xie 2015-11-10 12:04:10
	 *
	 */
	public static class Code {
		/**
		 * 成功
		 */
		public static final int SUCCESS = 1;
		/**
		 * 查询表示无数据，增删改表示失败
		 */
		public static final int FAILURE = 0;
		/**
		 * 请求参数列表为空
		 */
		public static final int PARAMS_NULL = -1;
		/**
		 * 请求参数解析失败
		 */
		public static final int PARAMS_PARSE_ERROR = -2;
		/**
		 * 参数非空校验失败
		 */
		public static final int CHECK_PARAMS_NULL = -3;
		/**
		 * 服务端异常
		 */
		public static final int EXECPTION = -4;
		/**
		 * 登录超时
		 */
		public static final int LOGIN_TIMEOUT = -5;
		/**
		 * 用户不存在
		 */
		public static final int USER_NOT_EXIST = -6;
		/**
		 * 密码错误
		 */
		public static final int PWD_ERROR = -7;
		/**
		 * 公司已存在
		 */
		public static final int COMPANY_EXIST = -8;
		/**
		 * 上传文件为空
		 */
		public static final int UPLOAD_FILE_IS_EMPTY = -9;
		/**
		 * 身份校验失败
		 */
		public static final int SECURITY_UNSUCCESS = -10;
	}
	
	/**
	 * 默认构造函数
	 */
	public ResultInfo() {
		super();
	}
	
	/**
	 * 带参构造函数
	 * @param code int 操作返回码
	 */
	public ResultInfo(int code) {
		super();
		this.code = code;
	}	
	
	/**
	 * 带参构造函数
	 * @param code int 操作返回码
	 * @param data List<T> 结果集
	 */
	public ResultInfo(int code, List<T> data) {
		super();
		this.code = code;
		this.data = data;
	}
	
	/**
	 * 带参构造函数
	 * @param code int 操作返回码
	 * @param data Object 结果集为单个对象
	 */
	public ResultInfo(int code, final Object obj) {
		super();
		this.code = code;
		this.data = (List<T>) new ArrayList<Object>(){
			private static final long serialVersionUID = 645688509874183550L;
			{
				add(obj);
			}
		};
	}	
	
	/**
	 * 编码，成功、失败、异常编码
	 */
	private int code;
	/**
	 * 返回数据集合
	 */
	private List<T> data = new ArrayList<T>();
	/**
	 * 总页数
	 */
	private int pageCount;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public List<T> getData() {
		return data;
	}

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public void setData(List<T> data) {
		if (Util.isNotEmpty(data)) {
			// 设置结果集大小
			this.code = data.size();
		}
		this.data = data;
	}
	
	public void setData(Object obj) {
		// 设置结果集大小
		this.code = Code.SUCCESS;
		this.data.add((T) obj);
	}

	@Override
	public String toString() {
		return "ResultInfo [code=" + code + ", data=" + data + "]";
	}
	
	

}
