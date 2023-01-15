package com.ml.ordermicroservice.restcontroller;

import com.ml.ordermicroservice.dto.CustomerDTO;
import com.ml.ordermicroservice.dto.PaginatedCustomerResponse;
import com.ml.ordermicroservice.dto.ResponseDTO;
import com.ml.ordermicroservice.model.Customer;
import com.ml.ordermicroservice.service.CustomerService;
import com.ml.ordermicroservice.utils.AppConstants;
import com.ml.ordermicroservice.utils.validators.CustomerValidator;
import com.ml.ordermicroservice.utils.validators.utils.ErrorResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/v1/customer")
@RequiredArgsConstructor
@Api(value = "Customer Service" , description = "A List Of REST Endpoint For Customer Service")
public class CustomerRestController {

    private final CustomerService customerService;

    @GetMapping
    @ApiOperation(value = "Fetch Paginated Customers")
    public ResponseEntity<?> fetchPaginatedCustomers(
            @RequestParam(name = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int page,
            @RequestParam(name = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int size,
            @RequestParam(name = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(name = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir,
            HttpServletRequest httpServletRequest
    ) {
        PaginatedCustomerResponse responseOrder = customerService.fetchPaginatedCustomer(page, size, sortBy, sortDir);
        return ResponseEntity.ok(new ResponseDTO<>(HttpStatus.OK.value(), HttpStatus.OK.toString(), responseOrder, httpServletRequest.getSession().getId()));
    }

    @ApiOperation(value = "Fetch customer by phone number")
    @GetMapping(value = "/phone-number",params = {"phoneNumber"})
    public ResponseEntity<?> fetchCustomer(@RequestParam(name = "phoneNumber", required = true) String phoneNumber,HttpServletRequest httpServletRequest){
        Optional<Customer> isCustomer = customerService.fetchByPhoneNumber(phoneNumber);
        if(isCustomer.isPresent()){
            return ResponseEntity.ok(new ResponseDTO<>(HttpStatus.OK.value(), HttpStatus.OK.toString(), isCustomer.get(), httpServletRequest.getSession().getId()));
        }
        return ResponseEntity.ok(new ResponseDTO<>(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.toString(), "Customer with phone number " + phoneNumber + " does not exist.", httpServletRequest.getSession().getId()));

    }

    @ApiOperation(value = "Fetch customer by id")
    @GetMapping("/{id}")
    public ResponseEntity<?> fetchCustomerById(@PathVariable UUID id, HttpServletRequest httpServletRequest) {
        Customer responseOrder = customerService.fetch(id);
        return ResponseEntity.ok(new ResponseDTO<>(HttpStatus.OK.value(), HttpStatus.OK.toString(), responseOrder, httpServletRequest.getSession().getId()));

    }

    @ApiOperation(value = "Create customer")
    @PostMapping
    public ResponseEntity<?> saveCustomer(@RequestBody CustomerDTO customer, HttpServletRequest httpServletRequest) {
        CustomerValidator customerValidator = new CustomerValidator(httpServletRequest.getSession().getId());
        boolean isValid = customerValidator.validateCreateRequest(customer);
        if (isValid) {
            log.info("CUSTOMER REQUEST BODY IS VALID");
            Customer responseOrder = customerService.save(customer);
            return ResponseEntity.ok(new ResponseDTO<>(HttpStatus.OK.value(), HttpStatus.OK.toString(), responseOrder, httpServletRequest.getSession().getId()));

        }
        log.info("CUSTOMER REQUEST BODY IS NOT VALID");
        List<ErrorResponse> errorResponses = customerValidator.errors();
        return ResponseEntity.badRequest().body(new ResponseDTO<>(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.toString(), errorResponses, Long.valueOf(errorResponses.size()), httpServletRequest.getSession().getId()));

    }

    @ApiOperation(value = "Edit customer")
    @PutMapping("/{id}/edit")
    public ResponseEntity<?> updateCustomer(@PathVariable UUID id, @RequestBody CustomerDTO customerDTO, HttpServletRequest httpServletRequest) {
        CustomerValidator customerValidator = new CustomerValidator(httpServletRequest.getSession().getId());
        boolean isValid = customerValidator.validateCreateRequest(customerDTO);
        if (isValid) {
            log.info("CUSTOMER REQUEST BODY IS VALID");
            Customer responseOrder = customerService.update(id, customerDTO);
            return ResponseEntity.ok(new ResponseDTO<>(HttpStatus.OK.value(), HttpStatus.OK.toString(), responseOrder, httpServletRequest.getSession().getId()));

        }
        log.info("CUSTOMER REQUEST BODY IS NOT VALID");
        List<ErrorResponse> errorResponses = customerValidator.errors();
        return ResponseEntity.badRequest().body(new ResponseDTO<>(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.toString(), errorResponses, Long.valueOf(errorResponses.size()), httpServletRequest.getSession().getId()));
    }

    @ApiOperation(value = "Delete customer using id")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable UUID id) {
        boolean isDeleted = customerService.delete(id);
        if (isDeleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.badRequest().body("Failed To Delete Customer with ID " + id);
    }

}
