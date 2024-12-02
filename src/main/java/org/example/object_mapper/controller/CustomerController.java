package org.example.object_mapper.controller;

import lombok.RequiredArgsConstructor;
import org.example.object_mapper.DTO.CustomerDTO;
import org.example.object_mapper.mapper.Mapper;
import org.example.object_mapper.model.Customer;
import org.example.object_mapper.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;
    private final Mapper mapper;

    @GetMapping
    public ResponseEntity<String> getAllCustomers() {
        List<Customer> customers = customerService.getAllCustomers();
        List<CustomerDTO> customerDTOs = customers.stream()
                .map(customer -> mapper.map(customer, CustomerDTO.class))
                .toList();
        return ResponseEntity.ok(mapper.writeAsString(customerDTOs));
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getCustomerById(@PathVariable Long id) {
        Customer customer = customerService.getCustomerById(id);
        CustomerDTO customerDTO = mapper.map(customer, CustomerDTO.class);
        return ResponseEntity.ok(mapper.writeAsString(customerDTO));
    }

    @PostMapping
    public ResponseEntity<CustomerDTO> createCustomer(@RequestBody CustomerDTO customerDTO) {
        Customer customer = mapper.map(customerDTO, Customer.class);
        Customer createdCustomer = customerService.createCustomer(customer);
        CustomerDTO createdDTO = mapper.map(createdCustomer, CustomerDTO.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerDTO> updateCustomer(@PathVariable Long id, @RequestBody CustomerDTO customerDTO) {
        Customer customerDetails = mapper.map(customerDTO, Customer.class);
        Customer updatedCustomer = customerService.updateCustomer(id, customerDetails);
        CustomerDTO updatedDTO = mapper.map(updatedCustomer, CustomerDTO.class);
        return ResponseEntity.ok(updatedDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }
}


