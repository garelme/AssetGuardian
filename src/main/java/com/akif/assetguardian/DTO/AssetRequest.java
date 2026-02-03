package com.akif.assetguardian.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record AssetRequest(
        @NotBlank(message = "Name cannot be blank!")
        String name,

        @NotBlank(message = "Serial number cannot be blank!")
        String serialNumber,

        @NotNull(message = "Price is required!")
        @Positive(message = "Price must be a positive value!")
        Integer price,

        @NotNull(message = "Category ID is required!")
        Integer categoryId,

        String description
) { }
