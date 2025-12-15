package com.akif.assetguardian.DTO;

import com.akif.assetguardian.enums.AssetStatus;

public record AssetResponse(
        int assetId,
        String name,
        String serialNumber,
        int price,
        String categoryName,
        AssetStatus status,

        // Assigned to user info in format "id - name" or "Unassigned"
        String assignedToInfo
) { }
