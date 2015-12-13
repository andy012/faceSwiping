package com.face.data.youtu;

import com.face.data.base.DataBase;
import com.face.data.user.FaceIdentifyUserBaseInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andy on 12/10/15.
 */
public class UserFaceIdentifyResponseArray extends DataBase{
    private List<UserFaceIdentifyResponse> data =new ArrayList<>();
    public List<UserFaceIdentifyResponse> getData() {
        return data;
    }
    public void setData(List<UserFaceIdentifyResponse> data) {
        this.data = data;
    }
    public void add(UserFaceIdentifyResponse userFaceIdentifyResponse){
        if(!data.contains(userFaceIdentifyResponse)){
            data.add(userFaceIdentifyResponse);
        }
    }
}
