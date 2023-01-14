package com.ml.ordermicroservice.restcontrollertest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ml.ordermicroservice.dto.CustomerDTO;
import com.ml.ordermicroservice.dto.InstallationAddressDTO;
import com.ml.ordermicroservice.dto.OrderDTO;
import com.ml.ordermicroservice.dto.OrderItemDTO;
import com.ml.ordermicroservice.model.Packages;
import com.ml.ordermicroservice.model.Product;
import com.ml.ordermicroservice.restcontroller.OrderRestController;
import com.ml.ordermicroservice.service.OrderService;
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
import org.springframework.web.context.WebApplicationContext;

import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = OrderRestController.class)
@ActiveProfiles("test")
public class OrderRestControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private OrderService orderService;


    @Autowired
    private MockMvc mockMvc;


    @Autowired
    private ObjectMapper objectMapper;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }


    @Test
    @DisplayName("Should List All Order When Making A GET Request To Endpoint - /api/v1/order/search")
    public void shouldListAllOrders() throws Exception {

        OrderDTO expectedOrder = orderDTOSampleData();

        Mockito.when(orderService.acceptOrder(expectedOrder)).thenReturn(expectedOrder);


        mockMvc.perform(get("/api/v1/order/search-order")
                .queryParam("orderNumber", "000001")
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        ;
//                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
//                .andExpect(jsonPath("$.size()", Matchers.is(2)));
    }

    @Test
    @DisplayName("Should SAVE Order when making a POST Request To Endpoint -  /api/v1/order/accept-order")
    public void shouldAcceptOrder() throws Exception {
        OrderDTO mockOrderDTO = orderDTOSampleData();

        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/v1/order/accept-order")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(mockOrderDTO))
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(print());
    }


    private OrderDTO orderDTOSampleData() {

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

    private Product sampleProductData() {
        Product product = new Product();
        List<Integer> samplePackageSize = Arrays.asList(1, 2, 3);
        List<Packages> productPackages = samplePackageSize.stream().map(w -> {
            Packages __package = new Packages();
            __package.setProductId(null);
            __package.setPackageName("Package " + w.toString());
            __package.setRate(100.21);
            __package.setCreatedAt(ZonedDateTime.now());
            __package.setUpdatedAt(ZonedDateTime.now());
            return __package;
        }).collect(Collectors.toList());
        product.setProductName("Product 1");
        product.setId(null);
        product.setPackages(productPackages);
        product.setCreatedAt(ZonedDateTime.now());
        product.setUpdatedAt(ZonedDateTime.now());
        return product;
    }
}

