package com.tuandang.student_manager.exception;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
public class AppException extends RuntimeException{
    ErrorCode errorCode;
    public AppException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public void setErrorCode(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}
