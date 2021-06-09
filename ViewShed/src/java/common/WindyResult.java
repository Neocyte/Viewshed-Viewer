package common;

/**
 * The result from calling windy.com's REST API.
 *
 * @author Michael O'Donnell (2021)
 */
public class WindyResult {
	public int offset;
	public int limit;
	public int total;
	
	public WindyWebcam[] webcams;
}
