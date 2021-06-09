package mysql;

import common.Webcam;
import database.SQLUtility;
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
 * Manages <code>Webcam</code>s in the webcams table. This class implements the
 * <code>database.WebcamManager</code> interface. This manager can change the
 * <code>Webcam</code> data stored in the webcams table.
 *
 * @author Michael O'Donnell (2021)
 */
public class WebcamManager implements database.WebcamManager {

	/**
	 * Adds a <code>Webcam</code> to the webcams table.
	 * 
	 * @param webcam The <code>Webcam</code> to be added.
	 * 
	 * @return The <code>Webcam</code>.
	 */
	@Override
	public Webcam addWebcam(Webcam webcam) {
		String sql = "INSERT INTO webcams(userNumber, webcamName, description, "
				+ "purpose, url, latitude, longitude, city, "
				+ "stateProvinceRegion, country, height, heightUnits, azimuth, "
				+ "fieldOfView, rotating, rotatingFieldOfView, "
				+ "verticalViewAngle, verticalFieldOfView, minViewRadius, "
				+ "minViewRadiusUnits, maxViewRadius, maxViewRadiusUnits, "
				+ "webcamColor, viewshedColor, isActive, dateSubmitted, "
				+ "dateApproved, approvalStatus) "
				+ "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		Connection connect = Web_MYSQL_Helper.getConnection();
		PreparedStatement ps = null;
		
		try {
			ps = connect.prepareStatement(sql);
			ps.setInt(1, webcam.getUserNumber());
			ps.setString(2, webcam.getWebcamName());
			ps.setString(3, webcam.getDescription());
			ps.setString(4, webcam.getPurpose());
			ps.setString(5, webcam.getUrl());
			ps.setDouble(6, webcam.getLatitude());
			ps.setDouble(7, webcam.getLongitude());
			ps.setString(8, webcam.getCity());
			ps.setString(9, webcam.getStateProvinceRegion());
			ps.setString(10, webcam.getCountry());
			ps.setInt(11, webcam.getHeight());
			ps.setString(12, webcam.getHeightUnits());
			ps.setInt(13, webcam.getAzimuth());
			ps.setInt(14, webcam.getFieldOfView());
			ps.setString(15, webcam.getRotating());
			ps.setInt(16, webcam.getRotatingFieldOfView());
			ps.setInt(17, webcam.getVerticalViewAngle());
			ps.setInt(18, webcam.getVerticalFieldOfView());
			ps.setInt(19, webcam.getMinViewRadius());
			ps.setString(20, webcam.getMinViewRadiusUnits());
			ps.setInt(21, webcam.getMaxViewRadius());
			ps.setString(22, webcam.getMaxViewRadiusUnits());
			ps.setString(23, webcam.getWebcamColor());
			ps.setString(24, webcam.getViewshedColor());
			ps.setBoolean(25, webcam.isActive());
			ps.setObject(26, webcam.getDateSubmitted());
			ps.setObject(27, webcam.getDateApproved());
			ps.setString(28, webcam.getApprovalStatus());
			ps.executeUpdate();
		} catch (SQLException ex) {
			WebErrorLogger.log(Level.SEVERE, "SQLException in addWebcam()", ex);
			Web_MYSQL_Helper.closePreparedStatement(ps);
			Web_MYSQL_Helper.returnConnection(connect);
			return null;
		}
		Web_MYSQL_Helper.closePreparedStatement(ps);
		Web_MYSQL_Helper.returnConnection(connect);

		return getWebcamByGreatestID();
	}

