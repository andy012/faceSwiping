package com.face.init.config;

import com.face.service.youtu.Youtu;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import javax.annotation.Resource;

/**
 * Created by andy on 12/6/15.
 */
@Configuration
@PropertySource("classpath:application.properties")
public class YoutuConfig {

    @Resource
    private Environment env;
    public static final String APP_ID = "YOUTU_APP_ID";
    public static final String SECRET_ID = "YOUTU_SECRET_ID";
    public static final String SECRET_KEY = "YOUTU_SECRET_KEY";

    private static  String APP_ID_VALUE ;
    private static  String SECRET_ID_VALUE ;
    private static  String SECRET_KEY_VALUE ;

    @Bean
    public Youtu initYoutuProperties(){

        APP_ID_VALUE=env.getRequiredProperty(APP_ID);
        SECRET_ID_VALUE=env.getRequiredProperty(SECRET_ID);
        SECRET_KEY_VALUE=env.getRequiredProperty(SECRET_KEY);
        return new Youtu(YoutuConfig.getAppIdValue(),YoutuConfig.getSecretIdValue(),
                YoutuConfig.getSecretKeyValue(), Youtu.API_YOUTU_END_POINT);
    }

    public static String getAppIdValue() {
        return APP_ID_VALUE;
    }



    public static String getSecretIdValue() {
        return SECRET_ID_VALUE;
    }



    public static String getSecretKeyValue() {
        return SECRET_KEY_VALUE;
    }


}
