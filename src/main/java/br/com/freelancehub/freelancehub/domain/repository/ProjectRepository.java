package br.com.freelancehub.freelancehub.domain.repository;

import br.com.freelancehub.freelancehub.application.usecases.dtos.FinancialSummaryResponse;
import br.com.freelancehub.freelancehub.domain.Project;

import java.util.List;
import java.util.Optional;

public interface ProjectRepository {

    void save(Project project);

    void update(Project project);

    Optional<Project> findByIdAndUserId(Long projectId, Long userId);

    List<Project> findAllProjectsByUserPaginated(Long userId, int limit, int offset);

    FinancialSummaryResponse getFinancialSummaryByUser(Long userId);

    long countProjectByUser(Long userId);

    void deleteProjectById(Long id, Long userId);
}