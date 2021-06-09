
package mysql;

import common.DatabaseProperty;
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
public class DatabasePropertyManager implements database.DatabasePropertyManager{

    /**
     * 
     * @return All the properties in the database. 
     */
    @Override
    public Collection<DatabaseProperty> getAllDatabaseProperties() {
        Collection<DatabaseProperty> properties = new ArrayList<>();
        String sql = "SELECT * FROM properties;";
        Connection conn = mysql.Web_MYSQL_Helper.getConnection();
        ResultSet rs = null;
        PreparedStatement stmt2 = null;
        DatabaseProperty databaseProperty = null;
        try {
            stmt2 = conn.prepareStatement(sql);
            rs = stmt2.executeQuery();
            if (rs == null ) {
                Web_MYSQL_Helper.returnConnection(conn);
                return properties;
            }
            while (rs.next()) {
                databaseProperty = SQLUtility.convertResultSetToDatabaseProperty(rs);
                properties.add(databaseProperty);
            }
            
        } catch (SQLException ex) {
            WebErrorLogger.log(Level.SEVERE, "SQLException in getAllDatabaseProperties()" + ex);
        } finally {
            Web_MYSQL_Helper.closePreparedStatement(stmt2);
            Web_MYSQL_Helper.closeResultSet(rs);
            Web_MYSQL_Helper.returnConnection(conn);
        }
        return properties;
    }

    /**
	 * Gets the property value from the property name.
     * 
     * @param propertyName The name of the property value we want to fetch from the database.
	 * 
     * @return The property value associated with this property name or null if it does not exist. 
     */
    @Override
    public String getPropertyValue(String propertyName) {
        if(propertyName == null) return null;
        propertyName = propertyName.trim();
        String sql = "SELECT * FROM properties WHERE propertyName = ?;";
        Connection conn = mysql.Web_MYSQL_Helper.getConnection();
        ResultSet rs = null;
        PreparedStatement stmt2 = null;
        DatabaseProperty databaseProperty = null;
        try {
            stmt2 = conn.prepareStatement(sql);
            stmt2.setString(1, propertyName);
            rs = stmt2.executeQuery();
            if (rs == null || rs.next() == false) {
                Web_MYSQL_Helper.returnConnection(conn);
                return null;
            }
            databaseProperty = SQLUtility.convertResultSetToDatabaseProperty(rs);
            return databaseProperty.getPropertyValue();
            
        } catch (SQLException ex) {
            WebErrorLogger.log(Level.SEVERE, "SQLException in getPropertyValue(String propertyName)" + ex);
        } finally {
            Web_MYSQL_Helper.closePreparedStatement(stmt2);
            Web_MYSQL_Helper.closeResultSet(rs);
            Web_MYSQL_Helper.returnConnection(conn);
        }
        return null;
    }

    /**
	 * Gets the property value from the propertyID.
     * 
     * @param propertyID The ID number of the property we want to fetch as listed in the database.
	 * 
     * @return The database property with this identification number or null if it does not exist. 
     */
    @Override
    public DatabaseProperty getDataBasePropertyByID(int propertyID) {
       String sql = "SELECT * FROM properties WHERE propertyNumber = ?;";
        Connection conn = mysql.Web_MYSQL_Helper.getConnection();
        ResultSet rs = null;
        PreparedStatement stmt2 = null;
        DatabaseProperty databaseProperty = null;
        try {
            stmt2 = conn.prepareStatement(sql);
            stmt2.setInt(1, propertyID);
            rs = stmt2.executeQuery();
            if (rs == null || rs.next() == false) {
                Web_MYSQL_Helper.returnConnection(conn);
                return null;
            }
            databaseProperty = SQLUtility.convertResultSetToDatabaseProperty(rs);
            return databaseProperty;
            
        } catch (SQLException ex) {
            WebErrorLogger.log(Level.SEVERE, "SQLException in getDataBasePropertyByID(int propertyID)" + ex);
        } finally {
            Web_MYSQL_Helper.closePreparedStatement(stmt2);
            Web_MYSQL_Helper.closeResultSet(rs);
            Web_MYSQL_Helper.returnConnection(conn);
        }
        return null;
    }


