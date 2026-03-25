package br.com.freelancehub.freelancehub.application.usecases;

import br.com.freelancehub.freelancehub.domain.Project;
import br.com.freelancehub.freelancehub.domain.User;
import br.com.freelancehub.freelancehub.domain.enums.ProjectType;
import br.com.freelancehub.freelancehub.domain.repository.ProjectRepository;
import br.com.freelancehub.freelancehub.domain.repository.UserRepository;
import br.com.freelancehub.freelancehub.domain.valueobjects.Deadline;

import java.math.BigDecimal;
import java.time.LocalDate;

public class CreateProjectUseCase {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    public CreateProjectUseCase (
            ProjectRepository projectRepository,
            UserRepository userRepository
    ) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }

    public void execute(Long userId, String title, String description, ProjectType type, BigDecimal value, LocalDate deadline) {
        User owner = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Deadline projectDeadline = new Deadline(deadline);

        Project project = new Project(userId, title, description, type, value, projectDeadline);

        projectRepository.save(project);
    }

}