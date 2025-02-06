package com.joaovictornovais.pokedex.services.impl;

import com.joaovictornovais.pokedex.repositories.PokemonRepository;
import com.joaovictornovais.pokedex.services.PokemonService;
import com.joaovictornovais.pokedex.services.dto.MinPokemonDTO;
import com.joaovictornovais.pokedex.services.dto.PokemonDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PokemonServiceImpl implements PokemonService {

    private final PokemonRepository pokemonRepository;

    public List<PokemonDTO> findAllPokemon() {
        return this.pokemonRepository.findAll().stream()
                .map(p -> new PokemonDTO(
                        p.getName(),
                        p.getType(),
                        p.getLevel()
                )).toList();
    }

    public List<PokemonDTO> findPokemonByType(String type) {
        return this.findAllPokemon().stream()
                .filter(p -> p.type().equals(type)).toList();
    }

    public List<PokemonDTO> findAllAboveLevel(int minLevel) {
        return this.findAllPokemon().stream()
                .filter(p -> p.level() >= 10).toList();
    }

    public Map<String, List<MinPokemonDTO>> groupPokemonsByType() {
        return this.findAllPokemon().stream()
                .collect(Collectors.groupingBy(
                                PokemonDTO::type,
                                Collectors.mapping(
                                        p -> new MinPokemonDTO(p.name(), p.level()),
                                        Collectors.toList()
                                )
                        )
                );
    }

    public List<PokemonDTO> sortPokemonsByLevel() {
        return this.findAllPokemon().stream()
                .sorted(Comparator.comparingInt(PokemonDTO::level)).toList();
    }

    public Map<String, Long> countByType() {
        return this.findAllPokemon().stream()
                .collect(Collectors.groupingBy(PokemonDTO::type, Collectors.counting()));
    }

    public PokemonDTO findStrongestPokemon() {
        return this.findAllPokemon().stream()
                .max(Comparator.comparingInt(PokemonDTO::level)).orElse(null);
    }

    public Map<String, Double> averageTypeLevel() {
        return this.findAllPokemon().stream()
                .collect(Collectors.groupingBy(
                        PokemonDTO::type,
                        Collectors.averagingLong(PokemonDTO::level)
                ));
    }

    public List<PokemonDTO> getTop3StrongestPokemons() {
        return this.findAllPokemon().stream()
                .sorted(Comparator.comparingInt(PokemonDTO::level).reversed())
                .limit(3).toList();
    }

}
