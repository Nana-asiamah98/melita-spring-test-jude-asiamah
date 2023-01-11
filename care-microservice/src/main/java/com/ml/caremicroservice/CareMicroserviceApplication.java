package com.ml.caremicroservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class CareMicroserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CareMicroserviceApplication.class, args);
    }

}
