package com.ml.ordermicroservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ml.ordermicroservice.dto.OrderDTO;

public interface OrderService {

    OrderDTO acceptOrder(OrderDTO orderDTO) throws JsonProcessingException;
    OrderDTO updateOrder(String orderNumber, OrderDTO orderDTO);
    OrderDTO searchAnOrder(String orderNumber) throws JsonProcessingException;
}
