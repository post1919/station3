package com.station3.exception.dto;

import com.station3.common.dto.BasicResponse;
import com.station3.exception.type.ErrorCode;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ErrorResponse<T> extends BasicResponse {
    protected Integer errorCode;
    protected String errorDefinition;
    protected String errorMessage;
    protected String exceptionClass;
    protected String status;

    public ErrorResponse(ErrorCode errorCode){
        this.errorCode = errorCode.getErrorCode();
        this.errorDefinition = errorCode.getDefinition();
        this.errorMessage = errorCode.getErrorMessage();
        this.exceptionClass = errorCode.getExceptionClass();
        this.status = errorCode.getStatus();
    }

    @Builder
    public ErrorResponse(Integer errorCode, String errorDefinition, String errorMessage, String exceptionClass, String status){
        this.errorCode = errorCode;
        this.errorDefinition = errorDefinition;
        this.errorMessage = errorMessage;
        this.exceptionClass = exceptionClass;
        this.status = status;
    }

    public static ErrorResponse of(ErrorCode errorCode){
        return new ErrorResponse(errorCode);
    }
}
