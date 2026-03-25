package br.com.freelancehub.freelancehub.application.usecases;

import br.com.freelancehub.freelancehub.application.ports.PasswordEncoder;
import br.com.freelancehub.freelancehub.application.ports.TokenService;
import br.com.freelancehub.freelancehub.domain.User;
import br.com.freelancehub.freelancehub.domain.repository.UserRepository;
import br.com.freelancehub.freelancehub.domain.valueobjects.Email;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LoginUserUseCaseTest {

    private UserRepository userRepositoryMock;
    private PasswordEncoder passwordEncoderMock;
    private TokenService tokenServiceMock;
    private LoginUserUseCase loginUserUseCase;

    @BeforeEach
    void setUp() {
        userRepositoryMock = Mockito.mock(UserRepository.class);
        passwordEncoderMock = Mockito.mock(PasswordEncoder.class);
        tokenServiceMock = Mockito.mock(TokenService.class);

        loginUserUseCase = new LoginUserUseCase(userRepositoryMock, passwordEncoderMock, tokenServiceMock);
    }

    @Test
    void deveRetornarTokenQuandoCredenciaisForemValidas() {
        String plainPassword = "MinhaSenhaForte123!";
        String emailStr = "teste@teste.com";

        User fakeUser = new User(1L, "João", "Silva", new Email(emailStr), "hash_do_banco");

        when(userRepositoryMock.findByEmail(emailStr)).thenReturn(Optional.of(fakeUser));
        when(passwordEncoderMock.matches(plainPassword, "hash_do_banco")).thenReturn(true);
        when(tokenServiceMock.generateToken(fakeUser)).thenReturn("token.jwt.falso");

        // Act
        String tokenRetornado = loginUserUseCase.execute(emailStr, plainPassword);

        // Assert
        assertEquals("token.jwt.falso", tokenRetornado);
        verify(tokenServiceMock, times(1)).generateToken(fakeUser); // Garante que o gerador foi chamado
    }

    @Test
    void deveLancarExcecaoQuandoSenhaEstiverIncorreta() {
        // Arrange
        String wrongPassword = "SenhaErrada123!";
        String emailStr = "teste@teste.com";

        User fakeUser = new User(1L, "João", "Silva", new Email(emailStr), "hash_do_banco");

        when(userRepositoryMock.findByEmail(emailStr)).thenReturn(Optional.of(fakeUser));

        when(passwordEncoderMock.matches(wrongPassword, "hash_do_banco")).thenReturn(false);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> loginUserUseCase.execute(emailStr, wrongPassword)
        );

        assertEquals("Invalid Credential", exception.getMessage());

        verify(tokenServiceMock, never()).generateToken(any());
    }
}