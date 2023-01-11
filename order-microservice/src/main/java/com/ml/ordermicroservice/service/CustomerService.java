package com.ml.ordermicroservice.service;

import com.ml.ordermicroservice.dto.CustomerDTO;
import com.ml.ordermicroservice.model.Customer;

import java.util.List;
import java.util.UUID;

public interface CustomerService {

    Customer save(Customer customer);

    List<Customer> fetchAll();

    Customer fetch(UUID customerId);

    Customer update(UUID customerId, CustomerDTO customerDTO);

    Boolean delete(UUID customerId);
}
