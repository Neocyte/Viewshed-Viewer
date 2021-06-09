package common;

/**
 * The root level response from windy.com's REST API.
 * 
 * This class or any class that's part of this clas may need to be modified
 * should windy.com's REST API use a different structure.
 * 
 * The following is an example of the JSON returned with only one webcam:
 * 
 * {
 *   "status": "OK",
 *   "result": {
 *     "offset": 0,
 *     "limit": 1,
 *     "total": 51412,
 *     "webcams": [
 *       {
 *         "id": "1000550952",
 *         "status": "active",
 *         "title": "Beinwil am See: Hallwilersee Nord",
 *         "category": [
 *           {
 *             "id": "landscape",
 *             "name": "Outdoor",
 *             "count": 1
 *           },
 *           {
 *             "id": "lake",
 *             "name": "Lake/River",
 *             "count": 1
 *           }
 *         ],
 *         "location": {
 *           "city": "Beinwil am See",
 *           "region": "Canton of Aargau",
 *           "region_code": "CH.AG",
 *           "country": "Switzerland",
 *           "country_code": "CH",
 *           "continent": "Europe",
 *           "continent_code": "EU",
 *           "latitude": 47.260586,
 *           "longitude": 8.205056,
 *           "timezone": "Europe/Zurich",
 *           "wikipedia": "https://de.wikipedia.org/wiki/Beinwil am See"
 *         },
 *         "url": {
 *           "current": {
 *             "desktop": "https://www.windy.com/webcams/1000550952",
 *             "mobile": "https://www.windy.com/webcams/1000550952"
 *           },
 *           "edit": "https://www.windy.com/webcams/1000550952",
 *           "daylight": {
 *             "desktop": "https://www.windy.com/webcams/1000550952",
 *             "mobile": "https://www.windy.com/webcams/1000550952"
 *           }
 *         }
 *       }
 *     ]
 *   }
 * }
 *
 * @author Michael O'Donnell (2021)
 */
public class WindyResponse {
	public String status;
	
	public WindyResult result;
}
