package org.example.object_mapper.mapper;

import org.example.object_mapper.DTO.CustomerDTO;
import org.example.object_mapper.model.Customer;

public class CustomerMapper {
    public static CustomerDTO toDTO(Customer customer) {
        CustomerDTO dto = new CustomerDTO();
        dto.setId(customer.getCustomerId());
        dto.setFirstName(customer.getFirstName());
        dto.setLastName(customer.getLastName());
        dto.setEmail(customer.getEmail());
        dto.setContactNumber(customer.getContactNumber());
        return dto;
    }

    public static Customer toEntity(CustomerDTO dto) {
        Customer customer = new Customer();
        customer.setCustomerId(dto.getId());
        customer.setFirstName(dto.getFirstName());
        customer.setLastName(dto.getLastName());
        customer.setEmail(dto.getEmail());
        customer.setContactNumber(dto.getContactNumber());
        return customer;
    }
}

