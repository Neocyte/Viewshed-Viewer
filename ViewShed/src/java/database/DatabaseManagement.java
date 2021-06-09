
package database;

/**
 * <code>DatabaseManagement</code> is a <code>interface</code> designed to
 * specify the database managers  
 * along with high level operations available for the database we are 
 * using in this application. 
 * Examples include:
 * <pre>
 * public void CreateTables();
 * public void initializeDatabaseManagement();
 * public UserManager getUserManager();
 * </pre>
 * 
 * @author Curt Jones (2017)
 * @author Michael O'Donnell (2021)
 */
public interface DatabaseManagement {
    
    public void CreateTables();
    public void initializeDatabaseManagement();
    public UserManager getUserManager();
    public DatabasePropertyManager getDatabasePropertyManager();
    public DatabaseErrorLogManager getDatabaseErrorLogManager();
    public WebcamManager getWebcamManager();
	public PurposeManager getPurposeManager();

}
