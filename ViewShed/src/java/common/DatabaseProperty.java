/* Database table properties is shown here. All SQL code is in the folder sql_scripts
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
package common;

/**
 * Represents one row in the property database table. 
 * @author Curt Jones (2018)
 */
public class DatabaseProperty {
   private int propertyNumber ;// Primary Key
   private String propertyName; // The name of this property, must be unique --> VARCHAR(64) NOT NULL UNIQUE
   private String propertyValue; // Value of this property --> VARCHAR(128) NOT NULL,
   private String description; // Description of this property --> VARCHAR (1024)
   private String previousValue; // Allows the system to keep the previous value in case of a mistake --> VARCHAR(128),
   private String defaultValue; //Allows the system administrator to keep the default value inorder to reset changes.  

    public int getPropertyNumber() {
        return propertyNumber;
    }

    public void setPropertyNumber(int propertyNumber) {
        this.propertyNumber = propertyNumber;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getPropertyValue() {
        return propertyValue;
    }

    public void setPropertyValue(String propertyValue) {
        this.propertyValue = propertyValue;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPreviousValue() {
        return previousValue;
    }

    public void setPreviousValue(String previousValue) {
        this.previousValue = previousValue;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + this.propertyNumber;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final DatabaseProperty other = (DatabaseProperty) obj;
        if (this.propertyNumber != other.propertyNumber) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DatabaseProperty{" + "propertyNumber=" + propertyNumber + 
                ", propertyName=" + propertyName + ", propertyValue=" + 
                propertyValue + ", description=" + description +
                ", previousValue=" + previousValue + ", defaultValue=" + 
                defaultValue + '}';
    }
    
}
