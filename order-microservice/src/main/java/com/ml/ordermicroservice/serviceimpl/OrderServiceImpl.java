package com.ml.ordermicroservice.serviceimpl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ml.ordermicroservice.dto.CustomerDTO;
import com.ml.ordermicroservice.dto.OrderDTO;
import com.ml.ordermicroservice.dto.OrderItemDTO;
import com.ml.ordermicroservice.dto.UpdateOrderStatusDTO;
import com.ml.ordermicroservice.events.OrderEvent;
import com.ml.ordermicroservice.exceptions.OrderNotExistsException;
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

import java.time.ZonedDateTime;
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
        if (customerExist.isPresent()) {
            log.info("[CUSTOMER EXISTS]");
            //Save Customer
            Customer savedCustomer = customerExist.get();
            return this.savedOrder(savedCustomer, orderDTO);
        } else {
            log.info("[CUSTOMER DOESN'T EXISTS]");
            //Save Customer
            Customer savedCustomer = customerRepository.save(mappedCustomer);
            return this.savedOrder(savedCustomer, orderDTO);

        }
    }

    /*
    *  Update The Order with the following
    *  1. username( Officer's username from their account)
    *  2. orderStatus( The selected status of the order by the officer. )
    *  3. comment ( The comment of an order )
    * */
    @Override
    public UpdateOrderStatusDTO updateOrderStatus(String orderNumber, UpdateOrderStatusDTO orderDTO) {
        //Check if the order Exists
        Optional<Order> isOrder = Optional.ofNullable(orderRepository.findByOrderNumber(orderNumber).orElseThrow(OrderNotExistsException::new));
        if (isOrder.isPresent()) {
            Order __mainOrder = isOrder.get();

            log.info("[ORDER {} EXIST]", orderNumber);

            __mainOrder.setOrderStatus(orderDTO.getOrderStatus());
            __mainOrder.setComment(orderDTO.getComment());
            __mainOrder.setUserName(orderDTO.getUserName());
            __mainOrder.setUpdatedAt(ZonedDateTime.now());

            switch (orderDTO.getOrderStatus()) {
                case AppConstants.ORDER_PROCESSING:
                case AppConstants.ORDER_PROCESSED:
                case AppConstants.ORDER_COMPLETED:
                case AppConstants.ORDER_CANCELLED:
                    orderRepository.save(__mainOrder);
                    OrderDTO mappedToDTo = mapEntityToDTO(__mainOrder);
                    //Publish To Kafka
                    eventPublisher.publishEvent(new OrderEvent(this, mappedToDTo.getOrderStatus(), mappedToDTo));
                    return UpdateOrderStatusDTO
                            .builder()
                            .userName(orderDTO.getUserName())
                            .comment(orderDTO.getComment())
                            .orderStatus(orderDTO.getOrderStatus())
                            .order(mappedToDTo)
                            .build();
                default:
                    return new UpdateOrderStatusDTO();
            }
        }

        return new UpdateOrderStatusDTO();
    }

    /*
    *   Search for an order using the orderNumber
    * */
    @Override
    public OrderDTO searchAnOrder(String orderNumber) throws JsonProcessingException {
        Optional<Order> isOrder = Optional.ofNullable(orderRepository.findByOrderNumber(orderNumber).orElseThrow(OrderNotExistsException::new));
        if (isOrder.isPresent()) {
            log.info("[ORDER {} EXIST]", orderNumber);
            Order __mainOrder = isOrder.get();
            return mapEntityToDTO(__mainOrder);
        }
        return new OrderDTO();
    }


    /*
    *  Calculate the total Amount using the order items.
    * */
    private Double totalAmount(List<OrderItem> orderItem) {
        return orderItem.stream().mapToDouble(OrderItem::getAmount).sum();
    }


    /*
    *  Save an order
    * */
    private OrderDTO savedOrder(Customer savedCustomer, OrderDTO orderDTO) {
        String orderNumber = orderNumberGenerator.generateOrderNumber();

        //Model map Installation Address
        InstallationAddress mappedInstallationAddress = modelMapper.map(orderDTO.getInstallationAddress(), InstallationAddress.class);

        //Save Installation Address
        InstallationAddress savedInstallationAddress = installationAddressRepository.save(mappedInstallationAddress);

        //Model Map OrderList
        List<OrderItem> mappedOrderItems = orderDTO.getOrderItem().stream().map(w -> {
            return modelMapper.map(w, OrderItem.class);
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
        OrderDTO responseOrderDTO = modelMapper.map(savedOrder, OrderDTO.class);
        // Map to OrderItemDTO
        List<OrderItemDTO> responseOrderItemDTO = savedOrder.getOrderItems().stream().map(w -> {
            OrderItemDTO __orderItemDTO = modelMapper.map(w, OrderItemDTO.class);
            __orderItemDTO.setOrderNumber(orderNumber);
            return __orderItemDTO;
        }).collect(Collectors.toList());
        responseOrderDTO.setOrderItem(responseOrderItemDTO);
        // Map to CustomerDTO
        CustomerDTO __customerDTO = modelMapper.map(savedCustomer, CustomerDTO.class);
        responseOrderDTO.setCustomer(__customerDTO);
        //Publish To Kafka
        eventPublisher.publishEvent(new OrderEvent(this, AppConstants.PRODUCT_CREATED, responseOrderDTO));
        return responseOrderDTO;
    }

    /*
    *  Map an Order Entity To it's DTO
    * */
    private OrderDTO mapEntityToDTO(Order __mainOrder) {
        OrderDTO mappedOrder = modelMapper.map(__mainOrder, OrderDTO.class);
        CustomerDTO mappedCustomer = modelMapper.map(__mainOrder.getCustomerDetails(), CustomerDTO.class);
        List<OrderItemDTO> mappedOrderDTO = __mainOrder.getOrderItems().stream().map(w -> {
            return modelMapper.map(w, OrderItemDTO.class);
        }).collect(Collectors.toList());
        mappedOrder.setCustomer(mappedCustomer);
        mappedOrder.setOrderItem(mappedOrderDTO);
        return mappedOrder;
    }
}
