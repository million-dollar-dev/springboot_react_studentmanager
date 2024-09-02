package com.tuandang.student_manager.dto.response;

import com.tuandang.student_manager.entity.Role;

import java.util.Set;

public class UserResponse {
    String username;
    String password;
    Set<Role> role;
}
