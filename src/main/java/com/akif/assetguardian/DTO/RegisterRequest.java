package com.akif.assetguardian.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


public record RegisterRequest(
        @NotBlank(message = "Name cannot be blank!")
        String name,

        @NotBlank(message = "Username cannot be blank!")
        String username,

        @NotBlank(message = "Password cannot be blank!")
        @Size(min = 6, message = "Password must be at least 6 characters long!")
        String password,

        @Email(message = "Please enter a valid email address!")
        @NotBlank(message = "Email cannot be blank!")
        String email,

        @NotBlank(message = "Department is required!")
        String department
) { }
