package ru.itmentor.spring.boot_security.demo.exception;

import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

public enum Error {

    WRONG_PASSWORD(UNAUTHORIZED, 401, "wrong password! ");

    Error(HttpStatus httpStatus, Integer code, String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    private final HttpStatus httpStatus;
    private final Integer code;
    private final String message;
}
