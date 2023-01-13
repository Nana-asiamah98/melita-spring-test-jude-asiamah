package com.ml.ordermicroservice;

import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

import java.util.UUID;


@SpringBootApplication
@EnableEurekaClient
public class OrderMicroserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderMicroserviceApplication.class, args);
    }


    @Bean
    protected ModelMapper modelMapper(){
        final ModelMapper mapper = new ModelMapper();

        Converter<String, UUID> toUUID = new AbstractConverter<String, UUID>() {
            protected UUID convert(String source) {
                return source == null ? null : UUID.fromString(source);
            }
        };
        mapper.addConverter(toUUID);
        mapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT);
        return mapper;
    }
}
