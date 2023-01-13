package com.ml.ordermicroservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ml.ordermicroservice.dto.OrderDTO;

import java.util.UUID;

public interface OrderService {

    OrderDTO acceptOrder(OrderDTO orderDTO) throws JsonProcessingException;
    OrderDTO updateOrder(String orderNumber, OrderDTO orderDTO);
    OrderDTO searchAnOrder(OrderDTO orderDTO);
}
