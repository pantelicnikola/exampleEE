package connections;

import javax.naming.NamingException;
import java.sql.Connection;
import java.sql.SQLException;

public class HBaseConnection {
    public static Connection getConnection() {

        javax.naming.Context ic = null;
        try {
            ic = new javax.naming.InitialContext();
            javax.naming.Context ctx = (javax.naming.Context) ic.lookup("java:/");
            javax.sql.DataSource ds = (javax.sql.DataSource) ctx.lookup("PhoenixDS");
            return ds.getConnection();

        } catch (NamingException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}

//
//Name:
//        PhoenixDS
//        JNDI Name:
//        java:/phoenixds
//        Connection URL:
//        jdbc:phoenix:quickstart.cloudera
//        Username:
//        Password: