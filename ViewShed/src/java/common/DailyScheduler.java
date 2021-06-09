package common;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import utilities.Debug;
import utilities.WebErrorLogger;

/**
 * Class is meant to be used to execute events on a 24 hour timer. Currently,
 * it's used to load webcams from windy.com. Time to execute is currently stored
 * in the database.
 * 
 * NOTE: This class is not linked up to be used; please contact your professor.
 *
 * @author Harman Dhillon (2019)
 * @author Michael O'Donnell (2021)
 */
public class DailyScheduler {

    /**
     * Default Constructor for creating a new <code>DailyScheduler</code>
     * object.
     */
    public DailyScheduler() {

    }

    ScheduledExecutorService scheduler;

    /**
     * This method generates a new ScheduledExecutorService scheduler.
     *
     * @return A ScheduledExectorService scheduler for our time zone.
     */
    public ScheduledExecutorService execute() {
        //Getting time to execute the code from the database
        String timeToExecuteString = database.Database.getDatabaseManagement().getDatabasePropertyManager().getPropertyValue("TimeUpdated");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");//formatting time
        //Converting String(time to execute) into a LocalTime object
        LocalTime timeToExecute = LocalTime.parse(timeToExecuteString, formatter);
        LocalDateTime localTime = LocalDateTime.now();//getting current time
        //Insuring current time is correct by setting the local time zone(apparently "-05:00" that is supposed to be eastern time zone...)
        ZonedDateTime localZonedTime = ZonedDateTime.of(localTime, ZoneId.of("-05:00"));
        //Converting LocalTime (representing the time to execute) into a ZonedDateTime object
        ZonedDateTime zonedTimeToExecute = localZonedTime.withHour(timeToExecute.getHour())
                .withMinute(timeToExecute.getMinute())
                .withSecond(timeToExecute.getSecond());
        //Checking if the time difference isn't negative
        if (localZonedTime.compareTo(zonedTimeToExecute) > 0) {
            zonedTimeToExecute = zonedTimeToExecute.plusDays(1);
        }
        //Getting the difference between the current time and the time to first execute the commands
        long initalTimeDelay = Duration.between(localZonedTime, zonedTimeToExecute).getSeconds();
        //initializing  the scheduler object
        scheduler = Executors.newSingleThreadScheduledExecutor();
        //Setting up the scheduler to execute every 24 hrs after the first inital delayed time
        scheduler.scheduleAtFixedRate(toExecute, initalTimeDelay, 86400, TimeUnit.SECONDS); //24 hour == 86400 seconds

        return scheduler;
    }

    /**
     * Code to execute when the timer is triggered. Two methods are called, one
     * loading MyHusky data and one loading ALEKS data.
     */
    final Runnable toExecute = () -> {
        WindyRequest wr = new WindyRequest();
		try {
			database.DatabasePropertyManager dpm = database.Database.getDatabaseManagement().getDatabasePropertyManager();
			wr.getWebcams(dpm.getPropertyValue("x-windy-key"));
		} catch (IOException ex) {
			WebErrorLogger.log(Level.SEVERE, "IOException in toExecute()", ex);
		}
    };
}
