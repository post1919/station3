package com.station3.exception.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode implements ErrorResponseEnumDto {
    NOT_FOUND(404, "PAGE NOT FOUND"),
    INTERNAL_SERVER_ERROR(500, "INTERNAL SERVER ERROR"),
    EMAIL_DUPLICATION(400, "EMAIL DUPLICATED"),
    NULL_POINTER(500, "NULL POINTER"),
    ILLEGAL_STATE(500, "ILLEGALSTATE"),
    BAD_REQUEST(400, "BAD_REQUEST"),
    VALITATION(500, "VALITATION"),
    NEED_AUTH(403, "NEED_AUTH")
    ;

    private Integer errorCode;
    private String errorDefinition;
    private String errorMessage;
    private String exceptionClass;
    private String status;

    ErrorCode(Integer errorCode, String errorDefinition){
        this.errorCode = errorCode;
        this.errorDefinition = errorDefinition;
    }

    @Override
    public Integer getErrorCode() {
        return this.errorCode;
    }

    @Override
    public String getDefinition() {
        return this.errorDefinition;
    }
}
