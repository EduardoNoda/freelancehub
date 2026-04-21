package br.com.freelancehub.freelancehub.application.usecases.dtos;

import java.math.BigDecimal;

public record FinancialSummaryResponse (
        BigDecimal totalRevenue,
        BigDecimal totalCost,
        BigDecimal netProfit
) {
}
