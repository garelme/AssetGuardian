package com.akif.assetguardian.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record AssetRequest(
        @NotBlank(message="İsim boş olamaz!")
        String name,

        @NotBlank(message="Seri numarası boş olamaz!")
        String serialNumber,

        @NotNull(message = "Fiyat boş olamaz!")
        @Positive(message = "Fiyat pozitif bir değer olmalıdır!")
        int price,


        @NotNull(message = "Kategori seçilmelidir!")
        Integer categoryId,

        String description
) { }
