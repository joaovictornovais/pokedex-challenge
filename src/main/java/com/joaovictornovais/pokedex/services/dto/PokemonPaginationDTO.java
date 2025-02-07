package com.joaovictornovais.pokedex.services.dto;

import java.util.List;
import java.util.Map;

public record PokemonPaginationDTO(
        Integer currentPage,
        Integer totalPages,
        Integer pokemonPerPage,
        Integer totalPokemon,
        Map<String, Object> filters,
        List<PokemonDTO> content) {
}
