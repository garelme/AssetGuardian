package com.akif.assetguardian.controller;

import com.akif.assetguardian.DTO.ChangePasswordRequest;
import com.akif.assetguardian.DTO.UserUpdateRequest;
import com.akif.assetguardian.DTO.UserUpdateResponse;
import com.akif.assetguardian.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/settings")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<UserUpdateResponse> getUserProfile() {
        UserUpdateResponse response = userService.getCurrentUserProfile();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/profile-image")
    public ResponseEntity<String> setProfileImage(@RequestParam("image") MultipartFile image) {
        String photoUrl = userService.saveProfileImage(image);
        return ResponseEntity.ok("Profile image updated successfully" + photoUrl);
    }

    @PutMapping("/profile")
    public ResponseEntity<UserUpdateResponse> updateProfile(@RequestBody UserUpdateRequest userUpdateRequest) {
        UserUpdateResponse response = userService.updateUserProfile(userUpdateRequest);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/password")
    public ResponseEntity<String> changePassword(@RequestBody ChangePasswordRequest changePasswordRequest) {
        userService.changeUserPassword(changePasswordRequest);
        return ResponseEntity.ok("Password changed successfully");
    }
}
