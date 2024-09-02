package com.tuandang.student_manager.service;

import com.tuandang.student_manager.dto.request.UserRequest;
import com.tuandang.student_manager.dto.request.UserUpdateRequest;
import com.tuandang.student_manager.dto.response.UserResponse;
import com.tuandang.student_manager.entity.User;

import java.util.List;

public interface IUserService {
    User create(UserRequest request);
    List<UserResponse> getUsers();
    UserResponse updatePassword(String username, UserUpdateRequest request);
    void deleteUserById(String username);
}
