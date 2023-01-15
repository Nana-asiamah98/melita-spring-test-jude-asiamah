package com.ml.ordermicroservice.restcontrollertest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ml.ordermicroservice.repository.OrderRepository;
import com.ml.ordermicroservice.restcontroller.CustomerRestController;
import com.ml.ordermicroservice.service.CustomerService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@WebMvcTest(controllers = CustomerRestController.class)
@ActiveProfiles("test")
public class CustomerRestControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private CustomerService customerService;

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
    public void givenCustomers_whenGetCustomers_thenStatus200() throws Exception {

    }


}