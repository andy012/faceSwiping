package com.face.data.youtu;

import com.face.data.base.DataBase;
import com.face.data.util.ResponseCode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andy on 12/9/15.
 */
public class UserAddFacesResponse extends DataBase{

    List<FaceResultBean> data =new ArrayList<>();
    private int count=0;
    public List<FaceResultBean> getData() {
        return data;
    }

    public void addFaceResultBean(FaceResultBean faceResultBean){
        this.data.add(faceResultBean);
    }
    public void setData(List<FaceResultBean> data) {
        this.data = data;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setResponseCode(){

        for(FaceResultBean faceResultBean: data){
            if(faceResultBean.getErrorcode()==0) count++;
        }
        if(data.size()==0){
            setResponseCode(ResponseCode.DATA_ZERO);
        }else if(count== data.size()){
            this.errorcode= ResponseCode.SUCCESS.getCode();
            this.errormsg=ResponseCode.SUCCESS.getDescription();
        }else if(count>0){
            this.errorcode= ResponseCode.NOT_ALL_SUCCESS.getCode();
            this.errormsg=ResponseCode.NOT_ALL_SUCCESS.getDescription();
        }else {
            this.errorcode= ResponseCode.ALL_NOT_SUCCESS.getCode();
            this.errormsg=ResponseCode.ALL_NOT_SUCCESS.getDescription();
        }
    }
}
