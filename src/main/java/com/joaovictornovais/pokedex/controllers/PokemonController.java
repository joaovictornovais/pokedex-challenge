package com.joaovictornovais.pokedex.controllers;

import com.joaovictornovais.pokedex.services.PokemonService;
import com.joaovictornovais.pokedex.services.dto.MinPokemonDTO;
import com.joaovictornovais.pokedex.services.dto.PokemonDTO;
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
    public ResponseEntity<List<PokemonDTO>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(this.pokemonService.findAllPokemon());
    }

    @GetMapping("/filter")
    public ResponseEntity<List<PokemonDTO>> findPokemonByType(@RequestParam(value = "type", required = true) String type) {
        return ResponseEntity.status(HttpStatus.OK).body(this.pokemonService.findPokemonByType(type));
    }

    @GetMapping("/level-up")
    public ResponseEntity<List<PokemonDTO>> findAllAboveLevel(@RequestParam(value = "minLevel", required = true) Integer minLevel) {
        return ResponseEntity.status(HttpStatus.OK).body(this.pokemonService.findAllAboveLevel(minLevel));
    }

    @GetMapping("/group-by-type")
    public ResponseEntity<Map<String, List<MinPokemonDTO>>> groupPokemonsByType() {
        return ResponseEntity.status(HttpStatus.OK).body(this.pokemonService.groupPokemonsByType());
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
    public ResponseEntity<List<PokemonDTO>> sortPokemonsByLevel() {
        return ResponseEntity.status(HttpStatus.OK).body(this.pokemonService.sortPokemonsByLevel());
    }

    @GetMapping("/strongest")
    public ResponseEntity<PokemonDTO> findStrongestPokemon() {
        return ResponseEntity.status(HttpStatus.OK).body(this.pokemonService.findStrongestPokemon());
    }

    @GetMapping("/top3")
    public ResponseEntity<List<PokemonDTO>> getTop3StrongestPokemons() {
        return ResponseEntity.status(HttpStatus.OK).body(this.pokemonService.getTop3StrongestPokemons());
    }


}
