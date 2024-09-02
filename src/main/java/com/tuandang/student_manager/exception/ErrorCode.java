package com.tuandang.student_manager.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {
    STUDENT_EXISTED(1001, "Student already existed", HttpStatus.BAD_REQUEST),
    STUDENT_NOT_EXISTED(1002, "Student not existed", HttpStatus.NOT_FOUND),
    TEACHER_EXISTED(1003, "Teacher already existed", HttpStatus.BAD_REQUEST),
    TEACHER_NOT_EXISTED(1004, "Teacher not existed", HttpStatus.NOT_FOUND),
    USER_EXISTED(1003, "User already existed", HttpStatus.BAD_REQUEST),
    USER_NOT_EXISTED(1004, "User not existed", HttpStatus.NOT_FOUND)
    ;
    private int code;
    private String message;
    private HttpStatusCode httpStatusCode;

    ErrorCode(int code, String message, HttpStatusCode httpStatusCode) {
        this.code = code;
        this.message = message;
        this.httpStatusCode = httpStatusCode;
    }
}
