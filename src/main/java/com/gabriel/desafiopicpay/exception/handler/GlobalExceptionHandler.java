package com.gabriel.desafiopicpay.exception.handler;

import com.gabriel.desafiopicpay.exception.BusinessException;
import com.gabriel.desafiopicpay.exception.NotFoundException;
import com.gabriel.desafiopicpay.exception.ServiceUnavailableException;
import com.gabriel.desafiopicpay.exception.dto.ErrorDTO;
import com.gabriel.desafiopicpay.exception.dto.ErrorFieldDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final HttpStatus NOT_FOUND = HttpStatus.NOT_FOUND;
    private static final HttpStatus BAD_REQUEST = HttpStatus.BAD_REQUEST;
    private static final HttpStatus SERVICE_UNAVAILABLE = HttpStatus.SERVICE_UNAVAILABLE;

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorDTO> handleBusinessException(BusinessException ex, WebRequest request) {
        ErrorDTO ErrorDTO = buildErrorDTO(ex, BAD_REQUEST);
        return ResponseEntity.badRequest().body(ErrorDTO);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorDTO> handleNotFoundException(NotFoundException ex, WebRequest request) {
        ErrorDTO ErrorDTO = buildErrorDTO(ex, NOT_FOUND);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorDTO);
    }

    @ExceptionHandler(ServiceUnavailableException.class)
    public ResponseEntity<ErrorDTO> handleServiceUnavailableException(ServiceUnavailableException ex, WebRequest request) {
        ErrorDTO ErrorDTO = buildErrorDTO(ex, SERVICE_UNAVAILABLE);
        return ResponseEntity.status(SERVICE_UNAVAILABLE).body(ErrorDTO);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ErrorDTO> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, WebRequest request) {

        List<ErrorFieldDTO> fieldErrors = new ArrayList<>();

        for (FieldError field : ex.getBindingResult().getFieldErrors()) {
            fieldErrors.add(new ErrorFieldDTO(field.getField(), field.getDefaultMessage()));
        }

        ErrorDTO ErrorDTO = buildErrorsValidationResponse(BAD_REQUEST, "Request invalid", fieldErrors);
        return ResponseEntity.badRequest().body(ErrorDTO);
    }

    private ErrorDTO buildErrorDTO(Exception ex, HttpStatus status) {
        return ErrorDTO.builder()
                .timestamp(String.valueOf(LocalDateTime.now()))
                .status(status.value())
                .error(status.getReasonPhrase())
                .message(ex.getMessage())
                .build();
    }

    private ErrorDTO buildErrorsValidationResponse(HttpStatus status, String message, List<ErrorFieldDTO> errors) {
        return ErrorDTO.builder()
                .timestamp(String.valueOf(LocalDateTime.now()))
                .status(status.value())
                .error(status.getReasonPhrase())
                .message(message)
                .erros(errors)
                .build();
    }

}
