package com.face.web.youtu;

import com.face.data.mapper.UrlMapper;
import com.face.data.user.UserBaseInfo;
import com.face.data.youtu.UserFacesRequest;
import com.face.data.youtu.UserFacesResponse;
import com.face.model.user.User;
import com.face.model.user.UserFaceImagesEntity;
import com.face.service.qiniu.QiniuService;
import com.face.service.user.UserFaceImagesService;
import com.face.service.youtu.Youtu;
import com.face.service.youtu.YoutuService;
import com.face.web.common.BaseController;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
    private QiniuService qiniuService;
    @RequestMapping(value = "/youtu/face/detect", method = RequestMethod.POST)
    public String detectFace(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        StringBuffer imageBase64=new StringBuffer();
        JSONObject re=null;
//        String line;
//        BufferedReader reader = request.getReader();
//        while ((line = reader.readLine()) != null)
//            imageBase64.append(line);
        try {
            String url=qiniuService.createPrivateUrl("12345");
            System.out.println(url);
           // re = youtuService.DetectFaceBase64(imageBase64.toString(),1); "http://face-10013840.file.myqcloud.com/test.jpg"
            re=youtuService.DetectFaceURL(url,1);
            System.out.println(re);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return re.toString();
    }


    @RequestMapping(value = "/user/faces", method = RequestMethod.PUT)
    public String addFace(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JSONObject re=null;
        UserBaseInfo userBaseInfo=getUserBase();
        UserFacesRequest faceAddRequest= new ObjectMapper().readValue(request.getInputStream(), UserFacesRequest.class);
        try {


            re=youtuService.AddFaceUrl(userBaseInfo.getId(),faceAddRequest.getKeys(),userFaceImagesService,qiniuService);

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

    @RequestMapping(value = "/user/faces", method = RequestMethod.DELETE)
    public String deleteFace(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JSONObject re=null;
        UserBaseInfo userBaseInfo=getUserBase();
        UserFacesRequest faceAddRequest= new ObjectMapper().readValue(request.getInputStream(), UserFacesRequest.class);
        try {


            re=youtuService.DelFace(userBaseInfo.getId(),faceAddRequest.getKeys(),userFaceImagesService,qiniuService);
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
        User user= getUser();
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


}
