package com.tuandang.student_manager.service;

import com.tuandang.student_manager.dto.request.StudentRequest;
import com.tuandang.student_manager.dto.response.StudentResponse;
import com.tuandang.student_manager.entity.Student;

import java.util.List;

public interface IStudentService {
    Student create(StudentRequest request);
    List<StudentResponse> getStudents();
    StudentResponse getStudentById(Long id);
    StudentResponse updateStudentById(Long id, StudentRequest request);
    void deleteStudentById(Long id);
}
