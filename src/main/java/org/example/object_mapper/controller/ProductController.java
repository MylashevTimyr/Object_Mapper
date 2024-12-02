package org.example.object_mapper.controller;

import lombok.RequiredArgsConstructor;
import org.example.object_mapper.DTO.ProductDTO;
import org.example.object_mapper.mapper.Mapper;
import org.example.object_mapper.model.Product;
import org.example.object_mapper.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final Mapper mapper;

    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        List<ProductDTO> productDTOs = products.stream()
                .map(product -> mapper.map(product, ProductDTO.class))
                .toList();
        return ResponseEntity.ok(productDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getProductById(@PathVariable Long id) {
        Product product = productService.getProductById(id);
        ProductDTO productDTO = mapper.map(product, ProductDTO.class);
        return ResponseEntity.ok(mapper.writeAsString(productDTO));
    }

    @PostMapping
    public ResponseEntity<ProductDTO> createProduct(@RequestBody ProductDTO productDTO) {
        Product product = mapper.map(productDTO, Product.class);
        Product createdProduct = productService.createProduct(product);
        ProductDTO createdDTO = mapper.map(createdProduct, ProductDTO.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long id, @RequestBody ProductDTO productDTO) {
        Product productDetails = mapper.map(productDTO, Product.class);
        Product updatedProduct = productService.updateProduct(id, productDetails);
        ProductDTO updatedDTO = mapper.map(updatedProduct, ProductDTO.class);
        return ResponseEntity.ok(updatedDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}

