package ru.itmentor.spring.boot_security.demo.exception;

public class BaseException extends RuntimeException{
    private final Error error;

    public BaseException(Error error) {
        this.error = error;
    }
    public Error getError() {
        return error;
    }
}
