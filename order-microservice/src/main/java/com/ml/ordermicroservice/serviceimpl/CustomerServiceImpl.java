package com.ml.ordermicroservice.serviceimpl;

import com.ml.ordermicroservice.dto.CustomerDTO;
import com.ml.ordermicroservice.model.Customer;
import com.ml.ordermicroservice.repository.CustomerRepository;
import com.ml.ordermicroservice.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    @Override
    public Customer save(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public List<Customer> fetchAll() {
        return customerRepository.findAll();
    }

    @Override
    public Customer fetch(UUID customerId) {
        return customerRepository.findById(customerId).orElseGet(Customer::new);
    }

    @Override
    public Customer update(UUID customerId, CustomerDTO customerDTO) {
        Optional<Customer> isCustomer = customerRepository.findById(customerId);
        if (isCustomer.isPresent()) {
            Customer __mainCustomer = isCustomer.get();
            __mainCustomer.setAddress(customerDTO.getAddress());
            __mainCustomer.setDob(customerDTO.getDob());
            __mainCustomer.setEmail(customerDTO.getEmail());
            __mainCustomer.setFirstName(customerDTO.getFirstName());
            __mainCustomer.setLastName(customerDTO.getLastName());
            return __mainCustomer;
        }
        return new Customer();
    }

    @Override
    public Boolean delete(UUID customerId) {
        boolean isCustomer = customerRepository.existsById(customerId);
        if (isCustomer) {
            customerRepository.deleteById(customerId);
            return true;
        }
        return false;
    }
}
