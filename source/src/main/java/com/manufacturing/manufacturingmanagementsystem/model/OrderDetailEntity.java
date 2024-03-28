package com.manufacturing.manufacturingmanagementsystem.model;

import com.manufacturing.manufacturingmanagementsystem.model.audit.Auditable;
import com.manufacturing.manufacturingmanagementsystem.repository.ID.InventoriesDetailEntityId;
import com.manufacturing.manufacturingmanagementsystem.repository.ID.OrderDetailEntityId;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import jakarta.persistence.*;

@Entity
@Table(name = TablePrefix.PREFIX_TABLE + "order_detail")
@EntityListeners(AuditingEntityListener.class)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrderDetailEntity extends Auditable<String> {

    @EmbeddedId
    private OrderDetailEntityId id;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @MapsId("orderId")
    private OrderEntity orderId;

    @OneToOne(cascade = CascadeType.PERSIST)
    @MapsId("productId")
    private ProductsEntity productId;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "total_price")
    private Float totalPrice;
}
