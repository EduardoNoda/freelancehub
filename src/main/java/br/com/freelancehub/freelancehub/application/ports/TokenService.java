package br.com.freelancehub.freelancehub.application.ports;

import br.com.freelancehub.freelancehub.domain.User;

public interface TokenService {

    String generateToken(User user);

    String getSubjectFromToken(String token);

}