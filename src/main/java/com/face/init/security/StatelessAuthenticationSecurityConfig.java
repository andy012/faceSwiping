package com.face.init.security;

import com.face.filter.CustomUsernamePasswordAuthenticationFilter;
import com.face.filter.StatelessAuthenticationFilter;
import com.face.filter.StatelessLoginFilter;
import com.face.filter.TokenAuthenticationService;
import com.face.service.qiniu.QiniuService;
import com.face.service.user.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestFilter;

@EnableWebSecurity
@Configuration
public class StatelessAuthenticationSecurityConfig extends WebSecurityConfigurerAdapter {


	@Autowired
	private CustomUserDetailsService customUserDetailsService;

	@Autowired
	private TokenAuthenticationService tokenAuthenticationService;

	@Autowired
	private QiniuService qiniuService;
	public StatelessAuthenticationSecurityConfig() {
		super(true);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		CustomUsernamePasswordAuthenticationFilter usernamePasswordAuthenticationFilter=new CustomUsernamePasswordAuthenticationFilter("/loginSuccess","/loginFailure");
		usernamePasswordAuthenticationFilter.setAuthenticationManager(authenticationManager());
		http
				.exceptionHandling().and()
				.anonymous().and()
				.servletApi().and()
				.headers().cacheControl().and()
				.authorizeRequests()
								
				//allow anonymous resource requests
				.antMatchers("/").permitAll()
				.antMatchers("/favicon.ico").permitAll()
				.antMatchers("/resources/**").permitAll()
				.antMatchers("/company/**").permitAll()
				.antMatchers("/verification/**").permitAll()
				.antMatchers("/signup").permitAll()
				//allow anonymous POSTs to login
				.antMatchers(HttpMethod.POST, "/api/login").permitAll()
				
				//allow anonymous GETs to API
				.antMatchers(HttpMethod.GET, "/api/**").permitAll()
				.antMatchers("/loginFailure").permitAll()
				.antMatchers("/loginSuccess").hasAnyRole("USER", "ADMIN")
				//defined Admin only API area
				.antMatchers("/admin/**").hasRole("ADMIN")
				
				//all other request need to be authenticated
				//.anyRequest().hasRole("USER").and()
				.anyRequest().permitAll().and()//.hasAnyRole("USER", "ADMIN").and()
				.addFilterBefore(usernamePasswordAuthenticationFilter,SecurityContextHolderAwareRequestFilter.class)
				// custom JSON based authentication by POST of {"username":"<name>","password":"<password>"} which sets the token header upon authentication
		        .addFilterBefore(new StatelessLoginFilter("/api/login", tokenAuthenticationService, customUserDetailsService, qiniuService,authenticationManager()), CustomUsernamePasswordAuthenticationFilter.class)

				// custom Token based authentication based on the header previously given to the client
				.addFilterAfter(new StatelessAuthenticationFilter(tokenAuthenticationService), CustomUsernamePasswordAuthenticationFilter.class);

		System.out.println("-----------stateless");

	}
	
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(customUserDetailsService).passwordEncoder(new BCryptPasswordEncoder());
	}

	@Override
	protected CustomUserDetailsService userDetailsService() {
		return customUserDetailsService;
	}
}
