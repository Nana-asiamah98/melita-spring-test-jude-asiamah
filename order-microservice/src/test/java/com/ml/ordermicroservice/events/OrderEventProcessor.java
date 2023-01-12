package com.ml.ordermicroservice.events;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderEventProcessor implements ApplicationListener<OrderEvent> {

    private final ObjectMapper objectMapper;

    @Override
    public void onApplicationEvent(OrderEvent event) {

    }
}
