package com.gabriel.desafiopicpay.domain.exception.handler;

import com.gabriel.desafiopicpay.domain.exception.BusinessException;
import com.gabriel.desafiopicpay.domain.exception.ErrorField;
import com.gabriel.desafiopicpay.domain.exception.ErrorResponse;
import com.gabriel.desafiopicpay.domain.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class ExceptionHandler {

    private static final HttpStatus NOT_FOUND = HttpStatus.NOT_FOUND;
    private static final HttpStatus BAD_REQUEST = HttpStatus.BAD_REQUEST;

    @org.springframework.web.bind.annotation.ExceptionHandler(BusinessException.class)
    public ResponseEntity<Object> handleBusinessException(BusinessException ex, WebRequest request) {
        ErrorResponse errorResponse = buildErrorResponse(ex, BAD_REQUEST);
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> handleNotFoundException(NotFoundException ex, WebRequest request) {
        ErrorResponse errorResponse = buildErrorResponse(ex, NOT_FOUND);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, WebRequest request) {

        List<ErrorField> fieldErrors = new ArrayList<>();

        for (FieldError field : ex.getBindingResult().getFieldErrors()) {
            fieldErrors.add(new ErrorField(field.getField(), field.getDefaultMessage()));
        }

        ErrorResponse errorResponse = buildErrorsValidationResponse(BAD_REQUEST, "Request invalid", fieldErrors);
        return ResponseEntity.badRequest().body(errorResponse);
    }

    private ErrorResponse buildErrorResponse(Exception ex, HttpStatus status) {
        return ErrorResponse.builder()
                .timestamp(String.valueOf(LocalDateTime.now()))
                .status(status.value())
                .error(status.getReasonPhrase())
                .message(ex.getMessage())
                .build();
    }

    private ErrorResponse buildErrorsValidationResponse(HttpStatus status, String message, List<ErrorField> errors) {
        return ErrorResponse.builder()
                .timestamp(String.valueOf(LocalDateTime.now()))
                .status(status.value())
                .error(status.getReasonPhrase())
                .message(message)
                .erros(errors)
                .build();
    }

}
