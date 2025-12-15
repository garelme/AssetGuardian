package com.akif.assetguardian.DTO;

import com.akif.assetguardian.enums.AssetStatus;
import com.akif.assetguardian.enums.DemandStatus;

import java.time.LocalDateTime;

public record AssetAllocationResponse(
        int assignmentId,

        int assetId,
        String assetName,
        String serialNumber,

        int userId,
        String userName,

        AssetStatus assetStatus,
        DemandStatus newStatus,

        int assignedByAdminId,
        String assignedByAdminName,

        LocalDateTime assignedAt
) { }
