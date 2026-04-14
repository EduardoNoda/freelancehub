package br.com.freelancehub.freelancehub.application.usecases;

import br.com.freelancehub.freelancehub.domain.Project;
import br.com.freelancehub.freelancehub.domain.User;
import br.com.freelancehub.freelancehub.domain.enums.ProjectStatus;
import br.com.freelancehub.freelancehub.domain.repository.ProjectRepository;
import br.com.freelancehub.freelancehub.domain.repository.UserRepository;

import java.time.Clock;

public class ChangeStatusProjectUseCase {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final Clock clock;

    public ChangeStatusProjectUseCase (
            ProjectRepository projectRepository,
            UserRepository userRepository,
            Clock clock
    ) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
        this.clock = clock;
    }

    public void execute(Long userId, Long projectId, ProjectStatus status) {
        Project project = projectRepository.findByIdAndUserId(projectId, userId)
                .orElseThrow(() -> new IllegalArgumentException("Project not found or User does not own the project"));

        project.changeStatus(status, clock.instant());
        projectRepository.update(project);
    }

}