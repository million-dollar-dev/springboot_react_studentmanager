package com.tuandang.student_manager.service;

import com.tuandang.student_manager.entity.Token;
import com.tuandang.student_manager.exception.AppException;
import com.tuandang.student_manager.exception.ErrorCode;
import com.tuandang.student_manager.repository.TokenRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TokenService {
    TokenRepository tokenRepository;

    public Token getByUsername(String username) {
        return tokenRepository.findByUsername(username).orElseThrow(() -> new AppException(ErrorCode.TOKEN_INVALID));
    }

    public int save(Token token) {
        Optional<Token> optional = tokenRepository.findByUsername(token.getUsername());
        if (optional.isEmpty()) {
            tokenRepository.save(token);
            return token.getId();
        } else {
            Token t = optional.get();
            t.setAccessToken(token.getAccessToken());
            t.setRefreshToken(token.getRefreshToken());
            tokenRepository.save(t);
            return t.getId();
        }
    }

    public void delete(String username) {
        Token token = getByUsername(username);
        tokenRepository.delete(token);
    }
}
