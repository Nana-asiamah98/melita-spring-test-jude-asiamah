package com.ml.ordermicroservice.utils;

import com.ml.ordermicroservice.model.Order;
import com.ml.ordermicroservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class OrderNumberGenerator {


    private final OrderRepository orderRepository;

    public String generateOrderNumber(){
        Optional<Order> lastRecordNumber = this.orderRepository.findTopByOrderByCreatedAtDesc();
        if(lastRecordNumber.isPresent()){
            int lastRecord = Integer.parseInt(lastRecordNumber.get().getOrderNumber());
            return String.format("%06d", lastRecord+1);
        }
        return  "000000";
    }
}
