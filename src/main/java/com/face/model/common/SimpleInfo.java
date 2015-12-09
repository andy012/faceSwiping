package com.face.model.common;

import com.face.model.Domain;

import javax.persistence.MappedSuperclass;
import java.io.Serializable;


/**
 * 所有类的必须继承
 * @author Jason Xie 2015-11-9 17:35:29
 *
 */
@MappedSuperclass
public class SimpleInfo implements Serializable, Cloneable, Domain {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2588954687974022788L;

	/**
	 * 默认构造函数
	 */
	public SimpleInfo() {
	}
	
	@Override
	protected Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
	@Override
	public Boolean compareTo(Object x, Object y) {
		return null;
	}
	@Override
	public Object copy(Object x, Object y) {
		return null;
	}
}
