package br.com.freelancehub.freelancehub.domain.valueobjects;

public record Password(String password) {
    public Password {
        if (password == null || password.isBlank())
            throw new IllegalArgumentException("Password cannot be null");

        if (password.length() < 8 || password.length() > 30)
            throw new IllegalArgumentException("Password must be between 8 and 30 characters");

        if (password.matches(".*\\p{Lu}.*"))
            throw new IllegalArgumentException("Password must contain at least one uppercase character");

        if (password.matches(".*\\p{Ll}.*"))
            throw new IllegalArgumentException("Password must contain at least one lowercase character");

        if (password.matches(".*\\d.*"))
            throw new IllegalArgumentException("Password must contain at least one number");

        if(password.matches(".*[^\\p{L}\\d\\s].*"))
            throw new IllegalArgumentException("Password must contain at least one special character");
    }
}