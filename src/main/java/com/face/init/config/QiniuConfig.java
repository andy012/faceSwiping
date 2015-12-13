package com.face.init.config;

import com.face.service.youtu.Youtu;
import com.qiniu.util.Auth;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import javax.annotation.Resource;

/**
 *
 * 七牛图库配置类
 * Created by andy on 12/6/15.
 */
@Configuration
@PropertySource("classpath:application.properties")
public class QiniuConfig {

    @Resource
    private Environment env;
    public static final String ACCESS_KEY = "QINIU_ACCESS_KEY";
    public static final String SECRET_KEY = "QINIU_SECRET_KEY";
    public static final String DOMAIN="QINIU_DOMAIN";
    public static final String BUCKET="QINIU_BUCKET";
    private static  String DOMAIN_VALUE;
    private static  String BUCKET_VALUE;

    @Bean
    public Auth initAuthProperties(){
        DOMAIN_VALUE=env.getRequiredProperty(DOMAIN);
        BUCKET_VALUE=env.getRequiredProperty(BUCKET);
        return Auth.create(env.getRequiredProperty(ACCESS_KEY),env.getRequiredProperty(SECRET_KEY));
    }

    public static String getDomainValue() {
        return DOMAIN_VALUE;
    }

    public static void setDomainValue(String domainValue) {
        DOMAIN_VALUE = domainValue;
    }

    public static String getBucketValue() {
        return BUCKET_VALUE;
    }

    public static void setBucketValue(String bucketValue) {
        BUCKET_VALUE = bucketValue;
    }
}
