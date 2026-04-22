package br.com.freelancehub.freelancehub.application.usecases;

import br.com.freelancehub.freelancehub.domain.Project;
import br.com.freelancehub.freelancehub.domain.repository.ProjectRepository;

public class DeleteProjectUseCase {

    private final ProjectRepository projectRepository;

    public DeleteProjectUseCase(
            ProjectRepository projectRepository
    ) {
        this.projectRepository = projectRepository;
    }

    public void execute(Long projectId, Long userId) {
        Project project = projectRepository.findByIdAndUserId(projectId, userId)
                .orElseThrow(() -> new IllegalArgumentException("Error: user or project not found"));

        project.deleteProject();
        projectRepository.deleteProjectById(projectId, userId);
    }

}