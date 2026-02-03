package com.akif.assetguardian.controller;

import com.akif.assetguardian.DTO.ChangePasswordRequest;
import com.akif.assetguardian.DTO.RoleUpdateRequest;
import com.akif.assetguardian.DTO.UserUpdateRequest;
import com.akif.assetguardian.DTO.UserUpdateResponse;
import com.akif.assetguardian.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/settings")
@RequiredArgsConstructor
@Validated
public class UserController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<UserUpdateResponse> getUserProfile() {
        UserUpdateResponse response = userService.getCurrentUserProfile();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/profile-image")
    public ResponseEntity<String> setProfileImage(@NotNull(message = "Image file is required!") @RequestParam("image") MultipartFile image) {
        String photoUrl = userService.saveProfileImage(image);
        return ResponseEntity.ok("Profile image updated successfully" + photoUrl);
    }

    @PutMapping("/profile")
    public ResponseEntity<UserUpdateResponse> updateProfile(@Valid @RequestBody UserUpdateRequest userUpdateRequest) {
        UserUpdateResponse response = userService.updateUserProfile(userUpdateRequest);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/password")
    public ResponseEntity<String> changePassword(@Valid @RequestBody ChangePasswordRequest changePasswordRequest) {
        userService.changeUserPassword(changePasswordRequest);
        return ResponseEntity.ok("Password changed successfully");
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/users/{userId}/role")
    public ResponseEntity<Void> updateUserRole(@Positive(message = "User ID must be a positive value!") @PathVariable Integer userId, @Valid @RequestBody RoleUpdateRequest request) {
        userService.updateUserRole(userId, request.newRole());
        return ResponseEntity.ok().build();
    }
}
