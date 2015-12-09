package com.face.execption;

/**
 * 自定义异常:Base64解码异常
 * @author Jason Xie 2015-11-10 13:46:17
 *
 */
public class Base64ParseExecption extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1021721058068927233L;
	public Base64ParseExecption() {
		super();
	}

	public Base64ParseExecption(String msg) {
		super(msg);
	}

	public Base64ParseExecption(String msg, Throwable cause) {
		super(msg, cause);
	}

	public Base64ParseExecption(Throwable cause) {
		super(cause);
	}

}
