package com.ml.apigateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig{

    @Bean
    public SecurityWebFilterChain springSecurityFilter(ServerHttpSecurity serverHttpSecurity) {
        return serverHttpSecurity.csrf()
                .disable()
                .authorizeExchange(exhcange -> exhcange
                        .pathMatchers("/eureka/**","/order/**", "/api/**", "/v3/api-docs/**", "/swagger-ui/**", "/webjars/**", "/swagger-ui.html")
                        .permitAll()
                        .pathMatchers(HttpMethod.GET, "/order/**")
                        .hasAnyAuthority("SCOPE_read")
                        .anyExchange()
                        .authenticated())
                .oauth2ResourceServer(ServerHttpSecurity.OAuth2ResourceServerSpec::jwt).build();
    }
}
