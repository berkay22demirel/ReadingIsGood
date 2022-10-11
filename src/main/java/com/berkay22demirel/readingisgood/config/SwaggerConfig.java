package com.berkay22demirel.readingisgood.config;

import io.swagger.models.auth.In;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Collections;

@Configuration
public class SwaggerConfig {

    @Bean
    public Docket readingIsGoodApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .securityContexts(Collections.singletonList(securityContext()))
                .securitySchemes(Collections.singletonList(new ApiKey("JWT", HttpHeaders.AUTHORIZATION, In.HEADER.name())))
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.berkay22demirel.readingisgood.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    private SecurityContext securityContext() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return SecurityContext
                .builder()
                .securityReferences(Collections.singletonList(new SecurityReference("JWT", authorizationScopes)))
                .build();
    }
}
