package by.andersen.intensive4.jdbc.dao;

import by.andersen.intensive4.entities.Employee;
import by.andersen.intensive4.entities.Feedback;
import by.andersen.intensive4.entities.Team;
import by.andersen.intensive4.jdbc.SpringConfigTest;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class FeedbackDAOTest {
    public static TeamDAO teamDAO;
    public static EmployeeDAO employeeDAO;
    public static FeedbackDAO feedbackDAO;
    public static Team lastTeamInList;
    public static Employee lastEmployeeInList;
    public static Feedback testFeedback;
    public static Feedback lastFeedbackInList;
    public static int countAdd;

    @BeforeClass
    public static void initDAO() throws SQLException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(
                SpringConfigTest.class);
        teamDAO = context.getBean("teamDAO", TeamDAO.class);
        employeeDAO = context.getBean("employeeDAO", EmployeeDAO.class);
        feedbackDAO = context.getBean("feedbackDAO", FeedbackDAO.class);

        Team testTeam = context.getBean("testTeam", Team.class);
        teamDAO.create(testTeam);
        List<Team> teams = teamDAO.findAll();
        lastTeamInList = teams.get(teams.size() - 1);

        Employee testEmployee = context.getBean("testEmployee", Employee.class);
        testEmployee.setTeam(lastTeamInList);
        employeeDAO.create(testEmployee);
        List<Employee> employees = employeeDAO.findAll();
        lastEmployeeInList = employees.get(employees.size() - 1);

        testFeedback = context.getBean("testFeedback", Feedback.class);
        testFeedback.setEmployee(lastEmployeeInList);
        feedbackDAO.create(testFeedback);
        List<Feedback> feedbacks = feedbackDAO.findAll();
        lastFeedbackInList = feedbacks.get(feedbacks.size() - 1);
    }

    @AfterClass
    public static void clearDAO() {
        testFeedback = null;
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
        testFeedback.setDescription("Test feedback 2");
        int expected = 1;
        int actual = feedbackDAO.create(testFeedback);
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
        Feedback actual = feedbackDAO.findById(expected.getId());
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
        testFeedback.setDescription("Test feedback 4");
        feedbackDAO.create(testFeedback);
        countAdd++;
        int expected = 1;
        int actual = feedbackDAO.delete(lastFeedbackInList.getId() + countAdd);
        assertEquals(expected, actual);
    }
}
