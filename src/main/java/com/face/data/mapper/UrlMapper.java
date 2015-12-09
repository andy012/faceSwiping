package com.face.data.mapper;

import com.face.model.user.UserFaceImagesEntity;
import com.face.service.qiniu.QiniuService;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by andy on 12/8/15.
 */
public class UrlMapper {
    public static List<String> map2Urls(List<String> keys,QiniuService qiniuService){
        List<String> urlLists=new ArrayList<>();
        for(String key:keys){
            urlLists.add(qiniuService.createPrivateUrl(key));
        }
        return urlLists;
    }
    public static String map2Url(String key,QiniuService qiniuService){
        return qiniuService.createPrivateUrl(key);
    }

    public static LinkedHashMap<String ,String> linkedHashMap2Urls(List<UserFaceImagesEntity> userFaceImagesEntities,QiniuService qiniuService){
        LinkedHashMap<String ,String> facesUrlMap=new LinkedHashMap<>();
        for(UserFaceImagesEntity userFaceImagesEntity:userFaceImagesEntities){
            facesUrlMap.put(userFaceImagesEntity.getImageKey(), qiniuService.createPrivateUrl(userFaceImagesEntity.getImageKey()));
        }
        return facesUrlMap;
    }

}
