package com.example.test.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

@Configuration
@EnableWebMvc
@ComponentScan("com.example.test")
public class WebConfig implements WebMvcConfigurer {

	/*
	 * MULTIPART UPLOADER SETTINGS
	 */
	@Bean
	public StandardServletMultipartResolver multipartResolver() {
	    return new StandardServletMultipartResolver();
	}

	/*
	 * VIEW SETTINGS
	 * */
	@Override
	public void addViewControllers(final ViewControllerRegistry registry) {
		registry.addViewController("/").setViewName("index");
	}
	
	@Bean
	public ViewResolver viewResoler() {
		InternalResourceViewResolver bean = new InternalResourceViewResolver();
		
		bean.setViewClass(JstlView.class);
		bean.setPrefix("/WEB-INF/views/jsp/");
		bean.setSuffix(".jsp");
		
		return bean;
	}
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry){
		registry
			.addResourceHandler("/resources/css/**")
			.addResourceLocations("/WEB-INF/resources/css/");
		registry
			.addResourceHandler("/resources/images/**")
			.addResourceLocations("/WEB-INF/resources/images/");
		registry
			.addResourceHandler("/resources/js/**")
			.addResourceLocations("/WEB-INF/resources/js/");
	}
}
