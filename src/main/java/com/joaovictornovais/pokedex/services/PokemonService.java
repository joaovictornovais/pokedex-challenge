package com.joaovictornovais.pokedex.services;

import com.joaovictornovais.pokedex.services.dto.MinPokemonDTO;
import com.joaovictornovais.pokedex.services.dto.PokemonDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface PokemonService {

    List<PokemonDTO> findAllPokemon();

    List<PokemonDTO> findPokemonByType(String type);

    List<PokemonDTO> findAllAboveLevel(int minLevel);

    Map<String, List<MinPokemonDTO>> groupPokemonsByType();

    List<PokemonDTO> sortPokemonsByLevel();

    Map<String, Long> countByType();

    PokemonDTO findStrongestPokemon();

    Map<String, Double> averageTypeLevel();

    List<PokemonDTO> getTop3StrongestPokemons();

}
