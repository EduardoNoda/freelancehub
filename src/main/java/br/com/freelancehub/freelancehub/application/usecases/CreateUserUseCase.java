package br.com.freelancehub.freelancehub.application.usecases;

import br.com.freelancehub.freelancehub.application.ports.PasswordEncoder;
import br.com.freelancehub.freelancehub.domain.User;
import br.com.freelancehub.freelancehub.domain.repository.UserRepository;
import br.com.freelancehub.freelancehub.domain.valueobjects.Email;
import br.com.freelancehub.freelancehub.domain.valueobjects.Password;

public class CreateUserUseCase {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public CreateUserUseCase (
            UserRepository userRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void execute(String name, String lastName, String emailStr, String plainPassword) {

        Email email = new Email(emailStr);
        Password password = new Password(plainPassword);

        String hashedPassword = passwordEncoder.encoder(password.password());

        User user = new User(name, lastName, email, hashedPassword);

        userRepository.save(user);
    }

}