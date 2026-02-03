package com.akif.assetguardian.DTO;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record AssetAllocationRequest(
        @NotNull(message = "Asset ID is required!")
        @Positive(message = "Asset ID must be a positive value!")
        Integer assetId,

        @NotNull(message = "Demand ID is required!")
        @Positive(message = "Demand ID must be a positive value!")
        Integer demandId,

        @FutureOrPresent(message = "Return date must be in the present or future!")
        LocalDate returnDate,

        @Size(max = 500, message = "Notes cannot exceed 500 characters!")
        String notes
) { }
