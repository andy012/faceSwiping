package com.face.init.config;

import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.face.init.security.StatelessAuthenticationSecurityConfig;
import org.springframework.context.annotation.*;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.UrlBasedViewResolver;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;

@Configuration
@ComponentScan(basePackages = {"com.face"}, excludeFilters={
@ComponentScan.Filter(type= FilterType.ASSIGNABLE_TYPE, value=StatelessAuthenticationSecurityConfig.class)})
@EnableWebMvc
@EnableTransactionManagement
@Import({  DataSourceConfig.class, MessageSourceConfig.class,StatelessAuthenticationSecurityConfig.class,YoutuConfig.class,QiniuConfig.class})
public class WebAppConfig extends WebMvcConfigurerAdapter {	
	/**
	 * Configuration for application views.
	 * 
	 * Note: This is equivalent to the spring-java-example-servlet.xml
	 */
	@Bean
	public UrlBasedViewResolver setupViewResolver() {
		UrlBasedViewResolver resolver = new UrlBasedViewResolver();
		resolver.setPrefix("/WEB-INF/views/");
		resolver.setSuffix(".jsp");
		resolver.setViewClass(JstlView.class);
		return resolver;
	}

	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {

		super.configureMessageConverters(converters);
		StringHttpMessageConverter stringConverter = new StringHttpMessageConverter();
		stringConverter.setSupportedMediaTypes(Arrays.asList(new MediaType("text", "plain", Charset.forName("utf-8"))));
		FastJsonHttpMessageConverter fastJsonHttpMessageConverter = new FastJsonHttpMessageConverter();
		converters.add(stringConverter);
		converters.add(fastJsonHttpMessageConverter);
	}

	@Bean
	public CommonsMultipartResolver multipartResolver(){
		CommonsMultipartResolver cmr = new CommonsMultipartResolver();
		cmr.setDefaultEncoding("UTF-8");
		cmr.setMaxUploadSize(-1);
		return cmr;
	}



	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/js", "/resources/images", "/resources/css");
    }
}
