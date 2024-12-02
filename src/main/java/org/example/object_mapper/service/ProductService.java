package org.example.object_mapper.service;

import org.example.object_mapper.model.Product;

import java.util.List;

public interface ProductService {
    List<Product> getAllProducts();

    Product getProductById(Long id);

    Product createProduct(Product product);

    Product updateProduct(Long id, Product productDetails);

    boolean deleteProduct(Long id);
}

