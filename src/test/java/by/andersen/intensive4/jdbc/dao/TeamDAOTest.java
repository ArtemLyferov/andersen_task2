package by.andersen.intensive4.jdbc.dao;

import by.andersen.intensive4.entities.Team;
import by.andersen.intensive4.jdbc.connector.ConnectionPool;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.*;

public class TeamDAOTest {
    public static TeamDAO dao;

    @BeforeClass
    public static void initDAO() throws SQLException {
        ConnectionPool connectionPool = ConnectionPool.create(
                "jdbc:postgresql://localhost:5432/employee_control_system_db",
                "postgres", "postgres");
        dao = new TeamDAO(connectionPool.getConnection());
    }

    @AfterClass
    public static void clearDAO() {
        dao = null;
    }

    @Test
    public void createTeamTest() {
        Team team = new Team("Test team");
        int expected = 1;
        int actual = dao.create(team);
        assertEquals(expected, actual);
        List<Team> teams = dao.findAll();
        dao.delete(teams.size() - 1);
    }

    @Test
    public void findAllTeamsTest() {
        assertNotNull(dao.findAll());
    }

    @Test
    public void getTeamByIdTest() {
        Team team = new Team("Test team");
        dao.create(team);
        List<Team> teams = dao.findAll();
        Team expected = teams.get(teams.size() - 1);
        Team actual = dao.findEntityById(expected.getId());
        assertEquals(expected, actual);
        dao.delete(actual.getId());
    }

    @Test
    public void updateTeamTest() {
        Team team = new Team("Test team");
        dao.create(team);
        List<Team> teams = dao.findAll();
        team = teams.get(teams.size() - 1);
        team.setTeamName("Test team 2");
        int expected = 1;
        int actual = dao.update(team);
        assertEquals(expected, actual);
        dao.delete(team.getId());
    }

    @Test
    public void deleteTeamTest() {
        Team team = new Team("Test team");
        dao.create(team);
        List<Team> teams = dao.findAll();
        team = teams.get(teams.size() - 1);
        int expected = 1;
        int actual = dao.delete(team.getId());
        assertEquals(expected, actual);
    }
}
