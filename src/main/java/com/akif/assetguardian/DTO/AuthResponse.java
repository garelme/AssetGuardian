package com.akif.assetguardian.DTO;

public record AuthResponse(
        int userId,
        String token,
        String message,
        String username,
        String role
) { }
