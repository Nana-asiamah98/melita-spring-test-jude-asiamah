package com.ml.ordermicroservice.service;

import com.ml.ordermicroservice.dto.CustomerDTO;
import com.ml.ordermicroservice.dto.PaginatedCustomerResponse;
import com.ml.ordermicroservice.model.Customer;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerService {

    Customer save(CustomerDTO customer);

    List<Customer> fetchAll();

    Customer fetch(UUID customerId);

    Customer update(UUID customerId, CustomerDTO customerDTO);

    Boolean delete(UUID customerId);

    PaginatedCustomerResponse fetchPaginatedCustomer(int page, int size, String sortBy, String sortDir);

    Optional<Customer> fetchByPhoneNumber(String phoneNumber);
}
