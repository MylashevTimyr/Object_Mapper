package org.example.object_mapper.DTO;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class ProductDTO {
    private Long productId;
    private String name;
    private String description;
    private BigDecimal price;
    private int quantityInStock;
}

