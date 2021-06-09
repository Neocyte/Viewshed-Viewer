
package utilities;

import common.DatabaseProperty;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import static java.lang.Thread.sleep;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Properties;
import java.util.logging.Level;



/**
 * Utility class to access all properties and property files.
 *
 * 
 * @author Curt Jones (2018)
 */
public class PropertyManager {
  

    private static Properties properties;
 
  
    /**
     * Configures properties for this program.  
     * @param filePath Path to property file location.
     * 
     */
    public static void configure(String filePath) {
        properties = new Properties();
        Debug.println("The filepath to the properties is " +filePath );
        if(filePath==null || filePath.isEmpty()) {
         //   Debug.println("The filepath to the properties is " +filePath +"it did not work");
            WebErrorLogger.log(Level.SEVERE,"Could not open the properties file: "+ filePath);
            return;
        }
       // else Debug.println("The path to the propertiy file given was #"+ filePath+"#");
       // String propertyFilepath = filePath;
            
        try {
            properties.load(new FileInputStream(new File(filePath)));
            Debug.println("Properties loaded from "+ filePath);
        } 
        catch (FileNotFoundException ex) {
            WebErrorLogger.log(Level.SEVERE,"Could not open the properties file: "+ filePath,  ex); 
            /*
            Debug.println("Could not open the properties file: "+ filePath );
            ex.printStackTrace();
            */
        } catch (IOException ex) {
            WebErrorLogger.log(Level.SEVERE,"Could not read the properties file: "+ filePath,  ex);
            // Debug.println("Could not read the properties file: "+ filePath );
        }
        
        
        try {
            sleep(30);
         } catch (InterruptedException ex) {
            
         }
         if(Debug.isEnabled()) displayProperties();
    }

    /**
     * Obtains the requested property from the general properties file.
     *
     * @param key the name of the property requested.
     * @return The value of the property requested or null if 
     * this key does not exist in the property file. 
     */
    public static String getProperty(String key) {
        if (properties == null) {
            configure(null);
        }
        return properties.getProperty(key);
    }
    
   /**
    * Sets the a property value for the given key and stores it in the property file 
    * for the next program execution. 
    * 
    * @param key The key for this property value. 
    * @param value The property value associated with the given key. 
    */
    public static void setProperty(String key, String value) {
        if (properties == null) {
            configure(null);
        }
        properties.setProperty(key, value);
    }

    /**
    * Saves the current property collection to the default file. 
    * 
    */
    public static void saveFile() {
        saveFile(null);
        
    }

    
   /**
    * Saves the current property collection to a file. 
    * 
     * @param filePath The file path or null to use the default path. 
    */
    public static void saveFile(String filePath) {
        if (properties == null) return;
        if(filePath==null || filePath.isEmpty()){
         return;
        }
        
        try {
              properties.store(new FileOutputStream(new File(filePath)), null);
        } catch (IOException ex) {
            WebErrorLogger.log(Level.SEVERE,"Could not save property file "+ 
                            filePath,  ex);
          }
    }

    /**
     * This method will populate our property file with the properties stored in 
     * the database for this application.
     */
    
    public static void loadDatabaseProperties() {
        database.DatabasePropertyManager dpm=database.Database.getDatabaseManagement().getDatabasePropertyManager();
        Collection<DatabaseProperty> dbProperties = dpm.getAllDatabaseProperties();
        Iterator<DatabaseProperty> iterator = dbProperties.iterator();
        while( iterator.hasNext()){
           DatabaseProperty dp = iterator.next();
           setProperty(dp.getPropertyName(),dp.getPropertyValue());
        }
    }
    
    public static boolean isProduction() {
        String value = getProperty("Configuration");
        if (value == null) return false;
        Debug.println("Configuration is " +value);
        return value.equalsIgnoreCase("production");
    }
    public static void displayProperties(){
        System.out.println("The properties are");
        Enumeration<Object> keys =  properties.keys();
        while (keys.hasMoreElements()) {
            String value = keys.nextElement().toString();
            System.out.println(value+" #"+ properties.getProperty(value)+"#" );
        }
    }
    
    public static void main(String [] args){
        final String propertyFilePath = common.Paths.HOMEDIRECTORY+"/web/WEB-INF/config/General.properties";
        PropertyManager.configure(propertyFilePath);
        displayProperties();
    }
}