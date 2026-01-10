package com.valuable.valuable._one.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public final class PaginationUtils {

    private PaginationUtils() {
        throw new UnsupportedOperationException("Pagination Utils class cannot be instantiate");
    }


    public static Pageable create(int page, int size, String sortField, boolean ascending) {
        Sort sort = ascending ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();
        return PageRequest.of(page, size, sort);
    }
}
