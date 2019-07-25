package com.lz.springbootjwt.swagger;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lzj
 * @create 2019-07-24 19:15
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Autowired
    private SwaggerProperties swaggerProperties;

    @Bean
    public Docket createDocket(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .groupName(swaggerProperties.getGroupName())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.lz.springbootjwt"))
                .build()
                .securitySchemes(apiKeys())
                .securityContexts(securityContexts());
        //return docket;

    }

    private ApiInfo apiInfo(){
       return new ApiInfoBuilder()
               .title(swaggerProperties.getTitle())
               .description(swaggerProperties.getDesc())
               .version(swaggerProperties.getVersion())
               .build();
        //return new ApiInfo("lalala", "ceshi", "v1.0", null, "", "", "");
    }

    private List<ApiKey> apiKeys(){
        //设置请求头信息
        List<ApiKey> keys = new ArrayList<>();
        ApiKey apiKey = new ApiKey("Authorization", "Authorization", "header");
        keys.add(apiKey);
        return keys;
    }

    private List<SecurityContext> securityContexts(){
        //设置需要认证的路径
        List<SecurityContext> securityContexts = new ArrayList<>();
        securityContexts.add(SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(PathSelectors.regex("/.*?"))
                .build());
        return securityContexts;
    }

    private List<SecurityReference> defaultAuth(){
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[]{authorizationScope};
        List<SecurityReference> securityReferences = new ArrayList<>();
        securityReferences.add(new SecurityReference("Authorization", authorizationScopes));
        return securityReferences;
    }
}
