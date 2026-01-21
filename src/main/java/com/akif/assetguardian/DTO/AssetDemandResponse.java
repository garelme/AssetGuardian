package com.akif.assetguardian.DTO;

import com.akif.assetguardian.enums.DemandStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record AssetDemandResponse(
    int demandId,
    int userId,
    String name,
    String categoryName,
    String allocatedAssetName,
    DemandStatus status,
    LocalDate requestDate
) { }
