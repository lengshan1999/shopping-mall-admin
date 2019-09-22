package com.hbsi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	@Bean
	public Docket createRestApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(apiInfo())
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.hbsi"))
				.paths(PathSelectors.any())
				.build();
	}
 
	private ApiInfo apiInfo() { 
		return new ApiInfoBuilder().title("shopping-mall")
				.description("shopping-mall-admin API 服务管理")
				.termsOfServiceUrl("http://49.235.206.214:80/swagger-ui.html")
				.contact(new Contact("white","49.235.206.214:8081/swagger-ui.html","m15176733539@163.com"))
				.version("1.0")
				.build();
 
	}

}