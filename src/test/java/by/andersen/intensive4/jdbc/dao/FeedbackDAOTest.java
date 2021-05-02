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
    public static TeamDAO teamDAO;
    public static EmployeeDAO employeeDAO;
    public static FeedbackDAO feedbackDAO;
    public static Team lastTeamInList;
    public static Employee lastEmployeeInList;
    public static Feedback lastFeedbackInList;
    public static int countAdd;

    @BeforeClass
    public static void initDAO() throws SQLException {
        ConnectionPool connectionPool = ConnectionPool.create(
                "jdbc:postgresql://localhost:5432/employee_control_system_db",
                "postgres", "postgres");
        teamDAO = new TeamDAO(connectionPool.getConnection());
        employeeDAO = new EmployeeDAO(connectionPool.getConnection());
        feedbackDAO = new FeedbackDAO(connectionPool.getConnection());

        Team team = new Team("Test team");
        teamDAO.create(team);
        List<Team> teams = teamDAO.findAll();
        lastTeamInList = teams.get(teams.size() - 1);

        Employee employee = new Employee("Petrov", "Anton", "Semenovich",
                LocalDate.of(1990, 3, 12), "petrov@gmail.com", "live:petrov",
                "+375291112233", LocalDate.of(2018, 3, 2), 4,
                Employee.DeveloperLevel.J3, Employee.EnglishLevel.A2, lastTeamInList);
        employeeDAO.create(employee);
        List<Employee> employees = employeeDAO.findAll();
        lastEmployeeInList = employees.get(employees.size() - 1);

        Feedback feedback = new Feedback("Test feedback 1", LocalDate.of(2020, 4, 15),
                lastEmployeeInList);
        feedbackDAO.create(feedback);
        List<Feedback> feedbacks = feedbackDAO.findAll();
        lastFeedbackInList = feedbacks.get(feedbacks.size() - 1);
    }

    @AfterClass
    public static void clearDAO() {
        feedbackDAO.delete(lastFeedbackInList.getId());
        employeeDAO.delete(lastEmployeeInList.getId());
        teamDAO.delete(lastTeamInList.getId());
        lastFeedbackInList = null;
        lastEmployeeInList = null;
        lastTeamInList = null;
        feedbackDAO = null;
        employeeDAO = null;
        teamDAO = null;
    }

    @Test
    public void createFeedbackTest() {
        Feedback feedback = new Feedback("Test feedback 2", LocalDate.of(2020, 4, 15),
                lastEmployeeInList);
        int expected = 1;
        int actual = feedbackDAO.create(feedback);
        countAdd++;
        assertEquals(expected, actual);
        feedbackDAO.delete(lastFeedbackInList.getId() + countAdd);
    }

    @Test
    public void findAllFeedbacksTest() {
        assertNotNull(feedbackDAO.findAll());
    }

    @Test
    public void getFeedbackByIdTest() {
        Feedback expected = lastFeedbackInList;
        Feedback actual = feedbackDAO.findEntityById(expected.getId());
        assertEquals(expected, actual);
    }

    @Test
    public void updateFeedbackTest() {
        lastFeedbackInList.setDescription("Test feedback 3");
        int expected = 1;
        int actual = feedbackDAO.update(lastFeedbackInList);
        assertEquals(expected, actual);
    }

    @Test
    public void deleteFeedbackTest() {
        Feedback feedback = new Feedback("Test feedback 4", LocalDate.of(2020, 4, 15),
                lastEmployeeInList);
        feedbackDAO.create(feedback);
        countAdd++;
        int expected = 1;
        int actual = feedbackDAO.delete(lastFeedbackInList.getId() + countAdd);
        assertEquals(expected, actual);
    }
}
