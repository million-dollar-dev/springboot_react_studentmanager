package com.tuandang.student_manager.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface IJwtService {
    String generateToken(UserDetails user);
    String extractUsername(String token);
    boolean isValid(String token, UserDetails userDetails);
}
