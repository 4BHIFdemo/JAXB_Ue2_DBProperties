package at.htlkaindorf.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author dorjab18
 * Klasse: 4BHIF
 */
public class DBDatabase {
    private final String DB_DRIVER;
    private final String DB_URL;
    private final String DB_USER;
    private final String DB_PASSWORD;

    private Connection connection;
    private DBCachedConnection cachedConnection;

    public DBDatabase(DBProperties properties) throws ClassNotFoundException, SQLException {
        this.DB_DRIVER = properties.getDriverClassName();
        this.DB_URL = properties.getUrl();
        this.DB_USER = properties.getUsername();
        this.DB_PASSWORD = properties.getPassword();

        Class.forName(DB_DRIVER);
        connect();
    }

    public void connect() throws SQLException {
        connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        cachedConnection = new DBCachedConnection(connection);
    }

    public void disconnect() throws SQLException {
        if(connection != null) {
            connection.close();

            connection = null;
        }
    }

    public boolean isConnected() {
        return connection != null;
    }

    public Statement getStatement() throws SQLException {
        if(connection == null || cachedConnection == null)
            throw new IllegalStateException("Not connected to the database");

        return cachedConnection.getStatement();
    }

    public void releaseStatement(Statement statement) {
        if(connection == null || cachedConnection == null)
            throw new IllegalStateException("Not connected to the database");

        cachedConnection.releaseStatement(statement);
    }

    public Connection getConnection() {
        return connection;
    }
}
