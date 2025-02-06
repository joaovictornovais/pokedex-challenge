package com.joaovictornovais.pokedex.repositories;

import com.joaovictornovais.pokedex.domain.pokemon.Pokemon;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class PokemonRepository {

    private final List<Pokemon> list = new ArrayList<>();

    public List<Pokemon> findAll() {
        return list;
    }

    public void addAll(List<Pokemon> toAdd) {
        list.addAll(toAdd);
    }

}
