package br.com.freelancehub.freelancehub.infrastructure.security;

import br.com.freelancehub.freelancehub.application.ports.TokenService;
import br.com.freelancehub.freelancehub.domain.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Component
public class JwtTokenAdapter implements TokenService {

    private final String secret;
    private final String issuer;

    public JwtTokenAdapter (
            @Value("${security.jwt.secret:very-long-key}") String secret,
            @Value("${security.jwt.issuer:freelancehub}") String issuer
    ) {
        this.secret = secret;
        this.issuer = issuer;
    }


    @Override
    public String generateToken(User user) {
        try {

            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer(issuer)
                    .withSubject(user.getEmailAdress())
                    .withExpiresAt(generateOverdueDate())
                    .sign(algorithm);

        } catch (IllegalArgumentException exception) {
            throw new IllegalArgumentException("Erro ao criar token JWT", exception);
        }
    }

    @Override
    public String getSubjectFromToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);

            return JWT.require(algorithm)
                    .withIssuer(issuer)
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (IllegalArgumentException exception) {
            throw new IllegalArgumentException("Token inválido ou expirado", exception);
        }
    }

    public Instant generateOverdueDate() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.UTC);
    }
}