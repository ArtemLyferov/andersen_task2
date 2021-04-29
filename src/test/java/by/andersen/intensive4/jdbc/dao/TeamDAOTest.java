package by.andersen.intensive4.jdbc.dao;

import by.andersen.intensive4.jdbc.connector.ConnectionPool;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.SQLException;

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
    public static void clearDAO(){
        dao = null;
    }

    @Test
    public void createTeamTest(){

    }

}
