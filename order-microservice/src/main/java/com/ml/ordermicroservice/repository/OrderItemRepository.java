package com.ml.ordermicroservice.repository;

import com.ml.ordermicroservice.model.Order;
import com.ml.ordermicroservice.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface OrderItemRepository  extends JpaRepository<OrderItem, UUID> {
}
