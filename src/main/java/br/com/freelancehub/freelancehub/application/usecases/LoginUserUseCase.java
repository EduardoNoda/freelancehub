package br.com.freelancehub.freelancehub.application.usecases;

import br.com.freelancehub.freelancehub.application.ports.PasswordEncoder;
import br.com.freelancehub.freelancehub.application.ports.TokenService;
import br.com.freelancehub.freelancehub.domain.User;
import br.com.freelancehub.freelancehub.domain.repository.UserRepository;
import br.com.freelancehub.freelancehub.domain.valueobjects.Email;

public class LoginUserUseCase {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    public LoginUserUseCase (
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            TokenService tokenService
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenService = tokenService;
    }

    public String execute (String emailStr, String plainPassword) {

        Email email = new Email(emailStr);

        User user = userRepository.findByEmail(email.email())
                .orElseThrow(() -> new IllegalArgumentException("Invalid Credential"));

        if(!passwordEncoder.matches(plainPassword, user.getPasswordHash()))
            throw new IllegalArgumentException("Invalid Credential");

        return tokenService.generateToken(user);
    }

}