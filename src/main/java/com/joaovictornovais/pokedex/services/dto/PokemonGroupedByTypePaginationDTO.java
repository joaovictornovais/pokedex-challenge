package com.joaovictornovais.pokedex.services.dto;

import java.util.List;
import java.util.Map;

public record PokemonGroupedByTypePaginationDTO(
        Integer currentPage,
        Integer totalPages,
        Integer pokemonPerPage,
        Integer totalPokemon,
        Map<String, Object> filters,
        Map<String, List<MinPokemonDTO>> content
) {
}
