package com.todo.app.service.exception.custom;

public class InternalServerErrorException extends RuntimeException {
    public InternalServerErrorException() {
        super();
    }
    public InternalServerErrorException(String message, Throwable cause) {
        super(message, cause);
    }
    public InternalServerErrorException(String message) {
        super(message);
    }
    public InternalServerErrorException(Throwable cause) {
        super(cause);
    }
}
