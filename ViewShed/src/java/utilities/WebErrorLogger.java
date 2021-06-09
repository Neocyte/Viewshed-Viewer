package utilities;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import javax.servlet.ServletContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
//import org.apache.logging.log4j.web.WebLoggerContextUtils;

/**
 *  
 * 
 * @author Curt Jones (2019)
 */
public class WebErrorLogger {
    static Logger logger= null; 
    static ServletContext servletContext = null;
    public static void initialize(ServletContext sc) {
       servletContext = sc;
       logger = LogManager.getRootLogger();
       
       //Uncomment to test configuration
       /*
       Logger logger2=LogManager.getLogger(WebErrorLogger.class); // instead of root logger
 
        logger.info("Hello, servlet!");
        logger.debug("Good bye");
        logger2.debug("Logger 2 debug statement");
        try{
           int i =  1/0; 
        }catch (Exception e){
            logger.error("Division by zero error in WebErrorLogger.initialize(ServletContext)", e);
            logger2.error("Logger 2 Division by zero error in initialize(ServletContext)", e);
            //No need for the method name in logger2, if you are also outputting the logger name
            //Use this idea if all files ask for their own logger
            //If one location is using a root logger, then include the method name as well
        }
         servletContext.log(sc.getRealPath("/WEB-INF"));//Put the message in the Tomcat log file
        */
    }
    public static void initialize() {

       logger = LogManager.getRootLogger();
      //Uncomment to test configuration
      /*
        logger.info("Hello, servlet!");
        logger.debug("Good bye");
        try{
           int i =  1/0; 
        }catch (Exception e){
            logger.error("Division by zero error in WebErrorLogger.initialize()", e);
        }
      */

    }


    /**
     * Takes the given date and formats it into an easily readable String
     * representing this date. 
     * 
     * Date format: MM-dd-yyyy
     *
     * @param date The date we want to format.
     * @return An easily read, String representation of the given date.
     */
    private static String getFormattedDate(Date date) {
        
        DateFormat format;
        format = new SimpleDateFormat ("MM-dd-yyyy");

        return (format.format (date));
    }

    /**
     * Takes the given date, extracts the time out of it and creates a String
     * representation of this time that is easily read. 
     * 
     * Time format: HH.MM.AM/PM
     *
     * @param date The date from which we want to extract the time from.
     * @return A string representation of the time extracted from the given date
     * object.
     */
    private static String getFormattedTime(Date date) {
        DateFormat format;
        format = new SimpleDateFormat ("hh.mm.a");

        return (format.format (date));
    }

    /**
     * Uses the other utility methods in this class to create a date/time
     * string that is formatted in a way that is convenient for naming the
     * Weather Error file.
     * 
     * @param date The date we want to create string for.
     * @return A string that represents the data and time contained in the given
     * date.
     */
    private static String getDateTime(Date date) {
        String sDateTime = getFormattedDate(date) + "-" +
                           getFormattedTime(date);
        return sDateTime;
    }

   
    
   
    /**
     * Log message and exception with timestamp at the provided log level.
     * A log message is also placed into the Tomcat log file.
     *
     * @param level The Level of the message.
     * @param message The message to log with the exception.
     * @param ex The exception that is being logged.
     */
     public static void log(Level level, String message, Throwable ex) {
        if(servletContext != null){
           servletContext.log(message, ex); //Put the message into the Tomcat error log
        } 
        int levelValue = level.intValue();
        if(logger!=null){
            if ( levelValue >= Level.SEVERE.intValue()) logger.error(message,ex);
            else if (levelValue >= Level.WARNING.intValue()) logger.warn(message,ex);
            else if (levelValue >= Level.INFO.intValue()) logger.info(message,ex);
            else if (levelValue >= Level.CONFIG.intValue()) logger.debug(message,ex);
            else logger.trace(message,ex);
        }

        if(Debug.isEnabled()){
            Debug.println(message);
            ex.printStackTrace();
        }
        
        if(Debug.emailErrorlMessage && levelValue>=Level.SEVERE.intValue()){
            String useGmail = PropertyManager.getProperty("Use_gmail");
            String adminEmail = PropertyManager.getProperty("AdminEmail");
            if(adminEmail==null) return;
            if (useGmail!=null && useGmail.trim().equalsIgnoreCase("true")){
              GmailUtility.sendMail(adminEmail,"ErrorLogger" , 
                      "Error Message from a Web Application", message + "\n" +
                              ex.getMessage(), false);
            }
            String useBloomMail = PropertyManager.getProperty("Use_BU_Mail");
            if (useBloomMail!=null && useBloomMail.trim().equalsIgnoreCase("true")){
                EmailUtility.email(adminEmail, message + "\n" +
                              ex.getMessage(),"Error Message from a Web Application");
            }
        }  
    }

    /**
     * Log message with timestamp at the given standard log level.
     *
     * @param level The Level of the log message.
     * @param message The message to log.
     */
    public static void log(Level level, String message){
        if(servletContext != null){
           servletContext.log(message); //Put message into the Tomcat error log
        }
        int levelValue = level.intValue();
        if(logger!=null){
            if ( levelValue >= Level.SEVERE.intValue()) logger.error(message);
            else if (levelValue >= Level.WARNING.intValue()) logger.warn(message);
            else if (levelValue >= Level.INFO.intValue()) logger.info(message);
            else if (levelValue >= Level.CONFIG.intValue()) logger.debug(message);
            else logger.trace(message);
        }
        if(Debug.isEnabled()){
            Debug.println(message);
        }
        if(Debug.emailErrorlMessage && levelValue>=Level.SEVERE.intValue()){
            String useGmail = PropertyManager.getProperty("Use_gmail");
            String adminEmail = PropertyManager.getProperty("AdminEmail");
            if(adminEmail==null) return;
            if (useGmail!=null && useGmail.trim().equalsIgnoreCase("true")){
              GmailUtility.sendMail(adminEmail,"ErrorLogger" , 
                      "Error Message from "+logger.getName(), message, false);
            }
            String useBloomMail = PropertyManager.getProperty("Use_BU_Mail");
            if (useBloomMail!=null && useBloomMail.trim().equalsIgnoreCase("true")){
                EmailUtility.email(adminEmail, message,"Error Message from "+logger.getName());
            }
        }
    }

    
}