    /**
     * Adds a new property to the database. The property number is auto assigned by MySQL. 
     * If a property with the same name is already in the database, then that 
     * entry is updated with this new information. 
     * 
     * @param databaseProperty The <code>DatabaseProperty</code> object representing
	 * the property to be added to the database.
     */
    @Override
    public void addDatabaseProperty(DatabaseProperty databaseProperty) {
        if(databaseProperty == null) return;
        //See if a property by this name already exists in the database
        DatabaseProperty property2 = getDataBasePropertyByName(databaseProperty.getPropertyName());
        if(property2!=null){
            //Since this property already exists, update it instead. 
            databaseProperty.setPropertyNumber(property2.getPropertyNumber());
            this.updateDatabaseProperty(databaseProperty);
            return;
        }
        String sql = "INSERT INTO properties (propertyNumber,propertyName,propertyValue,"+
                "description,previousValue,defaultValue"
                + ") VALUES (DEFAULT,?,?,?,?,?,?); ";
        Connection conn = mysql.Web_MYSQL_Helper.getConnection();
        PreparedStatement stmt2 = null;
        try {
            stmt2 = conn.prepareStatement(sql);
            stmt2.setString(1, databaseProperty.getPropertyName().trim());
            stmt2.setString(2, databaseProperty.getPropertyValue().trim());
            stmt2.setString(3, databaseProperty.getDescription().trim());
            stmt2.setString(4, databaseProperty.getPreviousValue().trim());
            stmt2.setString(5, databaseProperty.getDefaultValue().trim());
            stmt2.executeUpdate();

        } catch (SQLException ex) {
            WebErrorLogger.log(Level.SEVERE, "SQLException in addDatabaseProperty(DatabaseProperty databaseProperty)" + ex); 
        }
        finally{
            Web_MYSQL_Helper.closePreparedStatement(stmt2);
            Web_MYSQL_Helper.returnConnection(conn);
        }
    }

    
    /**
     * If the property id field is invalid, then the property name is checked to determine
     * which property should be updated.
     * A new property is added if we don't have a match.
     * 
     * @param databaseProperty The updated database property. 
     */
    @Override
    public void updateDatabaseProperty(DatabaseProperty databaseProperty) {
        if(databaseProperty == null) return;
        String sql = "UPDATE properties SET propertyName=?,propertyValue=?"+
                "description=?,previousValue=?,defaultValue=?"
                + ");";
        Connection conn = mysql.Web_MYSQL_Helper.getConnection();
        PreparedStatement stmt2 = null;
        try {
            stmt2 = conn.prepareStatement(sql);
            stmt2.setString(1, databaseProperty.getPropertyName().trim());
            stmt2.setString(2, databaseProperty.getPropertyValue().trim());
            stmt2.setString(3, databaseProperty.getDescription().trim());
            stmt2.setString(4, databaseProperty.getPreviousValue().trim());
            stmt2.setString(5, databaseProperty.getDefaultValue().trim());
            stmt2.executeUpdate();

        } catch (SQLException ex) {
            WebErrorLogger.log(Level.SEVERE, "SQLException in updateDatabaseProperty(DatabaseProperty databaseProperty)" + ex); 
        }
        finally{
            Web_MYSQL_Helper.closePreparedStatement(stmt2);
            Web_MYSQL_Helper.returnConnection(conn);
        }
    }

    /**
	 * Gets the property value by property name.
     * 
     * @param propertyName The name of the property we want to fetch as listed in the database.
	 * 
     * @return The database property associated with this property name. 
     */
    @Override
    public DatabaseProperty getDataBasePropertyByName(String propertyName) {
                String sql = "SELECT * FROM properties WHERE propertyName = ?;";
        Connection conn = mysql.Web_MYSQL_Helper.getConnection();
        ResultSet rs = null;
        PreparedStatement stmt2 = null;
        DatabaseProperty databaseProperty = null;
        try {
            stmt2 = conn.prepareStatement(sql);
            stmt2.setString(1, propertyName);
            rs = stmt2.executeQuery();
            if (rs == null || rs.next() == false) {
                Web_MYSQL_Helper.returnConnection(conn);
                return null;
            }
            databaseProperty = SQLUtility.convertResultSetToDatabaseProperty(rs);
            return databaseProperty;
            
        } catch (SQLException ex) {
            WebErrorLogger.log(Level.SEVERE, "SQLException in getPropertyValue(String propertyName)" + ex);
        } finally {
            Web_MYSQL_Helper.closePreparedStatement(stmt2);
            Web_MYSQL_Helper.closeResultSet(rs);
            Web_MYSQL_Helper.returnConnection(conn);
        }
        return null;
    }
    
    /*
   CREATE TABLE IF NOT EXISTS properties (
   propertyNumber INT NOT NULL UNIQUE AUTO_INCREMENT,
   propertyName VARCHAR(64) NOT NULL UNIQUE,
   propertyValue VARCHAR(128) NOT NULL,
   description VARCHAR (1024)DEFAULT '',
   previousValue VARCHAR(128),
   defaultValue VARCHAR(128),
   PRIMARY KEY (propertyNumber)
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
    */

    @Override
    public String getAllDatabasePropertiesAsHTMLTable() {
        String sql = "SELECT * FROM properties;";
        Connection conn = mysql.Web_MYSQL_Helper.getConnection();
        ResultSet rs = null;
        String table="No Property Data";
        PreparedStatement stmt2 = null;
        DatabaseProperty databaseProperty = null;
        try {
            stmt2 = conn.prepareStatement(sql);
            rs = stmt2.executeQuery();
            table = utilities.SQLUtil.getHtmlTableFromResultSet(rs);
            
        } catch (SQLException ex) {
            WebErrorLogger.log(Level.SEVERE, "SQLException in getAllDatabaseProperties()" + ex);
        } finally {
            Web_MYSQL_Helper.closePreparedStatement(stmt2);
            Web_MYSQL_Helper.closeResultSet(rs);
            Web_MYSQL_Helper.returnConnection(conn);
        }
        return table;
    }
}
