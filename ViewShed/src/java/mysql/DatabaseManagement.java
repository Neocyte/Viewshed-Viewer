package mysql;

import database.DatabaseErrorLogManager;
import database.DatabasePropertyManager;
import database.PurposeManager;
import database.WebcamManager;


/**
 * Represents a MySQL database that implements the <code>DatabaseManagement</code>
 * interface.
 *
 * @author Curt Jones (2018)
 */
public class DatabaseManagement implements database.DatabaseManagement {

    private database.UserManager userManager; 
    private database.DatabasePropertyManager databasePropertyManager;
    private database.DatabaseErrorLogManager databaseErrorLogManager;
	private database.WebcamManager webcamManager;
	private database.PurposeManager purposeManager;

    @Override
    public void initializeDatabaseManagement() {

    }

    @Override
    public void CreateTables() {
        throw new UnsupportedOperationException("Not yet a supported operation!"); 
    }

    /**
     * Returns a <code>UserManager</code> object for this
     * <code>DatabaseManagement</code>.
     *
     * @return A <code>UserManager</code>  object.
     * @see common.User 
     */
    @Override
    public database.UserManager getUserManager() {
        if (userManager == null) userManager = new mysql.UserManager();
        return userManager;
    }

	/**
	 * Returns a <code>DatabasePropertyManager</code> object for this
	 * <code>DatabaseManagement</code>.
	 * 
	 * @return A <code>DatabasePropertyManager</code> object.
	 * @see common.DatabaseProperty
	 */
    @Override
    public database.DatabasePropertyManager getDatabasePropertyManager() {
        if (databasePropertyManager == null) databasePropertyManager = new mysql.DatabasePropertyManager();
        return databasePropertyManager;
    }

	/**
	 * Returns a <code>DatabaseErrorLogManager</code> object for this
	 * <code>DatabaseManagement</code>.
	 * 
	 * @return A <code>DatabaseErrorLogManager</code> object.
	 * @see common.ErrorLog
	 */
    @Override
    public DatabaseErrorLogManager getDatabaseErrorLogManager() {
        if(databaseErrorLogManager==null) {
            databaseErrorLogManager = new mysql.DatabaseErrorLogManager();
        }
        return  databaseErrorLogManager;
    }

	/**
	 * Returns a <code>WebcamManager</code> object for this
	 * <code>DatabaseManagement</code>.
	 *
	 * @return A <code>WebcamManager</code> object.
	 * @see common.Webcam
	 */
	@Override
	public WebcamManager getWebcamManager() {
		if (webcamManager == null) webcamManager = new mysql.WebcamManager();
        return webcamManager;
	}

	/**
	 * Returns a <code>PurposeManager</code> object for this
	 * <code>DatabaseManagement</code>.
	 * 
	 * @return A <code>PurposeManager</code> object.
	 */
	@Override
	public PurposeManager getPurposeManager() {
		if (purposeManager == null) purposeManager = new mysql.PurposeManager();
		return purposeManager;
	}

 }
