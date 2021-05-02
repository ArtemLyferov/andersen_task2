package by.andersen.intensive4.jdbc.dao;

import by.andersen.intensive4.entities.Employee;
import by.andersen.intensive4.entities.Project;
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

public class ProjectDAOTest {
    public static TeamDAO teamDAO;
    public static EmployeeDAO employeeDAO;
    public static ProjectDAO projectDAO;
    public static Team lastTeamInList;
    public static Employee lastEmployeeInList;
    public static Project lastProjectInList;
    public static int countAdd;

    @BeforeClass
    public static void initDAO() throws SQLException {
        ConnectionPool connectionPool = ConnectionPool.create(
                "jdbc:postgresql://localhost:5432/employee_control_system_db",
                "postgres", "postgres");
        teamDAO = new TeamDAO(connectionPool.getConnection());
        employeeDAO = new EmployeeDAO(connectionPool.getConnection());
        projectDAO = new ProjectDAO(connectionPool.getConnection());

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

        Project project = new Project("Test project 1", "Test customer", 200,
                Project.Methodology.AGILE_MODEL, lastEmployeeInList, lastTeamInList);
        projectDAO.create(project);
        List<Project> projects = projectDAO.findAll();
        lastProjectInList = projects.get(projects.size() - 1);
    }

    @AfterClass
    public static void clearDAO() {
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
        Project project = new Project("Test project 2", "Test customer", 200,
                Project.Methodology.AGILE_MODEL, lastEmployeeInList, lastTeamInList);
        int expected = 1;
        int actual = projectDAO.create(project);
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
        Project actual = projectDAO.findEntityById(expected.getId());
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
        Project project = new Project("Test project 4", "Test customer", 200,
                Project.Methodology.AGILE_MODEL, lastEmployeeInList, lastTeamInList);
        projectDAO.create(project);
        countAdd++;
        int expected = 1;
        int actual = projectDAO.delete(lastProjectInList.getId() + countAdd);
        assertEquals(expected, actual);
    }
}
