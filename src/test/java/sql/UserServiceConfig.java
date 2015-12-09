package sql;

import com.face.service.user.CustomUserDetailsService;
import com.face.service.user.impl.CustomUserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserServiceConfig {

    @Bean
    public CustomUserDetailsService userJPAServece(){ return new CustomUserDetailsServiceImpl();}


}

