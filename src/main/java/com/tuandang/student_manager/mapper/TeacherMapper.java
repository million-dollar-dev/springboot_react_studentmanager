package com.tuandang.student_manager.mapper;

import com.tuandang.student_manager.dto.request.TeacherRequest;
import com.tuandang.student_manager.dto.response.TeacherResponse;
import com.tuandang.student_manager.entity.Teacher;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface TeacherMapper {
    Teacher toTeacher(TeacherRequest request);
    TeacherResponse toTeacherResponse(Teacher teacher);
    void updateTeacher(@MappingTarget Teacher teacher, TeacherRequest request);
}
