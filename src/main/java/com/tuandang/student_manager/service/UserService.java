package com.tuandang.student_manager.service;

import com.tuandang.student_manager.dto.request.UserRequest;
import com.tuandang.student_manager.dto.request.UserUpdateRequest;
import com.tuandang.student_manager.dto.response.UserResponse;
import com.tuandang.student_manager.entity.User;
import com.tuandang.student_manager.exception.AppException;
import com.tuandang.student_manager.exception.ErrorCode;
import com.tuandang.student_manager.mapper.UserMapper;
import com.tuandang.student_manager.repository.UserRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService implements IUserService {
    UserRepository userRepository;
    UserMapper userMapper;

    @Override
    public UserDetailsService userDetailsService() {
        return username -> userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Override
    public User create(UserRequest request) {
        return userMapper.toUser(request);
    }

    @Override
    public List<UserResponse> getUsers() {
        List<UserResponse> list = new ArrayList<>();
        userRepository.findAll().forEach(user -> list.add(userMapper.toUserResponse(user)));
        return list;
    }

    @Override
    public UserResponse updatePassword(String username, UserUpdateRequest request) {
        var user = userRepository.findById(username)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        userMapper.updateUser(user, request);
        return userMapper.toUserResponse(userRepository.save(user));
    }

    @Override
    public void deleteUserById(String username) {
        userRepository.deleteById(username);
    }

    @Override
    public List<String> findAllRolesByUserId(String username) {
        return userRepository.findAllRolesByUsername(username);
    }

    @Override
    public User getByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
    }
}
