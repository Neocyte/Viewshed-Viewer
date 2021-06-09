package mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;
import utilities.Debug;
import utilities.PropertyManager;
import utilities.WebErrorLogger;

/**
 * Manages Purposes in the webcam_purposes table. This class implements the
 * <code>database.PurposeManager</code> interface. This manager can change the
 * Purpose data stored in the webcams table.
 * 
 * @author Michael O'Donnell (2021)
 */
public class PurposeManager implements database.PurposeManager {

	/**
	 * Adds a purpose to the webcam_purposes table.
	 * 
	 * @param purpose The purpose to be added to the table.
	 * 
	 * @return The purpose that was just added.
	 */
	@Override
	public String addPurpose(String purpose) {
		String sql = "INSERT INTO webcam_purposes(purpose) VALUES(?)";
		Connection connect = Web_MYSQL_Helper.getConnection();
		PreparedStatement ps = null;
		
		try {
			ps = connect.prepareStatement(sql);
			ps.setString(1, purpose);
			ps.executeUpdate();
		} catch (SQLException ex) {
			WebErrorLogger.log(Level.SEVERE, "SQLException in addPurpose()", ex);
			Web_MYSQL_Helper.closePreparedStatement(ps);
			Web_MYSQL_Helper.returnConnection(connect);
			return null;
		}
		Web_MYSQL_Helper.closePreparedStatement(ps);
		Web_MYSQL_Helper.returnConnection(connect);
		
		return purpose;
	}

	/**
	 * Gets all purposes for the webcams.
	 * 
	 * @return A <code>Collection</code> of <code>String</code>s where each
	 * <code>String</code> is a webcam purpose.
	 */
	@Override
	public Collection<String> getAllPurposes() {
		String sql = "SELECT * FROM webcam_purposes;";
		Connection connect = Web_MYSQL_Helper.getConnection();
		Collection<String> purposes = new ArrayList<>();
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			stmt = connect.createStatement();
			rs = stmt.executeQuery(sql);
			
			if (rs == null) {
				Web_MYSQL_Helper.closeStatement(stmt);
				Web_MYSQL_Helper.returnConnection(connect);
				return null;
			}
			
			while (rs.next()) {
				purposes.add(rs.getString("purpose"));
			}
		} catch (SQLException ex) {
			WebErrorLogger.log(Level.SEVERE, "SQLException in getAllPurposes()", ex);
			Web_MYSQL_Helper.closeStatement(stmt);
			Web_MYSQL_Helper.returnConnection(connect);
			return null;
		}
		Web_MYSQL_Helper.closeStatement(stmt);
		Web_MYSQL_Helper.returnConnection(connect);
		
                //Move "Other" to the end of the ArrayList for display purposes
                purposes.remove("Other");
                purposes.add("Other");
		return purposes;
	}
	
	public static void main(String[] args) {
		Debug.setEnabled(true);
		final String propertyFilePath = common.Paths.HOMEDIRECTORY+"/web/WEB-INF/config/General.properties";
		PropertyManager.configure(propertyFilePath);
		PurposeManager pm = new PurposeManager();
		
		pm.addPurpose("skylineCam");
		
		Collection<String> purposes = pm.getAllPurposes();
		
		for (String purpose : purposes) {
			Debug.println(purpose);
		}
	}
}
