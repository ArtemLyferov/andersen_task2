package by.andersen.intensive4.jdbc.dao;

import by.andersen.intensive4.entities.Employee;
import by.andersen.intensive4.entities.Team;
import by.andersen.intensive4.jdbc.connector.ConnectorDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class EmployeeDAO extends EntityDAO<Employee> {

    public static final String SQL_INSERT_EMPLOYEE = "INSERT INTO employees (surname, name, patronymic, dob, email, " +
            "skype, phone_number, employment_date, experience, developer_level, english_level, team_id) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    public static final String SQL_SELECT_ALL_EMPLOYEES = "SELECT e.id, e.surname, e.name, e.patronymic, e.dob, " +
            "e.email, e.skype, e.phone_number, e.employment_date, e.experience, e.developer_level, e.english_level, " +
            "e.team_id, t.team_name FROM employees AS e " +
            "JOIN teams AS t ON e.team_id = t.id";
    public static final String SQL_SELECT_EMPLOYEE_BY_ID = SQL_SELECT_ALL_EMPLOYEES + " WHERE e.id = ?";
    public static final String SQL_UPDATE_EMPLOYEE = "UPDATE employees SET surname = ?, name = ?, patronymic = ?, " +
            "dob = ?, email = ?, skype = ?, phone_number = ?, employment_date = ?, experience = ?, developer_level = ?, " +
            "english_level = ?, team_id = ? WHERE id = ?";
    public static final String SQL_DELETE_EMPLOYEE_BY_ID = "DELETE FROM employees WHERE id = ?";

    @Autowired
    public EmployeeDAO(ConnectorDB connectorDB) {
        super(connectorDB);
    }

    private static PreparedStatement setEmployeeToPreparedStatement(PreparedStatement preparedStatement,
                                                                    Employee employee) throws SQLException {
        preparedStatement.setString(1, employee.getSurname());
        preparedStatement.setString(2, employee.getName());
        preparedStatement.setString(3, employee.getPatronymic());
        preparedStatement.setDate(4, Date.valueOf(employee.getDOB()));
        preparedStatement.setString(5, employee.getEmail());
        preparedStatement.setString(6, employee.getSkype());
        preparedStatement.setString(7, employee.getPhoneNumber());
        preparedStatement.setDate(8, Date.valueOf(employee.getEmploymentDate()));
        preparedStatement.setInt(9, employee.getExperience());
        preparedStatement.setString(10, employee.getDeveloperLevel().name());
        preparedStatement.setString(11, employee.getEnglishLevel().name());
        preparedStatement.setInt(12, employee.getTeam().getId());
        return preparedStatement;
    }

    @Override
    public int create(Employee employee) {
        int result = 0;
        Connection connection = null;
        try {
            connection = getConnectorDB().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_EMPLOYEE);
            result = setEmployeeToPreparedStatement(preparedStatement, employee).executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            getConnectorDB().releaseConnection(connection);
        }
        return result;
    }

    protected static Employee initEmployee(ResultSet resultSet, Employee employee, String idColumnName) throws SQLException {
        employee.setId(resultSet.getInt(idColumnName));
        employee.setSurname(resultSet.getString("surname"));
        employee.setName(resultSet.getString("name"));
        employee.setPatronymic(resultSet.getString("patronymic"));
        employee.setDOB(resultSet.getDate("dob").toLocalDate());
        employee.setEmail(resultSet.getString("email"));
        employee.setSkype(resultSet.getString("skype"));
        employee.setPhoneNumber(resultSet.getString("phone_number"));
        employee.setEmploymentDate(resultSet.getDate("employment_date").toLocalDate());
        employee.setExperience(resultSet.getInt("experience"));
        employee.setDeveloperLevel(Employee.DeveloperLevel.valueOf(resultSet.getString("developer_level")));
        employee.setEnglishLevel(Employee.EnglishLevel.valueOf(resultSet.getString("english_level")));
        employee.setTeam(TeamDAO.initTeam(resultSet, new Team(), "team_id"));
        return employee;
    }

    @Override
    public List<Employee> findAll() {
        List<Employee> employees = null;
        Connection connection = null;
        try {
            connection = getConnectorDB().getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL_EMPLOYEES);
            employees = new ArrayList<>();
            while (resultSet.next()) {
                employees.add(initEmployee(resultSet, new Employee(), "id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            getConnectorDB().releaseConnection(connection);
        }
        return employees;
    }

    @Override
    public Employee findById(int id) {
        Employee employee = null;
        Connection connection = null;
        try {
            connection = getConnectorDB().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_EMPLOYEE_BY_ID);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                employee = initEmployee(resultSet, new Employee(), "id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            getConnectorDB().releaseConnection(connection);
        }
        return employee;
    }

    @Override
    public int update(Employee employee) {
        int result = 0;
        Connection connection = null;
        try {
            connection = getConnectorDB().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_EMPLOYEE);
            preparedStatement = setEmployeeToPreparedStatement(preparedStatement, employee);
            preparedStatement.setInt(13, employee.getId());
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
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_EMPLOYEE_BY_ID);
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
