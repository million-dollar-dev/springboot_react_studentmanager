package com.tuandang.student_manager.service;

import com.tuandang.student_manager.dto.request.SignInRequest;
import com.tuandang.student_manager.dto.response.TokenResponse;
import com.tuandang.student_manager.entity.Token;
import com.tuandang.student_manager.entity.User;
import com.tuandang.student_manager.exception.AppException;
import com.tuandang.student_manager.exception.ErrorCode;
import com.tuandang.student_manager.repository.UserRepository;
import com.tuandang.student_manager.util.TokenType;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class AuthenticationService implements IAuthenticationService{
    UserRepository userRepository;
    AuthenticationManager authenticationManager;
    IJwtService jwtService;
    UserService userService;
    TokenService tokenService;
    @Override
    public TokenResponse authenticate(SignInRequest request) {
        var user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("Username or password not found"));
        List<String> roles = userService.findAllRolesByUserId(user.getUsername());
        List<SimpleGrantedAuthority> authorities = roles.stream().map(SimpleGrantedAuthority::new).toList();
        // Do User đã implements UserDetails nên authentication manager hỗ trợ xác thực
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(),
                request.getPassword(),
                authorities));

        String accessToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        // save token to db
        tokenService.save(Token.builder()
                .username(user.getUsername())
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build());

        return TokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    public TokenResponse refresh(HttpServletRequest request) {
        // validate
        String refreshToken = request.getHeader("x-token");
        if (StringUtils.isBlank(refreshToken)) {
            throw new AppException(ErrorCode.TOKEN_NOT_BLANK);
        }

        // extract user from token
        final String username = jwtService.extractUsername(refreshToken, TokenType.REFRESH_TOKEN);
        // extract it into db
        var user = userService.getByUsername(username);
        if (jwtService.isValid(refreshToken, TokenType.REFRESH_TOKEN, user)) {
            throw new AppException(ErrorCode.TOKEN_INVALID);
        }
        // create a new access token
        String accessToken = jwtService.generateToken(user);
        // save token to db
        tokenService.save(Token.builder()
                .username(user.getUsername())
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build());
        return TokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    public String logout(HttpServletRequest request) {
        log.info("---------- logout ----------");

        final String token = request.getHeader(HttpHeaders.REFERER);
        if (StringUtils.isBlank(token)) {
            throw new AppException(ErrorCode.TOKEN_NOT_BLANK);
        }

        final String userName = jwtService.extractUsername(token, TokenType.ACCESS_TOKEN);
        tokenService.delete(userName);

        return "Deleted!";
    }
}
