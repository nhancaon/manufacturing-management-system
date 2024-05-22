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
public class BOMDetailEntityId implements Serializable {
    private Long materialId;
    private Long BOMId;
}
