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
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
        web.ignoring().antMatchers("swagger-ui/**", "swagger-ui**", "/v3/api-docs/**", "/v3/api-docs**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
            http.csrf()
                    .ignoringAntMatchers("/static/").disable()
                    .cors().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                    .and().authorizeRequests()
                    .antMatchers("/eureka/**", "/swagger-ui.html/**", "/configuration/**", "/swagger-resources/**", "/v2/api-docs", "/webjars/**", "/assets/**", "/complaint/**", "/api/v1/regions/**", "api/v1/complaint/**").permitAll().anyRequest()
                    .authenticated().and().oauth2ResourceServer().jwt();
        }
    }


    /*@Bean
    public SecurityWebFilterChain springSecurityFilter(ServerHttpSecurity serverHttpSecurity){
        return  serverHttpSecurity.csrf()
                .disable()
                .authorizeExchange(exhcange -> exhcange
                .pathMatchers("/eureka/**","/api/**","/v3/api-docs/**", "/swagger-ui/**", "/webjars/**", "/swagger-ui.html")
                .permitAll()
                        .pathMatchers(HttpMethod.GET,"/order/**")
                        .hasAnyAuthority("SCOPE_read")
                .anyExchange()
                .authenticated())
                .oauth2ResourceServer(ServerHttpSecurity.OAuth2ResourceServerSpec::jwt).build();
    }*/
