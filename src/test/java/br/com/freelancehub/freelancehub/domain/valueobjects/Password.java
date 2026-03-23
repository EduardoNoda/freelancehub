package br.com.freelancehub.freelancehub.domain.valueobjects;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PasswordTest {

    @Test
    void deveCriarSenhaComSucessoQuandoAtenderTodosOsRequisitos() {

        String senhaValida = "SenhaForte@123";

        assertDoesNotThrow(() -> new Password(senhaValida));
    }

    @Test
    void deveLancarExcecaoQuandoSenhaForMuitoCurta() {

        String senhaCurta = "A@1b";
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Password(senhaCurta)
        );

        assertEquals("Password must be between 8 and 30 characters", exception.getMessage());
    }

    @Test
    void deveLancarExcecaoQuandoNaoTiverSimbolo() {

        String senhaSemSimbolo = "SenhaForte123";
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Password(senhaSemSimbolo)
        );

        assertEquals("Password must contain at least one special character", exception.getMessage());
    }
}