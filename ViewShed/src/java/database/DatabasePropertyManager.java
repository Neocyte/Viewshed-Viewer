
package database;

import common.DatabaseProperty;
import java.util.Collection;

/**
 * A <code>interface</code> that manages database properties in our system.
 * 
 * 
 * @author Curt Jones (2018)
 */
public interface DatabasePropertyManager {
    public Collection<DatabaseProperty> getAllDatabaseProperties();
    public String getAllDatabasePropertiesAsHTMLTable();
    public String getPropertyValue(String propertyName);
    public DatabaseProperty getDataBasePropertyByID(int propertyID);
    public DatabaseProperty getDataBasePropertyByName(String propertyName);
    public void addDatabaseProperty(DatabaseProperty databaseProperty);
    /**
     * If the property id field is invalid, then the property name is checked.
     * A new property is added if we don't have a match.
     * @param databaseProperty The updated database property. 
     */
    public void updateDatabaseProperty(DatabaseProperty databaseProperty);
    
    
}
