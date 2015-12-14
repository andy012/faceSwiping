package com.face.init.config;

import com.gexin.rp.sdk.http.IGtPush;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import javax.annotation.Resource;

/**
 * Created by andy on 12/11/15.
 */
@Configuration
@PropertySource("classpath:application.properties")
public class GetuiConfig {
    @Resource
    private Environment env;
    private static String GE_TUI_HOST="GE_TUI_HOST";
    private static String GE_TUI_APP_ID ="GE_TUI_APP_ID";
    private static String GE_TUI_APP_KEY ="GE_TUI_APP_KEY";
    private static String GE_TUI_MASTER ="GE_TUI_MASTER";
    public static String GE_TUI_APP_ID_VALUE ="";
    public static String GE_TUI_APP_KEY_VALUE ="";
    public static String GE_TUI_MASTER_VALUE ="";
    public static String GE_TUI_HOST_VALUE ="";


    @Bean
    public IGtPush getIGtPush(){
        this.GE_TUI_APP_ID_VALUE=env.getRequiredProperty(GE_TUI_APP_ID);
        this.GE_TUI_APP_KEY_VALUE=env.getRequiredProperty(GE_TUI_APP_KEY);
        this.GE_TUI_MASTER_VALUE=env.getRequiredProperty(GE_TUI_MASTER);
        this.GE_TUI_HOST_VALUE=env.getRequiredProperty(GE_TUI_HOST);
        return new IGtPush(env.getRequiredProperty(GE_TUI_HOST),
                env.getRequiredProperty(GE_TUI_APP_KEY),
                env.getRequiredProperty(GE_TUI_MASTER));
    }

}
