package com.joaovictornovais.pokedex.services;

import com.joaovictornovais.pokedex.services.dto.PokemonDTO;
import com.joaovictornovais.pokedex.services.dto.PokemonGroupedByTypePaginationDTO;
import com.joaovictornovais.pokedex.services.dto.PokemonPaginationDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface PokemonService {

    List<PokemonDTO> findAllPokemonDTO();

    PokemonPaginationDTO findAllPokemon(int page, int pokemonPerPage);

    PokemonPaginationDTO findPokemonByType(String type, int page, int pokemonPerPage);

    PokemonPaginationDTO findAllAboveLevel(int minLevel, int page, int pokemonPerPage);

    PokemonGroupedByTypePaginationDTO groupPokemonByType(int page, int pokemonPerPage);

    PokemonPaginationDTO sortPokemonByLevel(String sort, int page, int pokemonPerPage);

    Map<String, Long> countByType();

    PokemonDTO findStrongestPokemon();

    Map<String, Double> averageTypeLevel();

    List<PokemonDTO> findTop3StrongestPokemon();

}
