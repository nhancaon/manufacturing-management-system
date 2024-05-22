package com.manufacturing.manufacturingmanagementsystem.repositories.ID;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class OrderProductDetailEntityId implements Serializable {
    private Long productId;
    private Long orderId;
}
