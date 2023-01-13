package com.ml.ordermicroservice.utils;

import com.ml.ordermicroservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class OrderNumberGenerator {


    private final OrderRepository orderRepository;

    public String generateOrderNumber(){
        Optional<String> lastRecordNumber = this.orderRepository.findLastRecord();
        if(lastRecordNumber.isPresent()){
            int lastRecord = Integer.parseInt(lastRecordNumber.get());
            Integer record  = lastRecord+1;
            return record.toString();
        }
        return  "000000";
    }
}
