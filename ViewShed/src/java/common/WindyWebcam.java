package common;

/**
 * A webcam in the response from windy.com's REST API.
 *
 * @author Michael O'Donnell (2021)
 */
public class WindyWebcam {
	public String id;
	public String status;
	public String title;
	
	public WindyCategory[] category;
	public WindyLocation location;
	public WindyUrl url;
}
