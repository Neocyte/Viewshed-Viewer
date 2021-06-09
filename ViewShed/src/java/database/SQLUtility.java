package database;

import common.DatabaseProperty;
import common.ErrorLog;
import common.User;
import common.UserRole;
import common.Webcam;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.logging.Level;
import utilities.WebErrorLogger;

/**
 * A utility class designed to convert result set entries to  
 * Java objects. 
 * 
 * @author Curt Jones (2018)
 * @author Michael O'Donnell (2021)
 */
public class SQLUtility {
	
	/**
	 * Converts a <code>ResultSet</code> object to a <code>Webcam</code> object.
	 * 
	 * @param rs The <code>ResultSet</code> containing the information needed to
	 * make a <code>Webcam</code>.
	 * 
	 * @return A <code>Webcam</code>.
	 */
	public static Webcam convertResultSetToWebcam(ResultSet rs) {
		Webcam webcam = new Webcam();
		try {
			webcam.setWebcamID(rs.getInt("webcamID"));
			webcam.setUserNumber(rs.getInt("userNumber"));
			webcam.setWebcamName(rs.getString("webcamName"));
			webcam.setDescription(rs.getString("description"));
			webcam.setPurpose(rs.getString("purpose"));
			webcam.setUrl(rs.getString("url"));
			webcam.setLatitude(rs.getDouble("latitude"));
			webcam.setLongitude(rs.getDouble("longitude"));
			webcam.setCity(rs.getString("city"));
			webcam.setStateProvinceRegion(rs.getString("stateProvinceRegion"));
			webcam.setCountry(rs.getString("country"));
			webcam.setHeight(rs.getInt("height"));
			webcam.setHeightUnits(rs.getString("heightUnits"));
			webcam.setAzimuth(rs.getInt("azimuth"));
			webcam.setFieldOfView(rs.getInt("fieldOfView"));
			webcam.setRotating(rs.getString("rotating"));
			webcam.setRotatingFieldOfView(rs.getInt("rotatingFieldOfView"));
			webcam.setVerticalViewAngle(rs.getInt("verticalViewAngle"));
			webcam.setVerticalFieldOfView(rs.getInt("verticalFieldOfView"));
			webcam.setMinViewRadius(rs.getInt("minViewRadius"));
			webcam.setMinViewRadiusUnits(rs.getString("minViewRadiusUnits"));
			webcam.setMaxViewRadius(rs.getInt("maxViewRadius"));
			webcam.setMaxViewRadiusUnits(rs.getString("maxViewRadiusUnits"));
			webcam.setWebcamColor(rs.getString("webcamColor"));
			webcam.setViewshedColor(rs.getString("viewshedColor"));
			webcam.setActive(rs.getBoolean("isActive"));
			Timestamp t1 = rs.getTimestamp("dateSubmitted");
			if (t1 != null) {
				webcam.setDateSubmitted(rs.getTimestamp("dateSubmitted").toLocalDateTime());
			}
			Timestamp t2 = rs.getTimestamp("dateApproved");
			if (t2 != null) {
				webcam.setDateApproved(rs.getTimestamp("dateApproved").toLocalDateTime());
			}
			webcam.setApprovalStatus(rs.getString("approvalStatus"));
		} catch (SQLException ex) {
			WebErrorLogger.log(Level.SEVERE, "SQLException in convertResultSetToWebcam()", ex);
			return null;
		}
		
		return webcam;
	}

    public static User convertResultSetToUser(ResultSet rs) {
        User user = new User();
        try {
            user.setUserNumber(rs.getInt("userNumber"));
            user.setUserPassword(rs.getString("userPassword"));
            user.setLoginName(rs.getString("loginName"));
            user.setEmailAddress(rs.getString("emailAddress"));
            user.setFirstName(rs.getString("firstName"));
            user.setLastName(rs.getString("lastName"));
            user.setUserRole(UserRole.getUserRole(rs.getString("userRole")));
            LocalDateTime now = rs.getTimestamp("lastLoginTime").toLocalDateTime();
            user.setLastLoginTime(now);
            if (rs.getString("LastAttemptedLoginTime") == null) {
                user.setLastAttemptedLoginTime(LocalDateTime.now());
            } else {
                user.setLastAttemptedLoginTime(rs.getTimestamp("LastAttemptedLoginTime").toLocalDateTime());
            }
            int loginCount = rs.getInt("loginCount");
            user.setLoginCount(loginCount);
            user.setAttemptedLoginCount(rs.getInt("attemptedLoginCount"));
            user.setLocked(rs.getBoolean("locked"));
            user.setSalt(rs.getString("salt"));

        } catch (SQLException ex) {
            WebErrorLogger.log(Level.SEVERE, "SQLException in convertResultSetToUser()" + ex, ex);
            return null;
        }
        return user;
    }
    
    public static DatabaseProperty convertResultSetToDatabaseProperty(ResultSet rs) {
        DatabaseProperty property = new DatabaseProperty();
        try {
            property.setPropertyNumber(rs.getInt("propertyNumber"));
            property.setPropertyName(rs.getString("propertyName"));
            property.setPropertyValue(rs.getString("propertyValue"));
            property.setDescription(rs.getString("description"));
            property.setPreviousValue(rs.getString("previousValue"));
            property.setDefaultValue(rs.getString("defaultValue"));
        } catch (SQLException ex) {
            WebErrorLogger.log(Level.SEVERE, "SQLException in convertResultSetToDatabaseProperty. " + ex, ex);
            return null;
        }
        return property;
    }

    public static ErrorLog convertResultSetToErrorLog(ResultSet rs) {
         ErrorLog errorLog = new ErrorLog();
         try {
            errorLog.setErrorLogID(rs.getInt("EVENT_ID"));
            String date = rs.getString("EVENT_DATE");
            LocalDateTime time = LocalDateTime.parse(date);
            errorLog.setErrorLogDateTime(time);
            errorLog.setErrLevel(rs.getString("LEVEL"));
            errorLog.setLoggerName(rs.getString("LOGGER"));
            errorLog.setErrorMessage(rs.getString("MSG"));
            errorLog.setException(rs.getString("THROWABLE"));
        } catch (SQLException ex) {
            WebErrorLogger.log(Level.SEVERE, "SQLException in convertResultSetToErrorLog(ResultSet rs). " + ex, ex);
            return null;
        }
         
         return errorLog;
    }
}
