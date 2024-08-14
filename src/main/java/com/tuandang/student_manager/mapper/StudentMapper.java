package com.tuandang.student_manager.mapper;

import com.tuandang.student_manager.dto.request.StudentRequest;
import com.tuandang.student_manager.dto.response.StudentResponse;
import com.tuandang.student_manager.entity.Student;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface StudentMapper {
    Student toStudent(StudentRequest request);
    StudentResponse toStudentResponse(Student student);
    void updateStudent(@MappingTarget Student student, StudentRequest request);
}
