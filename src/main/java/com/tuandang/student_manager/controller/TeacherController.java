package com.tuandang.student_manager.controller;

import com.tuandang.student_manager.dto.request.TeacherRequest;
import com.tuandang.student_manager.dto.response.ApiResponse;
import com.tuandang.student_manager.dto.response.TeacherResponse;
import com.tuandang.student_manager.entity.Teacher;
import com.tuandang.student_manager.service.ITeacherService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/teachers")
public class TeacherController {
    ITeacherService teacherService;

    @PostMapping()
    public ApiResponse<Teacher> create(@RequestBody TeacherRequest request) {
        return ApiResponse.<Teacher>builder()
                .code(HttpStatus.OK.value())
                .result(teacherService.create(request))
                .build();
    }

    @GetMapping()
    public ApiResponse<List<TeacherResponse>> getTeachers() {
        return ApiResponse.<List<TeacherResponse>>builder()
                .code(HttpStatus.OK.value())
                .result(teacherService.getTeachers())
                .build();
    }

    @PutMapping("/id")
    public ApiResponse<TeacherResponse> updateTeacherById(@PathVariable Long id, @RequestBody TeacherRequest request) {
        return ApiResponse.<TeacherResponse>builder()
                .code(HttpStatus.OK.value())
                .result(teacherService.updateTeacherById(id, request))
                .build();
    }

    @DeleteMapping("/id")
    public ApiResponse<Void> deleteTeacherById(@PathVariable Long id) {
        teacherService.deleteTeacherById(id);
        return ApiResponse.<Void>builder()
                .code(HttpStatus.OK.value())
                .message("Delete Successfully")
                .build();
    }
}
