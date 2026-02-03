package com.akif.assetguardian.DTO;

import com.akif.assetguardian.enums.Urgency;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record AssetDemandRequest(
        @NotNull(message = "Category ID is required!")
        @Positive(message = "Category ID must be a positive value!")
        Integer categoryId,

        @NotNull(message = "Urgency level must be selected!")
        Urgency urgency,

        @Size(max = 500, message = "Notes cannot exceed 500 characters!")
        String notes
) { }
