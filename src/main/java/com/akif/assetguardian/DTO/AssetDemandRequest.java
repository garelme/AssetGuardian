package com.akif.assetguardian.DTO;

import jakarta.validation.constraints.NotNull;

public record AssetDemandRequest(
        @NotNull
        int categoryId,
        //optional
        String notes
) { }
