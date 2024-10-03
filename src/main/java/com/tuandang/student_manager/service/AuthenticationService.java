package com.tuandang.student_manager.service;

import com.tuandang.student_manager.dto.request.SignInRequest;
import com.tuandang.student_manager.dto.response.TokenResponse;
import com.tuandang.student_manager.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationService implements IAuthenticationService{
    UserRepository userRepository;
    AuthenticationManager authenticationManager;
    JwtService jwtService;
    @Override
    public TokenResponse authenticate(SignInRequest request) {
        // Do User đã implements UserDetails nên authentication manager hỗ trợ xác thực
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(),
                request.getPassword()));
        var user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("Username or password not found"));

        String accessToken = jwtService.generateToken(user);
        return TokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken("refresh token")
                .build();
    }
}
