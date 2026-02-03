package com.akif.assetguardian.DTO;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record UserUpdateResponse(
        @Positive(message = "User ID must be a positive value!")
        int userId,

        @NotBlank(message = "Name cannot be blank!")
        @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters!")
        String name,

        @NotBlank(message = "Email is required!")
        @Email(message = "Invalid email format!")
        String email,

        @NotBlank(message = "Role is required!")
        String role,

        @NotBlank(message = "Department is required!")
        String department,

        String profileImagePath
) { }
