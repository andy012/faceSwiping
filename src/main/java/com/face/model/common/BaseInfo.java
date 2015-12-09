package com.face.model.common;


import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

/**
 * 所有类的必须继承
 * @author Jason Xie 2015-11-9 17:35:29
 *
 */
@MappedSuperclass
public class BaseInfo extends SimpleInfo {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7977763328851290625L;

	/**
	 * 默认构造函数
	 */
	public BaseInfo() {
	}

	/**
	 * 创建时间
	 */
	private String createDateStr;
	
	/**
	 * 修改时间
	 */
	private String modifyDateStr;

	@Transient
	public String getCreateDateStr() {
		return createDateStr;
	}

	public void setCreateDateStr(String createDateStr) {
		this.createDateStr = createDateStr;
	}

	@Transient
	public String getModifyDateStr() {
		return modifyDateStr;
	}

	public void setModifyDateStr(String modifyDateStr) {
		this.modifyDateStr = modifyDateStr;
	}

}
