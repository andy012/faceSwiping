package com.face.filter;

import com.face.model.user.User;
import com.face.model.user.UserAuthentication;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by andy on 11/21/15.
 */
public class CustomUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter{



    public CustomUsernamePasswordAuthenticationFilter(){}

    /**
     * 设置登录成功和失败后的重定向的位置
     * @param loginSuccessRedirect
     * @param loginFailRedirect
     */
    public CustomUsernamePasswordAuthenticationFilter(String loginSuccessRedirect,String loginFailRedirect){

        if(loginFailRedirect!=null){
            SavedRequestAwareAuthenticationSuccessHandler savedRequestAwareAuthenticationSuccessHandler=new SavedRequestAwareAuthenticationSuccessHandler();
            savedRequestAwareAuthenticationSuccessHandler.setDefaultTargetUrl(loginSuccessRedirect);
            this.setAuthenticationSuccessHandler(savedRequestAwareAuthenticationSuccessHandler);
        }
        if(loginFailRedirect!=null){
            SimpleUrlAuthenticationFailureHandler simpleUrlAuthenticationFailureHandler=new SimpleUrlAuthenticationFailureHandler(loginFailRedirect);
            this.setAuthenticationFailureHandler(simpleUrlAuthenticationFailureHandler);
        }

    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {

        setDetails(request, (UsernamePasswordAuthenticationToken) authResult);

        HttpSession session = request.getSession(true);
        /**
         * 将User拷贝一份保留在session中,只保留一些基本信息.
         */
        User user=copyUser(authResult);
        UserAuthentication userAuthentication=new UserAuthentication(user);
        userAuthentication.setAuthenticated(authResult.isAuthenticated());
        session.setAttribute("SPRING_SECURITY_CONTEXT", userAuthentication);
        System.out.println(session.getAttribute("SPRING_SECURITY_CONTEXT"));
        System.out.println(request.getSession().getAttributeNames());

//        while (request.getSession().getAttributeNames().hasMoreElements()){
//            String aName=request.getSession().getAttributeNames().nextElement();
//            System.out.println(aName+":"+request.getSession().getAttribute(aName).getClass());
//        }
        //SecurityContextHolder.getContext().setAuthentication(authResult);
        super.successfulAuthentication(request, response, chain, authResult);
       // System.out.println("######################" + SecurityContextHolder.getContext().getAuthentication());

    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        super.unsuccessfulAuthentication(request, response, failed);
    }

    private User copyUser(Authentication authentication){
        User user=new User();
        User tempUser=(User)(authentication.getPrincipal());
        user.setUsername(tempUser.getUsername());
        user.setAuthorities(tempUser.getAuthorities());
        user.setId(tempUser.getId());
//        Iterator<UserAuthority> userAuthorityIterator=user.getAuthorities().iterator();
//        while(userAuthorityIterator.hasNext()){
//            userAuthorityIterator.next().setUser(null);
//        }

        return user;

    }
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        return super.attemptAuthentication(request, response);
    }

    @Override
    public void setSessionAuthenticationStrategy(SessionAuthenticationStrategy sessionStrategy) {
        super.setSessionAuthenticationStrategy(sessionStrategy);
    }


}
