package by.andersen.intensive4.jdbc.dao;

import by.andersen.intensive4.entities.Team;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.*;

public class TeamDAOTest {
    public static TeamDAO teamDAO;
    public static Team testTeam;
    public static Team lastTeamInList;
    public static int countAdd;

    @BeforeClass
    public static void initDAO() throws SQLException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                "applicationContext.xml");
        teamDAO = context.getBean("teamDAO", TeamDAO.class);
        testTeam = context.getBean("testTeam", Team.class);
        teamDAO.create(testTeam);
        List<Team> teams = teamDAO.findAll();
        lastTeamInList = teams.get(teams.size() - 1);
    }

    @AfterClass
    public static void clearDAO() {
        testTeam = null;
        teamDAO.delete(lastTeamInList.getId());
        lastTeamInList = null;
        teamDAO = null;
    }

    @Test
    public void createTeamTest() {
        testTeam.setTeamName("Team test 2");
        int expected = 1;
        int actual = teamDAO.create(testTeam);
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
        testTeam.setTeamName("Test team 4");
        teamDAO.create(testTeam);
        countAdd++;
        int expected = 1;
        int actual = teamDAO.delete(lastTeamInList.getId() + countAdd);
        assertEquals(expected, actual);
    }
}
