package com.joaovictornovais.pokedex.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(NoContentFoundException.class)
    public ResponseEntity<StandardError> noContentFound() {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
