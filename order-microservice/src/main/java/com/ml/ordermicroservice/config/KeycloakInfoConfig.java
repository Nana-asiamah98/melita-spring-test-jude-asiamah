package com.ml.ordermicroservice.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties("keycloak")
public class KeycloakInfoConfig {

    private String clientId;
    private String clientSecret;
    private String realm;
    private String server;

}
