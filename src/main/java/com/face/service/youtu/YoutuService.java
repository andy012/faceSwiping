package com.face.service.youtu;

import com.face.data.youtu.Base.AddFacesResponse;
import com.face.data.youtu.UserAddFacesResponse;
import com.face.service.qiniu.QiniuService;
import com.face.service.user.UserFaceImagesService;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 * Created by andy on 12/5/15.
 */
public interface YoutuService {

    public JSONObject DetectFaceBase64(String image_base64, int mode) throws KeyManagementException, NoSuchAlgorithmException, JSONException, IOException;

    public JSONObject DetectFace(String file, int mode) throws KeyManagementException, NoSuchAlgorithmException, JSONException, IOException;
    public JSONObject DetectFaceURL(String url, int mode) throws KeyManagementException, NoSuchAlgorithmException, JSONException, IOException;
    public UserAddFacesResponse AddFaceUrl(Long person_id, List<String> key_arr,UserFaceImagesService userFaceImagesService,QiniuService qiniuService) throws KeyManagementException, NoSuchAlgorithmException, JSONException, IOException ;
    JSONObject GetFaceIds(String person_id) throws KeyManagementException, NoSuchAlgorithmException, JSONException, IOException;

    public JSONObject DelFace(Long person_id, List<String> keys_arr,UserFaceImagesService userFaceImagesService,QiniuService qiniuService) throws KeyManagementException, NoSuchAlgorithmException, JSONException, IOException ;
}
