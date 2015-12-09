package com.face.data.util;

import java.util.HashMap;

/**
 * Created by andy on 12/9/15.
 */
public enum  ResponseCode {
    SUCCESS(0,"ok"),
    DATA_ZERO(-1,"没有数据返回"),
    LOGIN_FAIL(-2,"用户名或密码错误");
    private final int code;
    private final String description;
    private ResponseCode(int code, String description){
        this.code=code;
        this.description=description;
    }
    public String getDescription() {
        return description;
    }
    public int getCode() {
        return code;
    }
    @Override
    public String toString() {
        return code + ": " + description;
    }

}
