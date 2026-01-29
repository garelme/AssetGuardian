package com.akif.assetguardian.DTO;

import com.akif.assetguardian.enums.Role;

public record UserUpdateResponse(
        int userId,
        String name,
        String email,
        String role,
        String department,
        String profileImagePath
) { }
