package com.joaovictornovais.pokedex.utils;

import java.util.List;

public record PaginatedResult<T>(
        int currentPage,
        int totalPages,
        int itemsPerPage,
        int totalItems,
        List<T> content
) {
}
