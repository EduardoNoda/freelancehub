package br.com.freelancehub.freelancehub.domain.valueobjects;

import java.time.LocalDate;

public record Deadline(LocalDate deadline) {
    public Deadline {
        if (deadline == null)
            throw new IllegalArgumentException("Deadline data cannot be null");
    }
    public LocalDate getDueDate (){
        return deadline;
    }
    public boolean isOverdue(LocalDate today){
        return today.isAfter(deadline);
    }
}