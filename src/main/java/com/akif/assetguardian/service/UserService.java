package com.akif.assetguardian.service;

import com.akif.assetguardian.DTO.ChangePasswordRequest;
import com.akif.assetguardian.DTO.UserUpdateRequest;
import com.akif.assetguardian.DTO.UserUpdateResponse;
import com.akif.assetguardian.exception.BadRequestException;
import com.akif.assetguardian.exception.ResourceNotFoundException;
import com.akif.assetguardian.model.User;
import com.akif.assetguardian.repository.UserRepo;
import com.akif.assetguardian.utils.SecurityUtils;
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

    public String saveProfileImage(MultipartFile image) {
        Integer userId = SecurityUtils.getCurrentUserId();
        User user = userRepo.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        String fileName = storageService.save(image);

        user.setProfileImagePath(fileName);
        userRepo.save(user);
        return fileName;
    }

    public UserUpdateResponse updateUserProfile(UserUpdateRequest userUpdateRequest) {
        Integer userId = SecurityUtils.getCurrentUserId();
        User user = userRepo.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        user.setName(userUpdateRequest.name());
        user.setEmail(userUpdateRequest.email());
        user.setRole(userUpdateRequest.role());
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

    public void changeUserPassword(ChangePasswordRequest request) {
        if (!request.newPassword().equals(request.newPasswordConfirm())) {
            throw new BadRequestException("Yeni şifreler birbiriyle uyuşmuyor!");
        }
        Integer userId = SecurityUtils.getCurrentUserId();
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Kullanıcı bulunamadı."));

        if (!passwordEncoder.matches(request.oldPassword(), user.getPassword())) {
            throw new BadRequestException("Mevcut şifreniz hatalı!");
        }
        user.setPassword(passwordEncoder.encode(request.newPassword()));
        userRepo.save(user);
    }
}
