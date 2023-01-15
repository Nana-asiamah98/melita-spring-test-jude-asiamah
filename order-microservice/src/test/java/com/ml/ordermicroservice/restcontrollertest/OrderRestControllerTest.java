package com.ml.ordermicroservice.restcontrollertest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ml.ordermicroservice.dto.*;
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
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = OrderRestController.class)
@AutoConfigureMockMvc(addFilters = false)
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
    @DisplayName("Should SEARCH An Order With An Order Number AND Return OK  When Making A GET Request To Endpoint - /api/v1/order/search-order?orderNumber=")
    public void searchAnOrder_andReturnOK() throws Exception {

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
    @DisplayName("Should ACCEPT Order AND Return OK  when making a POST Request To Endpoint -  /api/v1/order/accept-order")
    public void acceptOrder_andReturnOK() throws Exception {
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
    @DisplayName("Should FAIL Order AND Return BAD REQUEST when making a POST Request To Endpoint -  /api/v1/order/accept-order")
    public void fAILOrder_andReturnBadRequest() throws Exception {
        OrderDTO mockRequestDTO = orderRequestWrongData();
        OrderDTO mockResponseDTO = orderResponseData();

        Mockito.when(orderService.acceptOrder(mockRequestDTO)).thenReturn(mockResponseDTO);
        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/v1/order/accept-order")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(mockRequestDTO))
        )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andDo(print())
                .andExpect(jsonPath("$.dataList[0].message").value("Customer email is not valid"))
                .andExpect(jsonPath("$.dataList[0].field").value("customer.email"));
    }

    @Test
    @DisplayName("Should UPDATE The Status Of An Order AND Return OK When  Using A Request Body And An orderNumber Request Parameter A PUT Method To The Endpoint - /api/v1/order/update-order?orderNumber=")
    public void updateAnOrder_andReturnOK() throws Exception{

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

    @Test
    @DisplayName("Should FAIL to UPDATE The Status Of An Order AND Return BAD REQUEST Using A Request Body And An orderNumber Request Parameter A PUT Method To The Endpoint - /api/v1/order/update-order?orderNumber=")
    public void failToUpdateAnOrder_andReturnBadRequest() throws Exception{

        UpdateOrderStatusDTO mockRequestData = updateRequestWrongData();
        UpdateOrderStatusDTO mockResponseData = updateResponseData();
        String orderNumber = "000001";
        MultiValueMap<String, String> paramsMap = new LinkedMultiValueMap<>();
        paramsMap.add("orderNumber", orderNumber);

        Mockito.when(orderService.updateOrderStatus(orderNumber,mockRequestData)).thenReturn(mockResponseData);

        mockMvc.perform(put("/api/v1/order/update-order-status")
                .params(paramsMap)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(mockRequestData)))
                .andExpect(status().isBadRequest())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.dataList[0].message").value("Username cannot be empty"))
                .andExpect(jsonPath("$.dataList[0].field").value("userName"));    }

    @Test
    @DisplayName("Should FETCH Paginated Order Data Using Request Parameters(size,page,sortDirection,sortBy) AND Return OK  Using A GET Method To The Endpoint - /api/v1/order")
    public void fetchPaginatedOrders_andReturnOK() throws Exception{
        PaginatedOrdersResponse mockResponseData = paginatedOrdersResponse();
        int page = 0;
        int size = 10;
        String sortBy = "id";
        String sortDir = "ASC";
        MultiValueMap<String, String> paramsMap = new LinkedMultiValueMap<>();
        paramsMap.add("page", "0");
        paramsMap.add("size", "10");
        paramsMap.add("sortBy", "id");
        paramsMap.add("sortDir", "ASC");

        Mockito.when(orderService.fetchPaginatedOrders(page,size,sortBy,sortDir)).thenReturn(mockResponseData);

        mockMvc.perform(get("/api/v1/order").params(paramsMap))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data.content[0].orderItem.size()").value(mockResponseData.getContent().get(0).getOrderItem().size()));

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
    private OrderDTO orderRequestWrongData() {

        CustomerDTO customer = CustomerDTO
                .builder()
                .firstName("")
                .lastName("Test Last Name")
                .phoneNumber("02011445697")
                .email("asas@")
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
    private UpdateOrderStatusDTO updateRequestWrongData(){
        return UpdateOrderStatusDTO.builder()
                .orderStatus(AppConstants.ORDER_COMPLETED)
                .userName("")
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

    private PaginatedOrdersResponse paginatedOrdersResponse(){
        OrderDTO orderDTO = orderResponseData();
        List<OrderDTO> orderDTOList = Arrays.asList(orderDTO,orderDTO);
        return PaginatedOrdersResponse
                .builder()
                .content(orderDTOList)
                .pageNo(0)
                .pageSize(10)
                .totalElements(3)
                .totalPages(1)
                .last(true).build();

    }

}