	/**
	 * Updates a <code>Webcam</code> in the webcams table.
	 * 
	 * @param webcam The up-to-date <code>Webcam</code>.
	 * 
	 * @return The <code>Webcam</code>.
	 */
	@Override
	public Webcam updateWebcam(Webcam webcam) {
		String sql = "UPDATE webcams SET userNumber = ?, webcamName = ?, "
				+ "description = ?, purpose = ?, url = ?, latitude = ?, "
				+ "longitude = ?, city = ?, stateProvinceRegion = ?, country = ?, "
				+ "height = ?, heightUnits = ?, azimuth = ?, "
				+ "fieldOfView = ?, rotating = ?, "
				+ "rotatingFieldOfView = ?, verticalViewAngle = ?, "
				+ "verticalFieldOfView = ?, minViewRadius = ?, minViewRadiusUnits = ?, "
				+ "maxViewRadius = ?, maxViewRadiusUnits = ?, webcamColor = ?, "
				+ "viewshedColor = ?, isActive = ?, dateSubmitted = ?, dateApproved = ?, approvalStatus = ? WHERE webcamID = ?;";
		Connection connect = Web_MYSQL_Helper.getConnection();
		PreparedStatement ps = null;
		
		try {
			ps = connect.prepareStatement(sql);
			ps.setInt(1, webcam.getUserNumber());
			ps.setString(2, webcam.getWebcamName());
			ps.setString(3, webcam.getDescription());
			ps.setString(4, webcam.getPurpose());
			ps.setString(5, webcam.getUrl());
			ps.setDouble(6, webcam.getLatitude());
			ps.setDouble(7, webcam.getLongitude());
			ps.setString(8, webcam.getCity());
			ps.setString(9, webcam.getStateProvinceRegion());
			ps.setString(10, webcam.getCountry());
			ps.setInt(11, webcam.getHeight());
			ps.setString(12, webcam.getHeightUnits());
			ps.setInt(13, webcam.getAzimuth());
			ps.setInt(14, webcam.getFieldOfView());
			ps.setString(15, webcam.getRotating());
			ps.setInt(16, webcam.getRotatingFieldOfView());
			ps.setInt(17, webcam.getVerticalViewAngle());
			ps.setInt(18, webcam.getVerticalFieldOfView());
			ps.setInt(19, webcam.getMinViewRadius());
			ps.setString(20, webcam.getMinViewRadiusUnits());
			ps.setInt(21, webcam.getMaxViewRadius());
			ps.setString(22, webcam.getMaxViewRadiusUnits());
			ps.setString(23, webcam.getWebcamColor());
			ps.setString(24, webcam.getViewshedColor());
			ps.setBoolean(25, webcam.isActive());
			ps.setObject(26, webcam.getDateSubmitted());
			ps.setObject(27, webcam.getDateApproved());
			ps.setString(28, webcam.getApprovalStatus());
			ps.setInt(29, webcam.getWebcamID());
			ps.executeUpdate();
		} catch (SQLException ex) {
			WebErrorLogger.log(Level.SEVERE, "SQLException in updateWebcam()", ex);
			Web_MYSQL_Helper.closePreparedStatement(ps);
			Web_MYSQL_Helper.returnConnection(connect);
			return null;
		}
		Web_MYSQL_Helper.closePreparedStatement(ps);
		Web_MYSQL_Helper.returnConnection(connect);

		return getWebcamByID(webcam.getWebcamID());
	}

	/**
	 * Deletes a <code>Webcam</code> from the webcams table.
	 * 
	 * @param webcam The <code>Webcam</code> to delete.
	 * 
	 * @return If the <code>Webcam</code> was successfully deleted.
	 */
	@Override
	public boolean deleteWebcam(Webcam webcam) {
		String sql = "DELETE FROM webcams WHERE webcamID = ?;";
		Connection connect = Web_MYSQL_Helper.getConnection();
		PreparedStatement ps = null;
		
		try {
			ps = connect.prepareStatement(sql);
			ps.setInt(1, webcam.getWebcamID());
			ps.executeUpdate();
			Web_MYSQL_Helper.closePreparedStatement(ps);
			Web_MYSQL_Helper.returnConnection(connect);
			return getWebcamByID(webcam.getWebcamID()) == null;
		} catch (SQLException ex) {
			WebErrorLogger.log(Level.SEVERE, "SQLException in deleteWebcam()", ex);
			Web_MYSQL_Helper.closePreparedStatement(ps);
			Web_MYSQL_Helper.returnConnection(connect);
			return false;
		}
	}

