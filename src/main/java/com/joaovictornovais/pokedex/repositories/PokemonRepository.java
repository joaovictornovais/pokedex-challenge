package com.joaovictornovais.pokedex.repositories;

import com.joaovictornovais.pokedex.domain.pokemon.Pokemon;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class PokemonRepository {

    private final List<Pokemon> list = new ArrayList<>();

    public List<Pokemon> findAll() {
        return list;
    }

    public Optional<Pokemon> findByName(String name) {
        return this.list.stream()
                .filter(p -> p.getName().equalsIgnoreCase(name))
                .findFirst();
    }

    public void addAll(List<Pokemon> toAdd) {
        for (Pokemon pokemon : toAdd) {
            if (findByName(pokemon.getName()).isEmpty()) {
                this.list.add(pokemon);
            } else {
                System.out.println(pokemon.getName() + " is already registered");
            }
        }
    }

}
