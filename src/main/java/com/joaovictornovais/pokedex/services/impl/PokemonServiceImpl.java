package com.joaovictornovais.pokedex.services.impl;

import com.joaovictornovais.pokedex.exceptions.InvalidArgumentException;
import com.joaovictornovais.pokedex.exceptions.NoContentFoundException;
import com.joaovictornovais.pokedex.repositories.PokemonRepository;
import com.joaovictornovais.pokedex.services.PokemonService;
import com.joaovictornovais.pokedex.services.dto.MinPokemonDTO;
import com.joaovictornovais.pokedex.services.dto.PokemonDTO;
import com.joaovictornovais.pokedex.services.dto.PokemonGroupedByTypePaginationDTO;
import com.joaovictornovais.pokedex.services.dto.PokemonPaginationDTO;
import com.joaovictornovais.pokedex.utils.PaginatedResult;
import com.joaovictornovais.pokedex.utils.PaginationUtils;
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

    public List<PokemonDTO> findAllPokemonDTO() {
        return this.pokemonRepository.findAll().stream()
                .map(p -> new PokemonDTO(
                        p.getName(),
                        p.getType(),
                        p.getLevel()
                )).toList();
    }

    public PokemonPaginationDTO findAllPokemon(int page, int pokemonPerPage) {
        List<PokemonDTO> allPokemon = this.findAllPokemonDTO();

        PaginatedResult<PokemonDTO> paginationResult = PaginationUtils.paginate(
                allPokemon,
                page,
                pokemonPerPage
        );

        return new PokemonPaginationDTO(
                page,
                paginationResult.totalPages(),
                pokemonPerPage,
                paginationResult.totalItems(),
                Map.of(),
                paginationResult.content()
        );
    }

    public PokemonPaginationDTO filterPokemon(Map<String, Object> filters, int page, int pokemonPerPage) {
        List<PokemonDTO> allPokemon = this.findAllPokemonDTO();

        List<PokemonDTO> verifiedFilters = verifyFilters(allPokemon, filters);

        PaginatedResult<PokemonDTO> paginationResult = PaginationUtils.paginate(verifiedFilters, page, pokemonPerPage);

        return new PokemonPaginationDTO(
                page,
                paginationResult.totalPages(),
                pokemonPerPage,
                paginationResult.totalItems(),
                filters,
                paginationResult.content()
        );
    }

    public PokemonPaginationDTO findAllAboveLevel(int minLevel, int page, int pokemonPerPage) {
        List<PokemonDTO> filteredPokemon = this.findAllPokemonDTO().stream()
                .filter(p -> p.level() >= minLevel).toList();

        PaginatedResult<PokemonDTO> paginationResult = PaginationUtils.paginate(filteredPokemon, page, pokemonPerPage);

        return new PokemonPaginationDTO(
                page,
                paginationResult.totalPages(),
                pokemonPerPage,
                paginationResult.totalItems(),
                Map.of("minLevel", minLevel),
                paginationResult.content()
        );
    }

    public PokemonGroupedByTypePaginationDTO groupPokemonByType(int page, int pokemonPerPage) {
        List<PokemonDTO> allPokemon = this.findAllPokemonDTO();

        PaginatedResult<PokemonDTO> paginationResult = PaginationUtils.paginate(allPokemon, page, pokemonPerPage);

        Map<String, List<MinPokemonDTO>> groupedPokemon = paginationResult.content().stream()
                .collect(Collectors.groupingBy(PokemonDTO::type,
                        Collectors.mapping(
                                p -> new MinPokemonDTO(p.name(), p.level()),
                                Collectors.toList()
                        )
                ));

        return new PokemonGroupedByTypePaginationDTO(
                page,
                paginationResult.totalPages(),
                pokemonPerPage,
                paginationResult.totalItems(),
                Map.of(),
                groupedPokemon
        );
    }

    public PokemonPaginationDTO sortPokemonByLevel(String sort, int page, int pokemonPerPage) {
        List<PokemonDTO> allPokemon = this.findAllPokemonDTO();

        if (sort.equals("asc")) {
            allPokemon = allPokemon.stream()
                    .sorted(Comparator.comparingInt(PokemonDTO::level)).toList();
        } else if (sort.equals("desc")) {
            allPokemon = allPokemon.stream()
                    .sorted(Comparator.comparingInt(PokemonDTO::level).reversed()).toList();
        } else {
            throw new InvalidArgumentException("Invalid sort type. Accepted values are 'asc' or 'desc'.");
        }

        PaginatedResult<PokemonDTO> paginationResult = PaginationUtils.paginate(allPokemon, page, pokemonPerPage);

        return new PokemonPaginationDTO(
                page,
                paginationResult.totalPages(),
                pokemonPerPage,
                paginationResult.totalItems(),
                Map.of("sort", sort),
                paginationResult.content()
        );
    }

    public Map<String, Long> countByType() {
        return this.findAllPokemonDTO().stream()
                .collect(Collectors.groupingBy(PokemonDTO::type, Collectors.counting()));
    }

    public PokemonDTO findStrongestPokemon() {
        return this.findAllPokemonDTO().stream()
                .max(Comparator.comparingInt(PokemonDTO::level)).orElseThrow(NoContentFoundException::new);
    }

    public Map<String, Double> averageTypeLevel() {
        return this.findAllPokemonDTO().stream()
                .collect(Collectors.groupingBy(
                        PokemonDTO::type,
                        Collectors.averagingLong(PokemonDTO::level)
                ));
    }

    public List<PokemonDTO> findTop3StrongestPokemon() {
        return this.findAllPokemonDTO().stream()
                .sorted(Comparator.comparingInt(PokemonDTO::level).reversed())
                .limit(3).toList();
    }

    private List<PokemonDTO> verifyFilters(List<PokemonDTO> pokemonList, Map<String, Object> filters) {
        if (filters.containsKey("name")) {
            pokemonList = pokemonList.stream()
                    .filter(p -> p.name().toLowerCase().contains(filters.get("name").toString().toLowerCase()))
                    .toList();
        }

        if (filters.containsKey("type")) {
            pokemonList = pokemonList.stream()
                    .filter(p -> p.type().equalsIgnoreCase(filters.get("type").toString()))
                    .toList();
        }

        try {
            if (filters.containsKey("minLevel")) {
                pokemonList = pokemonList.stream()
                        .filter(p -> p.level() >= Integer.parseInt(filters.get("minLevel").toString()))
                        .toList();
            }

            if (filters.containsKey("maxLevel")) {
                pokemonList = pokemonList.stream()
                        .filter(p -> p.level() <= Integer.parseInt(filters.get("maxLevel").toString()))
                        .toList();
            }
        } catch (NumberFormatException e) {
            throw new InvalidArgumentException("'Min Level' and 'Max Level' must be a Number");
        }

        filters.remove("page");
        filters.remove("pokemonPerPage");

        return pokemonList;
    }

}
