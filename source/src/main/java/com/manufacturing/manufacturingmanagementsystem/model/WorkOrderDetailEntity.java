package com.manufacturing.manufacturingmanagementsystem.model;
import com.manufacturing.manufacturingmanagementsystem.model.audit.Auditable;
import com.manufacturing.manufacturingmanagementsystem.repository.ID.InventoriesDetailEntityId;
import com.manufacturing.manufacturingmanagementsystem.repository.ID.WorkOrderDetailEntityId;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = TablePrefix.PREFIX_TABLE + "work_order_detail")
@EntityListeners(AuditingEntityListener.class)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class WorkOrderDetailEntity extends Auditable<String> {

    @EmbeddedId
    private WorkOrderDetailEntityId id;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @MapsId("workOrderId")
    private WorkOrderEntity workOrderId;

    @OneToOne(cascade = CascadeType.PERSIST)
    @MapsId("masterProductionScheduleId")
    private MasterProductionScheduleEntity masterProductionSchedule;

    @Column(name = "note")
    private String note;

    @Column(name = "finished_product")
    private Integer finishedProduct;

    @Column(name = "defective_product")
    private Integer defectiveProduct;

    @Column(name = "finished_product_price")
    private Double finishedProductPrice;

    @Column(name = "defective_product_price")
    private Double defectiveProductPrice;
}
