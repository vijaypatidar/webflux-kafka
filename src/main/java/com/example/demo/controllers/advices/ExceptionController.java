package com.example.demo.controllers.advices;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Optional;

@RestControllerAdvice
public class ExceptionController {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Error> handleNotFoundException(Exception ex) {
            HttpStatus status = Optional.of(ex).filter(e->e instanceof ValidationException)
                    .map(e->(ValidationException)e)
                    .map(ValidationException::getStatus).orElse(HttpStatus.BAD_REQUEST);

            return ResponseEntity
                    .status(status)
                    .body(Error
                            .builder()
                            .message(ex.getMessage())
                            .build()
                    );
    }

    @Builder
    @Data
    static class Error {
        private String message;
    }
}

