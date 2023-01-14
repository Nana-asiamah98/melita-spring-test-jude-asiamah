package com.ml.ordermicroservice.repository;

import com.ml.ordermicroservice.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {

    Optional<Order>  findTopByOrderByCreatedAtDesc();

    Optional<Order> findByOrderNumber(String number);

}
