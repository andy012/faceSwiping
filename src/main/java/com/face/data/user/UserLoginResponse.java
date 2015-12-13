package com.face.data.user;

import com.face.data.base.DataBase;
import com.face.data.mapper.UrlMapper;
import com.face.model.user.User;
import com.face.model.user.UserFaceImagesEntity;
import com.face.service.qiniu.QiniuService;
import com.face.service.user.UserFaceImagesService;

import java.util.List;

/**
 * Created by andy on 12/9/15.
 */
public class UserLoginResponse extends DataBase{

    UserBaseInfo data =new UserBaseInfo();

    public UserBaseInfo getData() {
        return data;
    }

    public void setData(UserBaseInfo data) {
        this.data = data;
    }


    public void setUser(User user,QiniuService qiniuService){
        data.setId(user.getId());
        data.setUserName(user.getUsername());
        data.setNickName(user.getUserDetailInfoEntity().getNickName() == null ? "" : user.getUserDetailInfoEntity().getNickName());
        data.setHeadImageUrl(UrlMapper.map2Url(user.getUserDetailInfoEntity().getHeadImageKey(), qiniuService));
        data.setSecret(user.isSecret() ? 1 : 0);

    }

    public void setUser(User user,QiniuService qiniuService,UserFaceImagesService userFaceImagesService){
        data.setId(user.getId());
        data.setUserName(user.getUsername());
        data.setNickName(user.getUserDetailInfoEntity().getNickName() == null ? "" : user.getUserDetailInfoEntity().getNickName());
        data.setHeadImageUrl(UrlMapper.map2Url(user.getUserDetailInfoEntity().getHeadImageKey(), qiniuService));
        data.setSecret(user.isSecret() ? 1 : 0);
        List<UserFaceImagesEntity> userFaceImagesEntities=userFaceImagesService.findAllByUserId(data.getId());
        if(userFaceImagesEntities.size()!=0) {

            //int imageLength = userFaceImagesService.findAllByUserId(data.getId()).size();
            getData().setCertification(1);
            data.setCertificationImageUrl(qiniuService.createPrivateUrl(userFaceImagesEntities.get(0).getImageKey()));

        }else {
            getData().setCertification(0);
            data.setCertificationImageUrl("");

        }
    }
}
