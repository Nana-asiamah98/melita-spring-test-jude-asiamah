package com.ml.ordermicroservice.restcontroller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ml.ordermicroservice.dto.OrderDTO;
import com.ml.ordermicroservice.dto.PaginatedOrdersResponse;
import com.ml.ordermicroservice.dto.ResponseDTO;
import com.ml.ordermicroservice.dto.UpdateOrderStatusDTO;
import com.ml.ordermicroservice.service.OrderService;
import com.ml.ordermicroservice.service.PackageService;
import com.ml.ordermicroservice.service.ProductService;
import com.ml.ordermicroservice.utils.AppConstants;
import com.ml.ordermicroservice.utils.validators.OrderValidator;
import com.ml.ordermicroservice.utils.validators.utils.ErrorResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/order")
@RequiredArgsConstructor
public class OrderRestController {


    private final OrderService orderService;
    private final ObjectMapper objectMapper;


    @GetMapping(params = {"page","size","sortBy","sortDir"})
    public ResponseEntity<?> fetchPaginatedOrders(
            @RequestParam(name = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int page,
            @RequestParam(name = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int size,
            @RequestParam(name = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(name = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir,
            HttpServletRequest httpServletRequest) throws JsonProcessingException{
        PaginatedOrdersResponse responseOrder = orderService.fetchPaginatedOrders(page,size,sortBy,sortDir);
        return ResponseEntity.ok(new ResponseDTO<>(HttpStatus.OK.value(),HttpStatus.OK.toString(), responseOrder, httpServletRequest.getSession().getId()));
    }

    @PostMapping("/accept-order")
    public ResponseEntity<?> acceptAnOrder(@RequestBody OrderDTO orderDTO, HttpServletRequest httpServletRequest) throws JsonProcessingException {
        OrderValidator orderValidator = new OrderValidator(httpServletRequest.getSession().getId());
        boolean isValid = orderValidator.validateCreateRequest(orderDTO);
        if (isValid) {
            log.info("ORDER IS VALID");
            OrderDTO responseOrder = orderService.acceptOrder(orderDTO);
            return ResponseEntity.ok(new ResponseDTO<>(HttpStatus.OK.value(),HttpStatus.OK.toString(), responseOrder, httpServletRequest.getSession().getId()));
        }
        log.info("ORDER IS NOT VALID");
        List<ErrorResponse> errorResponses = orderValidator.errors();
        return ResponseEntity.badRequest().body(new ResponseDTO<>(HttpStatus.BAD_REQUEST.value(),HttpStatus.BAD_REQUEST.toString(), errorResponses, Long.valueOf(errorResponses.size()), httpServletRequest.getSession().getId()));

    }

    @GetMapping("/search-order")
    @ResponseBody
    public ResponseEntity<?> searchOrderByOrderNumber(@RequestParam(name = "orderNumber", defaultValue = "") String orderNumber,HttpServletRequest httpServletRequest) throws JsonProcessingException {
        OrderDTO responseOrder = orderService.searchAnOrder(orderNumber);
            return ResponseEntity.ok(new ResponseDTO<>(HttpStatus.OK.value(),HttpStatus.OK.toString(), responseOrder, httpServletRequest.getSession().getId()));
    }

    @PutMapping("/update-order-status")
    public ResponseEntity<?> updateOrderStatus(
            @RequestParam(name = "orderNumber", defaultValue = "") String orderNumber,
            @RequestBody UpdateOrderStatusDTO updateOrderStatusDTO,
            HttpServletRequest httpServletRequest) {

        OrderValidator orderValidator = new OrderValidator(httpServletRequest.getSession().getId());
        boolean isValid = orderValidator.validateUpdateRequest(updateOrderStatusDTO, orderNumber);

        if (isValid) {
            log.info("ORDER IS VALID");
            UpdateOrderStatusDTO responseOrder = orderService.updateOrderStatus(orderNumber, updateOrderStatusDTO);
            return ResponseEntity.ok(new ResponseDTO<>(HttpStatus.OK.value(),HttpStatus.OK.toString(), responseOrder, httpServletRequest.getSession().getId()));
        }
        log.info("ORDER IS NOT VALID");
        List<ErrorResponse> errorResponses = orderValidator.errors();
        return ResponseEntity.badRequest().body(new ResponseDTO<>(HttpStatus.BAD_REQUEST.value(),HttpStatus.BAD_REQUEST.toString(), errorResponses, Long.valueOf(errorResponses.size()), httpServletRequest.getSession().getId()));
    }

}
