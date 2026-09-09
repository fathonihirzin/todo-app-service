package com.todo.app.service.exception;

import com.todo.app.service.exception.custom.InternalServerErrorException;
import com.todo.app.service.exception.custom.InvalidRequestException;
import com.todo.app.service.exception.custom.ResourceNotFoundException;
import com.todo.app.service.model.response.DefaultResponse;
import com.todo.app.service.model.response.ErrorsResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    // 400 – single message (invalid request, bad sort column, etc.)
    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<DefaultResponse> handleInvalidRequest(InvalidRequestException ex) {
        log.warn("Bad request: {}", ex.getMessage());
        return buildDefaultError(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    // 400 – validation errors on @Valid request body (multiple fields)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorsResponse> handleValidation(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult().getFieldErrors().stream()
                .map(fe -> fe.getField() + ": " + fe.getDefaultMessage())
                .collect(Collectors.toList());
        log.warn("Validation failed: {}", errors);
        return buildErrors(HttpStatus.BAD_REQUEST, "Validation failed", errors);
    }

    // 404
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<DefaultResponse> handleNotFound(ResourceNotFoundException ex) {
        log.warn("Not found: {}", ex.getMessage());
        return buildDefaultError(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    // 500 – custom internal service error
    @ExceptionHandler(InternalServerErrorException.class)
    public ResponseEntity<DefaultResponse> handleInternal(InternalServerErrorException ex) {
        log.error("Internal server error: {}", ex.getMessage(), ex);
        return buildDefaultError(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error");
    }

    // 500 – any other unexpected exception
    @ExceptionHandler(Exception.class)
    public ResponseEntity<DefaultResponse> handleGeneral(Exception ex) {
        log.error("Unhandled exception", ex);
        return buildDefaultError(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error");
    }

    private ResponseEntity<DefaultResponse> buildDefaultError(HttpStatus status, String detail) {
        DefaultResponse response = new DefaultResponse("ERROR", detail, status.value());
        return ResponseEntity.status(status).body(response);
    }

    private ResponseEntity<ErrorsResponse> buildErrors(HttpStatus status, String detail, List<String> errors) {
        ErrorsResponse response = new ErrorsResponse("ERROR", detail, status.value(), errors);
        return ResponseEntity.status(status).body(response);
    }
}

