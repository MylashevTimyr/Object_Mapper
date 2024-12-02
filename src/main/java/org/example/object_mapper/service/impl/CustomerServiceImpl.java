package org.example.object_mapper.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.object_mapper.exception.CustomerNotFoundException;
import org.example.object_mapper.model.Customer;
import org.example.object_mapper.repository.CustomerRepository;
import org.example.object_mapper.service.CustomerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public Customer getCustomerById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Customer ID must not be null");
        }
        System.out.println("Looking for Customer with ID: " + id);
        return customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(id));
    }

    @Override
    public Customer createCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    @Transactional
    @Override
    public Customer updateCustomer(Long id, Customer customerDetails) {
        Customer customer = getCustomerById(id);
        customer.setFirstName(customerDetails.getFirstName());
        customer.setLastName(customerDetails.getLastName());
        customer.setEmail(customerDetails.getEmail());
        customer.setContactNumber(customerDetails.getContactNumber());
        return customerRepository.save(customer);
    }

    @Transactional
    @Override
    public boolean deleteCustomer(Long id) {
        Customer customer = getCustomerById(id);
        customerRepository.delete(customer);
        return true;
    }
}

