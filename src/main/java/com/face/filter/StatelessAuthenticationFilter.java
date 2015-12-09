package com.face.filter;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class StatelessAuthenticationFilter extends GenericFilterBean {

	private final TokenAuthenticationService tokenAuthenticationService;

	public StatelessAuthenticationFilter(TokenAuthenticationService taService) {
		System.out.println("init StatelessAuthenticationFilter");
		this.tokenAuthenticationService = taService;
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException,
			ServletException {


		HttpSession session = ((HttpServletRequest)req).getSession(true);
		Authentication authentication=(Authentication)(session.getAttribute("SPRING_SECURITY_CONTEXT"));
		if(authentication!=null ){
			//System.out.println(this.getClass() + "********************************************1" + authentication.getName());
			//System.out.println(this.getClass() + "********************************************1" + chain.getClass());
			SecurityContextHolder.getContext().setAuthentication(authentication);
			chain.doFilter(req, res);
			return;
		}
		SecurityContextHolder.getContext().setAuthentication(
				tokenAuthenticationService.getAuthentication((HttpServletRequest) req));
		chain.doFilter(req, res); // always continue

	}
}