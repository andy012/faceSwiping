package com.face.data.base;

import com.face.data.util.ResponseCode;

/**
 * Created by andy on 12/8/15.
 */
public class DataBase {

    public Integer errorcode=0;
    public String errormsg="";

    public Integer getErrorcode() {
        return errorcode;
    }

    public void setErrorcode(Integer errorcode) {
        this.errorcode = errorcode;
    }

    public String getErrormsg() {
        return errormsg;
    }

    public void setErrormsg(String errormsg) {
        this.errormsg = errormsg;
    }

    public void setResponseCode(ResponseCode responseCode){
        this.errorcode=responseCode.getCode();
        this.errormsg=responseCode.getDescription();
    }

}
