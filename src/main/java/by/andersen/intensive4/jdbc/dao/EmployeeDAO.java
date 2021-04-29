package by.andersen.intensive4.jdbc.dao;

import by.andersen.intensive4.entities.Employee;
import by.andersen.intensive4.entities.Team;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAO extends AbstractDAO<Employee> {

    public static final String SQL_INSERT_EMPLOYEE = "INSERT INTO employees (surname, name, patronymic, dob, email, " +
            "skype, phone_number, employment_date, experience, developer_level, english_level, team_id) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    public static final String SQL_SELECT_ALL_EMPLOYEES = "SELECT e.id, e.surname, e.name, e.patronymic, e.dob, " +
            "e.email, e.skype, e.phone_number, e.employment_date, e.experience, e.developer_level, e.english_level, " +
            "e.team_id, t.name_team FROM employees AS e " +
            "JOIN teams AS t ON e.team_id = t.id";
    public static final String SQL_SELECT_EMPLOYEE_BY_ID = SQL_SELECT_ALL_EMPLOYEES + " WHERE e.id = ?";
    public static final String SQL_UPDATE_EMPLOYEE = "UPDATE employees SET surname = ?, name = ?, patronymic = ?, " +
            "dob = ?, email = ?, skype = ?, phone_number = ?, employment_date = ?, experience = ?, developer_level = ?, " +
            "english_level = ?, team_id = ? WHERE id = ?";
    public static final String SQL_DELETE_EMPLOYEE_BY_ID = "DELETE FROM employees WHERE id = ?";

    public EmployeeDAO(Connection connection) {
        super(connection);
    }

    @Override
    public void create(Employee employee) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_EMPLOYEE);
            preparedStatement = setEmployeeToPreparedStatement(preparedStatement, employee);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Employee> findAll() {
        List<Employee> employees = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL_EMPLOYEES);
            while (resultSet.next()) {
                Employee employee = new Employee();
                employees.add(initEmployee(resultSet, employee));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employees;
    }

    @Override
    public Employee findEntityById(int id) {
        Employee employee = new Employee();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_EMPLOYEE_BY_ID);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                employee = initEmployee(resultSet, employee);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employee;
    }

    public static Employee initEmployee(ResultSet resultSet, Employee employee) throws SQLException {
        employee.setId(resultSet.getInt("id"));
        employee.setSurname(resultSet.getString("surname"));
        employee.setName(resultSet.getString("name"));
        employee.setPatronymic(resultSet.getString("patronymic"));
        employee.setDOB(resultSet.getDate("dob"));
        employee.setEmail(resultSet.getString("email"));
        employee.setSkype(resultSet.getString("skype"));
        employee.setPhoneNumber(resultSet.getString("phone_number"));
        employee.setEmploymentDate(resultSet.getDate("employment_date"));
        employee.setExperience(resultSet.getInt("experience"));
        employee.setDeveloperLevel(Employee.DeveloperLevel.valueOf(resultSet.getString("developer_level")));
        employee.setEnglishLevel(Employee.EnglishLevel.valueOf(resultSet.getString("english_level")));
        Team team = new Team();
        team.setId(resultSet.getInt("team_id"));
        team.setNameTeam(resultSet.getString("name_team"));
        employee.setTeam(team);
        return employee;
    }

    @Override
    public void update(Employee employee) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_EMPLOYEE);
            preparedStatement = setEmployeeToPreparedStatement(preparedStatement, employee);
            preparedStatement.setInt(13, employee.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private static PreparedStatement setEmployeeToPreparedStatement(PreparedStatement preparedStatement,
                                                                    Employee employee) throws SQLException {
        preparedStatement.setString(1, employee.getSurname());
        preparedStatement.setString(2, employee.getName());
        preparedStatement.setString(3, employee.getPatronymic());
        preparedStatement.setDate(4, new Date(employee.getDOB().getTime()));
        preparedStatement.setString(5, employee.getEmail());
        preparedStatement.setString(6, employee.getSkype());
        preparedStatement.setString(7, employee.getPhoneNumber());
        preparedStatement.setDate(8, new Date(employee.getEmploymentDate().getTime()));
        preparedStatement.setInt(9, employee.getExperience());
        preparedStatement.setString(10, employee.getDeveloperLevel().name());
        preparedStatement.setString(11, employee.getEnglishLevel().name());
        preparedStatement.setInt(12, employee.getTeam().getId());
        return preparedStatement;
    }

    @Override
    public void delete(int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_EMPLOYEE_BY_ID);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
