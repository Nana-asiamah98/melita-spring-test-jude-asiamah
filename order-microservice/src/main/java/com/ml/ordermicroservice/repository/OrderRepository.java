package com.ml.ordermicroservice.repository;

import com.ml.ordermicroservice.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {

    @Query("SELECT  a.orderNumber from tbl_order  as a order by a.createdAt")
    Optional<String> findLastRecord();
}
