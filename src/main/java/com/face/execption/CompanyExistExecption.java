package com.face.execption;

/**
 * 自定义异常:公司已经存在
 * @author Jason Xie 2015-11-10 16:41:05
 *
 */
public class CompanyExistExecption extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1021721058068927233L;
	public CompanyExistExecption() {
		super();
	}

	public CompanyExistExecption(String msg) {
		super(msg);
	}

	public CompanyExistExecption(String msg, Throwable cause) {
		super(msg, cause);
	}

	public CompanyExistExecption(Throwable cause) {
		super(cause);
	}

}
