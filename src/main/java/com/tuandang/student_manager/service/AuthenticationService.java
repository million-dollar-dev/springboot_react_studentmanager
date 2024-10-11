package com.tuandang.student_manager.service;

import com.tuandang.student_manager.dto.request.ResetPasswordRequest;
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
import org.springframework.security.crypto.password.PasswordEncoder;
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
    PasswordEncoder passwordEncoder;
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

    @Override
    public String forgotPassword(String username) {
        log.info("---------- forgotPassword ----------");

        // check email exists or not
        User user = userService.getByUsername(username);

        // generate reset token
        String resetToken = jwtService.generateResetToken(user);

        // save to db
        tokenService.save(
                Token.builder()
                .username(user.getUsername())
                .resetToken(resetToken)
                .build());

        // send email to user
        String confirmLink = String.format("curl --location 'http://localhost:80/auth/reset-password' \\\n" +
                "--header 'accept: */*' \\\n" +
                "--header 'Content-Type: application/json' \\\n" +
                "--data '%s'", resetToken);
        log.info("--> confirmLink: {}", confirmLink);

        return resetToken;
    }

    @Override
    public String resetPassword(String secretKey) {
        log.info("---------- resetPassword ----------");

        // validate token
        var user = validateToken(secretKey);

        // check token by username
        tokenService.getByUsername(user.getUsername());

        return "Reset";
    }

    @Override
    public String changePassword(ResetPasswordRequest request) {
        log.info("---------- changePassword ----------");

        if (!request.getPassword().equals(request.getConfirmPassword())) {
            throw new AppException(ErrorCode.PASSWORD_WRONG);
        }

        // get user by reset token
        var user = validateToken(request.getSecretKey());

        // update password
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        userService.saveUser(user);

        return "Changed";
    }

    private User validateToken(String token) {
        // validate token
        var userName = jwtService.extractUsername(token, TokenType.RESET_TOKEN);

        // validate user is active or not
        var user = userService.getByUsername(userName);
        if (!user.isEnabled()) {
            throw new AppException(ErrorCode.USER_NOT_ACTIVE);
        }

        return user;
    }
}
