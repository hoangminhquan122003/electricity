package com.exercise.electricitybill.exception;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),
    CONFIG_NAME_EXITED(1001,"Config name exited", HttpStatus.BAD_REQUEST),
    CONFIG_NAME_NOT_EXITED(1002,"Config name not exited", HttpStatus.BAD_REQUEST),
    INVALID_MESSAGE_KEY(1003,"invalid message key",HttpStatus.BAD_REQUEST),
    KWH_USED_INVALID(1004,"kwh used must be greater than 0",HttpStatus.BAD_REQUEST),
    USER_EXITED(1005,"user exited",HttpStatus.BAD_REQUEST),
    USER_NOT_EXITED(1006,"user not exited",HttpStatus.BAD_REQUEST),
    UNAUTHENTICATED(1007, "Unauthenticated", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(1008, "Unauthorized", HttpStatus.FORBIDDEN),
    USER_IS_ELICTRICIAN(1009,"user must not be elictrician ",HttpStatus.BAD_REQUEST),
    EMAIL_INVALID(1010,"email invalid",HttpStatus.BAD_REQUEST),
    EMAIL_BLANK(1011,"email must not be blank",HttpStatus.BAD_REQUEST),
    EMAIL_EXITED(1012,"email exited",HttpStatus.BAD_REQUEST),
    EMAIL_NOT_EXITED(1013,"email not exited",HttpStatus.BAD_REQUEST)



    ;
    int code;
    String message;
    HttpStatusCode statusCode;

    ErrorCode(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }
}
