package com.ml.apigateway.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.AuthorizationCodeGrantBuilder;
import springfox.documentation.builders.OAuthBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger.web.SecurityConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.Collections;


@Slf4j
@Configuration
@EnableSwagger2
@RequiredArgsConstructor
public class SpringFoxConfig {

    private final KeycloakInfoConfig keycloakInfoConfig;

    @Bean
    public Docket api(){
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.stlghana.tms_backend.restcontroller"))
                .paths(PathSelectors.any())
                .build()
                .securitySchemes(Arrays.asList(securityScheme()))
                .securityContexts(Arrays.asList(securityContext()))
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                "Travelling Management System[TMS]",
                "The goal of this application is to generate travelling applications for users to apply for travelling or leave permit." +
                        "To continue, this application has the role and permission for users to perform peculiar actions on an application",
                "v1",
                "Terms of service",
                new Contact("Asiamah Jude Nana Konadu", "www.example.com", "judeasiamah@gmail.com"),
                "License of API", "API license URL", Collections.emptyList());
    }

    @Bean
    public SecurityConfiguration security() {
        log.info("[USER REALM] => {}",keycloakInfoConfig.getRealm());
        return SecurityConfigurationBuilder.builder()
                .clientId(keycloakInfoConfig.getClientId())
                .clientSecret(keycloakInfoConfig.getClientSecret())
                .scopeSeparator(" ")
                .realm(keycloakInfoConfig.getRealm())
                .additionalQueryStringParams(null)
                .useBasicAuthenticationWithAccessCodeGrant(true)
                .build();
    }




    private SecurityScheme securityScheme() {
        GrantType grantType = new AuthorizationCodeGrantBuilder()
                .tokenEndpoint(new TokenEndpoint(keycloakInfoConfig.getServer() + "/auth/realms/" + keycloakInfoConfig.getRealm()+ "/protocol/openid-connect/token", "oauthtoken"))
                .tokenRequestEndpoint(
                        new TokenRequestEndpoint( keycloakInfoConfig.getServer() + "/auth/realms/" + keycloakInfoConfig.getRealm()+ "/protocol/openid-connect/auth", keycloakInfoConfig.getClientId(), keycloakInfoConfig.getClientSecret()))
                .build();

        SecurityScheme oauth = new OAuthBuilder().name("spring_oauth")
                .grantTypes(Arrays.asList(grantType))
                .scopes(Arrays.asList(scopes()))
                .build();
        return oauth;
    }

    private AuthorizationScope[] scopes() {
        AuthorizationScope[] scopes = {
                new AuthorizationScope("openid", "openid"),
                new AuthorizationScope("profile", "profile") };
        return scopes;
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(
                        Arrays.asList(new SecurityReference("spring_oauth", scopes())))
                .forPaths(PathSelectors.any())
                .build();

    }
}
