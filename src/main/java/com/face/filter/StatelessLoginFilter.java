package com.face.filter;

import com.alibaba.fastjson.JSON;
import com.face.data.user.UserBaseInfo;
import com.face.data.user.UserLoginResponse;
import com.face.data.util.ResponseCode;
import com.face.execption.Base64ParseExecption;
import com.face.model.common.ResultInfo;
import com.face.model.common.ResultInfo.Code;
import com.face.model.user.User;
import com.face.model.user.UserAuthentication;
import com.face.service.qiniu.QiniuService;
import com.face.service.user.CustomUserDetailsService;
import com.face.util.Base64;
import com.face.util.Util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

public class StatelessLoginFilter extends AbstractAuthenticationProcessingFilter {

	private final TokenAuthenticationService tokenAuthenticationService;
	private final CustomUserDetailsService customUserDetailsService;


	@Autowired
	private QiniuService qiniuService;
	public StatelessLoginFilter(String urlMapping, TokenAuthenticationService tokenAuthenticationService,
								CustomUserDetailsService customUserDetailsService,QiniuService qiniuService,
								AuthenticationManager authManager) {
		super(new AntPathRequestMatcher(urlMapping));
		this.customUserDetailsService = customUserDetailsService;
		this.tokenAuthenticationService = tokenAuthenticationService;
		this.qiniuService=qiniuService;
		setAuthenticationManager(authManager);

	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException, IOException, ServletException {
//		String paramsBase64=request.getParameter("params");
//		System.out.println(paramsBase64);
//		//paramsBase64=paramsBase64.replace("\n","");
//		System.out.println("-----------------------------------");
//		if (Util.isEmpty(paramsBase64)) {
//			response.sendError(500,"参数不能为空");
//		}
//		String jsonStr;
//
//		try {
//			jsonStr= Base64.decode(paramsBase64);
//		} catch (Base64ParseExecption base64ParseExecption) {
//			base64ParseExecption.printStackTrace();
//			response.sendError(500,"base64 格式有问题");
//			return null;
//		}



		final User user = new ObjectMapper().readValue(request.getInputStream(), User.class);
		final UsernamePasswordAuthenticationToken loginToken = new UsernamePasswordAuthenticationToken(
				user.getUsername(), user.getPassword());
		return getAuthenticationManager().authenticate(loginToken);
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {

		//System.out.println("********************************************1"+chain.getClass());
		super.doFilter(req, res, chain);
		//chain.doFilter(req,res);
		//System.out.println("********************************************2" + chain.getClass());

	}


	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			FilterChain chain, Authentication authentication) throws IOException, ServletException {
		//System.out.println("********************************************3"+chain.getClass());

		// Lookup the complete User object from the database and create an Authentication for it
		final User authenticatedUser = customUserDetailsService.loadUserByUsername(authentication.getName());
		final UserAuthentication userAuthentication = new UserAuthentication(authenticatedUser);

		// Add the custom token as HTTP header to the response
		tokenAuthenticationService.addAuthentication(response, userAuthentication);
		// Add the authentication to the Security context
		SecurityContextHolder.getContext().setAuthentication(userAuthentication);
		PrintWriter pw = response.getWriter();

		UserLoginResponse userLoginResponse=new UserLoginResponse();
		userLoginResponse.setUser(userAuthentication.getDetails(),qiniuService);
		userLoginResponse.setResponseCode(ResponseCode.SUCCESS);
		System.out.println("login success :--->" + JSON.toJSONString(userLoginResponse));
		pw.write(JSON.toJSONString(userLoginResponse));
		pw.flush();
		pw.close();
	}
	
	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request,
			HttpServletResponse response, AuthenticationException failed)
			throws IOException, ServletException {
		PrintWriter pw = response.getWriter();



		UserLoginResponse userLoginResponse=new UserLoginResponse();
		userLoginResponse.setResponseCode(ResponseCode.LOGIN_FAIL);
		System.out.println("login success :--->"+JSON.toJSONString(userLoginResponse));
		pw.write(JSON.toJSONString(userLoginResponse));
		pw.flush();
		pw.close();
	}
}