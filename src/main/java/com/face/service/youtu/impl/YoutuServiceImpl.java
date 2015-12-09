package com.face.service.youtu.impl;

import com.face.data.mapper.UrlMapper;
import com.face.data.youtu.Base.AddFacesResponse;
import com.face.data.youtu.Base.DelPersonResponse;
import com.face.data.youtu.Base.NewPersonResponse;
import com.face.model.user.UserFaceImagesEntity;
import com.face.service.qiniu.QiniuService;
import com.face.service.user.UserFaceImagesService;
import com.face.service.youtu.Youtu;
import com.face.service.youtu.YoutuService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by andy on 12/5/15.
 */

@Service
public class YoutuServiceImpl implements YoutuService{



    private static String GROUP_ID="FACE_SWIPING";

    @Autowired
    private Youtu youtu;
    @Override
    public JSONObject DetectFaceBase64(String image_base64, int mode) throws KeyManagementException, NoSuchAlgorithmException, JSONException, IOException {
        System.out.println(youtu.toString());
        return youtu.DetectFaceBase64(image_base64,mode);
    }

    @Override
    public JSONObject DetectFace(String file, int mode) throws KeyManagementException, NoSuchAlgorithmException, JSONException, IOException {
        return youtu.DetectFace(file, mode);
    }

    @Override
    public JSONObject DetectFaceURL(String url, int mode) throws KeyManagementException, NoSuchAlgorithmException, JSONException, IOException {
        return youtu.DetectFaceUrl(url, mode);
    }

    @Override
    public JSONObject AddFaceUrl(Long person_id, List<String> key_arr,UserFaceImagesService userFaceImagesService,QiniuService qiniuService) throws KeyManagementException, NoSuchAlgorithmException, JSONException, IOException {
        List<String> url_arr= UrlMapper.map2Urls(key_arr,qiniuService);

        if(userFaceImagesService.findAllByUserId(person_id).size()>0){
            JSONObject jsonObject= youtu.AddFaceUrl(person_id.toString(),url_arr);

            System.out.println(jsonObject);
            AddFacesResponse addFacesResponse=new ObjectMapper().readValue(jsonObject.toString(),AddFacesResponse.class);

            for(int i=0;i<addFacesResponse.getAdded();++i){
                UserFaceImagesEntity userFaceImagesEntity=new UserFaceImagesEntity();
                userFaceImagesEntity.setUserId(person_id);
                userFaceImagesEntity.setImageKey(key_arr.get(i));
                userFaceImagesEntity.setFaceId(addFacesResponse.getFaceIds().get(i));
                userFaceImagesService.save(userFaceImagesEntity);
            }
            return jsonObject;
        }else {
            List<String> groupIds=new ArrayList<>();
            groupIds.add(GROUP_ID);
            JSONObject jsonObject=youtu.NewPersonUrl(url_arr.get(0), person_id.toString(), groupIds);
            System.out.println(jsonObject);
            NewPersonResponse newPersonReponse= new ObjectMapper().readValue(jsonObject.toString(), NewPersonResponse.class);
            UserFaceImagesEntity userFaceImagesEntity=new UserFaceImagesEntity();
            userFaceImagesEntity.setUserId(person_id);
            userFaceImagesEntity.setImageKey(key_arr.get(0));
            userFaceImagesEntity.setFaceId(newPersonReponse.getFaceId());
            userFaceImagesService.save(userFaceImagesEntity);
            url_arr.remove(0);
            if(url_arr.size()>0) {
                jsonObject =youtu.AddFaceUrl(person_id.toString(), url_arr);
                AddFacesResponse addFacesResponse=new ObjectMapper().readValue(jsonObject.toString(), AddFacesResponse.class);
                for(int i=0;i<addFacesResponse.getAdded();++i){
                    UserFaceImagesEntity userFaceImagesEntity1=new UserFaceImagesEntity();
                    userFaceImagesEntity1.setUserId(person_id);
                    userFaceImagesEntity1.setImageKey(key_arr.get(i+1));
                    userFaceImagesEntity1.setFaceId(addFacesResponse.getFaceIds().get(i));
                    userFaceImagesService.save(userFaceImagesEntity1);
                }
                return jsonObject;

            }
            else return jsonObject;
        }



    }

    @Override
    public JSONObject GetFaceIds(String person_id) throws KeyManagementException, NoSuchAlgorithmException, JSONException, IOException {
        return youtu.GetFaceIds(person_id);
    }

    @Override
    public JSONObject DelFace(Long person_id, List<String> keys_arr,UserFaceImagesService userFaceImagesService,QiniuService qiniuService) throws KeyManagementException, NoSuchAlgorithmException, JSONException, IOException {

        List<UserFaceImagesEntity> userFaceImagesEntities=userFaceImagesService.findAllByUserId(person_id);
        List<String> face_ids_arr=new ArrayList<>();
        JSONObject jsonObject=null;
        if(userFaceImagesEntities.size()==keys_arr.size()){
            jsonObject = youtu.DelPerson(person_id.toString());
            DelPersonResponse delPersonResponse= new ObjectMapper().readValue(jsonObject.toString(),DelPersonResponse.class);
            userFaceImagesService.deleteAllByUserId(person_id);
        }else {
            jsonObject = youtu.DelFace(person_id.toString(),keys2faceIds(keys_arr, userFaceImagesEntities));

            for(String key:keys_arr){
                userFaceImagesService.deleteOneByImageKey(key);
            }
        }
        return jsonObject;
    }

    List<String> keys2faceIds(List<String> keys_arr,List<UserFaceImagesEntity> userFaceImagesEntities){


        List<String> faceIdsArr=new ArrayList<>();

        for(UserFaceImagesEntity userFaceImagesEntity:userFaceImagesEntities){
            if(keys_arr.contains(userFaceImagesEntity.getImageKey())){
                faceIdsArr.add(userFaceImagesEntity.getFaceId());
            }
        }
        return faceIdsArr;
    }


}
