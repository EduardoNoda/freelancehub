package br.com.freelancehub.freelancehub.infrastructure.repository;

import br.com.freelancehub.freelancehub.domain.User;
import br.com.freelancehub.freelancehub.domain.repository.UserRepository;
import br.com.freelancehub.freelancehub.domain.valueobjects.Email;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@Repository
public class UserRepositoryJdbcImpl implements UserRepository {

    private final DataSource dataSource;

    public UserRepositoryJdbcImpl (DataSource dataSource) { this.dataSource = dataSource; }

    @Override
    public void save(User user) {

        String sql = "INSERT INTO users (name, last_name, email, password_hash) VALUES (?, ?, ?, ?)";

        try (Connection connection = dataSource.getConnection()) {

            PreparedStatement stmt = connection.prepareStatement(sql);

            stmt.setString(1, user.getName());
            stmt.setString(2, user.getLastName());
            stmt.setString(3, user.getEmailAdress());
            stmt.setString(4, user.getPasswordHash());

            stmt.executeUpdate();

        } catch (SQLException exception) {
            throw new RuntimeException("Error to save user", exception);
        }

    }

    @Override
    public Optional<User> findById(Long id) {
        String sql = "SELECT id, name, last_name, email, password_hash FROM users WHERE id = ?";

        try (Connection connection = dataSource.getConnection()) {

            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setLong(1, id);

            try (ResultSet result = stmt.executeQuery()) {
                User user = new User(
                        result.getLong("id"),
                        result.getString("name"),
                        result.getString("last_name"),
                        new Email(result.getString("email")),
                        result.getString("password_hash")
                );
                return Optional.of(user);
            }

        } catch (SQLException exception) {
            throw new RuntimeException("Error to find by id", exception);
        }
    }

    @Override
    public Optional<User> findByEmail(String email) {

        String sql = "SELECT id, name, last_name, email, password_hash FROM users WHERE email = ?";

        try (Connection connection = dataSource.getConnection()) {

            PreparedStatement stmt = connection.prepareStatement(sql);

            stmt.setString(1, email);

            try (ResultSet result = stmt.executeQuery()) {
                if (result.next()){
                    User user = new User(
                            result.getLong("id"),
                            result.getString("name"),
                            result.getString("last_name"),
                            new Email(result.getString("email")),
                            result.getString("password_hash")
                    );
                    return Optional.of(user);
                }
            }
        } catch (SQLException exception) {
            throw new RuntimeException("Error to find by email", exception);
        }

        return Optional.empty();
    }
}