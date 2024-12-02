package org.example.object_mapper.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.example.object_mapper.DTO.OrderDTO;
import org.example.object_mapper.model.Order;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Mapper {

    private final ObjectMapper objectMapper;

    public <T> T map(Object source, Class<T> targetType) {
        return objectMapper.convertValue(source, targetType);
    }

    public String writeAsString(Object source) {
        try {
            return objectMapper.writeValueAsString(source);
        } catch (Exception e) {
            throw new RuntimeException("Failed to write object as JSON string", e);
        }
    }

    public OrderDTO mapOrderToOrderDTO(Order order) {
        OrderDTO orderDTO = objectMapper.convertValue(order, OrderDTO.class);
        if (order.getCustomer() != null) {
            orderDTO.setCustomerId(order.getCustomer().getCustomerId());
        }
        return orderDTO;
    }
}

