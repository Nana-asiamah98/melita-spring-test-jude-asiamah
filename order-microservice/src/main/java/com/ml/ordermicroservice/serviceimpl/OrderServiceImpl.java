package com.ml.ordermicroservice.serviceimpl;

import com.ml.ordermicroservice.dto.OrderDTO;
import com.ml.ordermicroservice.model.InstallationAddress;
import com.ml.ordermicroservice.repository.CustomerRepository;
import com.ml.ordermicroservice.repository.InstallationAddressRepository;
import com.ml.ordermicroservice.repository.OrderItemRepository;
import com.ml.ordermicroservice.repository.OrderRepository;
import com.ml.ordermicroservice.service.OrderService;
import com.ml.ordermicroservice.utils.OrderNumberGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final InstallationAddressRepository installationAddress;
    private final CustomerRepository customerRepository;
    private final OrderItemRepository orderItemRepository;
    private final ApplicationEventPublisher eventPublisher;
    private final OrderNumberGenerator orderNumberGenerator;


    /*
    * Create An Order
    * */
    @Override
    public OrderDTO acceptOrder(OrderDTO orderDTO) {
        String orderNumber = orderNumberGenerator.generateOrderNumber();
        return null;
    }

    @Override
    public OrderDTO updateOrder(String orderNumber, OrderDTO orderDTO) {
        return null;
    }

    @Override
    public OrderDTO searchAnOrder(OrderDTO orderDTO) {
        return null;
    }

}
