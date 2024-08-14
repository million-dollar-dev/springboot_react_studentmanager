package com.tuandang.student_manager.controller;

import com.tuandang.student_manager.dto.request.StudentRequest;
import com.tuandang.student_manager.dto.response.ApiResponse;
import com.tuandang.student_manager.dto.response.StudentResponse;
import com.tuandang.student_manager.entity.Student;
import com.tuandang.student_manager.service.IStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {
    @Autowired
    private IStudentService studentService;

    @PostMapping()
    public ApiResponse<Student> create(@RequestBody StudentRequest request) {
        return ApiResponse.<Student>builder()
                .result(studentService.create(request))
                .build();
    }

    @GetMapping
    public ApiResponse<List<StudentResponse>> getStudents() {
        return ApiResponse.<List<StudentResponse>>builder()
                .result(studentService.getStudents())
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<StudentResponse> getStudentById(@PathVariable String id) {
        return ApiResponse.<StudentResponse>builder()
                .result(studentService.getStudentById(id))
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<StudentResponse> updateStudentById(@PathVariable String id, @RequestBody StudentRequest request) {
        return ApiResponse.<StudentResponse>builder()
                .result(studentService.updateStudentById(id, request))
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteStudentById(@PathVariable String id) {
        studentService.deleteStudentById(id);
        return ApiResponse.<Void>builder()
                .build();
    }


}
