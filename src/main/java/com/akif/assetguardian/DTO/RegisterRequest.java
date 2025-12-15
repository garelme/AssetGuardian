package com.akif.assetguardian.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


public record RegisterRequest(
        @NotBlank(message = "İsim boş olamaz")
        String name,

        @NotBlank(message = "Kullanıcı adı boş olamaz")
        String username,

        @NotBlank
        @Size(min = 6, message = "Şifre en az 6 karakter olmalı")
        String password,

        @Email(message = "Geçerli bir e-posta giriniz")
        String email,

        @NotBlank(message = "Rol seçilmelidir")
        String department
) { }
