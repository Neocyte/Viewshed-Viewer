/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mysql;

import common.ErrorLog;
import database.SQLUtility;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;
import utilities.WebErrorLogger;

/**
 *
 * @author Curt Jones
 */
/*
DROP TABLE IF EXISTS error_logs;
CREATE TABLE IF NOT EXISTS error_logs (
  `EVENT_ID` int(11) NOT NULL AUTO_INCREMENT,
  `EVENT_DATE` datetime DEFAULT NULL,
  `LEVEL` varchar(15) DEFAULT NULL,
  `LOGGER` varchar(60) DEFAULT NULL,
  `MSG` varchar(240) DEFAULT NULL,
  `THROWABLE` varchar(240) DEFAULT NULL,
  PRIMARY KEY (`EVENT_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
*/
public class DatabaseErrorLogManager implements database.DatabaseErrorLogManager {

    @Override
    public Collection<ErrorLog> getAllErrorLogs() {
        Collection<ErrorLog> errorLogs = new ArrayList<>();
        String sql = "SELECT * FROM error_logs;";
        Connection conn = mysql.Web_MYSQL_Helper.getConnection();
        ResultSet rs = null;
        PreparedStatement stmt2 = null;
        ErrorLog errorLog = null;
        try {
            stmt2 = conn.prepareStatement(sql);
            rs = stmt2.executeQuery();
            if (rs == null) {
                Web_MYSQL_Helper.returnConnection(conn);
                return errorLogs;
            }
            while (rs.next()) {
                errorLog = SQLUtility.convertResultSetToErrorLog(rs);
                errorLogs.add(errorLog);
            }
            
        } catch (SQLException ex) {
            WebErrorLogger.log(Level.SEVERE, "SQLException in getAllErrorLogs()" + ex);
        } finally {
            Web_MYSQL_Helper.closePreparedStatement(stmt2);
            Web_MYSQL_Helper.closeResultSet(rs);
            Web_MYSQL_Helper.returnConnection(conn);
        }
        return errorLogs;
    }

    //This method was not tested. 
    @Override
    public void addErrorLog(ErrorLog errorLog) {
        if(errorLog == null) return; 
        String sql = "INSERT INTO error_logs (EVENT_ID,EVENT_DATE,LEVEL,"+
                "LOGGER,MSG,THROWABLE"
                + ") VALUES (DEFAULT,?,?,?,?,?); ";
        Connection conn = mysql.Web_MYSQL_Helper.getConnection();
        PreparedStatement stmt2 = null;
        try {
            stmt2 = conn.prepareStatement(sql);
            stmt2.setString(1, errorLog.getErrorLogDateTime().toString().trim());
            stmt2.setString(2, errorLog.getErrLevel().trim());
            stmt2.setString(3, errorLog.getLoggerName().trim());
            stmt2.setString(4, errorLog.getErrorMessage().trim());
            stmt2.setString(5, errorLog.getException().trim());
            stmt2.executeUpdate();

        } catch (SQLException ex) {
            WebErrorLogger.log(Level.SEVERE, "SQLException in addErrorLog(ErrorLog errorLog) " + ex); 
        }
        finally{
            Web_MYSQL_Helper.closePreparedStatement(stmt2);
            Web_MYSQL_Helper.returnConnection(conn);
        }
        
    }

    @Override
    public ErrorLog getErrorLogByID(int id) {
        String sql = "SELECT * FROM error_logs WHERE EVENT_ID = ?;";
        Connection conn = mysql.Web_MYSQL_Helper.getConnection();
        ResultSet rs = null;
        PreparedStatement stmt2 = null;
        ErrorLog errorLog = null;
        try {
            stmt2 = conn.prepareStatement(sql);
            rs = stmt2.executeQuery();
            if (rs == null|| rs.next() == false) {
                Web_MYSQL_Helper.returnConnection(conn);
                return null;
            }
            errorLog = SQLUtility.convertResultSetToErrorLog(rs);
           
        } catch (SQLException ex) {
            WebErrorLogger.log(Level.SEVERE, "SQLException in getErrorLogByID(int id) " + ex);
        } finally {
            Web_MYSQL_Helper.closePreparedStatement(stmt2);
            Web_MYSQL_Helper.closeResultSet(rs);
            Web_MYSQL_Helper.returnConnection(conn);
        }
        return errorLog;
    }

    @Override
    public void deleteErrorLog(int id) {
       String sql = "DELETE FROM error_logs WHERE EVENT_ID =?; ";
        Connection conn = mysql.Web_MYSQL_Helper.getConnection();

        try {
            PreparedStatement stmt2 = conn.prepareStatement(sql);
            stmt2.setInt(1, id);
            stmt2.executeUpdate();
            Web_MYSQL_Helper.returnConnection(conn);
        } catch (SQLException ex) {
            WebErrorLogger.log(Level.SEVERE, "SQLException in deleteErrorLog(int " + id + " ) error: " + ex);
            Web_MYSQL_Helper.returnConnection(conn);
        }

    }

    @Override
    public void deleteAllErrorLogs() {
        String sql = "DELETE FROM error_logs ; ";
        Connection conn = mysql.Web_MYSQL_Helper.getConnection();

        try {
            PreparedStatement stmt2 = conn.prepareStatement(sql);
            stmt2.executeUpdate();
            Web_MYSQL_Helper.returnConnection(conn);
        } catch (SQLException ex) {
            WebErrorLogger.log(Level.SEVERE, "SQLException in deleteAllErrorLogs() error: " + ex);
            Web_MYSQL_Helper.returnConnection(conn);
        }
    }

    @Override
    public String getAllErrorLogsAsHTMLTable() {
        String sql = "SELECT * FROM error_logs;";
        Connection conn = mysql.Web_MYSQL_Helper.getConnection();
        ResultSet rs = null;
        PreparedStatement stmt2 = null;
        String table="No data";
        try {
            stmt2 = conn.prepareStatement(sql);
            rs = stmt2.executeQuery();
 
            table = utilities.SQLUtil.getHtmlTableFromResultSetNoHeaders(rs);
            
        } catch (SQLException ex) {
            WebErrorLogger.log(Level.SEVERE, "SQLException in getAllErrorLogs()" + ex);
        } finally {
            Web_MYSQL_Helper.closePreparedStatement(stmt2);
            Web_MYSQL_Helper.closeResultSet(rs);
            Web_MYSQL_Helper.returnConnection(conn);
        }
        return table;
    }
    
}
