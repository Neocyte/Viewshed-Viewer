package mysql;

import java.sql.*;
import java.util.logging.Level;
import javax.sql.DataSource;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import utilities.WebErrorLogger;
import utilities.PropertyManager;

/**
 * Represents and manages a MySQL database connection pool. The class uses the 
 * Resource element from context.xml to specify the database and connection
 * pool properties. 
 * Sample Usage"
 * <pre>
 * ConnectionPoolMySQL pool = ConnectionPoolMySQL.getInstance();
 * Connection connection = pool.getConnection();
 * // Use the connection
 * // When you are finished with this connection
 * pool.freeConnection(connection);
 * </pre>
 * 
 * @author Curt Jones (2018)
 */
public class ConnectionPoolMySQL {

    private static ConnectionPoolMySQL pool = null;
    private static DataSource dataSource = null;
/**
 * Constructs a <code>ConnectionPool</code> object with the default <code> DataSource</code>.
 */
    private ConnectionPoolMySQL() {
        try {
            InitialContext ic = new InitialContext();
            dataSource = (DataSource) ic.lookup("java:/comp/env/jdbc/mysql/Viewshed");
        } catch (NamingException e) {
            PropertyManager.setProperty("UseDBPooling", "no");
            WebErrorLogger.log(Level.SEVERE,"Could not connect to our data source: "+
                    "java:/comp/env/jdbc/mysql/Viewshed", e);
    //        utilities.Debug.println("Could not connect to our data source: "+"java:/comp/env/jdbc/mysql/Compsci480");
        }
    }

    /**
     * 
     * Gets a object of this connection pool.
     * @return This <code>ConnectionPoolMySQL</code> object.
     */
    public static synchronized ConnectionPoolMySQL getInstance() {
        if (pool == null) {
            pool = new ConnectionPoolMySQL();
        }
        return pool;
    }

    /**
     * Gets a connection to the database.
     * @return A connection to the database.
     */
    public Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            WebErrorLogger.log(Level.SEVERE,"Could not get a connection in ConnectionPoolMySQL()", e);
            return null;
        }
    }

    /**
     * Releases this connection from the connection pool.
     * @param c The name of this connection.
     */
    public void freeConnection(Connection c) {
        if(c==null) return; 
        try {
            c.close();
        } catch (SQLException e) {
             WebErrorLogger.log(Level.SEVERE,"Could not free connection in ConnectionPoolMySQL()", e);;
        }
    }
}