package com.face.service.qiniu.impl;

import com.face.init.config.QiniuConfig;
import com.face.service.qiniu.QiniuService;
import com.qiniu.util.Auth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by andy on 12/8/15.
 */
@Service
public class QiniuServiceImpl implements QiniuService {

    @Autowired
    private Auth auth;


    @Override
    public String createPrivateUrl(String key) {


        return QiniuConfig.getDomainValue()+key;
        //return auth.privateDownloadUrl(QiniuConfig.getDomainValue()+key);
    }

    @Override
    public String getToken() {

      //  System.out.println("--->"+QiniuConfig.getBucketValue());
        return auth.uploadToken(QiniuConfig.getBucketValue());
    }


}
