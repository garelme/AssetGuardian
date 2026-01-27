package com.akif.assetguardian.DTO;

public record CategoryResponse(
        int categoryId,
        String categoryName,
        String description
) { }