package com.ml.ordermicroservice.restcontrollertest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ml.ordermicroservice.dto.*;
import com.ml.ordermicroservice.model.Packages;
import com.ml.ordermicroservice.model.Product;
import com.ml.ordermicroservice.repository.OrderRepository;
import com.ml.ordermicroservice.restcontroller.OrderRestController;
import com.ml.ordermicroservice.service.OrderService;
import com.ml.ordermicroservice.utils.AppConstants;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static reactor.core.publisher.Mono.when;

@WebMvcTest(controllers = OrderRestController.class)
@ActiveProfiles("test")
public class OrderRestControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private OrderService orderService;

    @MockBean
    private OrderRepository orderRepository;

    @Autowired
    private MockMvc mockMvc;


    @Autowired
    private ObjectMapper objectMapper;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }




    @Test
    @DisplayName("Should Search An Order With An Order Number When Making A GET Request To Endpoint - /api/v1/order/search-order?orderNumber=")
    public void shouldSearchAnOrder() throws Exception {

        OrderDTO mockResponseData = orderResponseData();

        String orderNumber = "000001";
        MultiValueMap<String, String> paramsMap = new LinkedMultiValueMap<>();
        paramsMap.add("orderNumber", orderNumber);

        Mockito.when(orderService.searchAnOrder(orderNumber)).thenReturn(mockResponseData);

        mockMvc.perform(get("/api/v1/order/search-order").params(paramsMap)
        )
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                        .andExpect(jsonPath("$.data.orderItem.size()", Matchers.is(2)));
    }

    @Test
    @DisplayName("Should SAVE Order when making a POST Request To Endpoint -  /api/v1/order/accept-order")
    public void shouldAcceptOrder() throws Exception {
        OrderDTO mockRequestDTO = orderRequestData();
        OrderDTO mockResponseDTO = orderResponseData();

        Mockito.when(orderService.acceptOrder(mockRequestDTO)).thenReturn(mockResponseDTO);
        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/v1/order/accept-order")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(mockRequestDTO))
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.data.orderItem.size()").value(mockResponseDTO.getOrderItem().size()));
    }

    @Test
    @DisplayName("Should Update The Status Of An Order Using A Request Body And An orderNumber Request Parameter A PUT Method To The Endpoint - /api/v1/order/update-order?orderNumber=")
    public void shouldUpdateAnOrder() throws Exception{

        UpdateOrderStatusDTO mockRequestData = updateRequestData();
        UpdateOrderStatusDTO mockResponseData = updateResponseData();
        String orderNumber = "000001";
        MultiValueMap<String, String> paramsMap = new LinkedMultiValueMap<>();
        paramsMap.add("orderNumber", orderNumber);

        Mockito.when(orderService.updateOrderStatus(orderNumber,mockRequestData)).thenReturn(mockResponseData);

        mockMvc.perform(put("/api/v1/order/update-order-status")
                .params(paramsMap)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(mockRequestData)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data.order.orderItem.size()").value(mockResponseData.getOrder().getOrderItem().size()));
    }


    private OrderDTO orderRequestData() {

        CustomerDTO customer = CustomerDTO
                .builder()
                .firstName("Test Name")
                .lastName("Test Last Name")
                .phoneNumber("02011445697")
                .email("asas@asa.com")
                .address("Street Address")
                .build();

        InstallationAddressDTO installationAddress = InstallationAddressDTO.builder()
                .region("Test Region")
                .streetName("Nin")
                .town("NIni")
                .build();

        OrderItemDTO orderItem = OrderItemDTO.builder()
                .amount(12.13)
                .packageName("Test Package Name")
                .productName("Test Product Name")
                .build();
        return OrderDTO.builder()
                .customer(customer)
                .installationAddress(installationAddress)
                .orderItem(Arrays.asList(orderItem, orderItem))
                .notes("Test Notes")
                .orderNumber("000001")
                .build();
    }

    private OrderDTO orderResponseData() {

        CustomerDTO customer = CustomerDTO
                .builder()
                .id(UUID.randomUUID())
                .firstName("Test Name")
                .lastName("Test Last Name")
                .phoneNumber("02011445697")
                .email("asas@asa.com")
                .address("Street Address")
                .build();

        InstallationAddressDTO installationAddress = InstallationAddressDTO.builder()
                .id(UUID.randomUUID())
                .region("Test Region")
                .streetName("Nin")
                .town("NIni")
                .build();

        OrderItemDTO orderItem = OrderItemDTO.builder()
                .id(UUID.randomUUID())
                .amount(12.13)
                .packageName("Test Package Name")
                .productName("Test Product Name")
                .build();
        return OrderDTO.builder()
                .customer(customer)
                .installationAddress(installationAddress)
                .orderItem(Arrays.asList(orderItem, orderItem))
                .notes("Test Notes")
                .orderNumber("000001")
                .id(UUID.randomUUID())
                .build();
    }

    private UpdateOrderStatusDTO updateRequestData(){
        return UpdateOrderStatusDTO.builder()
                .orderStatus(AppConstants.ORDER_COMPLETED)
                .userName("username_1")
                .comment("The standard chunk of Lorem Ipsum used since the 1500s is reproduced below for those interested. Sections 1.10.32 and 1.10.33 from \"de Finibus Bonorum et Malorum\" by Cicero are also reproduced in their exact original form, accompanied by English versions from the 1914 translation by H. Rackham.")
                .build();
    }

    private UpdateOrderStatusDTO updateResponseData(){
        OrderDTO responseData = orderResponseData();
        return UpdateOrderStatusDTO.builder()
                .orderStatus(AppConstants.ORDER_COMPLETED)
                .userName("username_1")
                .comment("The standard chunk of Lorem Ipsum used since the 1500s is reproduced below for those interested. Sections 1.10.32 and 1.10.33 from \"de Finibus Bonorum et Malorum\" by Cicero are also reproduced in their exact original form, accompanied by English versions from the 1914 translation by H. Rackham.")
                .order(responseData)
                .build();
    }

}