	/**
	 * Gets a <code>Webcam</code> by it's ID number.
	 * 
	 * @param webcamID The <code>Webcam</code>'s ID number.
	 * 
	 * @return The <code>Webcam</code> with the given id.
	 */
	@Override
	public Webcam getWebcamByID(int webcamID) {
		String sql = "SELECT * FROM webcams WHERE webcamID = ?;";
		Connection connect = Web_MYSQL_Helper.getConnection();
		Webcam webcam = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = connect.prepareStatement(sql);
			ps.setInt(1, webcamID);
			rs = ps.executeQuery();
			
			if (rs == null || rs.next() == false) {
				close(rs, ps, connect);
				return null;
			}
			
			webcam = SQLUtility.convertResultSetToWebcam(rs);
		} catch (SQLException ex) {
			WebErrorLogger.log(Level.SEVERE, "SQLException in getWebcamByID()", ex);
			close(rs, ps, connect);
			return null;
		}
		close(rs, ps, connect);
		
		return webcam;
	}

	/**
	 * Gets all of the <code>Webcam</code>s in the webcams table.
	 * 
	 * @return All of the <code>Webcam</code>s in the webcams table.
	 */
	@Override
	public Collection<Webcam> getAllWebcams() {
		String sql = "SELECT * FROM webcams;";
		Connection connect = Web_MYSQL_Helper.getConnection();
		Webcam webcam = null;
		Collection<Webcam> webcams = new ArrayList<>();
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
				webcam = SQLUtility.convertResultSetToWebcam(rs);
				webcams.add(webcam);
			}
		} catch (SQLException ex) {
			WebErrorLogger.log(Level.SEVERE, "SQLException in getAllWebcams()", ex);
			Web_MYSQL_Helper.closeResultSet(rs);
			Web_MYSQL_Helper.closeStatement(stmt);
			Web_MYSQL_Helper.returnConnection(connect);
			return null;
		}
		Web_MYSQL_Helper.closeResultSet(rs);
		Web_MYSQL_Helper.closeStatement(stmt);
		Web_MYSQL_Helper.returnConnection(connect);

		return webcams;
	}
        
        
	/**
	 * Gets all of the <code>Webcam</code>s in the webcams table 
         * in sets of 20.
	 * 
         * @param offset The number of previously displayed webcams to skip
	 * @return All of the <code>Webcam</code>s in the webcams table.
	 */
	@Override
	public Collection<Webcam> getAllWebcamsWithOffset(int offset) {
		String sql = "SELECT * FROM webcams LIMIT 20 " + "OFFSET " + offset + ";";
		Connection connect = Web_MYSQL_Helper.getConnection();
		Webcam webcam = null;
		Collection<Webcam> webcams = new ArrayList<>();
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
				webcam = SQLUtility.convertResultSetToWebcam(rs);
				webcams.add(webcam);
			}
		} catch (SQLException ex) {
			WebErrorLogger.log(Level.SEVERE, "SQLException in getAllWebcamsWithOffset()", ex);
			Web_MYSQL_Helper.closeResultSet(rs);
			Web_MYSQL_Helper.closeStatement(stmt);
			Web_MYSQL_Helper.returnConnection(connect);
			return null;
		}
		Web_MYSQL_Helper.closeResultSet(rs);
		Web_MYSQL_Helper.closeStatement(stmt);
		Web_MYSQL_Helper.returnConnection(connect);

		return webcams;
	}

	/**
	 * Gets all webcams within the bounding box formed by a western-most
	 * longitude, southern-most latitude, eastern-most longitude, and
	 * northern-most latitude (inclusive to each latitude and longitude).
	 * 
	 * @param westLongitude The furthest west the returned <code>Webcam</code>s
	 * will be.
	 * 
	 * @param southLatitude The furthest south the returned <code>Webcam</code>s
	 * will be.
	 * 
	 * @param eastLongitude The furthest east the returned <code>Webcam</code>s
	 * will be.
	 * 
	 * @param northLatitude The furthest north the returned <code>Webcam</code>s
	 * will be.
	 * 
	 * @return A <code>Collection</code> of the <code>Webcam</code>s that are in
	 * the specified bounding box.
	 */
	@Override
	public Collection<Webcam> getWebcamsByLocation(float westLongitude,
			float southLatitude, float eastLongitude, float northLatitude) {
		String sql;
		if (westLongitude < eastLongitude) {
			sql = "SELECT * FROM webcams WHERE longitude >= ? && "
				+ "longitude <= ? && latitude >= ? && "
				+ "latitude <= ? && approvalStatus = 'approved';";
		}
		else {
			sql = "SELECT * FROM webcams WHERE (longitude >= ? || "
					+ "longitude <= ?) && latitude >= ? && latitude <= ? && "
					+ "approvalStatus = 'approved';";
		}
		Connection connect = Web_MYSQL_Helper.getConnection();
		Webcam webcam = null;
		Collection<Webcam> webcams = new ArrayList<>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = connect.prepareStatement(sql);
			ps.setDouble(1, westLongitude);
			ps.setDouble(2, eastLongitude);
			ps.setDouble(3, southLatitude);
			ps.setDouble(4, northLatitude);
			rs = ps.executeQuery();
			
			if (rs == null) {
				close(rs, ps, connect);
				return null;
			}
			
			while (rs.next()) {
				webcam = SQLUtility.convertResultSetToWebcam(rs);
				webcams.add(webcam);
			}
		} catch (SQLException ex) {
			WebErrorLogger.log(Level.SEVERE, "SQLException in getWebcamsByLocation()", ex);
			close(rs, ps, connect);
			return null;
		}
		close(rs, ps, connect);
		
		return webcams;
	}

	@Override
	public Collection<Webcam> getWebcamsByUserNumber(int userNumber) {
		String sql = "SELECT * FROM webcams WHERE userNumber = ?;";
		Connection connect = Web_MYSQL_Helper.getConnection();
		Webcam webcam = null;
		Collection<Webcam> webcams = new ArrayList<>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = connect.prepareStatement(sql);
			ps.setInt(1, userNumber);
			rs = ps.executeQuery();
			
			if (rs == null) {
				close(rs, ps, connect);
				return null;
			}
			
			while (rs.next()) {
				webcam = SQLUtility.convertResultSetToWebcam(rs);
				webcams.add(webcam);
			}
			rs.close();
		} catch (SQLException ex) {
			WebErrorLogger.log(Level.SEVERE, "SQLException in getWebcamsByUserNumber()", ex);
			close(rs, ps, connect);
			return null;
		}
		close(rs, ps, connect);
		
		return webcams;
	}
	
	/**
	 * Gets all webcams from the database associated with the specified user
         * in sets of 20.
	 * 
	 * @param userNumber The user number of the user associated with the
	 * webcams.
         * @param offset The number of previously displayed webcams to skip
	 * 
	 * @return A <code>Collection</code> of <code>Webcam</code>s that are
	 * associated with the specified user.
	 */
	@Override
	public Collection<Webcam> getWebcamsByUserNumber(int userNumber, int offset) {
		String sql = "SELECT * FROM webcams WHERE userNumber = ? LIMIT 20 OFFSET " + offset + ";";
		Connection connect = Web_MYSQL_Helper.getConnection();
		Webcam webcam = null;
		Collection<Webcam> webcams = new ArrayList<>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = connect.prepareStatement(sql);
			ps.setInt(1, userNumber);
			rs = ps.executeQuery();
			
			if (rs == null) {
				close(rs, ps, connect);
				return null;
			}
			
			while (rs.next()) {
				webcam = SQLUtility.convertResultSetToWebcam(rs);
				webcams.add(webcam);
			}
			rs.close();
		} catch (SQLException ex) {
			WebErrorLogger.log(Level.SEVERE, "SQLException in getWebcamsByUserNumber()", ex);
			close(rs, ps, connect);
			return null;
		}
		close(rs, ps, connect);
		
		return webcams;
	}

	/**
	 * Gets all webcams from the database with the specified approval rating
         * in sets of 20.
	 * 
	 * @param approvalStatus If the webcams are approved.
         * @param offset The number of previously displayed webcams to skip
	 * 
	 * @return A <code>Collection</code> of <code>Webcam</code>s that have the
	 * specified approval rating.
	 */
	@Override
	public Collection<Webcam> getWebcamsByAdminApproved(String approvalStatus, int offset) {
		String sql = "SELECT * FROM webcams WHERE approvalStatus = ? LIMIT 20 OFFSET " + offset + ";";
		Connection connect = Web_MYSQL_Helper.getConnection();
		Webcam webcam = null;
		Collection<Webcam> webcams = new ArrayList<>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = connect.prepareStatement(sql);
			ps.setString(1, approvalStatus);
			rs = ps.executeQuery();
			
			if (rs == null) {
				close(rs, ps, connect);
				return null;
			}
			
			while (rs.next()) {
				webcam = SQLUtility.convertResultSetToWebcam(rs);
				webcams.add(webcam);
			}
		} catch (SQLException ex) {
			WebErrorLogger.log(Level.SEVERE, "SQLException in getWebcamsByAdminApproved()", ex);
			close(rs, ps, connect);
			return null;
		}
		close(rs, ps, connect);
		
		return webcams;
	}
	
	/**
	 * Returns a <code>Collection</code> of <code>Webcam</code>s with the
	 * maximum length of the limit.
	 * 
	 * @param name The name the returned <code>Webcam</code>s will have similar
	 * names to.
	 * 
	 * @param limit The maximum length of the <code>Collection</code>.
	 * 
	 * @return A <code>Collection</code> of <code>Webcam</code>s with similar
	 * names to the input string.
	 */
	@Override
	public Collection<Webcam> getWebcamsBySimilarName(String name, int limit) {
		String sql = "SELECT * FROM webcams WHERE approvalStatus = 'approved' and isActive = 1 and upper(webcamName) LIKE ? LIMIT ?;";
		Connection connect = Web_MYSQL_Helper.getConnection();
		Webcam webcam = null;
		Collection<Webcam> webcams = new ArrayList<>();
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = connect.prepareStatement(sql);
			name = name.replace('+', ' ').toUpperCase();
			ps.setString(1, "%" + name + "%");
			ps.setInt(2, limit);
			rs = ps.executeQuery();
			
			if (rs == null) {
				close(rs, ps, connect);
				return null;
			}
			
			while (rs.next()) {
				webcam = SQLUtility.convertResultSetToWebcam(rs);
				webcams.add(webcam);
			}
		} catch (SQLException ex) {
			WebErrorLogger.log(Level.SEVERE, "SQLException in getWebcamsBySimilarName()", ex);
			close(rs, ps, connect);
			return null;
		}
		close(rs, ps, connect);
		
		return webcams;
	}

	/**
	 * Returns a <code>Collection</code> of <code>Webcam</code>s that are both
	 * approved and active.
	 * 
	 * @return A <code>Collection</code> of <code>Webcam</code>s that are both
	 * approved and active.
	 */
	@Override
	public Collection<Webcam> getAllActiveAndApprovedWebcams() {
		String sql = "SELECT * FROM webcams WHERE approvalStatus = 'approved' and isActive = 1;";
		Connection connect = Web_MYSQL_Helper.getConnection();
		Webcam webcam = null;
		Collection<Webcam> webcams = new ArrayList<>();
		
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
				webcam = SQLUtility.convertResultSetToWebcam(rs);
				webcams.add(webcam);
			}
		} catch (SQLException ex) {
			WebErrorLogger.log(Level.SEVERE, "SQLException in getAllActiveAndApprovedWebcams()", ex);
			Web_MYSQL_Helper.closeResultSet(rs);
			Web_MYSQL_Helper.closeStatement(stmt);
			Web_MYSQL_Helper.returnConnection(connect);
			return null;
		}
		
		Web_MYSQL_Helper.closeResultSet(rs);
		Web_MYSQL_Helper.closeStatement(stmt);
		Web_MYSQL_Helper.returnConnection(connect);
		
		return webcams;
	}

	/**
	 * Deletes all of the webcams associated with a user.
	 * 
	 * @param userNumber The user's number.
	 * 
	 * @return If this method succeeded.
	 */
	@Override
	public boolean deleteWebcamsByUserNumber(int userNumber) {
		String sql = "DELETE FROM webcams WHERE userNumber = ?;";
		Connection connect = Web_MYSQL_Helper.getConnection();
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = connect.prepareStatement(sql);
			ps.setInt(1, userNumber);
			rs = ps.executeQuery();
			
			if (rs == null) {
				close(rs, ps, connect);
				return false;
			}
		} catch (SQLException ex) {
			WebErrorLogger.log(Level.SEVERE, "SQLException in deleteWebcamsByUserNumber()", ex);
			close(rs, ps, connect);
			return false;
		}
		close(rs, ps, connect);
		
		return true;
	}
	
	/**
	 * Closes the given <code>ResultSet</code>, <code>PreparedStatement</code>, and <code>Connection</code>.
	 * 
	 * @param rs The <code>ResultSet</code> to close.
	 * 
	 * @param ps The <code>PreparedStatement</code> to close.
	 * 
	 * @param connect The <code>Connection</code> to close.
	 */
	private void close(ResultSet rs, PreparedStatement ps, Connection connect) {
		if (rs != null)
			Web_MYSQL_Helper.closeResultSet(rs);
		Web_MYSQL_Helper.closePreparedStatement(ps);
		Web_MYSQL_Helper.returnConnection(connect);
	}
	
	/**
	 * Gets the <code>Webcam</code> with the greatest webcamID (which would be
	 * the most recently added <code>Webcam</code>).
	 * 
	 * @return The <code>Webcam</code> with the greatest webcamID.
	 */
	private Webcam getWebcamByGreatestID() {
		String sql = "SELECT * FROM webcams WHERE webcamID = (SELECT max(webcamID) FROM webcams);";
		Connection connect = Web_MYSQL_Helper.getConnection();
		Webcam webcam = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			stmt = connect.createStatement();
			rs = stmt.executeQuery(sql);
			
			if (rs == null || rs.next() == false) {
				Web_MYSQL_Helper.closeStatement(stmt);
				Web_MYSQL_Helper.returnConnection(connect);
				return null;
			}
			
			webcam = SQLUtility.convertResultSetToWebcam(rs);
		} catch (SQLException ex) {
			WebErrorLogger.log(Level.SEVERE, "SQLException in getWebcamByGreatestID()", ex);
			Web_MYSQL_Helper.closeResultSet(rs);
			Web_MYSQL_Helper.closeStatement(stmt);
			Web_MYSQL_Helper.returnConnection(connect);
			return null;
		}
		Web_MYSQL_Helper.closeResultSet(rs);
		Web_MYSQL_Helper.closeStatement(stmt);
		Web_MYSQL_Helper.returnConnection(connect);
		
		return webcam;
	}
	
	/**
	 * Please do not remove this method.
	 * 
	 * DISCLAIMER: If this method needs to be run for any reason, back up the
	 * current webcams table if necessary as this modifies the webcams table and
	 * clears it at the end.
	 *
	 * @param args Java arguments.
	 */
	public static void main(String[] args) {
		Debug.setEnabled(true);
		final String propertyFilePath = common.Paths.HOMEDIRECTORY+"/web/WEB-INF/config/General.properties";
		PropertyManager.configure(propertyFilePath);
		WebcamManager wm = new WebcamManager();
		
		// Test getWebcamByID() and addWebcam()
		Webcam w1 = wm.getWebcamByID(1);
		w1.setLatitude(3);
		w1.setLongitude(-0.5F);
		wm.addWebcam(w1);
		
		// Test getWebcamsByLocation()
		Collection<Webcam> loc = wm.getWebcamsByLocation(1, 0, 0, 90);
		
		for (Webcam webcam : loc) {
			Debug.println(webcam);
		}
		
		// Test getAllWebcams()
		Collection<Webcam> webcams = wm.getAllWebcams();
		
		for (Webcam webcam : webcams) {
			Debug.println(webcam);
		}

		// Test getWebcamsByAdminApproved()
		Collection<Webcam> approved = wm.getWebcamsByAdminApproved("approved", 0);
		
		for (Webcam webcam : approved) {
			Debug.println(webcam);
		}
		
		// Test getWebcamsByUserNumber() (assumes there is a user 6)
		Collection<Webcam> user6Cams = wm.getWebcamsByUserNumber(6, 0);
		
		for (Webcam webcam : user6Cams) {
			Debug.println(webcam);
		}
		
		// Test addWebcam()
		for (Webcam webcam : webcams) {
			Webcam w2 = wm.addWebcam(webcam);
			Debug.println("Webcam " + w2.getWebcamID() + " added: " + w2);
		}
		
		// Test updateWebcam()
		Webcam w4 = wm.getWebcamByID(4);
		w4.setDescription("I am still a dummy");
		wm.updateWebcam(w4);

		// Test deleteWebcam()
		Webcam w5 = wm.getWebcamByID(5);
		wm.deleteWebcam(w5);
		
		// Clear the webcams table
		for (Webcam webcam : webcams) {
			Debug.println("Webcam " + webcam.getWebcamID() + " deleted: " + wm.deleteWebcam(webcam));
		}
	}
}
