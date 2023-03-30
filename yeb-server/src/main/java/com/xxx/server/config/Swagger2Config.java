package com.xxx.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * Keafmd
 *
 * @ClassName: Swagger2Config
 * @Description: Swagger2配置类
 * @author: liuchen
 * @date: 2022/4/12 11:30
 * @Blog:
 */
@Configuration
@EnableSwagger2
public class Swagger2Config  extends WebMvcConfigurationSupport {

    @Bean
    public Docket createRestApi(){
        return  new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.xxx.server.controller"))
                .paths(PathSelectors.any())
                .build()
                //获取路径以及令牌
                .securityContexts(securityContexts())
                .securitySchemes(securitySchemes());
    }
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("doc.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    public List<ApiKey> securitySchemes(){
        List<ApiKey> result = new ArrayList<>();
        ApiKey apiKey = new ApiKey("Authorization","Authorization","header");
        result.add(apiKey);
        return result;
    }

    public List<SecurityContext> securityContexts(){
        //设置需要认证的路径
        List<SecurityContext> result = new ArrayList<>();
        result.add(getSecurityContext("/hello/.*"));
        return result;
    }

    private SecurityContext getSecurityContext(String pathRegex) {
        return SecurityContext.builder()
                //默认授权
                .securityReferences(defaultAu())
                //指代哪个路径
                .forPaths(PathSelectors.regex(pathRegex))
                .build();
    }

    private List<SecurityReference> defaultAu() {
        List<SecurityReference>  result  = new ArrayList<>();
        AuthorizationScope authorizationScope = new AuthorizationScope("global","accessEverything");
        AuthorizationScope [] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] =authorizationScope;
        result.add(new SecurityReference("Authorization",authorizationScopes));
        return result;
    }

    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("云E办接口文档")
                .description("云E办接口文档")
                .contact(new Contact("xxx","http:localhost:8081/doc.html","xxx@xxx.com"))
                .version("1.0")
                .build();
    }
}
