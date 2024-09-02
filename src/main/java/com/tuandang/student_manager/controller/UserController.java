package com.tuandang.student_manager.controller;

import com.tuandang.student_manager.dto.request.UserRequest;
import com.tuandang.student_manager.dto.request.UserUpdateRequest;
import com.tuandang.student_manager.dto.response.ApiResponse;
import com.tuandang.student_manager.dto.response.UserResponse;
import com.tuandang.student_manager.entity.User;
import com.tuandang.student_manager.service.IUserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/users")
public class UserController {
    IUserService userService;

    @PostMapping
    public ApiResponse<User> create(@RequestBody UserRequest request) {
        return ApiResponse.<User>builder()
                .result(userService.create(request))
                .build();
    }

    @GetMapping
    public ApiResponse<List<UserResponse>> getUsers() {
        return ApiResponse.<List<UserResponse>>builder()
                .result(userService.getUsers())
                .build();
    }

    @PutMapping("/{username}")
    public ApiResponse<UserResponse> updateUser(@PathVariable String username, @RequestBody UserUpdateRequest request) {
        return ApiResponse.<UserResponse>builder()
                .result(userService.updatePassword(username, request))
                .build();
    }

    @DeleteMapping("/{username}")
    public ApiResponse<Void> deleteUser(@PathVariable String username) {
        userService.deleteUserById(username);
        return ApiResponse.<Void>builder()
                .message("Delete successfully!")
                .build();
    }


}
