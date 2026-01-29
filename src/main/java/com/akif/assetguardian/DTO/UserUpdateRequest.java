package com.akif.assetguardian.DTO;

import com.akif.assetguardian.enums.Department;
import com.akif.assetguardian.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserUpdateRequest(
        @NotBlank(message = "İsim boş olamaz!")
        String name,

        @Email(message = "Geçerli bir e-posta giriniz!")
        @NotBlank(message = "E-posta boş olamaz!")
        String email,

        @NotNull(message = "Departman seçilmelidir!")
        Department department,

        @NotNull(message = "Rol seçilmelidir!")
        Role role
) { }
