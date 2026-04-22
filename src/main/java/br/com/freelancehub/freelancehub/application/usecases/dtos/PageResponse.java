package br.com.freelancehub.freelancehub.application.usecases.dtos;

import java.util.List;

public record PageResponse<T>(
    List<T> current,
    int currentPage,
    int pageSize,
    long totalElements,
    int totalPages
) {
}