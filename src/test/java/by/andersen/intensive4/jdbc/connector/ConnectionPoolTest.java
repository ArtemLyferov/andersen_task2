package by.andersen.intensive4.jdbc.connector;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ConnectionPoolTest {

    @Test
    public void getConnectionTest() throws Exception {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                "applicationContextTest.xml");
        ConnectionPool connectionPool = context.getBean("testConnectionPool", ConnectionPool.class);
        assertTrue(connectionPool.getConnection().isValid(1));
    }
}
