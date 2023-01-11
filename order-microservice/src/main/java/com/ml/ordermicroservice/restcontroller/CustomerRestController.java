package com.ml.ordermicroservice.restcontroller;

import com.ml.ordermicroservice.dto.CustomerDTO;
import com.ml.ordermicroservice.model.Customer;
import com.ml.ordermicroservice.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/customer")
@RequiredArgsConstructor
public class CustomerRestController {

    private final CustomerService customerService;

    @GetMapping
    public ResponseEntity<?> fetchAllCustomers() {
        return ResponseEntity.ok(customerService.fetchAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> fetchCustomerById(@PathVariable UUID id) {
        return ResponseEntity.ok(customerService.fetch(id));
    }

    @PostMapping
    public ResponseEntity<?> saveCustomer(@RequestBody Customer customer) {
        return ResponseEntity.ok(customerService.save(customer));
    }

    @PutMapping("/{id}/edit")
    public ResponseEntity<?> updateCustomer(@PathVariable UUID id, @RequestBody CustomerDTO customerDTO) {
        return ResponseEntity.ok(customerService.update(id, customerDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable UUID id) {
        boolean isDeleted = customerService.delete(id);
        if (isDeleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.badRequest().body("Failed To Delete Customer with ID " + id);
    }

}
