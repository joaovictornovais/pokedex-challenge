package com.joaovictornovais.pokedex.services.impl;

import com.joaovictornovais.pokedex.domain.pokemon.Pokemon;
import com.joaovictornovais.pokedex.exceptions.InvalidArgumentException;
import com.joaovictornovais.pokedex.repositories.PokemonRepository;
import com.joaovictornovais.pokedex.services.dto.PokemonDTO;
import com.joaovictornovais.pokedex.services.dto.PokemonPaginationDTO;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Test
    void itShouldFindAllFilteredPokemon() {
        List<Pokemon> allPokemon = List.of(
                new Pokemon("Pikachu", "Electric", 12),
                new Pokemon("Raichu", "Electric", 16),
                new Pokemon("Charmander", "Fire", 10)
        );
        List<PokemonDTO> allFilteredPokemon = List.of(
                new PokemonDTO("Raichu", "Electric", 16)
        );
        Map<String, Object> filters = new HashMap<>();
        filters.put("name", "chu");
        filters.put("type", "Electric");
        filters.put("minLevel", 15);
        filters.put("maxLevel", 20);

        when(this.pokemonRepository.findAll()).thenReturn(allPokemon);

        PokemonPaginationDTO response = this.pokemonService.filterPokemon(filters, 1, 20);

        assertNotNull(response);
        assertEquals(response.content(), allFilteredPokemon);
        assertEquals(1, response.content().size());

        verify(this.pokemonRepository, times(1)).findAll();
    }

    @Test
    void itShouldThrowExceptionWhenMinLevelOrMaxLeverFilterAreNotNumbers() {
        List<Pokemon> allPokemon = List.of(
                new Pokemon("Pikachu", "Electric", 12),
                new Pokemon("Raichu", "Electric", 16),
                new Pokemon("Charmander", "Fire", 10)
        );
        Map<String, Object> filters = new HashMap<>();
        filters.put("name", "chu");
        filters.put("type", "Electric");
        filters.put("minLevel", "aksdoaskodkadkpowkpod");
        filters.put("maxLevel", "as,dopakspodaksopd");

        when(this.pokemonRepository.findAll()).thenReturn(allPokemon);

        Exception thrown = assertThrows(InvalidArgumentException.class, () -> {
            this.pokemonService.filterPokemon(filters, 1, 20);
        });

        assertEquals("'minLevel' and 'maxLevel' must be a Number", thrown.getMessage());
    }

    @Test
    void itShouldFindAllAboveMinLevel() {
        List<Pokemon> allPokemon = List.of(
                new Pokemon("Pikachu", "Electric", 10),
                new Pokemon("Raichu", "Electric", 16),
                new Pokemon("Charmander", "Fire", 13)
        );
        List<PokemonDTO> allPokemonDTO = List.of(
                new PokemonDTO("Raichu", "Electric", 16),
                new PokemonDTO("Charmander", "Fire", 13)
        );
        int minLevel = 12;
        int page = 1;
        int pokemonPerPage = 20;

        when(this.pokemonRepository.findAll()).thenReturn(allPokemon);

        PokemonPaginationDTO response = this.pokemonService.findAllAboveLevel(minLevel, page, pokemonPerPage);

        assertNotNull(response);
        assertEquals(response.content(), allPokemonDTO);

        verify(this.pokemonRepository, times(1)).findAll();
    }

    @Test
    void itShouldThrowExceptionIfMinLevelIsLessThanOne() {
        int minLevel = -1;
        int page = 1;
        int pokemonPerPage = 20;

        Exception thrown = assertThrows(InvalidArgumentException.class, () -> {
            this.pokemonService.findAllAboveLevel(minLevel, page, pokemonPerPage);
        });

        assertEquals("'minLevel' must be equals or greater than 1.", thrown.getMessage());

        verify(this.pokemonRepository, times(0)).findAll();
    }
}