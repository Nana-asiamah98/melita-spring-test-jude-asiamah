package com.ml.ordermicroservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ml.ordermicroservice.dto.OrderDTO;
import com.ml.ordermicroservice.dto.UpdateOrderStatusDTO;

public interface OrderService {

    OrderDTO acceptOrder(OrderDTO orderDTO) throws JsonProcessingException;
    UpdateOrderStatusDTO updateOrderStatus(String orderNumber, UpdateOrderStatusDTO orderStatusDTO);
    OrderDTO searchAnOrder(String orderNumber) throws JsonProcessingException;
}
