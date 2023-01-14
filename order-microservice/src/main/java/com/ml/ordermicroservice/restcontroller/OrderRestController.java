package com.ml.ordermicroservice.restcontroller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ml.ordermicroservice.dto.OrderDTO;
import com.ml.ordermicroservice.model.Order;
import com.ml.ordermicroservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/order")
@RequiredArgsConstructor
public class OrderRestController {


    private final OrderService orderService;

    @PostMapping("/accept-order")
    public ResponseEntity<?> acceptAnOrder(@RequestBody OrderDTO orderDTO) throws JsonProcessingException {
        OrderDTO responseOrder = orderService.acceptOrder(orderDTO);
        return ResponseEntity.ok(responseOrder);
    }

    @GetMapping("/search-order")
    public ResponseEntity<OrderDTO> searchOrderByOrderNumber(@RequestParam(name = "orderNumber",defaultValue = "") String orderNumber) throws JsonProcessingException {
        OrderDTO responseOrder = orderService.searchAnOrder(orderNumber);
        return ResponseEntity.ok(responseOrder);
    }

}
