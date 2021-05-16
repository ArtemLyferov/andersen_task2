package by.andersen.intensive4.jdbc.dao;

import by.andersen.intensive4.entities.Employee;
import by.andersen.intensive4.entities.Project;
import by.andersen.intensive4.entities.Team;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ProjectDAOTest {
    public static TeamDAO teamDAO;
    public static EmployeeDAO employeeDAO;
    public static ProjectDAO projectDAO;
    public static Team lastTeamInList;
    public static Employee lastEmployeeInList;
    public static Project testProject;
    public static Project lastProjectInList;
    public static int countAdd;

    @BeforeClass
    public static void initDAO() throws SQLException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                "applicationContext.xml");
        teamDAO = context.getBean("teamDAO", TeamDAO.class);
        employeeDAO = context.getBean("employeeDAO", EmployeeDAO.class);
        projectDAO = context.getBean("projectDAO", ProjectDAO.class);

        Team testTeam = context.getBean("testTeam", Team.class);
        teamDAO.create(testTeam);
        List<Team> teams = teamDAO.findAll();
        lastTeamInList = teams.get(teams.size() - 1);

        Employee testEmployee = context.getBean("testEmployee", Employee.class);
        testEmployee.setTeam(lastTeamInList);
        employeeDAO.create(testEmployee);
        List<Employee> employees = employeeDAO.findAll();
        lastEmployeeInList = employees.get(employees.size() - 1);

        testProject = context.getBean("testProject", Project.class);
        testProject.setProjectManager(lastEmployeeInList);
        testProject.setTeam(lastTeamInList);
        projectDAO.create(testProject);
        List<Project> projects = projectDAO.findAll();
        lastProjectInList = projects.get(projects.size() - 1);
    }

    @AfterClass
    public static void clearDAO() {
        testProject = null;
        projectDAO.delete(lastProjectInList.getId());
        employeeDAO.delete(lastEmployeeInList.getId());
        teamDAO.delete(lastTeamInList.getId());
        lastProjectInList = null;
        lastEmployeeInList = null;
        lastTeamInList = null;
        projectDAO = null;
        employeeDAO = null;
        teamDAO = null;
    }

    @Test
    public void createProjectTest() {
        testProject.setNameProject("Test project 2");
        int expected = 1;
        int actual = projectDAO.create(testProject);
        countAdd++;
        assertEquals(expected, actual);
        projectDAO.delete(lastProjectInList.getId() + countAdd);
    }

    @Test
    public void findAllProjectsTest() {
        assertNotNull(projectDAO.findAll());
    }

    @Test
    public void getProjectByIdTest() {
        Project expected = lastProjectInList;
        Project actual = projectDAO.findById(expected.getId());
        assertEquals(expected, actual);
    }

    @Test
    public void updateProjectTest() {
        lastProjectInList.setNameProject("Test project 3");
        int expected = 1;
        int actual = projectDAO.update(lastProjectInList);
        assertEquals(expected, actual);
    }

    @Test
    public void deleteFeedbackTest() {
        testProject.setNameProject("Test project 4");
        projectDAO.create(testProject);
        countAdd++;
        int expected = 1;
        int actual = projectDAO.delete(lastProjectInList.getId() + countAdd);
        assertEquals(expected, actual);
    }
}
