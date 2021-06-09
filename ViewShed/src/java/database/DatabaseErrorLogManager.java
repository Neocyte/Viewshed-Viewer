
package database;

import common.ErrorLog;
import java.util.Collection;

/**
 *
 * @author Curt Jones
 */
public interface DatabaseErrorLogManager {
    public Collection<ErrorLog> getAllErrorLogs();
    public String getAllErrorLogsAsHTMLTable();
    public void addErrorLog(ErrorLog errorLog);
    public ErrorLog getErrorLogByID(int id);
    public void deleteErrorLog(int id);
    public void deleteAllErrorLogs();
    
  
}
