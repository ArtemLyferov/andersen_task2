package by.andersen.intensive4.jdbc.connector;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ConnectorDB {
    private static ConnectorDB connectorDB;
    private ConnectionPool connectionPool;

    private ConnectorDB() throws SQLException {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("database");
        String driver = resourceBundle.getString("db.driver");
        String url = resourceBundle.getString("db.url");
        String user = resourceBundle.getString("db.user");
        String password = resourceBundle.getString("db.password");
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        connectionPool = ConnectionPool.create(url, user, password);
    }

    public static ConnectorDB getInstance() throws SQLException {
        if (connectorDB == null) {
            connectorDB = new ConnectorDB();
        }
        return connectorDB;
    }

    public Connection getConnection() throws SQLException {
        return connectionPool.getConnection();
    }

    public boolean releaseConnection(Connection connection) {
        return connectionPool.releaseConnection(connection);
    }
}
