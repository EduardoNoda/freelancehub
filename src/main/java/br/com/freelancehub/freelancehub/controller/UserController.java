package br.com.freelancehub.freelancehub.controller;

import br.com.freelancehub.freelancehub.application.usecases.CreateUserUseCase;
import br.com.freelancehub.freelancehub.application.usecases.LoginUserUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final CreateUserUseCase createUserUseCase;
    private final LoginUserUseCase loginUserUseCase;

    public UserController (
            CreateUserUseCase createUserUseCase,
            LoginUserUseCase loginUserUseCase
    ) {
        this.createUserUseCase = createUserUseCase;
        this.loginUserUseCase = loginUserUseCase;
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
}