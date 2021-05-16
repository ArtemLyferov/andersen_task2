package by.andersen.intensive4.jdbc.dao;

import by.andersen.intensive4.entities.Team;
import by.andersen.intensive4.jdbc.connector.ConnectorDB;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.*;

public class TeamDAOTest {
    public static TeamDAO teamDAO;
    public static Team lastTeamInList;
    public static int countAdd;

    @BeforeClass
    public static void initDAO() throws SQLException {
        ConnectorDB connectorDB = ConnectorDB.getInstance();
        teamDAO = new TeamDAO(connectorDB);
        Team team = new Team("Test team 1");
        teamDAO.create(team);
        List<Team> teams = teamDAO.findAll();
        lastTeamInList = teams.get(teams.size() - 1);
    }

    @AfterClass
    public static void clearDAO() {
        teamDAO.delete(lastTeamInList.getId());
        lastTeamInList = null;
        teamDAO = null;
    }

    @Test
    public void createTeamTest() {
        int expected = 1;
        int actual = teamDAO.create(new Team("Team test 2"));
        countAdd++;
        assertEquals(expected, actual);
        teamDAO.delete(lastTeamInList.getId() + countAdd);
    }

    @Test
    public void findAllTeamsTest() {
        assertNotNull(teamDAO.findAll());
    }

    @Test
    public void getTeamByIdTest() {
        Team expected = lastTeamInList;
        Team actual = teamDAO.findById(expected.getId());
        assertEquals(expected, actual);
    }

    @Test
    public void updateTeamTest() {
        lastTeamInList.setTeamName("Test team 3");
        int expected = 1;
        int actual = teamDAO.update(lastTeamInList);
        assertEquals(expected, actual);
    }

    @Test
    public void deleteTeamTest() {
        teamDAO.create(new Team("Test tem 4"));
        countAdd++;
        int expected = 1;
        int actual = teamDAO.delete(lastTeamInList.getId() + countAdd);
        assertEquals(expected, actual);
    }
}
