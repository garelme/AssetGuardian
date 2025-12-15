package com.akif.assetguardian.DTO;

import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
        @NotBlank(message = "Kullanici adi boş olamaz!")
        String username,

        @NotBlank(message = "Şifre boş olamaz!")
        String password
) { }
