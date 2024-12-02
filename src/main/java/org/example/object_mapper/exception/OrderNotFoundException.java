package org.example.object_mapper.exception;

import org.springframework.http.HttpStatus;

public class OrderNotFoundException extends AppException {
    public OrderNotFoundException(Long id) {
        super("Order not found with id: " + id, HttpStatus.NOT_FOUND);
    }
}
