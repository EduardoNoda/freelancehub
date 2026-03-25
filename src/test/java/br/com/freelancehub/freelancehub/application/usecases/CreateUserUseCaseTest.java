package br.com.freelancehub.freelancehub.application.usecases;

import br.com.freelancehub.freelancehub.application.ports.PasswordEncoder;
import br.com.freelancehub.freelancehub.domain.User;
import br.com.freelancehub.freelancehub.domain.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CreateUserUseCaseTest {

    private UserRepository userRepositoryMock;
    private PasswordEncoder passwordEncoderMock;
    private CreateUserUseCase createUserUseCase;
    @BeforeEach
    void setUp() {
        userRepositoryMock = Mockito.mock(UserRepository.class);
        passwordEncoderMock = Mockito.mock(PasswordEncoder.class);

        createUserUseCase = new CreateUserUseCase(userRepositoryMock, passwordEncoderMock);
    }

    @Test
    void deveCriarESalvarUsuarioComSucesso() {
        String name = "Alan";
        String lastName = "Turing";
        String email = "alan@turing.com";
        String plainPassword = "PasswordForte@123";
        when(passwordEncoderMock.encoder(plainPassword)).thenReturn("hash_falso_gerado_aqui");

        createUserUseCase.execute(name, lastName, email, plainPassword);
        verify(userRepositoryMock, times(1)).save(any(User.class));

        verify(passwordEncoderMock, times(1)).encoder(plainPassword);
        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepositoryMock, times(1)).save(userCaptor.capture());

        User savedUser = userCaptor.getValue();

        assertEquals("Alan", savedUser.getName());
        assertEquals("hash_falso_gerado_aqui", savedUser.getPasswordHash());
    }
}