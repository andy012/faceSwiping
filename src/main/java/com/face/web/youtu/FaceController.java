package com.face.web.youtu;

import com.alibaba.fastjson.JSON;
import com.face.data.mapper.UrlMapper;
import com.face.data.user.UserBaseInfo;
import com.face.data.util.ResponseCode;
import com.face.data.youtu.*;
import com.face.data.youtu.Base.DetectionFaceResponse;
import com.face.model.user.User;
import com.face.model.user.UserFaceImagesEntity;
import com.face.service.qiniu.QiniuService;
import com.face.service.user.CustomUserDetailsService;
import com.face.service.user.UserFaceImagesService;
import com.face.service.youtu.Youtu;
import com.face.service.youtu.YoutuService;
import com.face.util.CutPicture;
import com.face.web.common.BaseController;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 * Created by andy on 12/7/15.
 */
@RestController
public class FaceController extends BaseController{

    @Autowired
    private UserFaceImagesService userFaceImagesService;
    @Autowired
    private YoutuService youtuService;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    @Autowired
    private QiniuService qiniuService;
    @RequestMapping(value = "/user/face/detect", method = RequestMethod.GET)
    public String detectFace(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        StringBuffer imageBase64=new StringBuffer();
        JSONObject re=null;
//        String line;
//        BufferedReader reader = request.getReader();
//        while ((line = reader.readLine()) != null)
//            imageBase64.append(line);

        UserFacesRequest faceDetect= new ObjectMapper().readValue(request.getInputStream(), UserFacesRequest.class);
        try {
            String url=qiniuService.createPrivateUrl(faceDetect.getKeys().get(0));
            System.out.println(url);
           // re = youtuService.DetectFaceBase64(imageBase64.toString(),1); "http://face-10013840.file.myqcloud.com/test.jpg"
            re=youtuService.DetectFaceURL(url,0);

            DetectionFaceResponse detectionFaceResponse=new ObjectMapper().readValue(re.toString(),DetectionFaceResponse.class);

            System.out.println(JSON.toJSONString(detectionFaceResponse));

            System.out.println(re);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return re.toString();
    }


    @RequestMapping(value = "/user/faces", method = RequestMethod.PUT)
    public String addFace(HttpServletRequest request, HttpServletResponse response) throws IOException {
        UserBaseInfo userBaseInfo=getUserBase();
        UserFacesRequest faceAddRequest= new ObjectMapper().readValue(request.getInputStream(), UserFacesRequest.class);
        UserAddFacesResponse userAddFacesResponse=null;
        try {
            userAddFacesResponse=youtuService.AddFaceUrl(userBaseInfo.getId(),faceAddRequest.getKeys(),userFaceImagesService,qiniuService);
//            String url=qiniuService.createPrivateUrl("12345");
//            System.out.println(url);
//            // re = youtuService.DetectFaceBase64(imageBase64.toString(),1); "http://face-10013840.file.myqcloud.com/test.jpg"
//            re=youtuService.DetectFaceURL(url,1);
//            System.out.println(re);
        } catch (Exception e) {
            e.printStackTrace();
        }
        UserAddFacesResponseOne userAddFacesResponseOne = new UserAddFacesResponseOne();
        if(userAddFacesResponse.getErrorcode()==0) {
            userAddFacesResponseOne.setData(qiniuService.createPrivateUrl(userAddFacesResponse.getData().get(0).getKey()));
            userAddFacesResponseOne.setResponseCode(ResponseCode.SUCCESS);
        }else {
            userAddFacesResponseOne.setResponseCode(ResponseCode.FACE_DETECT_FAILED);
        }
        System.out.println(JSON.toJSONString(userAddFacesResponseOne));
        return JSON.toJSONString(userAddFacesResponseOne);
    }
    @RequestMapping(value = "/user/faces", method = RequestMethod.DELETE)
    public String deleteFace(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JSONObject re=null;
        UserBaseInfo userBaseInfo=getUserBase();
        UserFacesRequest faceAddRequest= new ObjectMapper().readValue(request.getInputStream(), UserFacesRequest.class);
        try {
            re=youtuService.DelFace(userBaseInfo.getId(), faceAddRequest.getKeys(), userFaceImagesService, qiniuService);
//            String url=qiniuService.createPrivateUrl("12345");
//            System.out.println(url);
//            // re = youtuService.DetectFaceBase64(imageBase64.toString(),1); "http://face-10013840.file.myqcloud.com/test.jpg"
//            re=youtuService.DetectFaceURL(url,1);
//            System.out.println(re);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return re.toString();
    }

    /**
     *
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/user/faces", method = RequestMethod.GET)
    public UserFacesResponse getUserFaces(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //JSONObject re=null;
        User user=getUser();

        List<UserFaceImagesEntity> userFaceImagesEntities=null;
        try {
            userFaceImagesEntities=userFaceImagesService.findAllByUserId(user.getId());
            System.out.println(youtuService.GetFaceIds(user.getId().toString()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        UserFacesResponse userFacesResponse=new UserFacesResponse(UrlMapper.linkedHashMap2Urls(userFaceImagesEntities,qiniuService));
        return userFacesResponse;//re.toString();
    }
    @RequestMapping(value = "/user/face/identify/{key}",method = RequestMethod.GET)
    public UserFaceIdentifyResponseArray faceIdentify(@PathVariable String key, HttpServletRequest request, HttpServletResponse response){
        System.out.println("***********************************");
        UserFaceIdentifyResponseArray userFaceIdentifyResponseArray=new UserFaceIdentifyResponseArray();

        UserFaceIdentifyRequest userFaceIdentifyRequest=new UserFaceIdentifyRequest();
        userFaceIdentifyRequest.setKey(key);

        try {
            //UserFaceIdentifyRequest userFaceIdentifyRequest= new ObjectMapper().readValue(request.getInputStream(), UserFaceIdentifyRequest.class);
            String url=qiniuService.createPrivateUrl(key);
            JSONObject re=youtuService.DetectFaceURL(url, 0);
//            DetectionFaceResponse detectionFaceResponse=new ObjectMapper().readValue(re.toString(), DetectionFaceResponse.class);
//            //CutPicture.findAllFaces(url,detectionFaceResponse);
//            BufferedImage bufferedImage=CutPicture.getBufferImageByUser(url);
//            StringBuffer stringBuffer=new StringBuffer();
//            for(DetectionFaceResponse.FaceEntity faceEntity:detectionFaceResponse.getFace()){
//                stringBuffer.append(CutPicture.cropImage(bufferedImage, faceEntity.getX(), faceEntity.getY(), faceEntity.getWidth(), faceEntity.getHeight()));
//                //System.out.println(stringBuffer.toString());
//                UserFaceIdentifyResponse userFaceIdentifyResponse=null;
//                userFaceIdentifyResponse=youtuService.FaceIdentifyBase64(stringBuffer,getUser());
//                userFaceIdentifyResponse.setX(faceEntity.getX());userFaceIdentifyResponse.setY(faceEntity.getY());
//                userFaceIdentifyResponse.setWidth(faceEntity.getWidth());userFaceIdentifyResponse.setHeight(faceEntity.getHeight());
//                //System.out.println(JSON.toJSONString(faceEntity));
//                //System.out.println(JSON.toJSONString(userFaceIdentifyResponse));
//                userFaceIdentifyResponseArray.add(userFaceIdentifyResponse);
//            }
            UserFaceIdentifyResponse userFaceIdentifyResponse=youtuService.FaceIdentifyUrl(userFaceIdentifyRequest,getUser());

            userFaceIdentifyResponseArray.add(userFaceIdentifyResponse);


        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        System.out.println(JSON.toJSONString(userFaceIdentifyResponseArray));
        return userFaceIdentifyResponseArray;
    }

    @RequestMapping(value = "/user/face/identifyOne",method = RequestMethod.GET)
    public UserFaceIdentifyResponseOne faceIdentifyOne(HttpServletRequest request, HttpServletResponse response){
        UserFaceIdentifyResponse userFaceIdentifyResponse=null;
        try {
            UserFaceIdentifyRequest userFaceIdentifyRequest= new ObjectMapper().readValue(request.getInputStream(), UserFaceIdentifyRequest.class);
            userFaceIdentifyResponse=youtuService.FaceIdentifyUrl(userFaceIdentifyRequest,getUser());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        UserFaceIdentifyResponseOne userFaceIdentifyResponseOne=new UserFaceIdentifyResponseOne();
        if(userFaceIdentifyResponse!=null) {
            userFaceIdentifyResponseOne.add(userFaceIdentifyResponse.getData());
            userFaceIdentifyResponseOne.setErrorcode(userFaceIdentifyResponse.getErrorcode());
            userFaceIdentifyResponseOne.setErrormsg(userFaceIdentifyResponse.getErrormsg());
        }else {
            userFaceIdentifyResponseOne.setResponseCode(ResponseCode.FAIL);
        }
        return userFaceIdentifyResponseOne;
    }


}
