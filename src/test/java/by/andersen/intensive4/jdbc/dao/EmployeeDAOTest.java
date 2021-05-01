package by.andersen.intensive4.jdbc.dao;

import by.andersen.intensive4.entities.Employee;
import by.andersen.intensive4.entities.Team;
import by.andersen.intensive4.jdbc.connector.ConnectionPool;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class EmployeeDAOTest {
    public static EmployeeDAO employeeDAO;
    public static TeamDAO teamDAO;

    @BeforeClass
    public static void initDAO() throws SQLException {
        ConnectionPool connectionPool = ConnectionPool.create(
                "jdbc:postgresql://localhost:5432/employee_control_system_db",
                "postgres", "postgres");
        employeeDAO = new EmployeeDAO(connectionPool.getConnection());
        teamDAO = new TeamDAO(connectionPool.getConnection());
    }

    @AfterClass
    public static void clearDAO() {
        employeeDAO = null;
        teamDAO = null;
    }

    @Test
    public void createEmployeeTest() {
        Team team = new Team("Test team");
        teamDAO.create(team);
        List<Team> teams = teamDAO.findAll();
        team = teams.get(teams.size() - 1);
        Employee employee = new Employee("Petrov", "Anton", "Semenovich",
                LocalDate.of(1990,3,12),"petrov@gmail.com", "live:petrov",
                "+375291112233", LocalDate.of(2018,3,2), 4,
                Employee.DeveloperLevel.J3, Employee.EnglishLevel.A2, team);
        int expected = 1;
        int actual = employeeDAO.create(employee);
        assertEquals(expected, actual);
        List<Employee> employees = employeeDAO.findAll();
        employee = employees.get(employees.size() - 1);
        employeeDAO.delete(employee.getId());
        teamDAO.delete(team.getId());
    }

    @Test
    public void findAllEmployeesTest() {
        assertNotNull(employeeDAO.findAll());
    }

    @Test
    public void getEmployeeByIdTest() {
        Team team = new Team("Test team");
        teamDAO.create(team);
        List<Team> teams = teamDAO.findAll();
        team = teams.get(teams.size() - 1);
        Employee employee = new Employee("Petrov", "Anton", "Semenovich",
                LocalDate.of(1990,3,12),"petrov@gmail.com", "live:petrov",
                "+375291112233", LocalDate.of(2018,3,2), 4,
                Employee.DeveloperLevel.J3, Employee.EnglishLevel.A2, team);
        employeeDAO.create(employee);
        List<Employee> employees = employeeDAO.findAll();
        Employee expected = employees.get(employees.size() - 1);
        Employee actual = employeeDAO.findEntityById(expected.getId());
        assertEquals(expected, actual);
        employeeDAO.delete(expected.getId());
        teamDAO.delete(team.getId());
    }

    @Test
    public void updateEmployeeTest() {
        Team team = new Team("Test team");
        teamDAO.create(team);
        List<Team> teams = teamDAO.findAll();
        team = teams.get(teams.size() - 1);
        Employee employee = new Employee("Petrov", "Anton", "Semenovich",
                LocalDate.of(1990,3,12),"petrov@gmail.com", "live:petrov",
                "+375291112233", LocalDate.of(2018,3,2), 4,
                Employee.DeveloperLevel.J3, Employee.EnglishLevel.A2, team);
        employeeDAO.create(employee);
        List<Employee> employees = employeeDAO.findAll();
        employee = employees.get(employees.size() - 1);
        employee.setSurname("Kotov");
        int expected = 1;
        int actual = employeeDAO.update(employee);
        assertEquals(expected, actual);
        employeeDAO.delete(employee.getId());
        teamDAO.delete(team.getId());
    }

    @Test
    public void deleteEmployeeTest() {
        Team team = new Team("Test team");
        teamDAO.create(team);
        List<Team> teams = teamDAO.findAll();
        team = teams.get(teams.size() - 1);
        Employee employee = new Employee("Petrov", "Anton", "Semenovich",
                LocalDate.of(1990,3,12),"petrov@gmail.com", "live:petrov",
                "+375291112233", LocalDate.of(2018,3,2), 4,
                Employee.DeveloperLevel.J3, Employee.EnglishLevel.A2, team);
        employeeDAO.create(employee);
        List<Employee> employees = employeeDAO.findAll();
        employee = employees.get(employees.size() - 1);
        int expected = 1;
        int actual = employeeDAO.delete(employee.getId());
        assertEquals(expected, actual);
        teamDAO.delete(team.getId());
    }
}
