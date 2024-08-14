package com.tuandang.student_manager.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {
    STUDENT_EXISTED(1001, "Student already existed", HttpStatus.BAD_REQUEST),
    STUDENT_NOT_EXISTED(1002, "Student not existed", HttpStatus.NOT_FOUND),

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
