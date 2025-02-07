package com.joaovictornovais.pokedex.utils;

import com.joaovictornovais.pokedex.exceptions.InvalidArgumentException;

import java.util.List;

public class PaginationUtils {
    public static <T> PaginatedResult<T> paginate(List<T> items, int page, int itemsPerPage) {
        if (page < 1) {
            throw new InvalidArgumentException("Page must be equals or greater than 1");
        }
        if (itemsPerPage < 1) {
            throw new InvalidArgumentException("Items Per Page must be equals or greater than 1");
        }

        int totalItems = items.size();
        int totalPages = (int) Math.ceil((double) totalItems / itemsPerPage);
        int startIndex = (page - 1) * itemsPerPage;
        List<T> paginatedItems = items.stream().skip(startIndex).limit(itemsPerPage).toList();

        return new PaginatedResult<>(page, totalPages, itemsPerPage, totalItems, paginatedItems);
    }
}
