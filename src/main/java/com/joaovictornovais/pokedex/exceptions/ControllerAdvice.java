package com.joaovictornovais.pokedex.exceptions;

import jakarta.servlet.http.HttpServletRequest;
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

    @ExceptionHandler(InvalidArgumentException.class)
    public ResponseEntity<StandardError> invalidArgument(InvalidArgumentException e, HttpServletRequest request) {
        StandardError error = new StandardError(
                HttpStatus.BAD_REQUEST.value(),
                "Invalid Argument",
                e.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

}
