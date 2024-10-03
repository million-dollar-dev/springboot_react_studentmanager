package com.tuandang.student_manager.controller;

import com.tuandang.student_manager.dto.request.SignInRequest;
import com.tuandang.student_manager.dto.response.ApiResponse;
import com.tuandang.student_manager.dto.response.TokenResponse;
import com.tuandang.student_manager.service.IAuthenticationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
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
    public String refresh() {
        return "refresh successfully";
    }

    @PostMapping("/logout")
    public String logout() {
        return "logout successfully";
    }

}
