package com.ml.ordermicroservice.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class OrderDTO {

    private UUID id;
    private String orderNumber;
    private Double totalAmount;

}
