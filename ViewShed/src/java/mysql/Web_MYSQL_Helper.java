package mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import utilities.WebErrorLogger;
import utilities.PropertyManager;

/**
 * A helper class that implements multiple ways to get database connections. 
 * The best method is to use the Tomcat provided Database connection pool from a
 * MySQL database specified as a <code>DataSource</code> in the
 * context.xml file. 
 * A second method is to connect to a database specified in the properties 
 * for this web application. The flag that specifies if we are to use a connection 
 * pool comes from the properties for this application. 
 * 
 * Example usage:
 * <pre>
 *  Connection conn = Web_MYSQL_Helper.getConnection();
 *  PreparedStatement stmt2 = conn.prepareStatement(sql) 
 *  //Use the connection and statements here
 *  Web_MYSQL_Helper.closeResultSet(rs); //If you had a result set
 *  Web_MYSQL_Helper.closePreparedStatement(stmt2);
 *  Web_MYSQL_Helper.returnConnection(conn);
 * </pre>
 * 
 * 
 * @author Curt Jones (2018)
 */
public class Web_MYSQL_Helper { 
    private static boolean initialized = false; 
    private static final boolean REUSE_CONNECTION = false;
    private static final ConnectionPoolMySQL connectionPool = ConnectionPoolMySQL.getInstance();
    private static boolean USE_DB_POOLING = false;
    private static Connection connection = null;

    private static final String mysqlPrefix = "jdbc:mysql://";

    private static  String hostname;
    private static  String databaseName;
    private static  String databaseURL;
    private static  String userName;
    private static  String password;
    
    public static void initialize(){
      initialized = true;  
      USE_DB_POOLING = PropertyManager.getProperty("UseDBPooling").equalsIgnoreCase("yes");
      hostname = PropertyManager.getProperty("MySQLHostName").trim();
      databaseName = PropertyManager.getProperty("DatabaseName").trim();
      databaseURL = mysqlPrefix + hostname + "/" + databaseName;
      userName = PropertyManager.getProperty("MySQLUserName").trim();
      password = PropertyManager.getProperty("MySQLPassword").trim();
    }

    /**
     * Returns a connection to the database. (Needs better documentation)
     *
     * @return A <code>Connection</code> to the database.
     */
    public static synchronized Connection getConnection() {
        if(!initialized) initialize();
                //Best way first
        if (USE_DB_POOLING) {
            connection = connectionPool.getConnection();
            int count = 0;
            try { 
                while((!connection.isValid(1)) && count++ < 8) {
                    if(!connection.isClosed())returnConnection(connection);
                    try{
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                        utilities.WebErrorLogger.log(Level.SEVERE, "Error in getConnection, Interrupted Exception", ex);
                    }
                    connection = connectionPool.getConnection();
                }
            } catch (SQLException ex) {
                utilities.WebErrorLogger.log(Level.SEVERE, "Error in getConnection, SQLException " +ex.getMessage(), ex);
            } 
            if(count > 0) utilities.WebErrorLogger.log(Level.SEVERE, "Error in getConnection, it took  " +count+" attempts to get a connection");
            return connection;
            //return connectionPool.getConnection();
        }
        //If we are not using Database pooling, then are we reusing connections?
        if (REUSE_CONNECTION == false) {
            return Web_MYSQL_Helper.getNewConnection();
        }
        //At this point, we are not using pooling and we are reusing connections
        try {
            if (connection == null || connection.isClosed()) {
                connection = Web_MYSQL_Helper.getNewConnection();
                return connection;
            }
            // If we get here the old connection did exist, but is it still valid?

            //allow 1 seconds to see if connection is still valid
            if (connection.isValid(1)) {
                return connection;
            } else {
                connection = getNewConnection();
                return connection;
            }
        } catch (SQLException e) {
            return getNewConnection(); // if all else fails, get a new connection
        }
    }

    /**
     * Creates a new connection with a DriverManager and returns it if the
     * connection is established. Catches any SQLException exception thrown by
     * the <code>getConnection(String url, String user, String password)</code>
     * method of <code>DriverManager</code> class and logs an error message in
     * the error log system. This method is only used if we are not using 
     * connection pooling. 
     *
     * @return A <code>Connection</code> to the database or null if none could
     * be created.
     */
    private static Connection getNewConnection() {
        if(!initialized) initialize();
        Connection conn = null;
        /*
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance(); //Not normally needed for MySQL - but ...
        } catch (ClassNotFoundException ex) {
            WebErrorLogger.log(Level.SEVERE, "Could not find the class com.mysql.jdbc.Driver \n"
                    + "Program will now exit. ", ex);
            System.exit(1);
        } catch (InstantiationException ex) {
            WebErrorLogger.log(Level.SEVERE, "Could not instaniate the class com.mysql.jdbc.Driver \n"
                    + "Program will now exit. ", ex);
            System.exit(1);
        } catch (IllegalAccessException ex) {
            WebErrorLogger.log(Level.SEVERE, "Could not access the class com.mysql.jdbc.Driver \n"
                    + "Program will now exit. ", ex);
            System.exit(1);
        }
        */
        try {
            conn = DriverManager.getConnection(databaseURL, userName, password);
        } catch (SQLException ex) {
            WebErrorLogger.log(Level.SEVERE, "SQL Exception was thrown while "
                    + "trying to connect to the database. Database string = "
                    + databaseURL + " user =  " + userName + " password = " + password,ex);
        }
        //Connection is thread safe, but only one thread can access the database at once now
        return conn;
    }

