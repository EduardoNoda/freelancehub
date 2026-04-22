package br.com.freelancehub.freelancehub.application.usecases;

import br.com.freelancehub.freelancehub.application.usecases.dtos.PageResponse;
import br.com.freelancehub.freelancehub.application.usecases.dtos.ProjectSummaryResponse;
import br.com.freelancehub.freelancehub.domain.Project;
import br.com.freelancehub.freelancehub.domain.repository.ProjectRepository;

import java.util.List;

public class ListProjectByUserUseCase {

    private final ProjectRepository projectRepository;

    public ListProjectByUserUseCase(
            ProjectRepository projectRepository
    ) {
        this.projectRepository = projectRepository;
    }

    public PageResponse<ProjectSummaryResponse> execute(Long userId, int page, int size) {

        if(size < 1) page = 1;
        if(size < 1 || size > 50) size = 10;

        int limit = size;
        int offset = (page - 1) * size;

        List<Project> projects = projectRepository.findAllProjectsByUserPaginated(userId, limit, offset);

        long totalElements = projectRepository.countProjectByUser(userId);

        int totalPages = (int) Math.ceil((double) totalElements/size);

        List<ProjectSummaryResponse> content = projects.stream()
                .map(project -> new ProjectSummaryResponse(
                                project.getId(),
                                project.getName(),
                                project.getStatus().name(),
                                project.getType().name(),
                                project.getValue(),
                                project.getUpdatedAt()
                        ))
                .toList();
        return new PageResponse<>(content, page, size, totalElements, totalPages);

    }

}