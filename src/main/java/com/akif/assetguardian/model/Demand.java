package com.akif.assetguardian.model;

import com.akif.assetguardian.enums.DemandStatus;
import com.akif.assetguardian.enums.Urgency;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Demand extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Column(length = 500)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DemandStatus status = DemandStatus.PENDING;

    @OneToOne
    @JoinColumn(name = "assigned_asset_id")
    private Asset assignedAsset;

    @Enumerated(EnumType.STRING)
    private Urgency urgency;

}
