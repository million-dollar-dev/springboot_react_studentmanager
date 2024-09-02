package com.tuandang.student_manager.mapper;

import com.tuandang.student_manager.dto.request.UserRequest;
import com.tuandang.student_manager.dto.request.UserUpdateRequest;
import com.tuandang.student_manager.dto.response.UserResponse;
import com.tuandang.student_manager.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserRequest request);
    UserResponse toUserResponse(User user);
    void updateUser(@MappingTarget User user, UserUpdateRequest request);
}
