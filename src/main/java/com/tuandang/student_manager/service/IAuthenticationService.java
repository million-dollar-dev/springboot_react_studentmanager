package com.tuandang.student_manager.service;

import com.tuandang.student_manager.dto.request.SignInRequest;
import com.tuandang.student_manager.dto.response.TokenResponse;

public interface IAuthenticationService {
    TokenResponse authenticate(SignInRequest request);
}
