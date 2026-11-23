package com.hcl.hclassessment2.Services;

import com.hcl.hclassessment2.Entity.Customer;

import java.util.List;

public interface CustomerService {
    Customer registerCustomer(Customer customer);
    List<Customer> getAllCustomers();
    Customer getCustomerById(Long id);
    Customer updateCustomer(Long id, Customer customer);
    void deleteCustomer(Long id);
}

