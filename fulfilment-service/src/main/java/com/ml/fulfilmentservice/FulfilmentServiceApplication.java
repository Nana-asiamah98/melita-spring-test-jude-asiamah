package com.ml.fulfilmentservice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ml.fulfilmentservice.dto.OrderDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;

@SpringBootApplication
public class FulfilmentServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(FulfilmentServiceApplication.class, args);
    }


}
