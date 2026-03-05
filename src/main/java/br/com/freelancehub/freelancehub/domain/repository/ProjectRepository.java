package br.com.freelancehub.freelancehub.domain.repository;

import br.com.freelancehub.freelancehub.domain.Project;

import java.util.Optional;

public interface ProjectRepository {

    void save(Project project);

    Optional<Project> findById(Long id);

}