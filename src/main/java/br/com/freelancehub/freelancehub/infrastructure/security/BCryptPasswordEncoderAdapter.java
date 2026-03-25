package br.com.freelancehub.freelancehub.infrastructure.security;

import br.com.freelancehub.freelancehub.application.ports.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
public class BCryptPasswordEncoderAdapter implements PasswordEncoder {
    @Override
    public String encoder(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt(12));
    }

    @Override
    public boolean matches(String plainPassword, String hashedPassword) {
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }
}