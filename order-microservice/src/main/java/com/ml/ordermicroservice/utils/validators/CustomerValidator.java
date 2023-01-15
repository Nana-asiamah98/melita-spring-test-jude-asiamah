package com.ml.ordermicroservice.utils.validators;

import com.ml.ordermicroservice.dto.CustomerDTO;
import com.ml.ordermicroservice.dto.OrderDTO;
import com.ml.ordermicroservice.dto.UpdateOrderStatusDTO;
import com.ml.ordermicroservice.model.Customer;
import com.ml.ordermicroservice.utils.AppConstants;
import com.ml.ordermicroservice.utils.validators.utils.BaseValidator;
import com.ml.ordermicroservice.utils.validators.utils.ValidationUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Slf4j
public class CustomerValidator extends BaseValidator {

    public CustomerValidator(String requestId) {
        super(requestId);
    }

    public boolean validateCreateRequest(CustomerDTO request) {
        log.info("[ " + requestId + " ]" + "validating create order request payload");



        /*
         *  ---------------------------
         *  START : VALIDATING CUSTOMER OBJECT
         * ---------------------------
         *
         * */

        /*
         * General OrderDTO Object
         * */
        if (!ValidationUtils.isObjectPresent(request)) {
            errors.put("body", "Request body cannot be empty");
            log.info("[ " + requestId + " ]" + "validation failed : request body is empty");
        }


        /*
         *  Validate Customer's First Name
         * */
        if (!ValidationUtils.isStringPresent(request.getFirstName())) {
            errors.put("customer.firstName", "Customer first name cannot be empty");
            log.info("[ " + requestId + " ]" + "validation failed : customer first name is empty");
        }

        /*
         *  Validate Customer's Last Name
         * */
        if (!ValidationUtils.isStringPresent(request.getLastName())) {
            errors.put("customer.lastName", "Customer last name cannot be empty");
            log.info("[ " + requestId + " ]" + "validation failed : customer last name is empty");
        }

        /*
         *  Validate Customer's Phone Number
         * */
        if (!ValidationUtils.isStringPresent(request.getPhoneNumber())) {
            errors.put("customer.phoneNumber", "Customer phone number cannot be empty");
            log.info("[ " + requestId + " ]" + "validation failed : customer phone number is empty");
        }

        /*
         *  Validate Customer's Phone Number Is Correct
         * */
        if (!ValidationUtils.isPhoneNumber(request.getPhoneNumber())) {
            errors.put("phoneNumber", "Customer phone number is not valid");
            log.info("[ " + requestId + " ]" + "validation failed : customer phone number is not valid");
        }

        /*
         *  Validate Customer's Email is Present
         * */
        if (!ValidationUtils.isStringPresent(request.getEmail())) {
            errors.put("customer.email", "Customer email cannot be empty");
            log.info("[ " + requestId + " ]" + "validation failed : customer email is empty");
        }

        /*
         *  Validate Customer's Email is Valid
         * */
        if (!ValidationUtils.isValidEmail(request.getEmail())) {
            errors.put("customer.email", "Customer email is not valid");
            log.info("[ " + requestId + " ]" + "validation failed : customer email is not valid");
        }

        /*
         *  Validate Customer's Address is Present
         * */
        if (!ValidationUtils.isStringPresent(request.getAddress())) {
            errors.put("customer.email", "Customer address cannot be empty");
            log.info("[ " + requestId + " ]" + "validation failed : customer address is empty");
        }

        /*
         *  ---------------------------
         *  END : VALIDATING CUSTOMER OBJECT
         * ---------------------------
         *
         * */


        return errors.entrySet().size() <= 0;
    }

    public boolean validateUpdateRequest(CustomerDTO request, UUID customerId) {

        log.info("[ " + requestId + " ]" + "validating create order request payload");


        /*
         *  ---------------------------
         *  START : VALIDATING CUSTOMER OBJECT
         * ---------------------------
         *
         * */




        /*
         *  Validate Customer's First Name
         * */
        if (!ValidationUtils.isStringPresent(request.getFirstName())) {
            errors.put("customer.firstName", "Customer first name cannot be empty");
            log.info("[ " + requestId + " ]" + "validation failed : customer first name is empty");
        }

        /*
         *  Validate Customer's Last Name
         * */
        if (!ValidationUtils.isStringPresent(request.getLastName())) {
            errors.put("customer.lastName", "Customer last name cannot be empty");
            log.info("[ " + requestId + " ]" + "validation failed : customer last name is empty");
        }

        /*
         *  Validate Customer's Phone Number
         * */
        if (!ValidationUtils.isStringPresent(request.getPhoneNumber())) {
            errors.put("customer.phoneNumber", "Customer phone number cannot be empty");
            log.info("[ " + requestId + " ]" + "validation failed : customer phone number is empty");
        }

        /*
         *  Validate Customer's Email is Present
         * */
        if (!ValidationUtils.isStringPresent(request.getEmail())) {
            errors.put("customer.email", "Customer email cannot be empty");
            log.info("[ " + requestId + " ]" + "validation failed : customer email is empty");
        }

        /*
         *  Validate Customer's Email is Valid
         * */
        if (!ValidationUtils.isValidEmail(request.getEmail())) {
            errors.put("customer.email", "Customer email is not valid");
            log.info("[ " + requestId + " ]" + "validation failed : customer email is not valid");
        }

        /*
         *  Validate Customer's Address is Present
         * */
        if (!ValidationUtils.isStringPresent(request.getAddress())) {
            errors.put("customer.email", "Customer address cannot be empty");
            log.info("[ " + requestId + " ]" + "validation failed : customer address is empty");
        }

        /*
         *  ---------------------------
         *  END : VALIDATING CUSTOMER OBJECT
         * ---------------------------
         *
         * */


        /*
         *  Validate Order
         * */
        if (!ValidationUtils.isStringPresent(customerId.toString())) {
            errors.put("customer", "customer pathvariable id cannot be empty");
            log.info("[ " + requestId + " ]" + "validation failed : customer id is empty");
        }

        return errors.entrySet().size() <= 0;

    }
}

