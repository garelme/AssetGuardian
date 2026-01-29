package com.akif.assetguardian.DTO;

public record ErrorResponse(
        int status,
        String message,
        long timestamp
) { }
