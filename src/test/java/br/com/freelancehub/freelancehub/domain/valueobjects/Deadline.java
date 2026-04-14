package br.com.freelancehub.freelancehub.domain.valueobjects;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class DeadlineTest {

    @Test
    void shouldReturnTrueWhenDateIsAfterDeadline() {
        Deadline deadline = new Deadline(LocalDate.of(2026, 4, 10));

        boolean overdue = deadline.isOverdue(LocalDate.of(2026, 4, 11));

        assertTrue(overdue);
    }

    @Test
    void shouldReturnFalseWhenActualDateIsEqualDeadline() {
        Deadline deadline = new Deadline(LocalDate.of(2026, 4, 10));

        boolean overdue = deadline.isOverdue(LocalDate.of(2026, 4, 10));

        assertFalse(overdue);
    }

    @Test
    void shouldReturnFalseWhenActualDateIsBeforeDeadline() {
        Deadline deadline = new Deadline(LocalDate.of(2026, 4, 10));

        boolean overdue = deadline.isOverdue(LocalDate.of(2026, 4, 9));

        assertFalse(overdue);
    }
}