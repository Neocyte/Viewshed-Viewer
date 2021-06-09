package database;

import java.util.Collection;

/**
 * This <code>interface</code> declares the types of actions that may be done on
 * webcam purposes.
 * 
 * @author Michael O'Donnell (2021)
 */
public interface PurposeManager {
	public String addPurpose(String purpose);
	public Collection<String> getAllPurposes();
}
