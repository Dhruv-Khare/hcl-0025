package com.hcl.hclassessment2;

import com.hcl.hclassessment2.Controller.CustomerController;
import com.hcl.hclassessment2.Entity.Customer;
import com.hcl.hclassessment2.Exception.GlobalExceptionHandler;
import com.hcl.hclassessment2.Exception.ResourceNotFoundException;
import com.hcl.hclassessment2.Services.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class CustomerControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CustomerService customerService;

    @InjectMocks
    private CustomerController customerController;

    private Customer sampleCustomer;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(customerController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();

        sampleCustomer = new Customer();
        sampleCustomer.setCustomerId(1L);
        sampleCustomer.setCustomerName("John Doe");
        sampleCustomer.setEmail("john@example.com");
        sampleCustomer.setPassword("password123");
        sampleCustomer.setMobile("9876543210");
        sampleCustomer.setCity("New York");
    }

    @Test
    void testRegisterCustomer_Success() throws Exception {
        when(customerService.registerCustomer(any(Customer.class))).thenReturn(sampleCustomer);

        String json = "{\"customerName\":\"John Doe\",\"email\":\"john@example.com\",\"mobile\":\"9876543210\",\"password\":\"password123\",\"city\":\"New York\"}";

        mockMvc.perform(post("/api/customers/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.customerId").value(1L))
                .andExpect(jsonPath("$.customerName").value("John Doe"))
                .andExpect(jsonPath("$.email").value("john@example.com"));
    }

    @Test
    void testRegisterCustomer_BlankName_BadRequest() throws Exception {
        when(customerService.registerCustomer(any(Customer.class)))
                .thenThrow(new IllegalArgumentException("Customer name cannot be blank"));

        String json = "{\"customerName\":\"\",\"email\":\"john@example.com\",\"mobile\":\"9876543210\"}";

        mockMvc.perform(post("/api/customers/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Customer name cannot be blank"));
    }

    @Test
    void testRegisterCustomer_DuplicateEmail_BadRequest() throws Exception {
        when(customerService.registerCustomer(any(Customer.class)))
                .thenThrow(new IllegalArgumentException("Email must be unique"));

        String json = "{\"customerName\":\"John Doe\",\"email\":\"john@example.com\",\"mobile\":\"9876543210\"}";

        mockMvc.perform(post("/api/customers/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Email must be unique"));
    }

    @Test
    void testGetAllCustomers_Success() throws Exception {
        when(customerService.getAllCustomers()).thenReturn(Arrays.asList(sampleCustomer));

        mockMvc.perform(get("/api/customers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].customerName").value("John Doe"));
    }

    @Test
    void testGetCustomerById_Success() throws Exception {
        when(customerService.getCustomerById(1L)).thenReturn(sampleCustomer);

        mockMvc.perform(get("/api/customers/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerName").value("John Doe"));
    }

    @Test
    void testGetCustomerById_NotFound() throws Exception {
        when(customerService.getCustomerById(99L))
                .thenThrow(new ResourceNotFoundException("Customer not found with id: 99"));

        mockMvc.perform(get("/api/customers/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Customer not found with id: 99"));
    }

    @Test
    void testUpdateCustomer_Success() throws Exception {
        when(customerService.updateCustomer(eq(1L), any(Customer.class))).thenReturn(sampleCustomer);

        String json = "{\"customerName\":\"John Doe\",\"email\":\"john@example.com\",\"mobile\":\"9876543210\"}";

        mockMvc.perform(put("/api/customers/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerName").value("John Doe"));
    }

    @Test
    void testDeleteCustomer_Success() throws Exception {
        doNothing().when(customerService).deleteCustomer(1L);

        mockMvc.perform(delete("/api/customers/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Customer deleted successfully"));
    }
}
