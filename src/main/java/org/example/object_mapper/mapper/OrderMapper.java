package org.example.object_mapper.mapper;

import org.example.object_mapper.DTO.OrderDTO;
import org.example.object_mapper.model.Customer;
import org.example.object_mapper.model.Order;

public class OrderMapper {

    public static Order toEntity(OrderDTO dto, Customer customer) {
        Order order = new Order();
        order.setCustomer(customer);
        order.setProductsWithQuantities(dto.getProductsWithQuantities());
        order.setShippingAddress(dto.getShippingAddress());
        order.setOrderDate(dto.getOrderDate());
        order.setOrderStatus(dto.getOrderStatus());
        order.setTotalPrice(dto.getTotalPrice());
        return order;
    }

    public static OrderDTO toDTO(Order order) {
        OrderDTO dto = new OrderDTO();
        dto.setOrderId(order.getOrderId());
        dto.setCustomerId(order.getCustomer().getCustomerId());
        dto.setProductsWithQuantities(order.getProductsWithQuantities());
        dto.setShippingAddress(order.getShippingAddress());
        dto.setOrderDate(order.getOrderDate());
        dto.setOrderStatus(order.getOrderStatus());
        dto.setTotalPrice(order.getTotalPrice());
        return dto;
    }
}
