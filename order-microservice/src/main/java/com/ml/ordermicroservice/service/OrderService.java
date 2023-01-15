package com.ml.ordermicroservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ml.ordermicroservice.dto.OrderDTO;
import com.ml.ordermicroservice.dto.PaginatedOrdersResponse;
import com.ml.ordermicroservice.dto.UpdateOrderStatusDTO;
import org.springframework.data.domain.Page;

public interface OrderService {

    PaginatedOrdersResponse fetchPaginatedOrders(int page, int size, String sortBy, String sortDir);
    OrderDTO acceptOrder(OrderDTO orderDTO) throws JsonProcessingException;
    UpdateOrderStatusDTO updateOrderStatus(String orderNumber, UpdateOrderStatusDTO orderStatusDTO);
    OrderDTO searchAnOrder(String orderNumber) throws JsonProcessingException;
}
