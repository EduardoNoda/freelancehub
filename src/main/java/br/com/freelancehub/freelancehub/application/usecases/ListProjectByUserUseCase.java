package br.com.freelancehub.freelancehub.application.usecases;

import br.com.freelancehub.freelancehub.application.usecases.dtos.ProjectSummaryResponse;
import br.com.freelancehub.freelancehub.domain.Project;
import br.com.freelancehub.freelancehub.domain.repository.ProjectRepository;
import br.com.freelancehub.freelancehub.domain.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

public class ListProjectByUserUseCase {

    private final ProjectRepository projectRepository;

    public ListProjectByUserUseCase(
            ProjectRepository projectRepository
    ) {
        this.projectRepository = projectRepository;
    }

    public List<ProjectSummaryResponse> execute(Long userId, int page, int size) {

        if(size < 1) page = 1;
        if(size < 1 || size > 50) size = 10;

        int limit = size;
        int offset = (page - 1) * size;

        List<Project> projects = projectRepository.findAllProjectsByUserPaginated(userId, limit, offset);

        return projects.stream()
                .map(project -> new ProjectSummaryResponse(
                                project.getId(),
                                project.getName(),
                                project.getStatus().name(),
                                project.getType().name(),
                                project.getValue(),
                                project.getUpdatedAt()
                        ))
                .collect(Collectors.toList());

    }

}