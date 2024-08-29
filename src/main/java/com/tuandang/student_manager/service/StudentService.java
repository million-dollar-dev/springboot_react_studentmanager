package com.tuandang.student_manager.service;

import com.tuandang.student_manager.dto.request.StudentRequest;
import com.tuandang.student_manager.dto.response.StudentResponse;
import com.tuandang.student_manager.entity.Student;
import com.tuandang.student_manager.exception.AppException;
import com.tuandang.student_manager.exception.ErrorCode;
import com.tuandang.student_manager.mapper.StudentMapper;
import com.tuandang.student_manager.repository.StudentRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class StudentService implements IStudentService{
    StudentRepository studentRepository;
    StudentMapper studentMapper;
    @Override
    public Student create(StudentRequest request) {
        if (studentAlreadyExist(request.getEmail()))
            throw new AppException(ErrorCode.STUDENT_EXISTED);
        return studentRepository.save(studentMapper.toStudent(request));
    }

    @Override
    public List<StudentResponse> getStudents() {
        List<StudentResponse> list = new ArrayList<>();
        studentRepository.findAll().forEach(student -> list.add(studentMapper.toStudentResponse(student)));
        return list;
    }

    @Override
    public StudentResponse getStudentById(Long id) {
        return studentMapper.toStudentResponse(studentRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.STUDENT_NOT_EXISTED)));
    }

    @Override
    public StudentResponse updateStudentById(Long id, StudentRequest request) {
        var student = studentRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.STUDENT_NOT_EXISTED));
        studentMapper.updateStudent(student, request);
        return studentMapper.toStudentResponse(studentRepository.save(student));
    }

    @Override
    public void deleteStudentById(Long id) {
        studentRepository.deleteById(id);
    }

    private boolean studentAlreadyExist(String email) {
        return studentRepository.findByEmail(email).isPresent();
    }
}
