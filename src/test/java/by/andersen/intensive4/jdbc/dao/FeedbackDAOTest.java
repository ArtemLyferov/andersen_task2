package by.andersen.intensive4.jdbc.dao;

import by.andersen.intensive4.entities.Employee;
import by.andersen.intensive4.entities.Feedback;
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

public class FeedbackDAOTest {
    public static FeedbackDAO feedbackDAO;
    public static EmployeeDAO employeeDAO;
    public static TeamDAO teamDAO;

    @BeforeClass
    public static void initDAO() throws SQLException {
        ConnectionPool connectionPool = ConnectionPool.create(
                "jdbc:postgresql://localhost:5432/employee_control_system_db",
                "postgres", "postgres");
        feedbackDAO = new FeedbackDAO(connectionPool.getConnection());
        employeeDAO = new EmployeeDAO(connectionPool.getConnection());
        teamDAO = new TeamDAO(connectionPool.getConnection());
    }

    @AfterClass
    public static void clearDAO() {
        feedbackDAO = null;
        employeeDAO = null;
        teamDAO = null;
    }

    @Test
    public void createFeedbackTest() {
        Team team = new Team("Test team");
        teamDAO.create(team);
        List<Team> teams = teamDAO.findAll();
        team = teams.get(teams.size() - 1);
        Employee employee = new Employee("Petrov", "Anton", "Semenovich",
                LocalDate.of(1990, 3, 12), "petrov@gmail.com", "live:petrov",
                "+375291112233", LocalDate.of(2018, 3, 2), 4,
                Employee.DeveloperLevel.J3, Employee.EnglishLevel.A2, team);
        employeeDAO.create(employee);
        List<Employee> employees = employeeDAO.findAll();
        employee = employees.get(employees.size() - 1);
        Feedback feedback = new Feedback("Test feedback", LocalDate.of(2020, 4, 15),
                employee);
        int expected = 1;
        int actual = feedbackDAO.create(feedback);
        assertEquals(expected, actual);
        List<Feedback> feedbacks = feedbackDAO.findAll();
        feedback = feedbacks.get(feedbacks.size() - 1);
        feedbackDAO.delete(feedback.getId());
        employeeDAO.delete(employee.getId());
        teamDAO.delete(team.getId());
    }

    @Test
    public void findAllFeedbacksTest() {
        assertNotNull(feedbackDAO.findAll());
    }

    @Test
    public void getFeedbackByIdTest() {
        Team team = new Team("Test team");
        teamDAO.create(team);
        List<Team> teams = teamDAO.findAll();
        team = teams.get(teams.size() - 1);
        Employee employee = new Employee("Petrov", "Anton", "Semenovich",
                LocalDate.of(1990, 3, 12), "petrov@gmail.com", "live:petrov",
                "+375291112233", LocalDate.of(2018, 3, 2), 4,
                Employee.DeveloperLevel.J3, Employee.EnglishLevel.A2, team);
        employeeDAO.create(employee);
        List<Employee> employees = employeeDAO.findAll();
        employee = employees.get(employees.size() - 1);
        Feedback feedback = new Feedback("Test feedback", LocalDate.of(2020, 4, 15),
                employee);
        feedbackDAO.create(feedback);
        List<Feedback> feedbacks = feedbackDAO.findAll();
        Feedback expected = feedbacks.get(feedbacks.size() - 1);
        Feedback actual = feedbackDAO.findEntityById(expected.getId());
        assertEquals(expected, actual);
        feedbackDAO.delete(expected.getId());
        employeeDAO.delete(employee.getId());
        teamDAO.delete(team.getId());
    }

    @Test
    public void updateFeedbackTest() {
        Team team = new Team("Test team");
        teamDAO.create(team);
        List<Team> teams = teamDAO.findAll();
        team = teams.get(teams.size() - 1);
        Employee employee = new Employee("Petrov", "Anton", "Semenovich",
                LocalDate.of(1990, 3, 12), "petrov@gmail.com", "live:petrov",
                "+375291112233", LocalDate.of(2018, 3, 2), 4,
                Employee.DeveloperLevel.J3, Employee.EnglishLevel.A2, team);
        employeeDAO.create(employee);
        List<Employee> employees = employeeDAO.findAll();
        employee = employees.get(employees.size() - 1);
        Feedback feedback = new Feedback("Test feedback", LocalDate.of(2020, 4, 15),
                employee);
        feedbackDAO.create(feedback);
        List<Feedback> feedbacks = feedbackDAO.findAll();
        feedback = feedbacks.get(feedbacks.size() - 1);
        feedback.setDescription("Test feedback 2");
        int expected = 1;
        int actual = feedbackDAO.update(feedback);
        assertEquals(expected, actual);
        feedbackDAO.delete(feedback.getId());
        employeeDAO.delete(employee.getId());
        teamDAO.delete(team.getId());
    }

    @Test
    public void deleteFeedbackTest() {
        Team team = new Team("Test team");
        teamDAO.create(team);
        List<Team> teams = teamDAO.findAll();
        team = teams.get(teams.size() - 1);
        Employee employee = new Employee("Petrov", "Anton", "Semenovich",
                LocalDate.of(1990, 3, 12), "petrov@gmail.com", "live:petrov",
                "+375291112233", LocalDate.of(2018, 3, 2), 4,
                Employee.DeveloperLevel.J3, Employee.EnglishLevel.A2, team);
        employeeDAO.create(employee);
        List<Employee> employees = employeeDAO.findAll();
        employee = employees.get(employees.size() - 1);
        Feedback feedback = new Feedback("Test feedback", LocalDate.of(2020, 4, 15),
                employee);
        feedbackDAO.create(feedback);
        List<Feedback> feedbacks = feedbackDAO.findAll();
        feedback = feedbacks.get(feedbacks.size() - 1);
        int expected = 1;
        int actual = feedbackDAO.delete(feedback.getId());
        assertEquals(expected, actual);
        employeeDAO.delete(employee.getId());
        teamDAO.delete(team.getId());
    }
}
