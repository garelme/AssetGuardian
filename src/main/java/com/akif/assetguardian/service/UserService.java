package com.akif.assetguardian.service;

import com.akif.assetguardian.DTO.ChangePasswordRequest;
import com.akif.assetguardian.DTO.UserUpdateRequest;
import com.akif.assetguardian.DTO.UserUpdateResponse;
import com.akif.assetguardian.enums.Role;
import com.akif.assetguardian.exception.BadRequestException;
import com.akif.assetguardian.exception.ResourceNotFoundException;
import com.akif.assetguardian.model.User;
import com.akif.assetguardian.repository.UserRepo;
import com.akif.assetguardian.utils.SecurityUtils;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.couchbase.CouchbaseProperties;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepo userRepo;
    private final StorageService storageService;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public String saveProfileImage(MultipartFile image) {

        if (image.isEmpty()) {
            throw new BadRequestException("File cannot be empty!");
        }

        String contentType = image.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new BadRequestException("Only image files (JPEG, PNG, etc.) are allowed!");
        }

        if (image.getSize() > 5 * 1024 * 1024) {
            throw new BadRequestException("File size exceeds the limit of 5MB!");
        }

        User user = getCurrentUserOrThrow();
        String fileName = storageService.save(image);
        user.setProfileImagePath(fileName);
        return fileName;
    }

    @Transactional
    public UserUpdateResponse updateUserProfile(UserUpdateRequest userUpdateRequest) {
        User user = getCurrentUserOrThrow();

        if (!user.getEmail().equals(userUpdateRequest.email()) && userRepo.existsByEmail(userUpdateRequest.email())) {
            throw new BadRequestException("This email is already taken!");
        }

        user.setName(userUpdateRequest.name());
        user.setEmail(userUpdateRequest.email());
        user.setDepartment(userUpdateRequest.department());

        return mapToResponse(user);
    }

    private UserUpdateResponse mapToResponse(User user) {
        return new UserUpdateResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole().name(),
                user.getDepartment().name(),
                user.getProfileImagePath()
        );
    }

    @Transactional
    public void changeUserPassword(ChangePasswordRequest request) {
        if (!request.newPassword().equals(request.newPasswordConfirm())) {
            throw new BadRequestException("New passwords do not match!");
        }

        User user = getCurrentUserOrThrow();

        if (!passwordEncoder.matches(request.oldPassword(), user.getPassword())) {
            throw new BadRequestException("Current password is incorrect!");
        }
        user.setPassword(passwordEncoder.encode(request.newPassword()));
    }

    public UserUpdateResponse getCurrentUserProfile() {
        Integer userId = SecurityUtils.getCurrentUserId();
        User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return mapToResponse(user);
    }

    private User getCurrentUserOrThrow() {
        Integer userId = SecurityUtils.getCurrentUserId();
        return userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
    }

    @Transactional
    public void updateUserRole(Integer userId,Role newRole) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        int currentAdminId = SecurityUtils.getCurrentUserId();

        if (user.getId() == currentAdminId && newRole != Role.ADMIN) {
            throw new BadRequestException("You cannot change yourself! Another admin must change your role.");
        }

        if (user.getRole() == Role.ADMIN && newRole != Role.ADMIN) {
            if (userRepo.countByRole(Role.ADMIN) <= 2) {
                throw new BadRequestException("System must have at least two administrator!");
            }
        }

        user.setRole(newRole);
    }
}
