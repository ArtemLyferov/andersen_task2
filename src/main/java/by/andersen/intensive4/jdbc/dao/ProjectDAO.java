package by.andersen.intensive4.jdbc.dao;

import by.andersen.intensive4.entities.Employee;
import by.andersen.intensive4.entities.Project;
import by.andersen.intensive4.entities.Team;
import by.andersen.intensive4.jdbc.connector.ConnectorDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProjectDAO extends EntityDAO<Project> {

    public static final String SQL_INSERT_PROJECT = "INSERT INTO projects (name_project, customer, duration, methodology, " +
            "project_manager_id, team_id) " +
            "VALUES (?, ?, ?, ?, ?, ?)";
    public static final String SQL_SELECT_ALL_PROJECTS = "SELECT p.id, p.name_project, p.customer, p.duration, " +
            "p.methodology, p.project_manager_id, e.surname, e.name, e.patronymic, e.dob, e.email, e.skype, " +
            "e.phone_number, e.employment_date, e.experience, e.developer_level, e.english_level, e.team_id, " +
            "t.team_name, p.team_id, t.team_name FROM projects AS p " +
            "JOIN employees AS e ON p.project_manager_id = e.id " +
            "JOIN teams AS t ON p.team_id = t.id";
    public static final String SQL_SELECT_PROJECT_BY_ID = SQL_SELECT_ALL_PROJECTS + " WHERE p.id = ?";
    public static final String SQL_UPDATE_PROJECT = "UPDATE projects SET name_project = ?, customer = ?, duration = ?, " +
            "methodology = ?, project_manager_id = ?, team_id = ? WHERE id = ?";
    public static final String SQL_DELETE_PROJECT_BY_ID = "DELETE FROM projects WHERE id = ?";

    public ProjectDAO(ConnectorDB connectorDB) {
        super(connectorDB);
    }

    private static PreparedStatement setProjectToPreparedStatement(PreparedStatement preparedStatement,
                                                                   Project project) throws SQLException {
        preparedStatement.setString(1, project.getNameProject());
        preparedStatement.setString(2, project.getCustomer());
        preparedStatement.setInt(3, project.getDuration());
        preparedStatement.setString(4, project.getMethodology().name());
        preparedStatement.setInt(5, project.getProjectManager().getId());
        preparedStatement.setInt(6, project.getTeam().getId());
        return preparedStatement;
    }

    @Override
    public int create(Project project) {
        int result = 0;
        Connection connection = null;
        try {
            connection = getConnectorDB().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_PROJECT);
            result = setProjectToPreparedStatement(preparedStatement, project).executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            getConnectorDB().releaseConnection(connection);
        }
        return result;
    }

    protected static Project initProject(ResultSet resultSet, Project project, String idColumnName) throws SQLException {
        project.setId(resultSet.getInt(idColumnName));
        project.setNameProject(resultSet.getString("name_project"));
        project.setCustomer(resultSet.getString("customer"));
        project.setDuration(resultSet.getInt("duration"));
        project.setMethodology(Project.Methodology.valueOf(resultSet.getString("methodology")));
        project.setProjectManager(EmployeeDAO.initEmployee(resultSet, new Employee(), "project_manager_id"));
        project.setTeam(TeamDAO.initTeam(resultSet, new Team(), "team_id"));
        return project;
    }

    @Override
    public List<Project> findAll() {
        List<Project> projects = null;
        Connection connection = null;
        try {
            connection = getConnectorDB().getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL_PROJECTS);
            projects = new ArrayList<>();
            while (resultSet.next()) {
                projects.add(initProject(resultSet, new Project(), "id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            getConnectorDB().releaseConnection(connection);
        }
        return projects;
    }

    @Override
    public Project findById(int id) {
        Project project = null;
        Connection connection = null;
        try {
            connection = getConnectorDB().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_PROJECT_BY_ID);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                project = initProject(resultSet, new Project(), "id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            getConnectorDB().releaseConnection(connection);
        }
        return project;
    }

    @Override
    public int update(Project project) {
        int result = 0;
        Connection connection = null;
        try {
            connection = getConnectorDB().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_PROJECT);
            preparedStatement = setProjectToPreparedStatement(preparedStatement, project);
            preparedStatement.setInt(7, project.getId());
            result = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            getConnectorDB().releaseConnection(connection);
        }
        return result;
    }

    @Override
    public int delete(int id) {
        int result = 0;
        Connection connection = null;
        try {
            connection = getConnectorDB().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_PROJECT_BY_ID);
            preparedStatement.setInt(1, id);
            result = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            getConnectorDB().releaseConnection(connection);
        }
        return result;
    }
}
