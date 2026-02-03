package com.akif.assetguardian.DTO;

import com.akif.assetguardian.enums.Role;
import jakarta.validation.constraints.NotNull;

public record RoleUpdateRequest(
        @NotNull(message = "New role field cannot be null!")
        Role newRole
) { }
