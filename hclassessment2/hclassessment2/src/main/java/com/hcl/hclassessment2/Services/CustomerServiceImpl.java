package com.hcl.hclassessment2.Services;

import com.hcl.hclassessment2.Entity.Customer;
import com.hcl.hclassessment2.Exception.ResourceNotFoundException;
import com.hcl.hclassessment2.Repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Customer registerCustomer(Customer customer) {
        if (customer == null) {
            throw new IllegalArgumentException("Customer details cannot be null");
        }

        // Requirement: Customer name cannot be blank.
        if (customer.getCustomerName() == null || customer.getCustomerName().trim().isEmpty()) {
            throw new IllegalArgumentException("Customer name cannot be blank");
        }

        // Requirement: Email must be unique.
        if (customer.getEmail() == null || customer.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be blank");
        }
        if (customerRepository.existsByEmail(customer.getEmail().trim())) {
            throw new IllegalArgumentException("Email must be unique");
        }

        // Requirement: Mobile number must contain 10 digits.
        if (customer.getMobile() == null || !customer.getMobile().trim().matches("^\\d{10}$")) {
            throw new IllegalArgumentException("Mobile number must contain 10 digits");
        }

        customer.setCustomerId(null);
        customer.setCustomerName(customer.getCustomerName().trim());
        customer.setEmail(customer.getEmail().trim());
        customer.setMobile(customer.getMobile().trim());

        return customerRepository.save(customer);
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + id));
    }

    @Override
    public Customer updateCustomer(Long id, Customer customer) {
        Customer existingCustomer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + id));

        if (customer == null) {
            throw new IllegalArgumentException("Customer details cannot be null");
        }

        // Requirement: Customer name cannot be blank.
        if (customer.getCustomerName() == null || customer.getCustomerName().trim().isEmpty()) {
            throw new IllegalArgumentException("Customer name cannot be blank");
        }

        // Requirement: Email must be unique.
        if (customer.getEmail() == null || customer.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be blank");
        }
        Optional<Customer> customerWithEmail = customerRepository.findByEmail(customer.getEmail().trim());
        if (customerWithEmail.isPresent() && !customerWithEmail.get().getCustomerId().equals(id)) {
            throw new IllegalArgumentException("Email must be unique");
        }

        // Requirement: Mobile number must contain 10 digits.
        if (customer.getMobile() == null || !customer.getMobile().trim().matches("^\\d{10}$")) {
            throw new IllegalArgumentException("Mobile number must contain 10 digits");
        }

        existingCustomer.setCustomerName(customer.getCustomerName().trim());
        existingCustomer.setEmail(customer.getEmail().trim());
        existingCustomer.setMobile(customer.getMobile().trim());
        if (customer.getPassword() != null) {
            existingCustomer.setPassword(customer.getPassword());
        }
        if (customer.getCity() != null) {
            existingCustomer.setCity(customer.getCity());
        }

        return customerRepository.save(existingCustomer);
    }

    @Override
    public void deleteCustomer(Long id) {
        Customer existingCustomer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + id));
        customerRepository.delete(existingCustomer);
    }
}

