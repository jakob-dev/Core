package de.einfachjaakob.core.api.database.queries;

import com.sun.rowset.CachedRowSetImpl;
import de.einfachjaakob.core.api.database.MySQL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Query {

    private MySQL mysql;
    private Connection connection;
    private PreparedStatement statement;

    public Query(MySQL mysql, String sql) throws SQLException {
        this.mysql = mysql;
        connection = mysql.getConnectionManager().getConnection();
        statement = connection.prepareStatement(sql);

    }

    public void setParameter(int index, Object value) throws SQLException {
        statement.setObject(index, value);
    }

    public void addBatch() throws SQLException {
        if (connection.getAutoCommit()) {
            connection.setAutoCommit(false);
        }
        statement.addBatch();
    }

    public int executeUpdate() throws SQLException {
        try {
            return statement.executeUpdate();
        } finally {
            if (statement != null) {
                statement.close();
            }

            if (connection != null) {
                connection.close();
            }
        }
    }

    public ResultSet executeQuery() throws SQLException {
        CachedRowSetImpl rowSet = new CachedRowSetImpl();
        ResultSet resultSet = null;
        try {
            resultSet = statement.executeQuery();
            rowSet.populate(resultSet);
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }

            if (statement != null) {
                statement.close();
            }

            if (connection != null) {
                connection.close();
            }
        }
        return rowSet;
    }

    public int[] executeBatch() throws SQLException {
        try {
            return statement.executeBatch();
        } finally {
            if (statement != null) {
                statement.close();
            }

            if (connection != null) {
                connection.commit();
                connection.close();
            }
        }
    }

    public void executeUpdateAsync(final Callback<Integer, SQLException> callback) {
        mysql.getThreadPool().submit(new Runnable() {

            public void run() {
                try {
                    int rowsChanged = executeUpdate();
                    if (callback != null) {
                        callback.call(rowsChanged, null);
                    }
                } catch (SQLException e) {
                    if (callback != null) {
                        callback.call(0, e);
                    } else {
                        e.printStackTrace();
                    }
                }
            }

        });
    }

    public void executeUpdateAsync() {
        executeUpdateAsync(null);
    }

    public void executeQueryAsync(final Callback<ResultSet, SQLException> callback) {
        mysql.getThreadPool().submit(new Runnable() {

            public void run() {
                try {
                    ResultSet rs = executeQuery();
                    callback.call(rs, null);
                } catch (SQLException e) {
                    callback.call(null, e);
                }
            }

        });
    }

    public void executeBatchAsync() {
        executeBatchAsync(null);
    }

    public void executeBatchAsync(final Callback<int[], SQLException> callback) {
        mysql.getThreadPool().submit(new Runnable() {

            public void run() {
                try {
                    int[] rowsChanged = executeBatch();
                    if (callback != null) {
                        callback.call(rowsChanged, null);
                    }
                } catch (SQLException e) {
                    if (callback != null) {
                        callback.call(null, e);
                    }
                }
            }

        });
    }


    public void rollback() throws SQLException {
        if (connection != null) {
            connection.rollback();
        }
    }

}
