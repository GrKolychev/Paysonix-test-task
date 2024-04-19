package com.gr.kolychev.signatureproviderservice.exception.handler;

import com.gr.kolychev.signatureproviderservice.model.GenericResponse;
import com.gr.kolychev.signatureproviderservice.model.RequestStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GenericExceptionHandler {

    private static final String GENERIC_ERROR_MESSAGE = "Something went wrong. Please contact support team.";

    @ExceptionHandler
    protected ResponseEntity<GenericResponse<String>> handleExceptionInternal(Exception ex) {
        log.error("Unexpected error happened: {}, {}, cause: {}", ex.getClass(), ex.getMessage(), ex.getStackTrace());
        var errorResponse = GenericResponse.<String>builder()
                .status(RequestStatus.ERROR)
                .result(GENERIC_ERROR_MESSAGE)
                .build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}
