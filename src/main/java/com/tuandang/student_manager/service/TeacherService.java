package com.tuandang.student_manager.service;

import com.tuandang.student_manager.dto.request.TeacherRequest;
import com.tuandang.student_manager.dto.response.TeacherResponse;
import com.tuandang.student_manager.entity.Teacher;
import com.tuandang.student_manager.exception.AppException;
import com.tuandang.student_manager.exception.ErrorCode;
import com.tuandang.student_manager.mapper.TeacherMapper;
import com.tuandang.student_manager.repository.TeacherRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TeacherService implements ITeacherService{
    TeacherRepository teacherRepository;
    TeacherMapper teacherMapper;
    @Override
    public Teacher create(TeacherRequest request) {
        if (teacherAlreadyExist(request.getEmail()))
            throw new AppException(ErrorCode.TEACHER_EXISTED);
        return teacherRepository.save(teacherMapper.toTeacher(request));
    }

    @Override
    public List<TeacherResponse> getTeachers() {
        List<TeacherResponse> list = new ArrayList<>();
        teacherRepository.findAll().forEach(teacher -> list.add(teacherMapper.toTeacherResponse(teacher)));
        return list;
    }

    @Override
    public TeacherResponse getTeacherById(Long id) {
        return teacherMapper.toTeacherResponse(teacherRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.TEACHER_NOT_EXISTED)));
    }

    @Override
    public TeacherResponse updateTeacherById(Long id, TeacherRequest request) {
        var teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.TEACHER_NOT_EXISTED));
        teacherMapper.updateTeacher(teacher, request);
        return teacherMapper.toTeacherResponse(teacherRepository.save(teacher));
    }

    @Override
    public void deleteTeacherById(Long id) {
        teacherRepository.deleteById(id);
    }

    private boolean teacherAlreadyExist(String email) {
        return teacherRepository.findByEmail(email).isPresent();
    }
}
