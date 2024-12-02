package org.example.object_mapper.controller;

import lombok.RequiredArgsConstructor;
import org.example.object_mapper.DTO.OrderDTO;
import org.example.object_mapper.mapper.Mapper;
import org.example.object_mapper.model.Customer;
import org.example.object_mapper.model.Order;
import org.example.object_mapper.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final Mapper mapper;

    @PostMapping
    public ResponseEntity<OrderDTO> createOrder(@RequestBody OrderDTO orderDTO) {
        Order order = mapper.map(orderDTO, Order.class);

        if (orderDTO.getCustomerId() != null) {
            Customer customer = new Customer();
            customer.setCustomerId(orderDTO.getCustomerId());
            order.setCustomer(customer);
        }

        Order createdOrder = orderService.createOrder(order);
        OrderDTO createdDTO = mapper.mapOrderToOrderDTO(createdOrder);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getOrderById(@PathVariable Long id) {
        Order order = orderService.getOrderById(id);
        OrderDTO orderDTO = mapper.mapOrderToOrderDTO(order);
        return ResponseEntity.ok(mapper.writeAsString(orderDTO));
    }
}


