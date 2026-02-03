package com.akif.assetguardian.service;

import com.akif.assetguardian.DTO.AuthResponse;
import com.akif.assetguardian.DTO.LoginRequest;
import com.akif.assetguardian.DTO.RegisterRequest;
import com.akif.assetguardian.enums.Role;
import com.akif.assetguardian.exception.AccessDeniedException;
import com.akif.assetguardian.exception.BadRequestException;
import com.akif.assetguardian.exception.ResourceNotFoundException;
import com.akif.assetguardian.model.MyUserDetails;
import com.akif.assetguardian.model.User;
import com.akif.assetguardian.repository.UserRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepo userRepo;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public AuthResponse register(RegisterRequest request) {

        if (userRepo.existsByUsername(request.username())) {
            throw new BadRequestException("Bu kullanıcı adı zaten alınmış!");
        }

        User user = new User();
        user.setName(request.name());
        user.setUsername(request.username());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setEmail(request.email());
        user.setRole(Role.USER);

        user.setDepartment(request.department());

        userRepo.save(user);
        return mapToAuthResponse(user);
    }

    public AuthResponse login(LoginRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.username(), request.password())
            );
        }catch (org.springframework.security.authentication.BadCredentialsException e) {
            throw new AccessDeniedException("Invalid username or password");
        }catch (Exception e) {
            throw new BadRequestException("Authentication failed: " + e.getMessage());
        }

        User user = userRepo.findByUsername(request.username());

        if (user == null) {
            throw new ResourceNotFoundException("User not found with username: " + request.username());
        }
        return mapToAuthResponse(user);
    }

    private AuthResponse mapToAuthResponse(User user) {
        String token = jwtService.generateToken(new MyUserDetails(user));

        return new AuthResponse(
                user.getId(),
                token,
                "Success",
                user.getUsername(),
                user.getRole().name()
        );
    }
}
