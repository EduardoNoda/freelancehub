package br.com.freelancehub.freelancehub.application.ports;

public interface PasswordEncoder {

    public String encoder(String plainPassword);

    public boolean matches(String plainPassword, String hashedPassword);

}