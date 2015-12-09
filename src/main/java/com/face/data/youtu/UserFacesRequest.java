package com.face.data.youtu;

import com.face.service.qiniu.QiniuService;

import java.util.ArrayList;
import java.util.List;


/**
 *
 * 用户添加增加人脸
 * Created by andy on 12/8/15.
 */
public class UserFacesRequest {
    private List<String> keys=new ArrayList<String>();
    public UserFacesRequest(String key) {
        this.addKey(key);
    }
    public UserFacesRequest() {
    }
    public UserFacesRequest(List<String> keys) {
        this.keys = keys;
    }
    public List<String> addKey(String key){
        keys.add(key);
        return keys;
    }
    public List<String> addKeys(String keys){
        this.keys.add(keys);
        return this.keys;
    }
    public List<String> getKeys() {
        return keys;
    }
    public void setKeys(List<String> keys) {
        this.keys = keys;
    }



}
