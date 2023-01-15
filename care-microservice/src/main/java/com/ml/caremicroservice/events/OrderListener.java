package com.ml.caremicroservice.events;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ml.caremicroservice.dto.OrderDTO;
import com.ml.caremicroservice.utils.AppConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderListener {

    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "orderTopic")
    public void handlePayload(String message) throws JsonProcessingException {
        log.info("[ORDER RECEIVED]");
        OrderDTO orderDTO = objectMapper.readValue(message,OrderDTO.class);
        switch (orderDTO.getOrderStatus()){
            case AppConstants.ORDER_PROCESSING:
            case AppConstants.ORDER_CANCELLED:
            case AppConstants.ORDER_COMPLETED:
            case AppConstants.ORDER_PROCESSED:
                log.info("[ORDER  STATUS {}]", orderDTO.getOrderStatus().toUpperCase());
                break;
            default:
                break;
        }
    }
}
