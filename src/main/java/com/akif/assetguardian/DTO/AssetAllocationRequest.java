package com.akif.assetguardian.DTO;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record AssetAllocationRequest(
        @NotNull(message = "Eşya seçilmeli!")
        @Positive(message = "Geçersiz Eşya ID!")
        String assetId,

        @NotNull(message = "Personel seçilmeli!")
        @Positive(message = "Geçersiz User ID!")
        int userId,

        // Ek notlar(opsiyonel) belki eksik bir şey vermiş olabilir.
        String notes
) { }
