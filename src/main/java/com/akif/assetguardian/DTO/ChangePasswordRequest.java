package com.akif.assetguardian.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ChangePasswordRequest(
        @NotBlank(message = "Current password is required!")
        String oldPassword,

        @NotBlank(message = "New password cannot be blank!")
        @Size(min = 6, message = "New password must be at least 6 characters long!")
        String newPassword,

        @NotBlank(message = "Password confirmation is required!")
        String newPasswordConfirm
) { }
