package com.ml.fulfilmentservice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ml.fulfilmentservice.dto.OrderDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;

@Slf4j
@SpringBootApplication
@RequiredArgsConstructor
public class FulfilmentServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(FulfilmentServiceApplication.class, args);
    }

    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "orderTopic")
    public void handlePayload(String message) throws JsonProcessingException {
        OrderDTO orderDTO = objectMapper.readValue(message,OrderDTO.class);
        log.info("[KAFKA PAYLOAD] => {}", objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(orderDTO));
    }
}
