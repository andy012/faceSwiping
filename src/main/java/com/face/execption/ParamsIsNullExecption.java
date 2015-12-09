package com.face.execption;

/**
 * 自定义异常:请求参数为空
 * @author Jason Xie 2015-11-6 11:29:03
 *
 */
public class ParamsIsNullExecption extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1021721058068927233L;
	public ParamsIsNullExecption() {
		super();
	}

	public ParamsIsNullExecption(String msg) {
		super(msg);
	}

	public ParamsIsNullExecption(String msg, Throwable cause) {
		super(msg, cause);
	}

	public ParamsIsNullExecption(Throwable cause) {
		super(cause);
	}

}
