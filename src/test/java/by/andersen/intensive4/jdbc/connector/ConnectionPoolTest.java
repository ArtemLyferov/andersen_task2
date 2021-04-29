package by.andersen.intensive4.jdbc.connector;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ConnectionPoolTest {

    @Test
    public void getConnectionTest() throws Exception {
        ConnectionPool connectionPool = ConnectionPool.create("jdbc:postgresql://localhost:5432/employee_control_system_db",
                "postgres", "postgres");
        assertTrue(connectionPool.getConnection().isValid(1));
    }
}
