package com.ml.ordermicroservice.service;

import com.ml.ordermicroservice.dto.OrderDTO;

import java.util.UUID;

public interface OrderService {

    OrderDTO acceptOrder(OrderDTO orderDTO);
    OrderDTO updateOrder(String orderNumber, OrderDTO orderDTO);
    OrderDTO searchAnOrder(OrderDTO orderDTO);
}
