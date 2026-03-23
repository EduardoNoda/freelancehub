package br.com.freelancehub.freelancehub.infrastructure.config;

import br.com.freelancehub.freelancehub.application.ports.PasswordEncoder;
import br.com.freelancehub.freelancehub.application.ports.TokenService;
import br.com.freelancehub.freelancehub.application.usecases.AuthUserUseCase;
import br.com.freelancehub.freelancehub.application.usecases.CreateUserUseCase;
import br.com.freelancehub.freelancehub.application.usecases.LoginUserUseCase;
import br.com.freelancehub.freelancehub.domain.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfig {

    @Bean
    public CreateUserUseCase createUserUseCase(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return new CreateUserUseCase(userRepository, passwordEncoder);
    }

    @Bean
    public LoginUserUseCase loginUserUseCase(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            TokenService tokenService) {
        return new LoginUserUseCase(userRepository, passwordEncoder, tokenService);
    }

    @Bean
    public AuthUserUseCase authUserUseCase(UserRepository userRepository, TokenService tokenService) {
        return new AuthUserUseCase(userRepository, tokenService);
    }

}