package by.andersen.intensive4.jdbc.dao;

import by.andersen.intensive4.entities.Employee;
import by.andersen.intensive4.entities.Feedback;
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
    public static ProjectDAO projectDAO;
    public static EmployeeDAO employeeDAO;
    public static TeamDAO teamDAO;

    @BeforeClass
    public static void initDAO() throws SQLException {
        ConnectionPool connectionPool = ConnectionPool.create(
                "jdbc:postgresql://localhost:5432/employee_control_system_db",
                "postgres", "postgres");
        projectDAO = new ProjectDAO(connectionPool.getConnection());
        employeeDAO = new EmployeeDAO(connectionPool.getConnection());
        teamDAO = new TeamDAO(connectionPool.getConnection());
    }

    @AfterClass
    public static void clearDAO() {
        projectDAO = null;
        employeeDAO = null;
        teamDAO = null;
    }

    @Test
    public void createProjectTest() {
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
        Project project = new Project("Test project", "Test customer", 200,
                Project.Methodology.AGILE_MODEL, employee, team);
        int expected = 1;
        int actual = projectDAO.create(project);
        assertEquals(expected, actual);
        List<Project> projects = projectDAO.findAll();
        project = projects.get(projects.size() - 1);
        projectDAO.delete(project.getId());
        employeeDAO.delete(employee.getId());
        teamDAO.delete(team.getId());
    }

    @Test
    public void findAllProjectsTest() {
        assertNotNull(projectDAO.findAll());
    }

    @Test
    public void getProjectByIdTest() {
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
        Project project = new Project("Test project", "Test customer", 200,
                Project.Methodology.AGILE_MODEL, employee, team);
        projectDAO.create(project);
        List<Project> projects = projectDAO.findAll();
        Project expected = projects.get(projects.size() - 1);
        Project actual = projectDAO.findEntityById(expected.getId());
        assertEquals(expected, actual);
        projectDAO.delete(expected.getId());
        employeeDAO.delete(employee.getId());
        teamDAO.delete(team.getId());
    }

    @Test
    public void updateProjectTest() {
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
        Project project = new Project("Test project", "Test customer", 200,
                Project.Methodology.AGILE_MODEL, employee, team);
        projectDAO.create(project);
        List<Project> projects = projectDAO.findAll();
        project = projects.get(projects.size() - 1);
        project.setNameProject("Test project 2");
        int expected = 1;
        int actual = projectDAO.update(project);
        assertEquals(expected, actual);
        projectDAO.delete(project.getId());
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
        Project project = new Project("Test project", "Test customer", 200,
                Project.Methodology.AGILE_MODEL, employee, team);
        projectDAO.create(project);
        List<Project> projects = projectDAO.findAll();
        project = projects.get(projects.size() - 1);
        project = projects.get(projects.size() - 1);
        int expected = 1;
        int actual = projectDAO.delete(project.getId());
        assertEquals(expected, actual);
        employeeDAO.delete(employee.getId());
        teamDAO.delete(team.getId());
    }
}
