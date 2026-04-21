package br.com.freelancehub.freelancehub.infrastructure.config;

import br.com.freelancehub.freelancehub.application.ports.PasswordEncoder;
import br.com.freelancehub.freelancehub.application.ports.TokenService;
import br.com.freelancehub.freelancehub.application.usecases.*;
import br.com.freelancehub.freelancehub.domain.repository.ProjectRepository;
import br.com.freelancehub.freelancehub.domain.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;

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

    @Bean
    public Clock clock () { return Clock.systemUTC(); }

    @Bean
    public CreateProjectUseCase createProjectUseCase(ProjectRepository projectRepository, UserRepository userRepository, Clock clock) {
        return new CreateProjectUseCase(projectRepository, userRepository, clock);
    }

    @Bean
    public ChangeStatusProjectUseCase changeStatusProjectUseCase(ProjectRepository projectRepository, UserRepository userRepository, Clock clock) {
        return new ChangeStatusProjectUseCase(projectRepository, userRepository, clock);
    }

    @Bean
    public ListProjectByUserUseCase listProjectByUserUseCase(ProjectRepository projectRepository) {
        return new ListProjectByUserUseCase(projectRepository);
    }

    @Bean
    public GetFinancialSummaryUseCase getFinancialSummaryUseCase(ProjectRepository projectRepository) {
        return new GetFinancialSummaryUseCase(projectRepository);
    }
}