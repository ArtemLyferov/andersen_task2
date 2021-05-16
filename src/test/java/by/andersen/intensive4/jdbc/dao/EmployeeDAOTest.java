package by.andersen.intensive4.jdbc.dao;

import by.andersen.intensive4.entities.Employee;
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

public class EmployeeDAOTest {
    public static TeamDAO teamDAO;
    public static EmployeeDAO employeeDAO;
    public static Team lastTeamInList;
    public static Employee testEmployee;
    public static Employee lastEmployeeInList;
    public static int countAdd;

    @BeforeClass
    public static void initDAO() throws SQLException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(
                SpringConfigTest.class);
        teamDAO = context.getBean("teamDAO", TeamDAO.class);
        employeeDAO = context.getBean("employeeDAO", EmployeeDAO.class);

        Team testTeam = context.getBean("testTeam", Team.class);
        teamDAO.create(testTeam);
        List<Team> teams = teamDAO.findAll();
        lastTeamInList = teams.get(teams.size() - 1);

        testEmployee = context.getBean("testEmployee", Employee.class);
        testEmployee.setTeam(lastTeamInList);
        employeeDAO.create(testEmployee);
        List<Employee> employees = employeeDAO.findAll();
        lastEmployeeInList = employees.get(employees.size() - 1);
    }

    @AfterClass
    public static void clearDAO() {
        testEmployee = null;
        employeeDAO.delete(lastEmployeeInList.getId());
        teamDAO.delete(lastTeamInList.getId());
        lastEmployeeInList = null;
        lastTeamInList = null;
        employeeDAO = null;
        teamDAO = null;
    }

    @Test
    public void createEmployeeTest() {
        testEmployee.setSurname("Ivanov");
        int expected = 1;
        int actual = employeeDAO.create(testEmployee);
        countAdd++;
        assertEquals(expected, actual);
        employeeDAO.delete(lastEmployeeInList.getId() + countAdd);
    }

    @Test
    public void findAllEmployeesTest() {
        assertNotNull(employeeDAO.findAll());
    }

    @Test
    public void getEmployeeByIdTest() {
        Employee expected = lastEmployeeInList;
        Employee actual = employeeDAO.findById(expected.getId());
        assertEquals(expected, actual);
    }

    @Test
    public void updateEmployeeTest() {
        lastEmployeeInList.setSurname("Kotov");
        int expected = 1;
        int actual = employeeDAO.update(lastEmployeeInList);
        assertEquals(expected, actual);
    }

    @Test
    public void deleteEmployeeTest() {
        testEmployee.setSurname("Popov");
        employeeDAO.create(testEmployee);
        countAdd++;
        int expected = 1;
        int actual = employeeDAO.delete(lastEmployeeInList.getId() + countAdd);
        assertEquals(expected, actual);
    }
}
