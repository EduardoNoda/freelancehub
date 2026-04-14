package br.com.freelancehub.freelancehub.domain.repository;

import br.com.freelancehub.freelancehub.domain.Project;

import java.util.Optional;

public interface ProjectRepository {

    void save(Project project);

    void update(Project project);

    Optional<Project> findByIdAndUserId(Long projectId, Long userId);

}