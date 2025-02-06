package com.joaovictornovais.pokedex;

import com.joaovictornovais.pokedex.domain.pokemon.Pokemon;
import com.joaovictornovais.pokedex.repositories.PokemonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
@RequiredArgsConstructor
public class PokedexApplication implements CommandLineRunner {

    private final PokemonRepository pokemonRepository;

    public static void main(String[] args) {
        SpringApplication.run(PokedexApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        this.pokemonRepository.addAll(List.of(
                new Pokemon("Bulbasaur", "Grass", 5),
                new Pokemon("Ivysaur", "Grass", 16),
                new Pokemon("Venusaur", "Grass", 32),
                new Pokemon("Charmander", "Fire", 5),
                new Pokemon("Charmeleon", "Fire", 16),
                new Pokemon("Charizard", "Fire", 36),
                new Pokemon("Squirtle", "Water", 5),
                new Pokemon("Wartortle", "Water", 16),
                new Pokemon("Blastoise", "Water", 36),
                new Pokemon("Pidgey", "Flying", 3),
                new Pokemon("Pidgeotto", "Flying", 18),
                new Pokemon("Pidgeot", "Flying", 36),
                new Pokemon("Rattata", "Normal", 3),
                new Pokemon("Raticate", "Normal", 20),
                new Pokemon("Jigglypuff", "Fairy", 3),
                new Pokemon("Wigglytuff", "Fairy", 20),
                new Pokemon("Zubat", "Poison", 5),
                new Pokemon("Golbat", "Poison", 22),
                new Pokemon("Machop", "Fighting", 6),
                new Pokemon("Machoke", "Fighting", 28)
        ));
    }
}
