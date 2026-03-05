package br.com.freelancehub.freelancehub.domain.valueobjects;

public record Email(String email) {
    public Email {
        if(email == null || email.isBlank())
            throw new IllegalArgumentException("E-mail cannot be null");

        if (!email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$"))
            throw new IllegalArgumentException("E-mail com formato inválido.");
    }
}