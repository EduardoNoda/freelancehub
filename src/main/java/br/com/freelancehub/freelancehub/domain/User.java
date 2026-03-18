package br.com.freelancehub.freelancehub.domain;

import br.com.freelancehub.freelancehub.domain.valueobjects.Email;

public class User {

    private Long id;

    private String name;

    private String lastName;

    private Email email;

    private String password;

    public User (
            String name,
            String lastName,
            Email email,
            String password
    ) {
        if (name == null || name.isBlank())
            throw new IllegalArgumentException("Name cannot be null");

        if (lastName == null || lastName.isBlank())
            throw new IllegalArgumentException("lastName cannot be null");

        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public String getFullName(String name, String lastName) {
        return name + " " + lastName;
    }

}