package br.com.freelancehub.freelancehub.controller;

import br.com.freelancehub.freelancehub.application.usecases.ChangeStatusProjectUseCase;
import br.com.freelancehub.freelancehub.application.usecases.CreateProjectUseCase;
import br.com.freelancehub.freelancehub.domain.User;
import br.com.freelancehub.freelancehub.domain.enums.ProjectStatus;
import br.com.freelancehub.freelancehub.domain.enums.ProjectType;
import br.com.freelancehub.freelancehub.domain.valueobjects.Deadline;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    private final CreateProjectUseCase createProjectUseCase;
    private final ChangeStatusProjectUseCase changeStatusProjectUseCase;

    public ProjectController (
            CreateProjectUseCase createProjectUseCase,
            ChangeStatusProjectUseCase changeStatusProjectUseCase
    ) {
        this.createProjectUseCase = createProjectUseCase;
        this.changeStatusProjectUseCase = changeStatusProjectUseCase;
    }

    public record CreateProjectRequest(String name, String description, ProjectType type, BigDecimal value, LocalDate deadline) {}
    public record ChangeProjectRequest(ProjectStatus status) {}

    @PostMapping
    public ResponseEntity<Void> createProject(
            @AuthenticationPrincipal User loggedUser,
            @RequestBody CreateProjectRequest request
    ) {
        createProjectUseCase.execute(
                loggedUser.getId(),
                request.name,
                request.description,
                request.type,
                request.value,
                request.deadline
        );
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PatchMapping("/{projectId}/status")
    public ResponseEntity<Void> changeStatus (
            @AuthenticationPrincipal User loggedUser,
            @PathVariable Long projectId,
            @RequestBody ChangeProjectRequest request
    ) {
        changeStatusProjectUseCase.execute(
                loggedUser.getId(),
                projectId,
                request.status
        );

        return ResponseEntity.noContent().build();
    }

}
