package com.face.model;

public interface Domain {
	Boolean compareTo(Object x, Object y);
	boolean equals(Object obj);
	Object copy(Object x, Object y);
}
