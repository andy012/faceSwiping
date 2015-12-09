package com.face.data.user;

import com.face.data.base.DataBase;
import com.face.data.mapper.UrlMapper;
import com.face.data.util.ResponseCode;
import com.face.model.user.User;
import com.face.service.qiniu.QiniuService;

/**
 * Created by andy on 12/9/15.
 */
public class UserLoginResponse extends DataBase{

    UserBaseInfo userBaseInfo=new UserBaseInfo();

    public UserBaseInfo getUserBaseInfo() {
        return userBaseInfo;
    }

    public void setUserBaseInfo(UserBaseInfo userBaseInfo) {
        this.userBaseInfo = userBaseInfo;
    }


    public void setUser(User user,QiniuService qiniuService){
        userBaseInfo.setId(user.getId());
        userBaseInfo.setUserName(user.getUsername());
        userBaseInfo.setNickName(user.getUserDetailInfoEntity().getNickName()==null ? "":user.getUserDetailInfoEntity().getNickName());
        userBaseInfo.setHeadImageUrl(UrlMapper.map2Url(user.getUserDetailInfoEntity().getHeadImageKey(), qiniuService));
    }


}
