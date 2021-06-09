package database;

import common.Webcam;
import java.util.Collection;

/**
 * This <code>interface</code> declares the types of actions that may be done on
 * webcams.
 * 
 * @author Michael O'Donnell (2021)
 */
public interface WebcamManager {
	public Webcam addWebcam(Webcam webcam);
	public Webcam updateWebcam(Webcam webcam);
	public boolean deleteWebcam(Webcam webcam);
	public Webcam getWebcamByID(int webcamID);
	public Collection<Webcam> getAllWebcams();
        public Collection<Webcam> getAllWebcamsWithOffset(int offset);
	public Collection<Webcam> getWebcamsByLocation(float westLongitude,
			float southLatitude, float eastLongitude, float northLatitude);
	public Collection<Webcam> getWebcamsByUserNumber(int userNumber);
	public Collection<Webcam> getWebcamsByUserNumber(int userNumber, int offset);
	public Collection<Webcam> getWebcamsByAdminApproved(String approvalStatus, int offset);
	public Collection<Webcam> getWebcamsBySimilarName(String name, int limit);
	public Collection<Webcam> getAllActiveAndApprovedWebcams();
	public boolean deleteWebcamsByUserNumber(int userNumber);
}
