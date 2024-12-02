package org.example.object_mapper.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.object_mapper.exception.CustomerNotFoundException;
import org.example.object_mapper.exception.OrderNotFoundException;
import org.example.object_mapper.exception.ProductNotFoundException;
import org.example.object_mapper.model.Customer;
import org.example.object_mapper.model.Order;
import org.example.object_mapper.model.Product;
import org.example.object_mapper.repository.CustomerRepository;
import org.example.object_mapper.repository.OrderRepository;
import org.example.object_mapper.repository.ProductRepository;
import org.example.object_mapper.service.OrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;

    @Transactional
    @Override
    public Order createOrder(Order order) {
        Customer customer = customerRepository.findById(order.getCustomer().getCustomerId())
                .orElseThrow(() -> new CustomerNotFoundException(order.getCustomer().getCustomerId()));

        Map<Product, Integer> validatedProducts = order.getProductsWithQuantities().entrySet().stream()
                .collect(Collectors.toMap(
                        entry -> {
                            Product product = productRepository.findById(entry.getKey())
                                    .orElseThrow(() -> new ProductNotFoundException(entry.getKey()));

                            if (product.getQuantityInStock() < entry.getValue()) {
                                throw new IllegalArgumentException("Insufficient stock for product ID " + product.getProductId());
                            }
                            return product;
                        },
                        Map.Entry::getValue
                ));

        BigDecimal totalPrice = validatedProducts.entrySet().stream()
                .map(entry -> {
                    Product product = entry.getKey();
                    Integer quantity = entry.getValue();

                    product.setQuantityInStock(product.getQuantityInStock() - quantity);
                    productRepository.save(product);

                    return product.getPrice().multiply(BigDecimal.valueOf(quantity));
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        order.setCustomer(customer);
        order.setProductsWithQuantities(validatedProducts.entrySet().stream()
                .collect(Collectors.toMap(entry -> entry.getKey().getProductId(), Map.Entry::getValue)));
        order.setTotalPrice(totalPrice);
        order.setOrderDate(LocalDateTime.now());
        order.setOrderStatus("Pending");

        return orderRepository.save(order);
    }

    @Override
    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));
    }
}

