package servlets.listener;

import java.util.logging.Level;
import mysql.Web_MYSQL_Helper;
import javax.servlet.*;
import utilities.PropertyManager;
import security.Encryption;
import utilities.WebErrorLogger;


/**
 * This listener will execute before the web application starts and when it is 
 * reloaded. It is used to initialize 
 * features and properties. It reads properties from the web.xml file and
 * used them to configure our <code>PropertyManager</code> class. 
 * This class is needed because we need to get our current context to know the
 * exact location of some of our required files. 
 * 
 * When the context is destroyed, the database connections are closed. 
 * 
 * @author cjones
 */
public class ContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent event) {
        ServletContext sc = event.getServletContext();
        WebErrorLogger.initialize(sc);
        //How to get the actual path to a relative directory
        String actualPath = sc.getRealPath("/WEB-INF");
        
        //Get parameters from web.xml
        String propertyFileLocation = sc.getInitParameter("PropertyFilePath");
        String propertyFilePath = sc.getRealPath(propertyFileLocation);
        String encryptKeyLocation = sc.getInitParameter("EncryptKeyPath");
        String encryptFilePath = sc.getRealPath(encryptKeyLocation);
        String configuration = sc.getInitParameter("Configuration");
       
        // Next the property manager
        PropertyManager.configure(propertyFilePath); // Read properties from a file first
        // The key for our encrytpion system (if needed -- most times no)
        Encryption.initialize(encryptFilePath);
        
        // Override properties in the file with the ones from web.xml
        PropertyManager.setProperty("WEB-INF Real Path", actualPath);
        PropertyManager.setProperty("PropertyFilePath", propertyFilePath);
        PropertyManager.setProperty("EncryptKeyLocation", encryptKeyLocation);
        PropertyManager.setProperty("Configuration", configuration);
        PropertyManager.setProperty("WebApplication", "true");
        PropertyManager.setProperty("Welcome-file", "jsp/loginScreen.jsp");
        PropertyManager.setProperty("LogDirectoryPath", sc.getRealPath(sc.getInitParameter("LogDirectoryPath")));
        PropertyManager.setProperty("UseDBPooling", sc.getInitParameter("UseDBPooling"));
        PropertyManager.setProperty("AdminEmail", sc.getInitParameter("AdminEmail"));
        PropertyManager.loadDatabaseProperties(); //This will override any changes to our properties

  //      WebErrorLogger.log(Level.WARNING, "Outside Logins Only Context Loaded");
  //     String adminEmail = PropertyManager.getProperty("AdminEmail");
  //     email(adminEmail,"Sample Web Application Test Error Message",  "Sample Web Application Test Error Message");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        Web_MYSQL_Helper.closeConnectionsOnExit();
    }
    
}
