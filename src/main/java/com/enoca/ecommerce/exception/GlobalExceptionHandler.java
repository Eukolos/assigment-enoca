package com.enoca.ecommerce.exception;

import com.enoca.ecommerce.aop.InMemoryLogger;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {
    private final InMemoryLogger log;


    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleEntityNotFoundException(EntityNotFoundException ex) {
        ResponseEntity<ErrorDetails> body = ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ErrorDetails.builder()
                        .path(ex.getStackTrace()[0].getClassName())
                        .message(ex.getMessage())
                        .error(HttpStatus.NOT_FOUND.getReasonPhrase())
                        .timestamp(LocalDateTime.now().toString())
                        .build());
        log.log("EntityNotFoundException: " + body);
        return body;
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorDetails> handleIllegalArgumentException(IllegalArgumentException ex) {
        ResponseEntity<ErrorDetails> body = ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ErrorDetails.builder()
                        .path(ex.getStackTrace()[0].getClassName())
                        .message(ex.getMessage())
                        .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                        .timestamp(LocalDateTime.now().toString())
                        .build());
        log.log("IllegalArgumentException: " + body);
        return body;
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ErrorDetails> handleIllegalStateException(IllegalStateException ex) {
        ResponseEntity<ErrorDetails> body = ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ErrorDetails.builder()
                        .path(ex.getStackTrace()[0].getClassName())
                        .message(ex.getMessage())
                        .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                        .timestamp(LocalDateTime.now().toString())
                        .build());
        log.log("IllegalStateException: " + body);
        return body;
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> handleException(Exception ex) {
        ResponseEntity<ErrorDetails> body = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ErrorDetails.builder()
                        .path(ex.getStackTrace()[0].getClassName())
                        .message(ex.getMessage())
                        .error(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                        .timestamp(LocalDateTime.now().toString())
                        .build());
        log.log("Exception: " + body);
        return body;
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorDetails> handleRuntimeException(RuntimeException ex) {
        ResponseEntity<ErrorDetails> body = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ErrorDetails.builder()
                        .path(ex.getStackTrace()[0].getClassName())
                        .message(ex.getMessage())
                        .error(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                        .timestamp(LocalDateTime.now().toString())
                        .build());
        log.log("RuntimeException: " + body);
        return body;
    }
}
