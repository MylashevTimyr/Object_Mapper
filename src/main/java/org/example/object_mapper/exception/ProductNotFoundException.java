package org.example.object_mapper.exception;

import org.springframework.http.HttpStatus;

public class ProductNotFoundException extends AppException {
    public ProductNotFoundException(Long id) {
        super("Product not found with id: " + id, HttpStatus.NOT_FOUND);
    }
}
