package com.akif.assetguardian.DTO;

public record ChangePasswordRequest(
        String oldPassword,
        String newPassword,
        String newPasswordConfirm
) { }
