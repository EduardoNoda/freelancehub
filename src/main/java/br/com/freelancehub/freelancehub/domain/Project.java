package br.com.freelancehub.freelancehub.domain;


import br.com.freelancehub.freelancehub.domain.enums.ProjectStatus;
import br.com.freelancehub.freelancehub.domain.enums.ProjectType;
import br.com.freelancehub.freelancehub.domain.valueobjects.Deadline;

import java.math.BigDecimal;
import java.time.Instant;

public class Project {

    private Long id;

    private Long userId;

    private String name;

    private ProjectType type;

    private String description;

    private Deadline deadline;

    private BigDecimal value;

    private BigDecimal cost;

    private ProjectStatus status;

    private Instant updatedAt;

    private Instant deletedAt;

    //private Long clientId;

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
        this.updatedAt = Instant.now();
        this.deletedAt = null;
    }

    public Project(
            Long id,
            Long userId,
            String name,
            String description,
            ProjectStatus status,
            ProjectType type,
            BigDecimal value,
            BigDecimal cost,
            Deadline deadline,
            Instant createdAt
    ) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.description = description;
        this.status = status;
        this.type = type;
        this.value = value;
        this.cost = cost;
        this.deadline = deadline;
        this.updatedAt = createdAt;
    }

    public Long getId() { return id; }
    public Long getUserId() { return userId; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public ProjectStatus getStatus() { return status; }
    public ProjectType getType() { return type; }
    public BigDecimal getValue() { return value; }
    public BigDecimal getCost() { return cost; }
    public Deadline getDeadline() { return deadline; }
    public Instant getUpdatedAt() { return updatedAt; }

    public void changeStatus (ProjectStatus newStatus, Instant updateTime) {
        if(!status.thisValidateTransaction(newStatus))
            throw new IllegalStateException(String.format("Invalid transaction! Cannot transition from %s -> %s", this.status, newStatus));

        this.status = newStatus;
        this.updatedAt = updateTime;
    }
    public void extendDeadline (Deadline newDeadLine, Instant updateTime) {
        if (this.deadline != null && newDeadLine.getDueDate().isBefore(this.deadline.getDueDate()))
            throw new IllegalArgumentException("New date must be after the old date");

        this.deadline = newDeadLine;
        this.updatedAt = updateTime;
    }

    public void deleteProject() {
        if(this.status == ProjectStatus.IN_PROGRESS || this.status == ProjectStatus.COMPLETED)
            throw new IllegalArgumentException("Error: project not cancellable if status IN_NEGOTIATION or COMPLETED.");

        this.deletedAt = Instant.now();
    }
}
