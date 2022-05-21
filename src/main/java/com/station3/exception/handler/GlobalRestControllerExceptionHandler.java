package com.station3.exception.handler;

import com.station3.common.CommonUtils;
import com.station3.common.ConstantProperties;
import com.station3.exception.CommonException;
import com.station3.exception.dto.BindExceptionResponse;
import com.station3.exception.dto.ErrorResponse;
import com.station3.exception.type.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;


@Slf4j
@RestControllerAdvice(annotations = RestController.class)
@Order(1)
public class GlobalRestControllerExceptionHandler {

    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<BindExceptionResponse<Object>> handleBindException(BindException bindResult){
        log.error(CommonUtils.getClassAndMethodName(), bindResult);

        BindExceptionResponse bindExceptionResponse = BindExceptionResponse.of(ErrorCode.BAD_REQUEST);

        //Dto유효성 체크 후 에러발생시 응답 JSON 형식중 defaultMessage 가져옴
        List<FieldError> list = bindResult.getFieldErrors();

        /*
        List<String> defaultMessageList = new ArrayList<>();
        list.forEach(fieldError->{
            System.out.println("fieldError => " + fieldError.toString());
            defaultMessageList.add(fieldError.getField()+"_"+fieldError.getDefaultMessage());
        });
        bindExceptionResponse.setErrorMessage(defaultMessageList.toString());
        */

        bindExceptionResponse.setFieldErrors(bindResult.getFieldErrors());
        bindExceptionResponse.setStatus(ConstantProperties.OUTPUT_FAIL);
        BindExceptionResponse<Object> rv = makeResponseModel(bindExceptionResponse);

        return new ResponseEntity<>(rv, HttpStatus.valueOf(bindExceptionResponse.getErrorCode()));
    }

    @ExceptionHandler({InternalAuthenticationServiceException.class})
    public ResponseEntity<ErrorResponse<Object>> handleInternalAuthenticationServiceException(InternalAuthenticationServiceException ex){
        log.error(CommonUtils.getClassAndMethodName(), ex);

        ErrorResponse errorResponse = ErrorResponse.of(ErrorCode.INTERNAL_SERVER_ERROR);

        errorResponse.setExceptionClass("InternalAuthenticationServiceException");
        errorResponse.setErrorMessage(ex.getMessage());
        errorResponse.setStatus(ConstantProperties.OUTPUT_FAIL);
        ErrorResponse<Object> rv = makeResponseModel(errorResponse);

        return new ResponseEntity<>(rv, HttpStatus.valueOf(errorResponse.getErrorCode()));
    }

    @ExceptionHandler({BadCredentialsException.class})
    public ResponseEntity<ErrorResponse<Object>> handleBadCredentialsExceptionException(BadCredentialsException ex){
        log.error(CommonUtils.getClassAndMethodName(), ex);

        ErrorResponse errorResponse = ErrorResponse.of(ErrorCode.INTERNAL_SERVER_ERROR);

        errorResponse.setExceptionClass("BadCredentialsException");
        errorResponse.setErrorMessage(ex.getMessage());
        errorResponse.setStatus(ConstantProperties.OUTPUT_FAIL);
        ErrorResponse<Object> rv = makeResponseModel(errorResponse);

        return new ResponseEntity<>(rv, HttpStatus.valueOf(errorResponse.getErrorCode()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse<Object>> handleException(Exception ex){
        log.error(CommonUtils.getClassAndMethodName(), ex);

        ErrorCode errorCode = ErrorCode.INTERNAL_SERVER_ERROR;
        CommonException ce = null;

        String message = ex.getMessage();
        if( ex instanceof NullPointerException ){
            message = "NullPointerException";
        } else if( ex instanceof CommonException){
            ce = (CommonException)ex;
            errorCode = ce.getErrorCode();
        }

        ErrorResponse errorResponse = ErrorResponse.of(errorCode);
        errorResponse.setErrorMessage(message);
        errorResponse.setStatus(ConstantProperties.OUTPUT_FAIL);
        ErrorResponse<Object> rv = makeResponseModel(errorResponse);

        return new ResponseEntity<>(rv, HttpStatus.valueOf(errorResponse.getErrorCode()));
    }

    private ErrorResponse<Object> makeResponseModel(Integer errorCode, String errorDefinition, String errorMessage, String status) {
        ErrorResponse<Object> rv = ErrorResponse.builder()
                .errorCode(errorCode)
                .errorDefinition(errorDefinition)
                .errorMessage(errorMessage)
                .status(status)
                .build();

        return rv;
    }

    private ErrorResponse<Object> makeResponseModel(ErrorResponse errorResponse) {
        ErrorResponse<Object> rv = ErrorResponse.builder()
                .errorCode(errorResponse.getErrorCode())
                .errorDefinition(errorResponse.getErrorDefinition())
                .errorMessage(errorResponse.getErrorMessage())
                .exceptionClass(errorResponse.getExceptionClass())
                .status(errorResponse.getStatus())
                .build();

        return rv;
    }

    private BindExceptionResponse<Object> makeResponseModel(BindExceptionResponse bindExceptionResponse) {
        BindExceptionResponse<Object> rv = new BindExceptionResponse(
                bindExceptionResponse.getErrorCode()
                , bindExceptionResponse.getErrorDefinition()
                , bindExceptionResponse.getStatus()
                , bindExceptionResponse.getFieldErrors()
        );

        return rv;
    }
}
