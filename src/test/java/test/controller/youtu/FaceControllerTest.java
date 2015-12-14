package test.controller.youtu;

import com.alibaba.fastjson.JSON;
import com.face.data.youtu.UserFaceIdentifyRequest;
import com.face.data.youtu.UserFacesRequest;
import com.face.init.Initializer;
import com.face.init.config.WebAppConfig;
import com.face.service.qiniu.QiniuService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
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
public class FaceControllerTest {
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

        String loginData="{ \"username\":\"Judy\", \"password\":\"1\"}";
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

    public HttpHeaders getHttpHeaders(){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        httpHeaders.add("X-AUTH-TOKEN", X_AUTH_TOKEN);

        return httpHeaders;
    }
    @Test
    public void testGetUserFaces(){
        testLogin();
        try {
            MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/user/faces/").headers(getHttpHeaders())).andDo(MockMvcResultHandlers.print()).andReturn();
            System.out.println(result.getResponse().getContentAsString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    public void testAddFace(){
        testLogin();
        UserFacesRequest userFacesRequest=new UserFacesRequest();
        userFacesRequest.addKey("15900176-b0ba-4b32-b30f-1df3452218b0");
//        userFacesRequest.addKey("zhoujielun1.jpg");
//        userFacesRequest.addKey("zhoujielun2.jpg");
//
//        userFacesRequest.addKey("zhoujielun3.jpg");

        System.out.println(JSON.toJSONString(userFacesRequest));
//        userFacesRequest.addKey("dhgtest2.jpg");
//        userFacesRequest.addKey("dhgtest3.jpg");
       // userFacesRequest.addKey("dhgtest4.jpg");
        System.out.println(JSON.toJSONString(userFacesRequest));
        //userFacesRequest.addKey("dhgtest2.jpg");
        try {
            MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/user/faces").headers(getHttpHeaders()).content(JSON.toJSONString(userFacesRequest)).contentType(MediaType.APPLICATION_JSON)).andDo(MockMvcResultHandlers.print()).andReturn();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testDelFace(){

        testLogin();
        UserFacesRequest userFacesRequest=new UserFacesRequest();
        userFacesRequest.addKey("zhoujielun1.jpg");
        //userFacesRequest.addKey("zhoujielun2.jpg");
       // userFacesRequest.addKey("zhoujielun3.jpg");
//        userFacesRequest.addKey("dhgtest3.jpg");
//
//        userFacesRequest.addKey("dhgtest4.jpg");
        try {
            MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete("/user/faces").headers(getHttpHeaders()).content(JSON.toJSONString(userFacesRequest)).contentType(MediaType.APPLICATION_JSON)).andDo(MockMvcResultHandlers.print()).andReturn();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    public void testIdentifyFace(){

        testLogin();
        UserFaceIdentifyRequest userFaceIdentifyRequest=new UserFaceIdentifyRequest();
        userFaceIdentifyRequest.setKey("96cdcb76-57b9-4162-a06e-3af10d4da04c");
        System.out.println(JSON.toJSONString(userFaceIdentifyRequest));
        try {
            MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/user/face/identify/96cdcb76-57b9-4162-a06e-3af10d4da04c").headers(getHttpHeaders()).content(JSON.toJSONString(userFaceIdentifyRequest)).contentType(MediaType.APPLICATION_JSON)).andDo(MockMvcResultHandlers.print()).andReturn();
        }catch (Exception e){

        }

    }
    @Test
    public void testFriendsRecordResponseFace(){

        testLogin();
        UserFaceIdentifyRequest userFaceIdentifyRequest=new UserFaceIdentifyRequest();
        userFaceIdentifyRequest.setKey("96cdcb76-57b9-4162-a06e-3af10d4da04c");
        System.out.println(JSON.toJSONString(userFaceIdentifyRequest));
        try {
            MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/user/friendsRecordResponse").headers(getHttpHeaders()).content(JSON.toJSONString(userFaceIdentifyRequest)).contentType(MediaType.APPLICATION_JSON)).andDo(MockMvcResultHandlers.print()).andReturn();
        }catch (Exception e){

        }

    }


    @Test
    public void testDetectFace(){

        testLogin();
        UserFacesRequest userFacesRequest=new UserFacesRequest();
        userFacesRequest.addKey("zhoujielunheying");
        try {
            MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/user/face/detect").headers(getHttpHeaders()).content(JSON.toJSONString(userFacesRequest)).contentType(MediaType.APPLICATION_JSON)).andDo(MockMvcResultHandlers.print()).andReturn();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Test
    public void testQiniuToken(){
        testLogin();
        try {
            MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/user/qiniu/token").headers(getHttpHeaders())).andDo(MockMvcResultHandlers.print()).andReturn();
        }catch (Exception e){
            e.printStackTrace();
        }


    }


}
