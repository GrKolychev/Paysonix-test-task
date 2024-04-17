package com.gr.kolychev.signatureproviderservice.exception.handler;

import com.gr.kolychev.signatureproviderservice.exception.InvalidTokenException;
import com.gr.kolychev.signatureproviderservice.model.RequestStatus;
import com.gr.kolychev.signatureproviderservice.model.GenericResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;

@RestControllerAdvice
@Order(1)
@Slf4j
public class SignatureExceptionHandler extends ResponseEntityExceptionHandler {

    private static final String TOKEN_HEADER = "Token";
    private static final String MISSING_TOKEN_HEADER = "Missing Token header in the request";

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<GenericResponse<String>> handleInvalidTokenException(InvalidTokenException ex) {
        log.error("Error when handling authentication token. Reason: {}", ex.getMessage());
        var failedResponse = GenericResponse.<String>builder()
                .status(RequestStatus.FAILED)
                .result(ex.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(failedResponse);
    }

    @ExceptionHandler(MissingRequestHeaderException.class)
    public ResponseEntity<Object> handleMissingHeaderException(MissingRequestHeaderException ex,
                                                               WebRequest webRequest) throws Exception {
        if (ex.getHeaderName().equalsIgnoreCase(TOKEN_HEADER)) {
            log.error("Missing Token header in the request {}", ex.getMessage());
            var errorResponse = GenericResponse.<String>builder()
                    .status(RequestStatus.ERROR)
                    .result(MISSING_TOKEN_HEADER)
                    .build();
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
        } else {
            return super.handleException(ex, webRequest);
        }
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<GenericResponse<List<String>>> handleConstraintException(ConstraintViolationException ex) {
        var violationsMessages = ex.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessage)
                .toList();
        log.error("Validation error occurs. Following violations encountered: {}", violationsMessages);
        var errorResponse = GenericResponse.<List<String>>builder()
                .status(RequestStatus.ERROR)
                .result(violationsMessages)
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

}
