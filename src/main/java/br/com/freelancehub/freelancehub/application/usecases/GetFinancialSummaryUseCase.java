package br.com.freelancehub.freelancehub.application.usecases;

import br.com.freelancehub.freelancehub.application.usecases.dtos.FinancialSummaryResponse;
import br.com.freelancehub.freelancehub.domain.repository.ProjectRepository;

public class GetFinancialSummaryUseCase {
    private final ProjectRepository projectRepository;
    public GetFinancialSummaryUseCase(ProjectRepository projectRepository) {this.projectRepository = projectRepository;}
    public FinancialSummaryResponse execute(Long userId) {return projectRepository.getFinancialSummaryByUser(userId);}
}
