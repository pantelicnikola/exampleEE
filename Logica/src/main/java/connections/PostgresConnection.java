package connections;

import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class PostgresConnection {

    private static final String DATA_SOURCE_CONTEXT = "java:/";
    private static final String DATA_SOURCE_NAME = "PostgresDS";
    private DataSource dataSource = null;
    private static PostgresConnection pConnection;

    protected PostgresConnection(){}

    public static PostgresConnection getInstance() {
        if (pConnection == null) {
            pConnection = new PostgresConnection();
        }
        return pConnection;
    }

    public void dataSourceSetup() {
        javax.naming.Context ic = null;
        try {
            ic = new javax.naming.InitialContext();
            javax.naming.Context ctx = (javax.naming.Context) ic.lookup(DATA_SOURCE_CONTEXT);
            dataSource = (javax.sql.DataSource) ctx.lookup(DATA_SOURCE_NAME);
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        if (dataSource == null) {
            dataSourceSetup();
        }
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            return connection;
        }
    }
}
