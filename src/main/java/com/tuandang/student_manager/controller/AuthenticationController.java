package com.tuandang.student_manager.controller;

import com.tuandang.student_manager.dto.request.ResetPasswordRequest;
import com.tuandang.student_manager.dto.request.SignInRequest;
import com.tuandang.student_manager.dto.response.ApiResponse;
import com.tuandang.student_manager.dto.response.TokenResponse;
import com.tuandang.student_manager.service.IAuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auths")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class AuthenticationController {
    IAuthenticationService authenticationService;
    // Các api không cần token:
    // Ngoài ra còn forget password, email, gửi email,...
    @PostMapping("/access")
    public ApiResponse<TokenResponse> login(@RequestBody SignInRequest request) {
        return ApiResponse.<TokenResponse>builder()
                .result(authenticationService.authenticate(request))
                .build();
    }

    @PostMapping("/refresh")
    public ApiResponse<TokenResponse> refresh(HttpServletRequest request) {
        return ApiResponse.<TokenResponse>builder()
                .result(authenticationService.refresh(request))
                .build();
    }

    @PostMapping("/logout")
    public ApiResponse<String> logout(HttpServletRequest request) {
        return ApiResponse.<String>builder()
                .result(authenticationService.logout(request))
                .code(HttpStatus.OK.value())
                .message("OK")
                .build();
    }

    @PostMapping("/forgot-password")
    public ApiResponse<String> forgotPassword(@RequestBody String username) {
        return ApiResponse.<String>builder()
                .result(authenticationService.forgotPassword(username))
                .build();
    }

    @PostMapping("/reset-password")
    public ApiResponse<String> resetPassword(@RequestBody String secretKey) {
        return ApiResponse.<String>builder()
                .result(authenticationService.resetPassword(secretKey))
                .build();
    }

    @PostMapping("/change-password")
    public ApiResponse<String> resetPassword(@RequestBody @Valid ResetPasswordRequest request) {
        return ApiResponse.<String>builder()
                .result(authenticationService.changePassword(request))
                .build();
    }


}
