package com.face.execption;

/**
 * 自定义异常:参数校验异常
 * @author Jason Xie 2015-11-10 13:46:17
 *
 */
public class ValidationExecption extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1021721058068927233L;
	public ValidationExecption() {
		super();
	}

	public ValidationExecption(String msg) {
		super(msg);
	}

	public ValidationExecption(String msg, Throwable cause) {
		super(msg, cause);
	}

	public ValidationExecption(Throwable cause) {
		super(cause);
	}

}
