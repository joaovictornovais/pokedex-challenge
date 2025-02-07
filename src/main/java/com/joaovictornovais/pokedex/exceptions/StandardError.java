package com.joaovictornovais.pokedex.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StandardError {

    private Instant moment;
    private Integer value;
    private String error;
    private String message;
    private String path;

    public StandardError(Integer value, String error, String message, String path) {
        this.moment = Instant.now();
        this.value = value;
        this.error = error;
        this.message = message;
        this.path = path;
    }

}
