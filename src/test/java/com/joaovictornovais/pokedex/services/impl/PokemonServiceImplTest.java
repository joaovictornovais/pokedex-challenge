package com.joaovictornovais.pokedex.services.impl;

import com.joaovictornovais.pokedex.domain.pokemon.Pokemon;
import com.joaovictornovais.pokedex.repositories.PokemonRepository;
import com.joaovictornovais.pokedex.services.dto.PokemonDTO;
import com.joaovictornovais.pokedex.services.dto.PokemonPaginationDTO;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PokemonServiceImplTest {

    private PokemonServiceImpl pokemonService;

    @Mock
    private PokemonRepository pokemonRepository;

    @BeforeAll
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.pokemonService = new PokemonServiceImpl(this.pokemonRepository);
    }

    @BeforeEach
    void beforeEach() {
        reset(this.pokemonRepository);
    }

    @Test
    void itShouldFindAllPokemonAndConvertToDTO() {
        Pokemon pokemon = new Pokemon("Pikachu", "Electric", 12);
        PokemonDTO pokemonDTO = new PokemonDTO("Pikachu", "Electric", 12);
        List<Pokemon> pokemonList = List.of(pokemon);

        when(this.pokemonRepository.findAll()).thenReturn(pokemonList);

        List<PokemonDTO> response = this.pokemonService.findAllPokemonDTO();

        assertNotNull(response);
        assertEquals(1, response.size());
        assertTrue(response.contains(pokemonDTO));

        verify(this.pokemonRepository, times(1)).findAll();
    }

    @Test
    void itShouldFindAllPokemonWithPagination() {
        Pokemon pokemon = new Pokemon("Pikachu", "Electric", 12);
        PokemonDTO pokemonDTO = new PokemonDTO("Pikachu", "Electric", 12);
        List<Pokemon> pokemonList = List.of(pokemon);
        List<PokemonDTO> pokemonDTOList = List.of(pokemonDTO);

        when(this.pokemonRepository.findAll()).thenReturn(pokemonList);

        PokemonPaginationDTO response = this.pokemonService.findAllPokemon(1, 20);

        assertNotNull(response);
        assertEquals(response.content(), pokemonDTOList);
        assertEquals(1, response.content().size());

        verify(this.pokemonRepository, times(1)).findAll();

    }

}