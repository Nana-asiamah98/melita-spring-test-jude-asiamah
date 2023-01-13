package com.ml.ordermicroservice.serviceimpl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ml.ordermicroservice.dto.OrderDTO;
import com.ml.ordermicroservice.events.OrderEvent;
import com.ml.ordermicroservice.model.Customer;
import com.ml.ordermicroservice.model.InstallationAddress;
import com.ml.ordermicroservice.model.Order;
import com.ml.ordermicroservice.model.OrderItem;
import com.ml.ordermicroservice.repository.CustomerRepository;
import com.ml.ordermicroservice.repository.InstallationAddressRepository;
import com.ml.ordermicroservice.repository.OrderItemRepository;
import com.ml.ordermicroservice.repository.OrderRepository;
import com.ml.ordermicroservice.service.OrderService;
import com.ml.ordermicroservice.utils.AppConstants;
import com.ml.ordermicroservice.utils.OrderNumberGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;
    private final InstallationAddressRepository installationAddressRepository;
    private final CustomerRepository customerRepository;
    private final OrderItemRepository orderItemRepository;
    private final ApplicationEventPublisher eventPublisher;
    private final OrderNumberGenerator orderNumberGenerator;
    private final ObjectMapper objectMapper;


    /*
    * Create An Order
    * */
    @Override
    public OrderDTO acceptOrder(OrderDTO orderDTO) throws JsonProcessingException {
        // Add Customer If He/She Does Not Exists In Our System
        Optional<Customer> customerExist = customerRepository.findByPhoneNumber(orderDTO.getCustomer().getPhoneNumber());
        Customer mappedCustomer = modelMapper.map(orderDTO.getCustomer(), Customer.class);
        if(customerExist.isPresent()){
            log.info("[CUSTOMER EXISTS]");
            //Save Customer
            Customer savedCustomer = customerExist.get();
            return this.savedOrder(savedCustomer,orderDTO);
        }else{
            log.info("[CUSTOMER DOESN'T EXISTS]");
            //Save Customer
            Customer savedCustomer = customerRepository.save(mappedCustomer);
            return this.savedOrder(savedCustomer,orderDTO);

        }
    }

    @Override
    public OrderDTO updateOrder(String orderNumber, OrderDTO orderDTO) {
        return null;
    }

    @Override
    public OrderDTO searchAnOrder(OrderDTO orderDTO) {
        return null;
    }


    private Double totalAmount(List<OrderItem> orderItem){
        return orderItem.stream().mapToDouble(OrderItem::getAmount).sum();
    }

    //TODO::Create a private function to save accepted order

    private OrderDTO savedOrder(Customer savedCustomer, OrderDTO orderDTO){
        String orderNumber = orderNumberGenerator.generateOrderNumber();

        //Model map Installation Address
        InstallationAddress mappedInstallationAddress = modelMapper.map(orderDTO.getInstallationAddress(), InstallationAddress.class);

        //Save Installation Address
        InstallationAddress savedInstallationAddress = installationAddressRepository.save(mappedInstallationAddress);

        //Model Map OrderList
        List<OrderItem> mappedOrderItems = orderDTO.getOrderItem().stream().map( w -> {
            return modelMapper.map(w,OrderItem.class);
        }).collect(Collectors.toList());

        //Model Map Order
        Order mappedOrder = modelMapper.map(orderDTO, Order.class);

        //Restructure Order Model
        mappedOrder.setOrderItems(mappedOrderItems);
        mappedOrder.setOrderStatus(AppConstants.ORDER_CREATED);
        mappedOrder.setOrderNumber(orderNumber);
        mappedOrder.setCustomerDetails(savedCustomer);
        mappedOrder.setInstallationAddress(savedInstallationAddress);
        mappedOrder.setTotalAmount(totalAmount(mappedOrderItems));


        //Save Order
        Order savedOrder = orderRepository.save(mappedOrder);
        OrderDTO responseOrderDTO =  modelMapper.map(savedOrder,OrderDTO.class);


        //Publish To Kafka
        eventPublisher.publishEvent(new OrderEvent(this, AppConstants.PRODUCT_CREATED,responseOrderDTO));

        return responseOrderDTO;

    }
}
