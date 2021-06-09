
package database;

import common.User;
import java.util.Collection;

/**
 * A <code>interface</code> that specifies the allowable operations on users in
 * the system. 
 * 
 * 
 * @author Curt Jones (2017)
 */
public interface UserManager {
    public User validateUser(String loginName, String password);
    public User addUser(User user); 
    public User updateUser(User user);
    public boolean deleteUser(User user);
    public User getUserByID(int userID); 
    public User getUserByLoginName(String loginName );
    public Collection<User> getAllUsersWithEmailAddress(String emailAddress);
    public Collection<User> getAllUsers();
    public boolean deleteUserbyID(int  userID);
    public Collection<User> getAllUsersWithoutSystemAdmins();
    public String getSaltByLoginName(String loginName);
    public Collection<User> getSystemAdmins();
}
