package com.station3.exception;

import com.station3.exception.type.ErrorCode;
import lombok.Getter;

@Getter
public class CommonException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private ErrorCode errorCode;

    public CommonException(String errorMessage, ErrorCode errorCode){
        super(errorMessage);
        this.errorCode = errorCode;
    }

    public CommonException(ErrorCode errorCode){
        super(errorCode.getErrorMessage());
        this.errorCode = errorCode;
    }
}
