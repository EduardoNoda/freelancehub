package br.com.freelancehub.freelancehub.domain;


import java.math.BigDecimal;

public class Project {

    Long id;

    String name;

    //String description;

    //TODO: prazo

    ProjectStatus status;

    ProjectType type;

    BigDecimal value;

    BigDecimal cost;

    Long clientId;

    public Project(Long id,
                   String name,
                   ProjectStatus status,
                   ProjectType type,
                   BigDecimal value,
                   BigDecimal cost,
                   Long clientId) {
        this.id = id;
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
}
