package utilities;

import common.Webcam;
import common.WindyWebcam;
import java.time.LocalDateTime;
import java.util.Random;

/**
 * Utility class with useful functions for the servlets.
 * 
 * @author Michael O'Donnell (2021)
 */
public class WebcamUtil {
	
	/**
	 * Converts a <code>WindyWebcam</code> to a <code>Webcam</code>.
	 * 
	 * @param webcam The <code>WindyWebcam</code> to be converted.
	 * 
	 * @return The equivalent <code>Webcam</code>.
	 */
	public static Webcam windyWebcamToCommonWebcam(WindyWebcam webcam) {
		Webcam result = new Webcam();

		database.UserManager um = database.Database.getDatabaseManagement().getUserManager();

		result.setUserNumber(um.getUserByLoginName("WindyUser").getUserNumber());
		result.setWebcamName(webcam.title);
		result.setUrl(webcam.url.current.desktop);
		result.setLatitude(webcam.location.latitude);
		result.setLongitude(webcam.location.longitude);
		result.setCity(webcam.location.city);
		result.setStateProvinceRegion(webcam.location.region);
		result.setCountry(webcam.location.country);
		result.setActive(webcam.status.equals("active"));
		result.setDateSubmitted(LocalDateTime.now());
		result.setDateApproved(LocalDateTime.now());
		result.setApprovalStatus("approved");
		
		// Defaults
		result.setDescription(webcam.title);
		result.setPurpose(webcam.category[0].name); // Get the first purpose
		result.setHeight(5);
		result.setHeightUnits("ft");
		result.setAzimuth(-1);
		result.setFieldOfView(360);
		result.setRotating("360DegreeRotation");
		result.setRotatingFieldOfView(0);
		result.setVerticalViewAngle(0);
		result.setVerticalFieldOfView(0);
		result.setMinViewRadius(0);
		result.setMinViewRadiusUnits("mi");
		result.setMaxViewRadius(10);
		result.setMaxViewRadiusUnits("mi");
		result.setWebcamColor("FFFFFF");
		result.setViewshedColor("FFFFFF");

		return result;
	}

	/**
	 * Convert unit label to fit database.
	 * 
	 * @param unit Unit from addWebcam.jsp.
	 * 
	 * @return Unit the database expects.
	 */
	public static String units(String unit) {
		switch (unit) {
			case "feet":
				return "ft";
			case "miles":
				return "mi";
			case "meters":
				return "m";
			case "kilometers":
				return "km";
			default:
				return null;
		}
	}

	/**
	 * Convert azimuth from <code>String</code> to int.
	 * 
	 * @param azimuth The general azimuth of the webcam.
	 * 
	 * @return The int representation of the general azimuth.
	 */
	public static int azimuth(String azimuth) {
		switch (azimuth) {
			case "north":
				return 0;
			case "northeast":
				return 45;
			case "east":
				return 90;
			case "southeast":
				return 135;
			case "south":
				return 180;
			case "southwest":
				return 225;
			case "west":
				return 270;
			case "northwest":
				return 315;
			default:
				return -1;
		}
	}
	
	/**
	 * Converts DMS coordinates to decimal format.
	 * 
	 * @param deg The degrees of the coordinate.
	 * 
	 * @param min The minutes of the coordinate.
	 * 
	 * @param sec The seconds of the coordinate.
	 * 
	 * @param dir The cardinal direction of the coordinate.
	 * 
	 * @return The coordinate in decimal format.
	 */
	public static double dmsToDecimal(int deg, int min, double sec, String dir) {
		double result = deg + (min / 60.0F) + (sec / 3600.0F);
		result *= (dir.equals("S") || dir.equals("W")) ? -1.0F : 1.0F;
		return result;
	}
	
	/**
	 * Converts decimal coordinates to DMS format (may be needed somewhere else
	 * in the project at some point).
	 * 
	 * @param dec The decimal representation of the coordinate.
	 * 
	 * @return The coordinate in DMS format.
	 */
	public static int[] decimalToDMS(double dec) {
		int deg = (int) dec;
		dec -= deg;
		int min = (int) (dec * 60);
		dec -= min;
		int sec = (int) (dec * 60);
		return new int[] {deg, min, sec};
	}
    
    /**
     * @return a random color formatted as a hexadecimal string
     */
    public static String randomColor() {
        Random rand = new Random();
        int num = rand.nextInt(0xffffff + 1); // random hex number
        String color = String.format("%06x", num); // format as string
        return color;
    }
}