    /**
     * Returns the given <code>Connection</code> object to the connection pool.
     *
     * @param connection The Connection object being returned.
     */
    public static void returnConnection(Connection connection) {
        if (USE_DB_POOLING) {
            connectionPool.freeConnection(connection);
            return;
        }
        if (REUSE_CONNECTION) {
            return;
        }

        // Close connection if we are not reusing them 
        if (connection != null) {
            try {
                connection.close();
                connection = null;
            } catch (SQLException e) {
                WebErrorLogger.log(Level.SEVERE, "SQL Exception is thrown while "
                        + "trying to close a Connection object. The connection "
                        + "object was not null.", e);
            }
        }

    }
    
    public static void closeConnection(Connection connection){
        returnConnection(connection);
    }

    /**
     * Closes the given <code>Connection</code> object if it is not null.
     * Catches SQLException if it is thrown by the <code>close</code> method of
     * <code>Connection</code> interface and logs an error message in the error
     * log file.
     *
     */
    public static void closeConnectionsOnExit() {
        if (USE_DB_POOLING) {
            return;
        }
        if (connection == null) {
            return;
        }
        try {
            connection.close();
        } catch (SQLException e) {
            WebErrorLogger.log(Level.SEVERE, "SQL Exception is thrown while "
                    + "trying to close a Connection object during "
                    + "program termination. The connection "
                    + "object was not null.", e);
        }

    }

    /**
     * Closes the given <code>Statement</code> object if it is not null. Catches
     * SQLException if it is thrown by the <code>close</code> method of
     * <code>Statement</code> interface and logs an error message in the error
     * log file.
     *
     * @param stmt The Statement object to close.
     */
    public static void closeStatement(Statement stmt) {
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                WebErrorLogger.log(Level.SEVERE, "SQL Exception is thrown while "
                        + "trying to close a Statement object. The connection "
                        + "object is not null.", e);
            }
        }
    }

    /**
     * Closes the given <code>PreparedStatement</code> object if it is not null.
     * Catches SQLException if it is thrown by the <code>close</code> method of
     * <code>PreparedStatement</code> interface and logs an error message in the
     * error log file.
     *
     * @param ps The PreparedStatement object to close.
     */
    public static void closePreparedStatement(PreparedStatement ps) {
        if (ps != null) {
            try {
                ps.close();
            } catch (SQLException e) {
                WebErrorLogger.log(Level.SEVERE, "SQL Exception is thrown while "
                        + "trying to close a PreparedStatement object. The connection "
                        + "object is not null.", e);
            }
        }
    }

    /**
     * Closes the given <code>ResultSet</code> object if it is not null. Catches
     * the SQLException if it is thrown by the <code>close</code> method of
     * <code>ResultSet</code> interface and logs an error message in the error
     * log file.
     *
     * @param rs The ResultSet object to close.
     */
    public static void closeResultSet(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                WebErrorLogger.log(Level.SEVERE, "SQL Exception is thrown while "
                        + "trying to close a ResultSet object. The connection "
                        + "object is not null.", e);
            }
        }
    }
//We can only test the non-connection pool idea in an application
    public static void main(String[] args) {
        utilities.Debug.setEnabled(true);
        final String propertyFilePath = common.Paths.HOMEDIRECTORY+"/web/WEB-INF/config/General.properties";
        PropertyManager.configure(propertyFilePath);
        PropertyManager.setProperty("UseDBPooling", "no");
        System.out.println("The Database name is "+PropertyManager.getProperty("DatabaseName")+"");
        Connection conn = Web_MYSQL_Helper.getConnection();
        Connection newconnection = Web_MYSQL_Helper.getConnection();
        Web_MYSQL_Helper.returnConnection(conn);
        Web_MYSQL_Helper.returnConnection(newconnection);
        newconnection = Web_MYSQL_Helper.getConnection();
        conn = Web_MYSQL_Helper.getConnection();
        conn = Web_MYSQL_Helper.getConnection();
        Web_MYSQL_Helper.returnConnection(conn);
        Web_MYSQL_Helper.returnConnection(newconnection);
        conn = Web_MYSQL_Helper.getConnection();
        conn = Web_MYSQL_Helper.getConnection();
        Web_MYSQL_Helper.closeConnectionsOnExit();
    }

}
