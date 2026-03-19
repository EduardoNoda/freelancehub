package br.com.freelancehub.freelancehub.domain.repository;

import br.com.freelancehub.freelancehub.domain.User;
import br.com.freelancehub.freelancehub.domain.valueobjects.Email;

import java.util.Optional;

public interface UserRepository {

    void save (User user);

    Optional<User> findById(Long id);

    Optional<User> findByEmail(String email);

}