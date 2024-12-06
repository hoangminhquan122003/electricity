package com.exercise.electricitybill.exception;

import com.exercise.electricitybill.dto.response.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalException {
    private static final Logger log = LoggerFactory.getLogger(GlobalException.class);

    @ExceptionHandler(Exception.class)
    ResponseEntity<ApiResponse> handlingRuntimeException(RuntimeException exception){
        log.error(String.valueOf(exception.getCause()));
        ApiResponse apiResponse = ApiResponse.builder()
                .code(ErrorCode.UNCATEGORIZED_EXCEPTION.getCode())
                .message(ErrorCode.UNCATEGORIZED_EXCEPTION.getMessage())
                .build();
        return ResponseEntity.status(ErrorCode.UNCATEGORIZED_EXCEPTION.getStatusCode())
                .body(apiResponse);
    }
    @ExceptionHandler(AppException.class)
    ResponseEntity<ApiResponse> handlingAppException(AppException exception){
        ErrorCode errorCode=exception.getErrorCode();
        ApiResponse apiResponse= ApiResponse.builder()
                .code(errorCode.getCode())
                .message(errorCode.getMessage())
                .build();
        return ResponseEntity.status(errorCode.getStatusCode())
                .body(apiResponse);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse> handlingMethodArgumentNotValid(MethodArgumentNotValidException exception){
        ApiResponse apiResponse =new ApiResponse();
        String e =exception.getFieldError().getDefaultMessage();
        ErrorCode errorCode = ErrorCode.INVALID_MESSAGE_KEY;
        try {
            errorCode = ErrorCode.valueOf(e); // Chuyển string lỗi thành ErrorCode
        } catch (IllegalArgumentException ex) {

        }
        apiResponse.setCode(errorCode.getCode());
        apiResponse.setMessage(errorCode.getMessage());
        return ResponseEntity.badRequest().body(apiResponse);
    }
}
