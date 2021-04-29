package by.andersen.intensive4.jdbc.dao;

import by.andersen.intensive4.entities.Employee;
import by.andersen.intensive4.entities.Project;
import by.andersen.intensive4.entities.Team;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProjectDAO extends AbstractDAO<Project> {

    public static final String SQL_INSERT_PROJECT = "INSERT INTO projects (name_project, customer, duration, methodology, " +
            "project_manager_id, team_id) " +
            "VALUES (?, ?, ?, ?, ?, ?)";
    public static final String SQL_SELECT_ALL_PROJECTS = "SELECT p.id, p.name_project, p.customer, p.duration, " +
            "p.methodology, p.project_manager_id, e.surname, e.name, e.patronymic, e.dob, e.email, e.skype, " +
            "e.phone_number, e.employment_date, e.experience, e.developer_level, e.english_level, e.team_id, " +
            "t.name_team, p.tem_id, t.name_team FROM projects AS p " +
            "JOIN employees AS e ON p.project_manager_id = e.id " +
            "JOIN teams AS t ON e.team_id = t.id " +
            "JOIN teams ON p.team_id = t.id";
    public static final String SQL_SELECT_PROJECT_BY_ID = SQL_SELECT_ALL_PROJECTS + " WHERE p.id = ?";
    public static final String SQL_UPDATE_PROJECT = "UPDATE projects SET name_project = ?, customer = ?, duration = ?, " +
            "methodology = ?, project_manager_id = ?, team_id = ? WHERE id = ?";
    public static final String SQL_DELETE_PROJECT_BY_ID = "DELETE FROM projects WHERE id = ?";

    public ProjectDAO(Connection connection) {
        super(connection);
    }

    @Override
    public void create(Project project) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_PROJECT);
            preparedStatement.setString(1, project.getNameProject());
            preparedStatement.setString(2, project.getCustomer());
            preparedStatement.setInt(3, project.getDuration());
            preparedStatement.setString(4, project.getMethodology().name());
            preparedStatement.setInt(5, project.getProjectManager().getId());
            preparedStatement.setInt(6, project.getTeam().getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public List<Project> findAll() {
        List<Project> projects = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL_PROJECTS);
            while (resultSet.next()) {
                Project project = new Project();
                project.setId(resultSet.getInt("id"));
                project.setNameProject(resultSet.getString("name_project"));
                project.setCustomer(resultSet.getString("customer"));
                project.setDuration(resultSet.getInt("duration"));
                project.setMethodology(Project.Methodology.valueOf(resultSet.getString("methodology")));
                Employee employee = new Employee();
                project.setProjectManager(EmployeeDAO.initEmployee(resultSet, employee));
                Team team = new Team();
                team.setId(resultSet.getInt("id"));
                team.setNameTeam(resultSet.getString("team_name"));
                project.setTeam(team);
                projects.add(project);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return projects;
    }

    @Override
    public Project findEntityById(int id) {
        Project project = new Project();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_PROJECT_BY_ID);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                project.setId(resultSet.getInt("id"));
                project.setNameProject(resultSet.getString("name_project"));
                project.setCustomer(resultSet.getString("customer"));
                project.setDuration(resultSet.getInt("duration"));
                project.setMethodology(Project.Methodology.valueOf(resultSet.getString("methodology")));
                Employee employee = new Employee();
                project.setProjectManager(EmployeeDAO.initEmployee(resultSet, employee));
                Team team = new Team();
                team.setId(resultSet.getInt("id"));
                team.setNameTeam(resultSet.getString("team_name"));
                project.setTeam(team);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return project;
    }

    @Override
    public void update(Project project) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_PROJECT);
            preparedStatement.setString(1, project.getNameProject());
            preparedStatement.setString(2, project.getCustomer());
            preparedStatement.setInt(3, project.getDuration());
            preparedStatement.setString(4, project.getMethodology().name());
            preparedStatement.setInt(5, project.getProjectManager().getId());
            preparedStatement.setInt(6, project.getTeam().getId());
            preparedStatement.setInt(7, project.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_PROJECT_BY_ID);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
