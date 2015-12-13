package com.face.data.youtu;

import com.face.data.base.DataBase;
import com.face.data.user.FaceIdentifyUserBaseInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andy on 12/13/15.
 */
public class UserFaceIdentifyResponseOne extends DataBase{


    List<NewFriendBean> data=new ArrayList<>();

    public List<NewFriendBean> getData() {
        return data;
    }

    public void setData(List<NewFriendBean> data) {
        this.data = data;
    }

    public void add(FaceIdentifyUserBaseInfo faceIdentifyUserBaseInfo){


        NewFriendBean newFriendBean=new NewFriendBean();
        newFriendBean.setName(faceIdentifyUserBaseInfo.getNickName());
        newFriendBean.setHeadImageUrl(faceIdentifyUserBaseInfo.getHeadImageUrl());
        newFriendBean.setGroupImageUrl(faceIdentifyUserBaseInfo.getFaceImageUrl());
        newFriendBean.setIsAddedFriend(faceIdentifyUserBaseInfo.getIsFriends()==1?true:false);
        newFriendBean.setFromResource("");
        newFriendBean.setContent("");
        data.add(newFriendBean);
    }

    public void add(List<FaceIdentifyUserBaseInfo> faceIdentifyUserBaseInfos){
      for(FaceIdentifyUserBaseInfo faceIdentifyUserBaseInfo:faceIdentifyUserBaseInfos){
          add(faceIdentifyUserBaseInfo);
      }
    }
}
