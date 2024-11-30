package org.example.object_mapper.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    @ManyToOne
    @NotNull(message = "Customer is required")
    private Customer customer;

    @ElementCollection
    @CollectionTable(name = "order_products", joinColumns = @JoinColumn(name = "order_id"))
    @Column(name = "quantity")
    private Map<Long, Integer> productsWithQuantities;

    private LocalDateTime orderDate;

    @NotBlank(message = "Shipping address is required")
    private String shippingAddress;

    private BigDecimal totalPrice;

    private String orderStatus;
}
