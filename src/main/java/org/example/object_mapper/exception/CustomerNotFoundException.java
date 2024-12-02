package org.example.object_mapper.exception;

import org.springframework.http.HttpStatus;

public class CustomerNotFoundException extends AppException {
    public CustomerNotFoundException(Long id) {
        super("Customer not found with id: " + id, HttpStatus.NOT_FOUND);
    }
}
