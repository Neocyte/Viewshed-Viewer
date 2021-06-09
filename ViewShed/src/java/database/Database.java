
package database;

/**
 *
 * @author Curt Jones (2017)
 */
public  class Database {
    private static DatabaseManagement database=null;
    
        
    public static synchronized DatabaseManagement getDatabaseManagement(){
       if(database == null) intialize();
       return database;
   }

    private static void intialize() {

            database = new mysql.DatabaseManagement();

        
        // database.initializeDatabaseManagement(); // may or may not be needed by your application
    }
    
}
