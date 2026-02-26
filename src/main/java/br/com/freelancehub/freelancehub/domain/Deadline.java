package br.com.freelancehub.freelancehub.domain;

import java.time.LocalDate;

public class Deadline {

    LocalDate dueDate;

    public Deadline (LocalDate dueDate) { this.dueDate = dueDate; }

    public boolean isOverdue (LocalDate today) {
        return today.isAfter(dueDate);
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

}