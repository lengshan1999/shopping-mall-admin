package com.hbsi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.*;

import com.hbsi.component.LoginHandlerInterceptor;
import com.hbsi.component.MyLocaleResolver;



/**
 * 扩展springMvc
 * @author lenovo
 *
 */

@Configuration
@EnableWebMvc
public class WebMvcConfig implements WebMvcConfigurer  {


	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("static/**")
				.addResourceLocations("classpath:static/")
				.addResourceLocations("classpath:/static/asserts/");
		registry.addResourceHandler("templates/**")
				.addResourceLocations("classpath:/resources/templates/**");
		registry.addResourceHandler("swagger-ui.html")
				.addResourceLocations("classpath:/META-INF/resources/");
		registry.addResourceHandler("/webjars/**")
				.addResourceLocations("classpath:/META-INF/resources/webjars/");

		registry.addResourceHandler("/static/image/productCode/**")
				.addResourceLocations("classpath:/static/image/productCode/",
						"file:"+System.getProperty("user.dir")+"\\src\\main\\resources\\static\\image\\productCode\\");
		registry.addResourceHandler("/static/image/productImage/**")
				.addResourceLocations("classpath:/static/image/productImage/",
						"file:"+System.getProperty("user.dir")+"\\src\\main\\resources\\static\\image\\productImage\\");

		WebMvcConfigurer.super.addResourceHandlers(registry);
	}


	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(
				new LoginHandlerInterceptor())
				.addPathPatterns("/**")
				.excludePathPatterns("/login.html","/","/admin/login","/static/**");
		WebMvcConfigurer.super.addInterceptors(registry);
	}


	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/").setViewName("login.html");
		registry.addViewController("/login.html").setViewName("login.html");
		registry.addViewController("/chart.html").setViewName("chart.html");
		registry.addViewController("/index").setViewName("dashboard.html");
		registry.addViewController("/logo.html").setViewName("logo.html");
		registry.addViewController("/logo1.html").setViewName("logo1.html");
		registry.addViewController("/logo2.html").setViewName("logo2.html");
		registry.addViewController("/theme.html").setViewName("theme.html");
		registry.addViewController("/404").setViewName("error/404.html");
		registry.addViewController("/500").setViewName("error/500.html");
		registry.addViewController("/error").setViewName("error/error.html");
		registry.addViewController("/adminUpdPwd.html").setViewName("adminUpdPwd.html");

		registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
		WebMvcConfigurer.super.addViewControllers(registry);
	}



	@Bean
	public org.springframework.web.servlet.LocaleResolver localeResolver() {
		return new MyLocaleResolver();
	}
	
	
}
