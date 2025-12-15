package com.akif.assetguardian.DTO;

import jakarta.validation.constraints.NotNull;

public record AssignmentRequest(
        @NotNull
        int assetId,
        @NotNull
        int userId
) { }
