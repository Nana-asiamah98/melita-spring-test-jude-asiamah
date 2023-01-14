package com.ml.ordermicroservice.utils.validators;

import com.ml.ordermicroservice.dto.OrderDTO;
import com.ml.ordermicroservice.dto.UpdateOrderStatusDTO;
import com.ml.ordermicroservice.service.PackageService;
import com.ml.ordermicroservice.service.ProductService;
import com.ml.ordermicroservice.utils.AppConstants;
import com.ml.ordermicroservice.utils.validators.utils.BaseValidator;
import com.ml.ordermicroservice.utils.validators.utils.ValidationUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Slf4j
public class OrderValidator extends BaseValidator {

    public OrderValidator(String requestId) {
        super(requestId);
    }

    public boolean validateCreateRequest(OrderDTO request) {
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
         *  Validate Customer's First Name
         * */
        if (!ValidationUtils.isStringPresent(request.getCustomer().getFirstName())) {
            errors.put("customer.firstName", "Customer first name cannot be empty");
            log.info("[ " + requestId + " ]" + "validation failed : customer first name is empty");
        }

        /*
         *  Validate Customer's Last Name
         * */
        if (!ValidationUtils.isStringPresent(request.getCustomer().getLastName())) {
            errors.put("customer.lastName", "Customer last name cannot be empty");
            log.info("[ " + requestId + " ]" + "validation failed : customer last name is empty");
        }

        /*
         *  Validate Customer's Phone Number
         * */
        if (!ValidationUtils.isStringPresent(request.getCustomer().getPhoneNumber())) {
            errors.put("customer.phoneNumber", "Customer phone number cannot be empty");
            log.info("[ " + requestId + " ]" + "validation failed : customer phone number is empty");
        }

        /*
         *  Validate Customer's Email is Present
         * */
        if (!ValidationUtils.isStringPresent(request.getCustomer().getEmail())) {
            errors.put("customer.email", "Customer email cannot be empty");
            log.info("[ " + requestId + " ]" + "validation failed : customer email is empty");
        }

        /*
         *  Validate Customer's Email is Valid
         * */
        if (!ValidationUtils.isValidEmail(request.getCustomer().getEmail())) {
            errors.put("customer.email", "Customer email is not valid");
            log.info("[ " + requestId + " ]" + "validation failed : customer email is not valid");
        }

        /*
         *  Validate Customer's Address is Present
         * */
        if (!ValidationUtils.isStringPresent(request.getCustomer().getAddress())) {
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
         *  ---------------------------
         *  START : VALIDATING INSTALLATION ADDRESS OBJECT
         * ---------------------------
         *
         * */


        /*
         *  Validate street Name is present
         * */
        if (!ValidationUtils.isStringPresent(request.getInstallationAddress().getStreetName())) {
            errors.put("installationAddress.streetName", "Street name cannot be empty");
            log.info("[ " + requestId + " ]" + "validation failed : town  is empty");
        }

        /*
         *  Validate town is present
         * */
        if (!ValidationUtils.isStringPresent(request.getInstallationAddress().getTown())) {
            errors.put("installationAddress.town", "Town  cannot be empty");
            log.info("[ " + requestId + " ]" + "validation failed : town  is empty");
        }

        /*
         *  Validate region is present
         * */
        if (!ValidationUtils.isStringPresent(request.getInstallationAddress().getRegion())) {
            errors.put("installationAddress.region", "Region  cannot be empty");
            log.info("[ " + requestId + " ]" + "validation failed : region  is empty");
        }

        /*
         *  ---------------------------
         *  END : VALIDATING CUSTOMER OBJECT
         * ---------------------------
         *
         * */


        /*
         *  Validate street Name is present
         * */
        if (!ValidationUtils.isStringPresent(request.getInstallationAddress().getStreetName())) {
            errors.put("installationAddress.streetName", "Street name cannot be empty");
            log.info("[ " + requestId + " ]" + "validation failed : town  is empty");
        }




        /*
         *  ---------------------------
         *  START : VALIDATING ORDER ITEMS OBJECT
         * ---------------------------
         *
         * */

        /*
         *  Validate Order Item Size
         * */
        if (!ValidationUtils.hasSizeGreaterThanOne(request.getOrderItem().size())) {
            errors.put("orderItems.size", "There is an empty list of order items");
            log.info("[ " + requestId + " ]" + "validation failed : order items  is empty");
        }


        // Create an AtomicInteger for index
        AtomicInteger index = new AtomicInteger();
        List<Map<String, String>> orderValidationRemarks = request.getOrderItem().stream().map(w -> {
            Map<String, String> message = new HashMap<>();

            String mapIndex = String.valueOf(index.getAndIncrement());
            /*
             * Validate Product Name is present
             * */
            if (!ValidationUtils.isStringPresent(w.getProductName())) {
                message.put("orderItem.productName.[".concat(mapIndex).concat("]"), "Product name cannot be empty");
                log.info("[ " + requestId + " ]" + "validation failed : product name  is empty");
            }

            /*
             * Validate Package Name is present
             * */
            if (!ValidationUtils.isStringPresent(w.getPackageName())) {
                message.put("orderItem.packageName.[".concat(mapIndex).concat("]"), "Package name cannot be empty");
                log.info("[ " + requestId + " ]" + "validation failed : packageName  is empty");
            }

            /*
             * Validate Amount is present
             * */
            if (!ValidationUtils.isDoublePresent(w.getAmount())) {
                message.put("orderItem.amount.[".concat(mapIndex).concat("]"), "Amount cannot be empty");
                log.info("[ " + requestId + " ]" + "validation failed : amount  is empty");
            }


            return message;

        }).collect(Collectors.toList()).stream().filter(m -> !m.isEmpty()).collect(Collectors.toList());


        if (ValidationUtils.hasSizeGreaterThanOne(orderValidationRemarks.size())) {
            errors.put("orderItems", orderValidationRemarks);
            log.info("[ " + requestId + " ]" + "validation failed : order items  is empty");
        }


        /*
         *  ---------------------------
         *  END : VALIDATING ORDER ITEMS OBJECT
         * ---------------------------
         *
         * */

        return errors.entrySet().size() <= 0;
    }

    public boolean validateUpdateRequest(UpdateOrderStatusDTO request, String orderNumber) {

        List<String> orderStatus = Arrays.asList(AppConstants.ORDER_CANCELLED, AppConstants.ORDER_COMPLETED, AppConstants.ORDER_PROCESSED, AppConstants.ORDER_PROCESSING, AppConstants.ORDER_CREATED);

        log.info("[ " + requestId + " ]" + "validating create order request payload");

        /*
         * General UpdateOrderStatusDTO Object
         * */
        if (!ValidationUtils.isObjectPresent(request)) {
            errors.put("body", "Request body cannot be empty");
            log.info("[ " + requestId + " ]" + "validation failed : request body is empty");
        }

        /*
         *  Validate Order Status
         * */
        if (!ValidationUtils.isStringPresent(request.getOrderStatus())) {
            errors.put("orderStatus", "Order status cannot be empty");
            log.info("[ " + requestId + " ]" + "validation failed : order status is empty");
        }

        /*
         *  Validate Order Status is valid
         * */
        if (!ValidationUtils.isWithinTheList(orderStatus, request.getOrderStatus())) {
            errors.put("status", "Order status : " + request.getOrderStatus() + " is not valid");
            log.info("[ " + requestId + " ]" + "validation failed : order status is not valid");
        }

        /*
         *  Validate Username
         * */
        if (!ValidationUtils.isStringPresent(request.getUserName())) {
            errors.put("userName", "Username cannot be empty");
            log.info("[ " + requestId + " ]" + "validation failed : username is empty");
        }

        /*
         *  Validate Order
         * */
        if (!ValidationUtils.isStringPresent(orderNumber)) {
            errors.put("orderNumber", "Order number cannot be empty");
            log.info("[ " + requestId + " ]" + "validation failed : orderNumber is empty");
        }

        return errors.entrySet().size() <= 0;

    }
}
