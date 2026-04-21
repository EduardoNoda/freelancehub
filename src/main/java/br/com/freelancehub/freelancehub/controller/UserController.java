package br.com.freelancehub.freelancehub.controller;

import br.com.freelancehub.freelancehub.application.usecases.CreateUserUseCase;
import br.com.freelancehub.freelancehub.application.usecases.GetFinancialSummaryUseCase;
import br.com.freelancehub.freelancehub.application.usecases.LoginUserUseCase;
import br.com.freelancehub.freelancehub.application.usecases.dtos.FinancialSummaryResponse;
import br.com.freelancehub.freelancehub.domain.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final CreateUserUseCase createUserUseCase;
    private final LoginUserUseCase loginUserUseCase;
    private final GetFinancialSummaryUseCase getFinancialSummaryUseCase;

    public UserController (
            CreateUserUseCase createUserUseCase,
            LoginUserUseCase loginUserUseCase,
            GetFinancialSummaryUseCase getFinancialSummaryUseCase
    ) {
        this.createUserUseCase = createUserUseCase;
        this.loginUserUseCase = loginUserUseCase;
        this.getFinancialSummaryUseCase = getFinancialSummaryUseCase;
    }

    public record RegisterRequest(String name, String lastName, String email, String password){}
    public record LoginRequest(String email, String password) {}
    public record TokenResponse(String token) {}

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody RegisterRequest request) {
        createUserUseCase.execute(
                request.name(),
                request.lastName(),
                request.email(),
                request.password()
        );
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody LoginRequest request) {
        String token = loginUserUseCase.execute(
                request.email(),
                request.password()
        );
        return ResponseEntity.ok(new TokenResponse(token));
    }

    @GetMapping("/me/financial-summary")
    public ResponseEntity<FinancialSummaryResponse> getFinancialSummary(
            @AuthenticationPrincipal User user
    ) {
        Long userId = user.getId();
        FinancialSummaryResponse summary = getFinancialSummaryUseCase.execute(userId);
        return ResponseEntity.ok(summary);
    }
}