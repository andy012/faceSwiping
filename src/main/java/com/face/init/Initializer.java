package com.face.init;


import com.face.init.config.WebAppConfig;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.filter.HttpPutFormContentFilter;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.*;
import java.util.EnumSet;

public class Initializer implements WebApplicationInitializer {
	//private static final Logger logger = Logger.getLogger(Initializer.class);
	
	public static String CONTAINER;
	public static String VERSION;

	public void onStartup(ServletContext servletContext) throws ServletException {
		AnnotationConfigWebApplicationContext ctx = new AnnotationConfigWebApplicationContext();
		ctx.register(WebAppConfig.class);
		//ctx.register(StatelessAuthenticationSecurityConfig.class);
        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setEncoding("UTF-8");
        characterEncodingFilter.setForceEncoding(true);

        servletContext.addFilter("characterEncoding", characterEncodingFilter)
                .addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");
        servletContext.addFilter("httpMethodFilter", new HttpPutFormContentFilter())
      		.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST), true, "/*");;

		servletContext.addListener(new ContextLoaderListener(ctx));
		//servletContext.addListener(new );
		CONTAINER = servletContext.getServerInfo().split("/")[0];
		VERSION = servletContext.getServerInfo().split("/")[1];
		
		ctx.setServletContext(servletContext);
		ctx.setDisplayName("Spring Java Example");

		ServletRegistration.Dynamic servlet = servletContext.addServlet("dispatcher", new DispatcherServlet(ctx));
		servlet.addMapping("/");
		servlet.setLoadOnStartup(1);

		FilterRegistration.Dynamic springSecurityFilterChain = servletContext.addFilter("springSecurityFilterChain", DelegatingFilterProxy.class);
		springSecurityFilterChain.addMappingForUrlPatterns(null, false, "/*");
//
//		StatelessFilter statelessFilter=new StatelessFilter(TokenAuthenticationService.getTokenAuthenticationService());
//		servletContext.addFilter("statelessFilter",statelessFilter).addMappingForUrlPatterns(null, false, "/mobile/*");

	}
}
