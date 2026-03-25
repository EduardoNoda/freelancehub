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
            Long id,
            Long userId,
            String name,
            ProjectStatus status,
            ProjectType type,
            BigDecimal value,
            BigDecimal cost,
            Long clientId) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.status = status;
        this.type = type;
        this.value = value;
        this.cost = cost;
        this.clientId = clientId;
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
