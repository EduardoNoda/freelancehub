package br.com.freelancehub.freelancehub.application.usecases;

import br.com.freelancehub.freelancehub.application.ports.TokenService;
import br.com.freelancehub.freelancehub.domain.User;
import br.com.freelancehub.freelancehub.domain.repository.UserRepository;

public class AuthUserUseCase {

    private final UserRepository userRepository;
    private final TokenService tokenService;

    public AuthUserUseCase (
            UserRepository userRepository,
            TokenService tokenService
    ) {
        this.userRepository = userRepository;
        this.tokenService = tokenService;
    }

    public User execute (String token) {

        String email = tokenService.getSubjectFromToken(token);

        return userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

    }
}