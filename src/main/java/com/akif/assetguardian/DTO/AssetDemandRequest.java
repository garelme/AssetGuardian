package com.akif.assetguardian.DTO;

import com.akif.assetguardian.enums.Urgency;
import jakarta.validation.constraints.NotNull;

public record AssetDemandRequest(
        @NotNull
        int categoryId,
        Urgency urgency,
        //optional
        String notes
) { }
