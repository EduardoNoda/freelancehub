package br.com.freelancehub.freelancehub.application.usecases.dtos;

import br.com.freelancehub.freelancehub.domain.enums.ProjectStatus;
import br.com.freelancehub.freelancehub.domain.enums.ProjectType;

import java.math.BigDecimal;
import java.time.Instant;

public record ProjectSummaryResponse(
        Long id,
        String name,
        String status,
        String type,
        BigDecimal value,
        Instant updatedAt
) {
}
