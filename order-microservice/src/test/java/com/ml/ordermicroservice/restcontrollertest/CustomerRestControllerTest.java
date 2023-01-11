package com.ml.ordermicroservice.restcontrollertest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ml.ordermicroservice.model.Customer;
import com.ml.ordermicroservice.repository.CustomerRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK
)
@TestPropertySource(
        locations = "classpath:application-test.yml"
)
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CustomerRestControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    public void givenCustomers_whenGetCustomers_thenStatus200() throws Exception {
        Customer mockCustomer = createTestCustomer();
        customerRepository.save(mockCustomer);
        mockMvc.perform(get("/api/v1/customer"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    public void addCustomer_withMockMVC_thenVerifyResponse() throws Exception {
        Customer mockCustomer = createTestCustomer();
        String stringCustomer = objectMapper.writeValueAsString(mockCustomer);
        mockMvc.perform(post("/api/v1/customer").contentType(MediaType.APPLICATION_JSON).content(stringCustomer))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
        Assertions.assertFalse(customerRepository.findAll().isEmpty());
    }


    private Customer createTestCustomer() {
        ZoneId defaultZoneId = ZoneId.systemDefault();
        Date date = Date.from(LocalDate.of(2002, 5, 12).atStartOfDay(defaultZoneId).toInstant());
        Customer customer = new Customer();
        customer.setFirstName("Sharon");
        customer.setLastName("Alicia");
        customer.setEmail("some@email.com");
        customer.setAddress("Pawpaw Street");

        customer.setDob(date);
        return customer;
    }

}