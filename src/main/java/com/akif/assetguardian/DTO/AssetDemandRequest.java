package com.akif.assetguardian.DTO;

import com.akif.assetguardian.enums.Urgency;
import jakarta.validation.constraints.NotNull;

public record AssetDemandRequest(
        @NotNull
        Integer categoryId,

        @NotNull
        Urgency urgency,

        String notes
) { }
