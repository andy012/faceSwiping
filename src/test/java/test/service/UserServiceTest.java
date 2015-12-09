package test.service;

import com.face.model.user.User;
import com.face.model.user.UserRole;
import com.face.service.user.CustomUserDetailsService;
import sql.DataSourceTestConfig;
import sql.UserServiceConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

/**
 * Created by sks on 2015/11/9.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration( classes = {DataSourceTestConfig.class, UserServiceConfig.class}, loader = AnnotationConfigContextLoader.class)
public class UserServiceTest {

    @Autowired private CustomUserDetailsService userService;
    @Test
    public void addUser() {

        User user=new User();



        //设置密码
        BCryptPasswordEncoder bCryptPasswordEncoder=new BCryptPasswordEncoder();
        System.out.println(bCryptPasswordEncoder.encode("pass"));

        user.setPassword(bCryptPasswordEncoder.encode("pass"));
        System.out.println(user.getPassword());
        user.setUsername("zhoujielun@face.com");
        user.setExpires(100000L);
        user.grantRole(UserRole.USER);
        System.out.println(user);
        try {
            userService.save(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    public void testReflect(){

        try {
            Class<?>  obj1=Class.forName("test.service.UserServiceTest");

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
