package com.akif.assetguardian.model;

import com.akif.assetguardian.enums.AssetStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Asset extends BaseEntity{
    String name;

    @Column(unique = true, nullable = false)
    String serialNumber;

    int price;

    @Enumerated(EnumType.STRING)
    AssetStatus status = AssetStatus.IN_STOCK;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "asset")
    private List<Assignment> assignments = new ArrayList<>();
}
