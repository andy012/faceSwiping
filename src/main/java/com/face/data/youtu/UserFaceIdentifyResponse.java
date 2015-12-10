package com.face.data.youtu;

import com.face.data.base.DataBase;
import com.face.data.user.FaceIdentifyUserBaseInfo;
import com.face.data.user.UserBaseInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andy on 12/10/15.
 */
public class UserFaceIdentifyResponse extends DataBase{
    private List<FaceIdentifyUserBaseInfo> faceIdentifyUserBaseInfos=new ArrayList<>();

    public List<FaceIdentifyUserBaseInfo> getFaceIdentifyUserBaseInfos() {
        return faceIdentifyUserBaseInfos;
    }

    public void setFaceIdentifyUserBaseInfos(List<FaceIdentifyUserBaseInfo> faceIdentifyUserBaseInfos) {
        this.faceIdentifyUserBaseInfos = faceIdentifyUserBaseInfos;
    }

    public void add(FaceIdentifyUserBaseInfo faceIdentifyUserBaseInfo){
        if(!faceIdentifyUserBaseInfos.contains(faceIdentifyUserBaseInfo)){
            faceIdentifyUserBaseInfos.add(faceIdentifyUserBaseInfo);
        }
    }

}
