package com.ml.ordermicroservice.utils.validators;

import com.ml.ordermicroservice.dto.CustomerDTO;
import com.ml.ordermicroservice.dto.ProductDTO;
import com.ml.ordermicroservice.utils.validators.utils.BaseValidator;
import com.ml.ordermicroservice.utils.validators.utils.ValidationUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Slf4j
public class ProductValidator extends BaseValidator {

    public ProductValidator(String requestId) {
        super(requestId);
    }

    public boolean validateCreateRequest(ProductDTO request) {
        log.info("[ " + requestId + " ]" + "validating create order request payload");

        /*
         * General OrderDTO Object
         * */
        if (!ValidationUtils.isObjectPresent(request)) {
            errors.put("body", "Request body cannot be empty");
            log.info("[ " + requestId + " ]" + "validation failed : request body is empty");
        }


        /*
         *  ---------------------------
         *  START : VALIDATING CUSTOMER OBJECT
         * ---------------------------
         *
         * */

        /*
        *  Validate Product Name is present
         * */
        if (!ValidationUtils.isStringPresent(request.getProductName())) {
            errors.put("product.name", "Product name cannot be empty");
            log.info("[ " + requestId + " ]" + "validation failed : product name is empty");
        }

        // Create an AtomicInteger for index
        AtomicInteger index = new AtomicInteger();
        List<Map<String, String>> packagesValidationRemarks = request.getPackages().stream().map(w -> {
            Map<String, String> message = new HashMap<>();

            String mapIndex = String.valueOf(index.getAndIncrement());
            /*
             * Validate Package Name is present
             * */
            if (!ValidationUtils.isStringPresent(w.getPackageName())) {
                message.put("package.packageName.[".concat(mapIndex).concat("]"), "Package name cannot be empty");
                log.info("[ " + requestId + " ]" + "validation failed : package name  is empty");
            }


            /*
             * Validate Rate is present
             * */
            if (!ValidationUtils.isDoublePresent(w.getRate())) {
                message.put("orderItem.amount.[".concat(mapIndex).concat("]"), "Amount cannot be empty");
                log.info("[ " + requestId + " ]" + "validation failed : amount  is empty");
            }

            return message;
        }).collect(Collectors.toList()).stream().filter(m -> !m.isEmpty()).collect(Collectors.toList());


        if (ValidationUtils.hasSizeGreaterThanOne(packagesValidationRemarks.size())) {
            errors.put("packagesItems", packagesValidationRemarks);
            log.info("[ " + requestId + " ]" + "validation failed : order items  has multiple errors");
        }


        /*
         *  ---------------------------
         *  END : VALIDATING PRODUCT OBJECT
         * ---------------------------
         *
         * */


        return errors.entrySet().size() <= 0;
    }

    public boolean validateUpdateRequest(ProductDTO request, UUID customerId) {
        log.info("[ " + requestId + " ]" + "validating create order request payload");

        /*
         * General OrderDTO Object
         * */
        if (!ValidationUtils.isObjectPresent(request)) {
            errors.put("body", "Request body cannot be empty");
            log.info("[ " + requestId + " ]" + "validation failed : request body is empty");
        }


        /*
         *  ---------------------------
         *  START : VALIDATING CUSTOMER OBJECT
         * ---------------------------
         *
         * */

        /*
         *  Validate Product Name is present
         * */
        if (!ValidationUtils.isStringPresent(request.getProductName())) {
            errors.put("product.name", "Product name cannot be empty");
            log.info("[ " + requestId + " ]" + "validation failed : product name is empty");
        }

        // Create an AtomicInteger for index
        AtomicInteger index = new AtomicInteger();
        List<Map<String, String>> packagesValidationRemarks = request.getPackages().stream().map(w -> {
            Map<String, String> message = new HashMap<>();

            String mapIndex = String.valueOf(index.getAndIncrement());
            /*
             * Validate Package Id is present
             * */
            if (!ValidationUtils.isStringPresent(w.getId().toString())) {
                message.put("package.id.[".concat(mapIndex).concat("]"), "Package id cannot be empty");
                log.info("[ " + requestId + " ]" + "validation failed : package id  is empty");
            }

            /*
             * Validate Package Name is present
             * */
            if (!ValidationUtils.isStringPresent(w.getPackageName())) {
                message.put("package.packageName.[".concat(mapIndex).concat("]"), "Package name cannot be empty");
                log.info("[ " + requestId + " ]" + "validation failed : package name  is empty");
            }


            /*
             * Validate Rate is present
             * */
            if (!ValidationUtils.isDoublePresent(w.getRate())) {
                message.put("orderItem.amount.[".concat(mapIndex).concat("]"), "Rate cannot be empty");
                log.info("[ " + requestId + " ]" + "validation failed : amount  is empty");
            }

            return message;
        }).collect(Collectors.toList()).stream().filter(m -> !m.isEmpty()).collect(Collectors.toList());


        if (ValidationUtils.hasSizeGreaterThanOne(packagesValidationRemarks.size())) {
            errors.put("packagesItems", packagesValidationRemarks);
            log.info("[ " + requestId + " ]" + "validation failed : order items  has multiple errors");
        }


        /*
         *  ---------------------------
         *  END : VALIDATING PRODUCT OBJECT
         * ---------------------------
         *
         * */


        return errors.entrySet().size() <= 0;
    }
}

