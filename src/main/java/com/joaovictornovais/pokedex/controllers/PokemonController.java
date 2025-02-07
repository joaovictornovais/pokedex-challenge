package com.joaovictornovais.pokedex.controllers;

import com.joaovictornovais.pokedex.services.PokemonService;
import com.joaovictornovais.pokedex.services.dto.PokemonDTO;
import com.joaovictornovais.pokedex.services.dto.PokemonGroupedByTypePaginationDTO;
import com.joaovictornovais.pokedex.services.dto.PokemonPaginationDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/pokemon")
public class PokemonController {

    private final PokemonService pokemonService;

    @GetMapping("/all")
    public ResponseEntity<PokemonPaginationDTO> findAll(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "pokemonPerPage", defaultValue = "20") int pokemonPerPage
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(this.pokemonService.findAllPokemon(page, pokemonPerPage));
    }

    @GetMapping("/filter")
    public ResponseEntity<PokemonPaginationDTO> filterPokemon(
            @RequestParam Map<String, Object> filters,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "pokemonPerPage", defaultValue = "20") int pokemonPerPage) {
        return ResponseEntity.status(HttpStatus.OK).body(this.pokemonService.filterPokemon(filters, page, pokemonPerPage));
    }

    @GetMapping("/level-up")
    public ResponseEntity<PokemonPaginationDTO> findAllAboveLevel(
            @RequestParam(value = "minLevel", defaultValue = "0") int minLevel,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "pokemonPerPage", defaultValue = "20") int pokemonPerPage) {
        return ResponseEntity.status(HttpStatus.OK).body(this.pokemonService.findAllAboveLevel(minLevel, page, pokemonPerPage));
    }

    @GetMapping("/group-by-type")
    public ResponseEntity<PokemonGroupedByTypePaginationDTO> groupPokemonByType(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "pokemonPerPage", defaultValue = "20") int pokemonPerPage
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(this.pokemonService.groupPokemonByType(page, pokemonPerPage));
    }

    @GetMapping("/count-by-type")
    public ResponseEntity<Map<String, Long>> countByType() {
        return ResponseEntity.status(HttpStatus.OK).body(this.pokemonService.countByType());
    }

    @GetMapping("/average-level")
    public ResponseEntity<Map<String, Double>> averageTypeLevel() {
        return ResponseEntity.status(HttpStatus.OK).body(this.pokemonService.averageTypeLevel());
    }

    @GetMapping("/sort-by-level")
    public ResponseEntity<PokemonPaginationDTO> sortPokemonByLevel(
            @RequestParam(name = "sort", defaultValue = "asc") String sortType,
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "pokemonPerPage", defaultValue = "20") int pokemonPerPage
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(this.pokemonService.sortPokemonByLevel(sortType, page, pokemonPerPage));
    }

    @GetMapping("/strongest")
    public ResponseEntity<PokemonDTO> findStrongestPokemon() {
        return ResponseEntity.status(HttpStatus.OK).body(this.pokemonService.findStrongestPokemon());
    }

    @GetMapping("/top3")
    public ResponseEntity<List<PokemonDTO>> findTop3StrongestPokemon() {
        return ResponseEntity.status(HttpStatus.OK).body(this.pokemonService.findTop3StrongestPokemon());
    }


}
