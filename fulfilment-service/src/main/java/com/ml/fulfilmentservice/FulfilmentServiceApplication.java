package com.ml.fulfilmentservice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;

@Slf4j
@SpringBootApplication
public class FulfilmentServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(FulfilmentServiceApplication.class, args);
    }


    @KafkaListener(topics = "orderTopic")
    public void handlePayload(String message){
        log.info("[KAFKA PAYLOAD] => {}", message);
    }
}
