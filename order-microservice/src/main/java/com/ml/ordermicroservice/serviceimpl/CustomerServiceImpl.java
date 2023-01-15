package com.ml.ordermicroservice.serviceimpl;

import com.ml.ordermicroservice.dto.CustomerDTO;
import com.ml.ordermicroservice.dto.PaginatedCustomerResponse;
import com.ml.ordermicroservice.model.Customer;
import com.ml.ordermicroservice.repository.CustomerRepository;
import com.ml.ordermicroservice.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final ModelMapper modelMapper;

    @Override
    public Customer save(CustomerDTO customer) {
        Customer mappedCustomer = modelMapper.map(customer,Customer.class);
        return customerRepository.save(mappedCustomer);
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
            __mainCustomer.setPhoneNumber(customerDTO.getPhoneNumber());
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

    @Override
    public PaginatedCustomerResponse fetchPaginatedCustomer(int page, int size, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Customer> customerPage = customerRepository.findAll(pageable);
        return toPaginatedCustomerResponse(customerPage);
    }

    @Override
    public Optional<Customer> fetchByPhoneNumber(String phoneNumber) {
        return customerRepository.findByPhoneNumber(phoneNumber);
    }

    private PaginatedCustomerResponse toPaginatedCustomerResponse(Page<Customer> customers) {
        List<CustomerDTO> customerDTOS = customers.getContent().stream().map(w -> modelMapper.map(w, CustomerDTO.class)).collect(Collectors.toList());

        return PaginatedCustomerResponse
                .builder()
                .content(customerDTOS)
                .last(customers.isLast())
                .pageNo(customers.getNumber())
                .pageSize(customers.getSize())
                .totalElements(customers.getTotalElements())
                .totalPages(customers.getTotalPages())
                .build();

    }


}
