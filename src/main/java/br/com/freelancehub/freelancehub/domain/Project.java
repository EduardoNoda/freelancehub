package br.com.freelancehub.freelancehub.domain;


import br.com.freelancehub.freelancehub.domain.enums.ProjectStatus;
import br.com.freelancehub.freelancehub.domain.enums.ProjectType;
import br.com.freelancehub.freelancehub.domain.valueobjects.Deadline;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Project {

    private Long id;

    private Long userId;

    private LocalDate updatedAt;

    private String name;

    private ProjectType type;

    private String description;

    private Deadline deadline;

    private BigDecimal value;

    private BigDecimal cost;

    private ProjectStatus status;

    Long clientId;

    public Project(
            Long userId,
            String name,
            String description,
            ProjectType type,
            BigDecimal value,
            Deadline deadline
            ) {
        this.userId = userId;
        this.name = name;
        this.description = description;
        this.status = ProjectStatus.IN_NEGOTIATION;
        this.type = type;
        this.value = value;
        this.deadline = deadline;
    }

    public void changeStatus (ProjectStatus newStatus) {
        if(!status.thisValidateTransaction(newStatus))
            throw new IllegalStateException("Invalid transaction!");

        this.status = newStatus;
    }
    public void extendDeadline (Deadline newDeadLine) {
        if (this.deadline != null && newDeadLine.getDueDate().isBefore(this.deadline.getDueDate()))
            throw new IllegalArgumentException("New date must be after the old date");
        this.deadline = newDeadLine;
    }
}
