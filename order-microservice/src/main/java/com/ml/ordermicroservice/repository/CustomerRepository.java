package com.ml.ordermicroservice.repository;

import com.ml.ordermicroservice.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CustomerRepository extends JpaRepository<Customer, UUID> {
}
