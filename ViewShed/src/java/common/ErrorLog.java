
package common;
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
import java.time.LocalDateTime;

/**
 *
 * @author Curt Jones
 */
public class ErrorLog {

   private int errorLogID ; // Primary key
   private LocalDateTime errorLogDateTime; 
   private String errLevel;
   private String loggerName;
   private String errorMessage;
   private String exception;

    public int getErrorLogID() {
        return errorLogID;
    }

    public void setErrorLogID(int errorLogID) {
        this.errorLogID = errorLogID;
    }

    public LocalDateTime getErrorLogDateTime() {
        return errorLogDateTime;
    }

    public void setErrorLogDateTime(LocalDateTime errorLogDateTime) {
        this.errorLogDateTime = errorLogDateTime;
    }

    public String getErrLevel() {
        return errLevel;
    }

    public void setErrLevel(String errLevel) {
        this.errLevel = errLevel;
    }

    public String getLoggerName() {
        return loggerName;
    }

    public void setLoggerName(String loggerName) {
        this.loggerName = loggerName;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getException() {
        return exception;
    }

    public void setException(String exception) {
        this.exception = exception;
    }

    @Override
    public String toString() {
        return "ErrorLog{" + "errorLogID=" + errorLogID + 
                ", errorLogDateTime=" + errorLogDateTime + 
                ", errLevel=" + errLevel + ", loggerName=" + 
                loggerName + ", errorMessage=" + errorMessage +
                ", exception=" + exception + '}';
    }
    
    
    
        @Override
    public int hashCode() {
        int hash = 3;
        hash = 83 * hash + this.errorLogID;
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
        final ErrorLog other = (ErrorLog) obj;
        if (this.errorLogID != other.errorLogID) {
            return false;
        }
        return true;
    }
}
    

