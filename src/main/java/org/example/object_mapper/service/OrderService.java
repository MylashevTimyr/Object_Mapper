package org.example.object_mapper.service;

import org.example.object_mapper.model.Order;

public interface OrderService {
    Order createOrder(Order order);

    Order getOrderById(Long id);
}

