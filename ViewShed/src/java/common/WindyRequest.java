package common;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;
import java.util.logging.Level;
import javax.net.ssl.HttpsURLConnection;
import utilities.Debug;
import utilities.WebErrorLogger;

import com.google.gson.Gson;


/**
 * Adds the webcams from windy.com to the database.
 *
 * @author Michael O'Donnell (2021)
 */
public class WindyRequest {
	public WindyRequest() {}
	
	/**
	 * Deletes the old webcams from windy.com and adds the current webcams. The
	 * algorithm for this is inspired by https://medium.com/swlh/getting-json-data-from-a-restful-api-using-java-b327aafb3751 .
	 * 
	 * @param apiKey The api key for windy.com
	 * 
	 * @throws IOException If the connection fails.
	 */
	public void getWebcams(String apiKey) throws IOException {
		database.WebcamManager wm = database.Database.getDatabaseManagement().getWebcamManager();
		database.UserManager um = database.Database.getDatabaseManagement().getUserManager();

		wm.deleteWebcamsByUserNumber(um.getUserByLoginName("WindyUser").getUserNumber());
		
		URL url = new URL("https://api.windy.com/api/webcams/v2/list/limit=1?show=webcams:category,location,url");
		HttpsURLConnection connect = (HttpsURLConnection) url.openConnection();
		connect.setRequestMethod("GET");
		
		connect.setRequestProperty("x-windy-key", apiKey);
		connect.connect();
		
		int stat = connect.getResponseCode();
		
		if (stat != 200) {
			throw new IOException(connect.getResponseMessage());
		} else {
			String data = "";
			InputStream is = connect.getInputStream();
			Scanner scan = new Scanner(is);
			while (scan.hasNextLine()) {
				data += scan.nextLine();
			}
			scan.close();
			connect.disconnect();
			
			Gson gson = new Gson();
			WindyResponse wr = gson.fromJson(data, WindyResponse.class);
			Debug.println("Number of webcams: " + wr.result.total);
			
			for (int i = 0; i < wr.result.total; i += 50) {
				getWebcams(apiKey, wr.result.total, i);
				Debug.println(i);
			}
		}
	}

	/**
	 * Gets the webcams starting from offset and adds them to the database.
	 * 
	 * @param apiKey The api key for windy.com.
	 * 
	 * @param length The total number of webcams on windy.com.
	 * 
	 * @param offset The beginning index of the webcams to start with.
	 * 
	 * @throws IOException If the connection fails.
	 */
	public void getWebcams(String apiKey, int length, int offset) throws IOException {
		database.WebcamManager wm = database.Database.getDatabaseManagement().getWebcamManager();
		
		int numCams = (length - offset < 50) ? length - offset : 50;
		
		URL url = new URL("https://api.windy.com/api/webcams/v2/list/limit="
				+ numCams + "," + offset + "?show=webcams:category,location,url");
		HttpsURLConnection connect = (HttpsURLConnection) url.openConnection();
		connect.setRequestMethod("GET");
		
		connect.setRequestProperty("x-windy-key", apiKey);
		connect.connect();
		
		int stat = connect.getResponseCode();
		
		if (stat != 200) {
			throw new IOException(connect.getResponseMessage());
		} else {
			String data = "";
			InputStream is = connect.getInputStream();
			Scanner scan = new Scanner(is);
			while (scan.hasNextLine()) {
				data += scan.nextLine();
			}
			scan.close();
			connect.disconnect();
			
			Gson gson = new Gson();
			WindyResponse wr = gson.fromJson(data, WindyResponse.class);
			for (WindyWebcam webcam : wr.result.webcams) {
				wm.addWebcam(utilities.WebcamUtil.windyWebcamToCommonWebcam(webcam));
			}
		}
	}

	public static void main(String[] args) {
		Debug.setEnabled(true);
		final String propertyFilePath = common.Paths.HOMEDIRECTORY+"/web/WEB-INF/config/General.properties";
		utilities.PropertyManager.configure(propertyFilePath);
		
		WindyRequest wr = new WindyRequest();
		database.DatabasePropertyManager pm = database.Database.getDatabaseManagement().getDatabasePropertyManager();
		String apiKey = pm.getPropertyValue("x-windy-key");
		try {
			wr.getWebcams(apiKey);
		} catch (IOException ex) {
			WebErrorLogger.log(Level.SEVERE, "IOException in getWebcams()", ex);
		}
	}
}
