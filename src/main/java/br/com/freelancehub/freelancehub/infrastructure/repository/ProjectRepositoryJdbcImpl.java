package br.com.freelancehub.freelancehub.infrastructure.repository;

import br.com.freelancehub.freelancehub.application.usecases.dtos.FinancialSummaryResponse;
import br.com.freelancehub.freelancehub.domain.Project;
import br.com.freelancehub.freelancehub.domain.enums.ProjectStatus;
import br.com.freelancehub.freelancehub.domain.enums.ProjectType;
import br.com.freelancehub.freelancehub.domain.repository.ProjectRepository;
import br.com.freelancehub.freelancehub.domain.valueobjects.Deadline;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class ProjectRepositoryJdbcImpl implements ProjectRepository {

    private final DataSource dataSource;

    public ProjectRepositoryJdbcImpl (DataSource dataSource) { this.dataSource = dataSource; }

    @Override
    public void save(Project project) {

        String sql = "INSERT INTO projects (user_id, name, description, status, type, value, cost, deadline, updated_at) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?) ";

        try(Connection connection = dataSource.getConnection()){

            PreparedStatement stmt = connection.prepareStatement(sql);

            stmt.setLong(1, project.getUserId());
            stmt.setString(2, project.getName());
            stmt.setString(3, project.getDescription());
            stmt.setString(4, project.getStatus().name());
            stmt.setString(5, project.getType().name());
            stmt.setBigDecimal(6, project.getValue());
            stmt.setBigDecimal(7, project.getCost());

            if(project.getDeadline() != null)
                stmt.setDate(8, Date.valueOf(project.getDeadline().deadline()));
            else
                stmt.setNull(8, java.sql.Types.DATE);

            stmt.setTimestamp(9, java.sql.Timestamp.from(project.getUpdatedAt()));

            stmt.executeUpdate();
        } catch (SQLException exception) {
            throw new RuntimeException("Error to save project.", exception);
        }

    }

    @Override
    public void update(Project project) {
        String sql = "UPDATE projects SET name = ?, description = ?, status = ?, type = ?, value = ?, cost = ?, deadline = ?, updated_at = ? " +
                    "WHERE id = ? AND user_id = ?";

        try(Connection connection = dataSource.getConnection()) {

            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, project.getName());
            stmt.setString(2, project.getDescription());
            stmt.setString(3, project.getStatus().name());
            stmt.setString(4, project.getType().name());
            stmt.setBigDecimal(5, project.getValue());
            stmt.setBigDecimal(6, project.getCost());

            if(project.getDeadline() != null)
                stmt.setDate(7, Date.valueOf(project.getDeadline().getDueDate()));
            else
                stmt.setNull(7, java.sql.Types.DATE);

            stmt.setTimestamp(8, java.sql.Timestamp.from(project.getUpdatedAt()));

            stmt.setLong(9, project.getId());
            stmt.setLong(10, project.getUserId());

            int rowsAffected = stmt.executeUpdate();

            if(rowsAffected == 0)
                throw new RuntimeException("User not found or not own project");

        } catch (SQLException exception) {
            throw new RuntimeException("Error to update project.");
        }

    }

    @Override
    public Optional<Project> findByIdAndUserId(Long projectId, Long userId) {

        String sql = "SELECT id, user_id, name, description, status, type, value, cost, deadline, updated_at " +
                    "FROM projects " +
                    "WHERE id = ? AND user_id = ?";

        try (Connection connection = dataSource.getConnection()) {

            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setLong(1, projectId);
            stmt.setLong(2, userId);

            try(ResultSet result = stmt.executeQuery()) {
                if(result.next()) {
                    Project project = mapResultSet(result);
                    return Optional.of(project);
                }
            }

        } catch (SQLException exception) {
            throw new RuntimeException("Error: User or project not found", exception);
        }

        return Optional.empty();
    }

    @Override
    public List<Project> findAllProjectsByUserPaginated(Long userId, int limit, int offset) {

        String sql = "SELECT * FROM projects WHERE user_id = ? ORDER BY updated_at DESC LIMIT ? OFFSET ?";

        List<Project> projects = new ArrayList<>();

        try(Connection connection = dataSource.getConnection()) {

            PreparedStatement stmt = connection.prepareStatement(sql);

            stmt.setLong(1, userId);
            stmt.setInt(2, limit);
            stmt.setInt(3, offset);

            try(ResultSet result = stmt.executeQuery()) {
                while(result.next()) {
                    Project project = mapResultSet(result);
                    projects.add(project);
                }
            }

        } catch(SQLException exception) {
            throw new RuntimeException("Error to find user or projects by user", exception);
        }

        return projects;
    }

    @Override
    public FinancialSummaryResponse getFinancialSummaryByUser(Long userId) {
        String sql = "SELECT " +
                "COALESCE(SUM(value), 0) AS total_revenue, " +
                "COALESCE(SUM(cost), 0) as total_cost " +
                "FROM projects " +
                "WHERE user_id = ? AND status = 'COMPLETED'";

        try(Connection connection = dataSource.getConnection()){
            PreparedStatement stmt = connection.prepareStatement(sql);

            stmt.setLong(1, userId);

            try(ResultSet resultSet = stmt.executeQuery()) {
                if(resultSet.next()) {
                    BigDecimal totalRevenue = resultSet.getBigDecimal("total_revenue");
                    BigDecimal totalCost = resultSet.getBigDecimal("total_cost");

                    BigDecimal profit = totalRevenue.subtract(totalCost);
                    return new FinancialSummaryResponse(totalRevenue, totalCost, profit);
                }
            }
        }catch (SQLException exception) {
            throw new RuntimeException("Erro ao calcular o montante total do usuario");
        }
        return new FinancialSummaryResponse(BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);
    }

    private Project mapResultSet(ResultSet result) throws SQLException{
        Date sqlDeadline = result.getDate("deadline");
        Deadline domainDeadline = null;
        if(sqlDeadline != null)
            domainDeadline = new Deadline(sqlDeadline.toLocalDate());

        return new Project(
                result.getLong("id"),
                result.getLong("user_id"),
                result.getString("name"),
                result.getString("description"),
                ProjectStatus.valueOf(result.getString("status")),
                ProjectType.valueOf(result.getString("type")),
                result.getBigDecimal("value"),
                result.getBigDecimal("cost"),
                domainDeadline,
                result.getTimestamp("updated_at").toInstant()
        );
    }
}