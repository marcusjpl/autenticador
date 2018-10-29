package br.com.auth.autenticador;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.ApiKeyVehicle;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Value("${api.key}")
	private String key;

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.basePackage("br.com.auth.autenticador.controller"))
				.paths(PathSelectors.any()).build().apiInfo(apiInfo());
	}

	private ApiInfo apiInfo() {
		return new ApiInfo("Autenticador REST", "API de Controler para autenticações", "", "Terms of service",
				new Contact("Marcus", "www.example.com", "myeaddress@company.com"), "License of API", "API license URL",
				Collections.emptyList());
	}

	@Bean
	SecurityConfiguration security() {
		return new SecurityConfiguration(null, null, null, // realm Needed for authenticate button to work
				null, // appName Needed for authenticate button to work
				key, // apiKeyValue
				ApiKeyVehicle.HEADER, "Authorization", // apiKeyName
				null);
	}

	// @Override
	// public void addViewControllers(ViewControllerRegistry registry) {
	// registry.addRedirectViewController("/rest",
	// "/swagger-ui.html").setKeepQueryParams(true);	
	// }
	
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
}
