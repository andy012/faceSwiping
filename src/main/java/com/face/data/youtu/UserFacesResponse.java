package com.face.data.youtu;

import com.face.data.base.DataBase;
import com.face.data.util.ResponseCode;

import java.util.LinkedHashMap;

/**
 * Created by andy on 12/8/15.
 */
public class UserFacesResponse extends DataBase{

    private LinkedHashMap<String ,String> data =new LinkedHashMap<>();

    public UserFacesResponse(){}
    public UserFacesResponse(LinkedHashMap<String ,String> data){
        this.data = data;
        setResponseCode();

    }
    public LinkedHashMap<String, String> getData() {
        return data;
    }

    public void setData(LinkedHashMap<String, String> data) {
        this.data = data;
    }

    public void setResponseCode(){
        if(data.size()==0){
            this.errorcode=ResponseCode.DATA_ZERO.getCode();
            this.errormsg=ResponseCode.DATA_ZERO.getDescription();
        }else if(data.size()>0){
            this.errorcode=ResponseCode.SUCCESS.getCode();
            this.errormsg=ResponseCode.SUCCESS.getDescription();
        }
    }

}
