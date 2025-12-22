package com.akif.assetguardian.DTO;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;

public record AssetAllocationRequest(
        @NotNull(message = "Eşya seçilmeli!")
        @Positive(message = "Geçersiz Eşya ID!")
        int assetId,

        @NotNull
        @Positive(message = "Geçersiz Eşya ID!")
        int demandId,

        LocalDate returnDate,

        // Ek notlar(opsiyonel) belki eksik bir şey vermiş olabilir.
        String notes
) { }
