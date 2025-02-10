package com.joaovictornovais.pokedex.controllers;

import com.joaovictornovais.pokedex.services.dto.PokemonDTO;
import com.joaovictornovais.pokedex.services.dto.PokemonPaginationDTO;
import com.joaovictornovais.pokedex.services.impl.PokemonServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PokemonControllerTest {

    private PokemonController pokemonController;

    @Mock
    PokemonServiceImpl pokemonService;

    @BeforeAll
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.pokemonController = new PokemonController(this.pokemonService);
    }

    @BeforeEach
    void beforeEach() {
        reset(this.pokemonService);
    }

    @Test
    void itShouldFindAllPokemonWithPagination() {
        PokemonPaginationDTO pokemonPaginationDTO = new PokemonPaginationDTO(
                1,
                5,
                20,
                20,
                Map.of(),
                List.of(mock(PokemonDTO.class))
        );

        when(this.pokemonService.findAllPokemon(1, 20)).thenReturn(pokemonPaginationDTO);

        ResponseEntity<PokemonPaginationDTO> response = this.pokemonController.findAll(1, 20);
        PokemonPaginationDTO body = response.getBody();

        assertNotNull(body);
        assertEquals(body, pokemonPaginationDTO);
        assertEquals(body.content().size(), pokemonPaginationDTO.content().size());
        assertEquals(HttpStatus.OK, response.getStatusCode());

        verify(this.pokemonService, times(1)).findAllPokemon(1, 20);
    }

    @Test
    void itShouldFindAllFilteredPokemon() {
        Map<String, Object> filters = Map.of("name", "saur", "minLevel", 16);
        List<PokemonDTO> filteredPokemon = List.of(
                new PokemonDTO("Ivysaur", "Grass", 16),
                new PokemonDTO("Venusaur", "Grass", 32)
        );
        int page = 1;
        int pokemonPerPage = 20;
        PokemonPaginationDTO paginationDTO = new PokemonPaginationDTO(
                1,
                1,
                20,
                20,
                filters,
                filteredPokemon
        );
        when(this.pokemonService.filterPokemon(filters, page, pokemonPerPage)).thenReturn(paginationDTO);

        ResponseEntity<PokemonPaginationDTO> response = this.pokemonController.filterPokemon(filters, page, pokemonPerPage);
        PokemonPaginationDTO body = response.getBody();

        assertNotNull(body);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, body.content().size());
        assertEquals(body.content(), filteredPokemon);

        verify(this.pokemonService, times(1)).filterPokemon(filters, page, pokemonPerPage);
    }


}