package org.example.object_mapper.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.object_mapper.DTO.OrderDTO;
import org.example.object_mapper.mapper.OrderMapper;
import org.example.object_mapper.model.Customer;
import org.example.object_mapper.model.Order;
import org.example.object_mapper.service.CustomerService;
import org.example.object_mapper.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final CustomerService customerService;

    @PostMapping
    public ResponseEntity<OrderDTO> createOrder(@Valid @RequestBody OrderDTO orderDTO) {
        Customer customer = customerService.getCustomerById(orderDTO.getCustomerId());
        Order order = OrderMapper.toEntity(orderDTO, customer);
        Order createdOrder = orderService.createOrder(order);
        return ResponseEntity.status(HttpStatus.CREATED).body(OrderMapper.toDTO(createdOrder));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable Long id) {
        Order order = orderService.getOrderById(id);
        return ResponseEntity.ok(OrderMapper.toDTO(order));
    }
}
