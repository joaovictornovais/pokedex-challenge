package com.joaovictornovais.pokedex.domain.pokemon;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class Pokemon {

    private UUID id;
    private String name;
    private String type;
    private Integer level;

    public Pokemon(String name, String type, Integer level) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.type = type;
        this.level = level;
    }

}
