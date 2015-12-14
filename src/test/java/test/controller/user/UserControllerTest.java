package test.controller.user;

import com.alibaba.fastjson.JSON;
import com.face.data.user.UserSecretRequest;
import com.face.init.Initializer;
import com.face.init.config.WebAppConfig;
import com.face.service.qiniu.QiniuService;
import com.qiniu.util.Json;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.Resource;
import javax.servlet.annotation.WebListener;

/**
 * Created by andy on 12/8/15.
 */


@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration(value = "src/main/webapp")
@ContextHierarchy({
        @ContextConfiguration(name = "parent", classes = Initializer.class),
       @ContextConfiguration(name = "child", classes = {WebAppConfig.class})
})
@WebListener
public class UserControllerTest {


    String X_AUTH_TOKEN="eyJpZCI6MTksInVzZXJuYW1lIjoidGVzdDFAZmFjZS5jb20iLCJleHBpcmVzIjoxNDUwNDM3NDY3NDA0LCJyb2xlcyI6WyJVU0VSIl0sInNpbXBsZU9iamVjdCI6eyJpZEYiOjE5fX0=.cUZEfpMiWRicx7Avx3PAaeJN0zG9yUs61z7I8xHhBTg=";

    /**
     * 为controller注册过滤器
     */
    @Resource
    private FilterChainProxy springSecurityFilterChain;


    @Autowired
    private WebApplicationContext wac;
    private MockMvc mockMvc;

    @Autowired
    QiniuService qiniuService;
    @Before
    public void setUp() {

        //Map<String,FilterRegistration> map=wac.getServletContext().getFilterRegistrations();

        mockMvc = MockMvcBuilders.webAppContextSetup(wac).addFilters(springSecurityFilterChain).build();


      //  System.out.println(wac.getServletContext().getFilterRegistration("statelessAuthenticationFilter"));

    }

    @Test
    public void testLogin(){

        String loginData="{ \"username\":\"1\", \"password\":\"1\"}";
        try {
            System.out.println(MediaType.APPLICATION_JSON);
            System.out.println(loginData);
            MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/login").content(loginData).contentType(MediaType.APPLICATION_JSON)).andReturn();
//            System.out.println(result.getRequest().getServerPort());
//            System.out.println(result.getRequest().getRequestURL());
            X_AUTH_TOKEN=result.getResponse().getHeader("X-AUTH-TOKEN");
            //System.out.println(result.getResponse().getHeader("X-AUTH-TOKEN"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        // doAuthenticatedExchange("test1@face.com",HttpMethod.GET,"api/users/current",null,"pass");
    }


    @Test
    public void testGetCurrentUser(){
        testLogin();
      try {
          MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/user/currentUser").headers(getHttpHeaders())).andDo(MockMvcResultHandlers.print()).andReturn();
          System.out.println(result.getResponse().getContentAsString());
        } catch (Exception e) {
            e.printStackTrace();
        }


        // doAuthenticatedExchange("test1@face.com",HttpMethod.GET,"api/users/current",null,"pass");
    }



    public HttpHeaders getHttpHeaders(){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        httpHeaders.add("X-AUTH-TOKEN", X_AUTH_TOKEN);

        return httpHeaders;
    }




    @Test
    public void testGetSelfFaces(){

        try {

            MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/youtu/faces/self").headers(getHttpHeaders())).andDo(MockMvcResultHandlers.print()).andReturn();
            System.out.println(result.getResponse().getContentAsString());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Test
    public void testAddFace(){

        String key="dhgtest.jpg";

        try {
            MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/youtu/face/detect")).andDo(MockMvcResultHandlers.print()).andReturn();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testChangeSecret() {
        testLogin();
        try {
            MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/user/secret/1").headers(getHttpHeaders())).andDo(MockMvcResultHandlers.print()).andReturn();
            System.out.println(result.getResponse().getContentAsString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    @Test
    public void testChangeGetuiClientId() {
        //System.out.println(JSON.toJSONString(userSecretRequest));
        testLogin();
        try {
            MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/user/getui/123234565").headers(getHttpHeaders())).andDo(MockMvcResultHandlers.print()).andReturn();
            System.out.println(result.getResponse().getContentAsString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetuiSendSingle() {
        //System.out.println(JSON.toJSONString(userSecretRequest));
        testLogin();
        try {
            MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/user/19/friend/image").headers(getHttpHeaders())).andDo(MockMvcResultHandlers.print()).andReturn();
            System.out.println(result.getResponse().getContentAsString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    public void testGetCertification() {
        //System.out.println(JSON.toJSONString(userSecretRequest));
        testLogin();
        try {
            MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/user/certification").headers(getHttpHeaders())).andDo(MockMvcResultHandlers.print()).andReturn();
            System.out.println(result.getResponse().getContentAsString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testAddFriends() {
        //System.out.println(JSON.toJSONString(userSecretRequest));
        testLogin();
        try {
            for(int i=32;i<=36;++i) {
                MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/user/friend/"+i).headers(getHttpHeaders())).andDo(MockMvcResultHandlers.print()).andReturn();
                System.out.println(result.getResponse().getContentAsString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetFriends() {
        //System.out.println(JSON.toJSONString(userSecretRequest));
        testLogin();
        try {

            MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/user/friends").headers(getHttpHeaders())).andDo(MockMvcResultHandlers.print()).andReturn();
            System.out.println(result.getResponse().getContentAsString());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
