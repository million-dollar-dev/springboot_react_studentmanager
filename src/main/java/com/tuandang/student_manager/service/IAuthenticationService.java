package com.tuandang.student_manager.service;

import com.tuandang.student_manager.dto.request.ResetPasswordRequest;
import com.tuandang.student_manager.dto.request.SignInRequest;
import com.tuandang.student_manager.dto.response.TokenResponse;
import jakarta.servlet.http.HttpServletRequest;

public interface IAuthenticationService {
    TokenResponse authenticate(SignInRequest request);
    TokenResponse refresh(HttpServletRequest request);
    String logout(HttpServletRequest request);

    String forgotPassword(String username);

    String resetPassword(String secretKey);

    String changePassword(ResetPasswordRequest request);
}
