package ru.itmentor.spring.boot_security.demo.exception;

public class IncorrectPasswordException extends BaseException{
    public IncorrectPasswordException(Error error) {
        super(error);
    }
}
