package com.station3.exception.dto;

import com.station3.exception.type.ErrorCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.FieldError;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class BindExceptionResponse<T> extends ErrorResponse<T> {
    private List<FieldError> fieldErrors;

    public BindExceptionResponse(ErrorCode errorCode){
        this.errorCode = errorCode.getErrorCode();
        this.errorDefinition = errorCode.getDefinition();
        this.status = errorCode.getStatus();
    }

    public BindExceptionResponse(Integer errorCode, String errorDefinition, String status, List<FieldError> fieldErrors){
        this.errorCode = errorCode;
        this.errorDefinition = errorDefinition;
        this.status = status;
        this.fieldErrors = fieldErrors;
    }

    public static BindExceptionResponse of(ErrorCode errorCode){
        return new BindExceptionResponse(errorCode);
    }
}
