package com.hcl.hclassessment2;

import com.hcl.hclassessment2.Entity.Customer;
import com.hcl.hclassessment2.Exception.ResourceNotFoundException;
import com.hcl.hclassessment2.Repository.CustomerRepository;
import com.hcl.hclassessment2.Services.CustomerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerServiceImpl customerService;

    private Customer sampleCustomer;

    @BeforeEach
    void setUp() {
        sampleCustomer = new Customer();
        sampleCustomer.setCustomerId(1L);
        sampleCustomer.setCustomerName("John Doe");
        sampleCustomer.setEmail("john@example.com");
        sampleCustomer.setPassword("password123");
        sampleCustomer.setMobile("9876543210");
        sampleCustomer.setCity("New York");
    }

    @Test
    void testRegisterCustomer_Success() {
        when(customerRepository.existsByEmail("john@example.com")).thenReturn(false);
        when(customerRepository.save(any(Customer.class))).thenReturn(sampleCustomer);

        Customer saved = customerService.registerCustomer(sampleCustomer);

        assertNotNull(saved);
        assertEquals("John Doe", saved.getCustomerName());
        assertEquals("john@example.com", saved.getEmail());
        verify(customerRepository, times(1)).save(any(Customer.class));
    }

    @Test
    void testRegisterCustomer_BlankName_ThrowsException() {
        sampleCustomer.setCustomerName("   ");

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () ->
                customerService.registerCustomer(sampleCustomer));
        assertEquals("Customer name cannot be blank", ex.getMessage());
    }

    @Test
    void testRegisterCustomer_NullName_ThrowsException() {
        sampleCustomer.setCustomerName(null);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () ->
                customerService.registerCustomer(sampleCustomer));
        assertEquals("Customer name cannot be blank", ex.getMessage());
    }

    @Test
    void testRegisterCustomer_BlankEmail_ThrowsException() {
        sampleCustomer.setEmail("");

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () ->
                customerService.registerCustomer(sampleCustomer));
        assertEquals("Email cannot be blank", ex.getMessage());
    }

    @Test
    void testRegisterCustomer_DuplicateEmail_ThrowsException() {
        when(customerRepository.existsByEmail("john@example.com")).thenReturn(true);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () ->
                customerService.registerCustomer(sampleCustomer));
        assertEquals("Email must be unique", ex.getMessage());
    }

    @Test
    void testRegisterCustomer_InvalidMobileLength_ThrowsException() {
        sampleCustomer.setMobile("12345"); // Not 10 digits

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () ->
                customerService.registerCustomer(sampleCustomer));
        assertEquals("Mobile number must contain 10 digits", ex.getMessage());
    }

    @Test
    void testRegisterCustomer_NonNumericMobile_ThrowsException() {
        sampleCustomer.setMobile("12345abcde");

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () ->
                customerService.registerCustomer(sampleCustomer));
        assertEquals("Mobile number must contain 10 digits", ex.getMessage());
    }

    @Test
    void testGetAllCustomers() {
        when(customerRepository.findAll()).thenReturn(Arrays.asList(sampleCustomer));

        List<Customer> list = customerService.getAllCustomers();
        assertEquals(1, list.size());
        assertEquals("John Doe", list.get(0).getCustomerName());
    }

    @Test
    void testGetCustomerById_Found() {
        when(customerRepository.findById(1L)).thenReturn(Optional.of(sampleCustomer));

        Customer found = customerService.getCustomerById(1L);
        assertNotNull(found);
        assertEquals(1L, found.getCustomerId());
    }

    @Test
    void testGetCustomerById_NotFound_ThrowsException() {
        when(customerRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> customerService.getCustomerById(99L));
    }

    @Test
    void testUpdateCustomer_Success() {
        Customer updateInfo = new Customer();
        updateInfo.setCustomerName("John Updated");
        updateInfo.setEmail("john.updated@example.com");
        updateInfo.setMobile("9998887776");
        updateInfo.setCity("Chicago");

        when(customerRepository.findById(1L)).thenReturn(Optional.of(sampleCustomer));
        when(customerRepository.findByEmail("john.updated@example.com")).thenReturn(Optional.empty());
        when(customerRepository.save(any(Customer.class))).thenAnswer(i -> i.getArgument(0));

        Customer updated = customerService.updateCustomer(1L, updateInfo);
        assertEquals("John Updated", updated.getCustomerName());
        assertEquals("john.updated@example.com", updated.getEmail());
        assertEquals("9998887776", updated.getMobile());
        assertEquals("Chicago", updated.getCity());
    }

    @Test
    void testDeleteCustomer_Success() {
        when(customerRepository.findById(1L)).thenReturn(Optional.of(sampleCustomer));
        doNothing().when(customerRepository).delete(sampleCustomer);

        assertDoesNotThrow(() -> customerService.deleteCustomer(1L));
        verify(customerRepository, times(1)).delete(sampleCustomer);
    }
}

