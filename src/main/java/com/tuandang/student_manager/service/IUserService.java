package com.tuandang.student_manager.service;

import com.tuandang.student_manager.dto.request.UserRequest;
import com.tuandang.student_manager.dto.request.UserUpdateRequest;
import com.tuandang.student_manager.dto.response.UserResponse;
import com.tuandang.student_manager.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface IUserService {
    UserDetailsService userDetailsService();
    User create(UserRequest request);
    List<UserResponse> getUsers();
    UserResponse updatePassword(String username, UserUpdateRequest request);
    void deleteUserById(String username);
    List<String> findAllRolesByUserId(String username);
    User getByUsername(String userName);
    String saveUser(User user);
}
