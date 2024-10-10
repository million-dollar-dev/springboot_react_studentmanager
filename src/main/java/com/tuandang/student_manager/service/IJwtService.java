package com.tuandang.student_manager.service;

import com.tuandang.student_manager.util.TokenType;
import org.springframework.security.core.userdetails.UserDetails;

public interface IJwtService {
    String generateToken(UserDetails user);
    String generateRefreshToken(UserDetails user);
    String extractUsername(String token, TokenType type);
    boolean isValid(String token, TokenType type, UserDetails userDetails);
}
