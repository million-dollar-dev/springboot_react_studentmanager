package com.tuandang.student_manager.service;

import com.tuandang.student_manager.dto.request.StudentRequest;
import com.tuandang.student_manager.dto.request.TeacherRequest;
import com.tuandang.student_manager.dto.response.StudentResponse;
import com.tuandang.student_manager.dto.response.TeacherResponse;
import com.tuandang.student_manager.entity.Student;
import com.tuandang.student_manager.entity.Teacher;

import java.util.List;

public interface ITeacherService {
    Teacher create(TeacherRequest request);
    List<TeacherResponse> getTeachers();
    TeacherResponse getTeacherById(Long id);
    TeacherResponse updateTeacherById(Long id, TeacherRequest request);
    void deleteTeacherById(Long id);
}
