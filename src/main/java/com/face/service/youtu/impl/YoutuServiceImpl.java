package com.face.service.youtu.impl;

import com.face.data.mapper.UrlMapper;
import com.face.data.user.FaceIdentifyUserBaseInfo;
import com.face.data.util.ResponseCode;
import com.face.data.youtu.Base.*;
import com.face.data.youtu.FaceResultBean;
import com.face.data.youtu.UserAddFacesResponse;
import com.face.data.youtu.UserFaceIdentifyRequest;
import com.face.data.youtu.UserFaceIdentifyResponse;
import com.face.model.user.User;
import com.face.model.user.UserFaceImagesEntity;
import com.face.service.qiniu.QiniuService;
import com.face.service.user.CustomUserDetailsService;
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

    @Autowired
    private UserFaceImagesService userFaceImagesService;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    @Autowired
    private QiniuService qiniuService;

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
    public UserAddFacesResponse AddFaceUrl(Long person_id, List<String> key_arr,UserFaceImagesService userFaceImagesService,QiniuService qiniuService) throws KeyManagementException, NoSuchAlgorithmException, JSONException, IOException {
        List<String> url_arr= UrlMapper.map2Urls(key_arr,qiniuService);
        UserAddFacesResponse userAddFacesResponse=new UserAddFacesResponse();
        List<UserFaceImagesEntity>  userFaceImagesEntities=userFaceImagesService.findAllByUserId(person_id);

        if(userFaceImagesEntities.size()>0){

            //删除已经存在的人脸

            List<String> old_faceId = new ArrayList<>();
            for(UserFaceImagesEntity userFaceImagesEntity:userFaceImagesEntities){
                old_faceId.add(userFaceImagesEntity.getFaceId());
            }
            //添加新的人脸
            JSONObject  jsonObject= youtu.AddFaceUrl(person_id.toString(), url_arr);
            System.out.println(jsonObject);
            AddFacesResponse addFacesResponse=new ObjectMapper().readValue(jsonObject.toString(),AddFacesResponse.class);

            if(addFacesResponse.getErrorcode()!=0){
                userAddFacesResponse.setResponseCode(ResponseCode.FACE_DETECT_FAILED);
                return userAddFacesResponse;
            }
            //删除已经存在的人脸
            jsonObject=youtu.DelFace(person_id.toString(), old_faceId);
            //DelPersonResponse delPersonResponse= new ObjectMapper().readValue(jsonObject.toString(),DelPersonResponse.class);
            userFaceImagesService.deleteAllByUserId(person_id);

            for(int i=0;i<addFacesResponse.getFaceIds().size();++i){
                FaceResultBean faceResultBean=new FaceResultBean();
                if(addFacesResponse.getFaceIds().get(i)!="") {
                    UserFaceImagesEntity userFaceImagesEntity = new UserFaceImagesEntity();
                    userFaceImagesEntity.setUserId(person_id);
                    userFaceImagesEntity.setImageKey(key_arr.get(i));
                    userFaceImagesEntity.setFaceId(addFacesResponse.getFaceIds().get(i));
                    userFaceImagesService.save(userFaceImagesEntity);
                    faceResultBean.setKey(key_arr.get(i));
                    faceResultBean.setResponseCode(ResponseCode.SUCCESS);
                }else{
                    faceResultBean.setKey(key_arr.get(i));
                    faceResultBean.setErrorcode(addFacesResponse.getRetCodes().get(i));
                }
                userAddFacesResponse.addFaceResultBean(faceResultBean);
            }
        }else {

            List<String> groupIds=new ArrayList<>();
            groupIds.add(GROUP_ID);
            NewPersonResponse newPersonReponse=null;
            JSONObject jsonObject=null;
            jsonObject= youtu.NewPersonUrl(url_arr.get(0), person_id.toString(), groupIds);
            System.out.println(jsonObject);
            newPersonReponse=new ObjectMapper().readValue(jsonObject.toString(), NewPersonResponse.class);
            FaceResultBean faceResultBean=new FaceResultBean();
            faceResultBean.setKey(key_arr.get(0));
            if(newPersonReponse.getErrorcode()!=0){
                //faceResultBean.setErrorcode(newPersonReponse.getErrorcode());

                if(newPersonReponse.getErrorcode()==-1302){


                    jsonObject = youtu.DelPerson(person_id.toString());
                    jsonObject= youtu.NewPersonUrl(url_arr.get(0), person_id.toString(), groupIds);
                    newPersonReponse=new ObjectMapper().readValue(jsonObject.toString(), NewPersonResponse.class);

                }else {
                    faceResultBean.setErrorcode(newPersonReponse.getErrorcode());
                }

            }else if(newPersonReponse.getErrorcode()==0){
                faceResultBean.setResponseCode(ResponseCode.SUCCESS);
            }else{
                faceResultBean.setErrorcode(newPersonReponse.getErrorcode());

            }
            userAddFacesResponse.addFaceResultBean(faceResultBean);



            if(newPersonReponse.getErrorcode()!=0){

                userAddFacesResponse.setResponseCode(ResponseCode.FACE_DETECT_FAILED);
                return userAddFacesResponse;
            }
            UserFaceImagesEntity userFaceImagesEntity1=new UserFaceImagesEntity();
            userFaceImagesEntity1.setUserId(person_id);
            userFaceImagesEntity1.setImageKey(key_arr.get(0));
            userFaceImagesEntity1.setFaceId(newPersonReponse.getFaceId());
            userFaceImagesService.save(userFaceImagesEntity1);
//            List<String> groupIds=new ArrayList<>();
//            groupIds.add(GROUP_ID);
//            NewPersonResponse newPersonReponse=null;
//            JSONObject jsonObject=null;
//            int index=0;
//            do {
//
//                jsonObject= youtu.NewPersonUrl(url_arr.get(index++), person_id.toString(), groupIds);
//                System.out.println(jsonObject);
//                newPersonReponse=new ObjectMapper().readValue(jsonObject.toString(), NewPersonResponse.class);
//                FaceResultBean faceResultBean=new FaceResultBean();
//                faceResultBean.setKey(key_arr.get(index-1));
//                if(newPersonReponse.getErrorcode()!=0){
//                    faceResultBean.setErrorcode(newPersonReponse.getErrorcode());
//                }else {
//                    faceResultBean.setResponseCode(ResponseCode.SUCCESS);
//                }
//                userAddFacesResponse.addFaceResultBean(faceResultBean);
//            }while (newPersonReponse.getErrorcode()!=0 && index <url_arr.size());
//
//
//            UserFaceImagesEntity userFaceImagesEntity1=new UserFaceImagesEntity();
//            userFaceImagesEntity1.setUserId(person_id);
//            userFaceImagesEntity1.setImageKey(key_arr.get(index - 1));
//            userFaceImagesEntity1.setFaceId(newPersonReponse.getFaceId());
//            userFaceImagesService.save(userFaceImagesEntity1);
//
//

//            System.out.println("url_arr.subList(" + index + ","+url_arr.size()+")-->");
//            url_arr=url_arr.subList(index,url_arr.size());
//
//            for(String url:url_arr){
//                System.out.println("-->"+url);
//            }
//            if(url_arr.size()>0) {
//                jsonObject =youtu.AddFaceUrl(person_id.toString(), url_arr);
//                AddFacesResponse addFacesResponse=new ObjectMapper().readValue(jsonObject.toString(), AddFacesResponse.class);
//                for(int i=0;i<addFacesResponse.getFaceIds().size();++i){
//                    FaceResultBean faceResultBean=new FaceResultBean();
//
//                    System.out.println("==>"+addFacesResponse.getFaceIds().get(i));
//                    if(addFacesResponse.getFaceIds().get(i)!="") {
//                        UserFaceImagesEntity userFaceImagesEntity = new UserFaceImagesEntity();
//                        userFaceImagesEntity.setUserId(person_id);
//                        userFaceImagesEntity.setImageKey(key_arr.get(i+index));
//                        userFaceImagesEntity.setFaceId(addFacesResponse.getFaceIds().get(i));
//                        userFaceImagesService.save(userFaceImagesEntity);
//                        faceResultBean.setKey(key_arr.get(i+index));
//                        faceResultBean.setResponseCode(ResponseCode.SUCCESS);
//                    }else{
//                        faceResultBean.setKey(key_arr.get(i+index));
//                        faceResultBean.setErrorcode(addFacesResponse.getRetCodes().get(i));
//                    }
//                    userAddFacesResponse.addFaceResultBean(faceResultBean);
//                }
//            }
        }


        //userAddFacesResponse.setResponseCode();
        return userAddFacesResponse;

    }




    @Override
    public JSONObject GetFaceIds(String person_id) throws KeyManagementException, NoSuchAlgorithmException, JSONException, IOException {
        return youtu.GetFaceIds(person_id);
    }

    @Override
    public UserFaceIdentifyResponse FaceIdentifyUrl(UserFaceIdentifyRequest userFaceIdentifyRequest,User myUser) throws KeyManagementException, NoSuchAlgorithmException, JSONException, IOException {
        JSONObject re=youtu.FaceIdentifyUrl(UrlMapper.map2Url(userFaceIdentifyRequest.getKey(), qiniuService),GROUP_ID);

        System.out.println(re);
        IdentityFaceResponse identityFaceResponse=new ObjectMapper().readValue(re.toString(), IdentityFaceResponse.class);
        UserFaceIdentifyResponse userFaceIdentifyResponse=new UserFaceIdentifyResponse();
        /**
         * 转化为用户基本信息
         */
        for(Candidate candidate:identityFaceResponse.getCandidates()){
            if(Long.parseLong(candidate.getPersonId())==myUser.getId()){
                continue;
            }
            User user=customUserDetailsService.findOne(Long.parseLong(candidate.getPersonId()));
            FaceIdentifyUserBaseInfo faceIdentifyUserBaseInfo=new FaceIdentifyUserBaseInfo();
            List<UserFaceImagesEntity>  userFaceImagesEntities=userFaceImagesService.findAllByUserId(user.getId());

            if(userFaceImagesEntities.size()>0){
                faceIdentifyUserBaseInfo.setHeadImageUrl(qiniuService.createPrivateUrl(userFaceImagesEntities.get(0).getImageKey()));


            }else {
                continue;
            }
            faceIdentifyUserBaseInfo.setConfidence(candidate.getConfidence());
            faceIdentifyUserBaseInfo.setId(user.getId());
            faceIdentifyUserBaseInfo.setNickName(user.getUserDetailInfoEntity().getNickName());
            faceIdentifyUserBaseInfo.setUserName(user.getUsername());




            if(customUserDetailsService.getFriend(myUser.getId(),user.getId())!=null){
                faceIdentifyUserBaseInfo.setIsFriends(1);
            }else {
                faceIdentifyUserBaseInfo.setIsFriends(0);
            }
            userFaceIdentifyResponse.add(faceIdentifyUserBaseInfo);
        }
        userFaceIdentifyResponse.setResponseCode(ResponseCode.SUCCESS);
        return userFaceIdentifyResponse;
    }



    @Override
    public UserFaceIdentifyResponse FaceIdentifyBase64(StringBuffer image_data,User myUser) throws KeyManagementException, NoSuchAlgorithmException, JSONException, IOException {
        JSONObject re=youtu.FaceIdentifyBase64(image_data, GROUP_ID);

        //System.out.println(re);
        IdentityFaceResponse identityFaceResponse=new ObjectMapper().readValue(re.toString(), IdentityFaceResponse.class);
        UserFaceIdentifyResponse userFaceIdentifyResponse=new UserFaceIdentifyResponse();

        /**
         * 转化为用户基本信息
         */
        for(Candidate candidate:identityFaceResponse.getCandidates()){
            User user=customUserDetailsService.findOne(Long.parseLong(candidate.getPersonId()));
            FaceIdentifyUserBaseInfo faceIdentifyUserBaseInfo=new FaceIdentifyUserBaseInfo();
            faceIdentifyUserBaseInfo.setConfidence(candidate.getConfidence());
            faceIdentifyUserBaseInfo.setId(user.getId());
            faceIdentifyUserBaseInfo.setNickName(user.getUserDetailInfoEntity().getNickName());
            faceIdentifyUserBaseInfo.setUserName(user.getUsername());
            faceIdentifyUserBaseInfo.setHeadImageUrl(qiniuService.createPrivateUrl(user.getUserDetailInfoEntity().getHeadImageKey()));


            UserFaceImagesEntity userFaceImagesEntity=userFaceImagesService.findAllByUserId(user.getId()).get(0);

            faceIdentifyUserBaseInfo.setFaceImageUrl(qiniuService.createPrivateUrl(userFaceImagesEntity.getImageKey()));



            userFaceIdentifyResponse.add(faceIdentifyUserBaseInfo);
        }
        userFaceIdentifyResponse.setResponseCode(ResponseCode.SUCCESS);
        return userFaceIdentifyResponse;
    }

    @Override
    public JSONObject DelFace(Long person_id, List<String> keys_arr,UserFaceImagesService userFaceImagesService,QiniuService qiniuService) throws KeyManagementException, NoSuchAlgorithmException, JSONException, IOException {

        List<UserFaceImagesEntity> userFaceImagesEntities=userFaceImagesService.findAllByUserId(person_id);
        List<String> face_ids_arr=new ArrayList<>();
        JSONObject jsonObject=null;
        if(userFaceImagesEntities.size() <= keys_arr.size()){
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
