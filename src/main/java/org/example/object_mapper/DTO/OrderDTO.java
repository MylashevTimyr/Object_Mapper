package org.example.object_mapper.DTO;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

@Data
public class OrderDTO {
    private Long orderId;
    private Long customerId;
    private Map<Long, Integer> productsWithQuantities;
    private LocalDateTime orderDate;
    private String shippingAddress;
    private BigDecimal totalPrice;
    private String orderStatus;
}
