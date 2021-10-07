package at.htlkaindorf.db;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.Queue;

/**
 * @author dorjab18
 * Klasse: 4BHIF
 */
public class DBCachedConnection {
    private Queue<Statement> statementQueue = new LinkedList<>();
    private Connection connection;

    public DBCachedConnection(Connection connection) {
        this.connection = connection;
    }

    public Statement getStatement() throws SQLException {
        if(connection == null)
            throw new IllegalStateException("Not connected to the database");

        if(statementQueue.size() > 0)
            return statementQueue.poll();

        return connection.createStatement();
    }

    public void releaseStatement(Statement statement) {
        statementQueue.offer(statement);
    }
}
