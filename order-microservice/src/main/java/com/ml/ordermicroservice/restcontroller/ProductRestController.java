package com.ml.ordermicroservice.restcontroller;

import com.ml.ordermicroservice.dto.CustomerDTO;
import com.ml.ordermicroservice.dto.PaginatedProductResponse;
import com.ml.ordermicroservice.dto.ProductDTO;
import com.ml.ordermicroservice.dto.ResponseDTO;
import com.ml.ordermicroservice.model.Customer;
import com.ml.ordermicroservice.model.Product;
import com.ml.ordermicroservice.service.ProductService;
import com.ml.ordermicroservice.utils.AppConstants;
import com.ml.ordermicroservice.utils.validators.CustomerValidator;
import com.ml.ordermicroservice.utils.validators.ProductValidator;
import com.ml.ordermicroservice.utils.validators.utils.ErrorResponse;
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
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/product")
public class ProductRestController {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<?> products(
            @RequestParam(name = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int page,
            @RequestParam(name = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int size,
            @RequestParam(name = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(name = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir,
            HttpServletRequest httpServletRequest
    ) {
        PaginatedProductResponse responseOrder = productService.fetchPaginatedResults(page, size, sortBy, sortDir);
        return ResponseEntity.ok(new ResponseDTO<>(HttpStatus.OK.value(), HttpStatus.OK.toString(), responseOrder, httpServletRequest.getSession().getId()));
    }

    @GetMapping(params = {"productName"})
    public ResponseEntity<?> fetchCustomer(@RequestParam(name = "productName", required = true) String productName, HttpServletRequest httpServletRequest) {
        Optional<Product> isProduct = productService.findByStringValue(productName);
        if (isProduct.isPresent()) {
            return ResponseEntity.ok(new ResponseDTO<>(HttpStatus.OK.value(), HttpStatus.OK.toString(), isProduct.get(), httpServletRequest.getSession().getId()));
        }
        return ResponseEntity.ok(new ResponseDTO<>(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.toString(), "Product with " + productName + " does not exist.", httpServletRequest.getSession().getId()));
    }

    @PostMapping
    public ResponseEntity<?> saveProduct(@RequestBody ProductDTO product, HttpServletRequest httpServletRequest) {

        ProductValidator productValidator = new ProductValidator(httpServletRequest.getSession().getId());
        boolean isValid = productValidator.validateCreateRequest(product);
        if (isValid) {
            log.info("PRODUCT REQUEST BODY IS VALID");
            Product responseOrder = productService.save(product);
            return ResponseEntity.ok(new ResponseDTO<>(HttpStatus.OK.value(), HttpStatus.OK.toString(), responseOrder, httpServletRequest.getSession().getId()));
        }
        log.info("PRODUCT REQUEST BODY IS NOT VALID");
        List<ErrorResponse> errorResponses = productValidator.errors();
        return ResponseEntity.badRequest().body(new ResponseDTO<>(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.toString(), errorResponses, Long.valueOf(errorResponses.size()), httpServletRequest.getSession().getId()));
    }

    @PutMapping("/{id}/edit")
    public ResponseEntity<?> updateProduct(@PathVariable Integer id, @RequestBody ProductDTO product, HttpServletRequest httpServletRequest) {
        ProductValidator productValidator = new ProductValidator(httpServletRequest.getSession().getId());
        boolean isValid = productValidator.validateCreateRequest(product);
        if (isValid) {
            log.info("PRODUCT REQUEST BODY IS VALID");
            Product responseOrder = productService.updateProduct(id,product);
            return ResponseEntity.ok(new ResponseDTO<>(HttpStatus.OK.value(), HttpStatus.OK.toString(), responseOrder, httpServletRequest.getSession().getId()));
        }
        log.info("PRODUCT REQUEST BODY IS NOT VALID");
        List<ErrorResponse> errorResponses = productValidator.errors();
        return ResponseEntity.badRequest().body(new ResponseDTO<>(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.toString(), errorResponses, Long.valueOf(errorResponses.size()), httpServletRequest.getSession().getId()));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Integer id) {
        boolean isDeleted = productService.deleteProduct(id);
        if (isDeleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.badRequest().body("Failed To Delete Customer with ID " + id);
    }
}
