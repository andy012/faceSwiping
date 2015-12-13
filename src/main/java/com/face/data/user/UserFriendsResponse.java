package com.face.data.user;

import com.face.data.base.DataBase;
import com.face.model.user.User;
import com.face.model.user.UserRelationEntity;
import com.face.service.qiniu.QiniuService;
import com.face.service.user.CustomUserDetailsService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andy on 12/13/15.
 */
public class UserFriendsResponse extends DataBase {
    List<FriendBean> data=new ArrayList<>();

    public List<FriendBean> getData() {
        return data;
    }



    public void setData(List<FriendBean> data) {
        this.data = data;
    }


    public void addFriend(User user,QiniuService qiniuService){
        FriendBean friendBean=new FriendBean();
        friendBean.setName(user.getUserDetailInfoEntity().getNickName());
        friendBean.setHeaderImageUrl(qiniuService.createPrivateUrl(user.getUserDetailInfoEntity().getHeadImageKey()));
        data.add(friendBean);
    }

}
