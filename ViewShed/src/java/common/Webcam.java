package common;

import com.google.gson.Gson;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Represents a webcam.
 * 
 * @author Michael O'Donnell (2021)
 */
public class Webcam implements Serializable {
	private int webcamID;
	private int userNumber;
	private String webcamName;
	private String description;
	private String purpose;
	private String url;
	private double latitude;
	private double longitude;
	private String city;
	private String stateProvinceRegion;
	private String country;
	private int height;
	private String heightUnits;
	private int azimuth;
	private int fieldOfView;
	private String rotating;
	private int rotatingFieldOfView;
	private int verticalViewAngle;
	private int verticalFieldOfView;
	private int minViewRadius;
	private String minViewRadiusUnits;
	private int maxViewRadius;
	private String maxViewRadiusUnits;
	private String webcamColor;
	private String viewshedColor;
	private boolean active;
	private LocalDateTime dateSubmitted;
	private LocalDateTime dateApproved;
	private String approvalStatus;

	/**
	 * Constructs an empty webcam.
	 */
	public Webcam() {
	}

	/**
	 * Constructs a webcam based on the arguments id, webcam user number, name,
	 * address, latitude, longitude, height, active, azimuth, field of view,
	 * rotating, rotating field of view, vertical view angle,
	 * vertical field of view, minimum view radius, maximum view radius,
	 * visible color, color.
	 * 
	 * @param webcamID The ID number for this webcam. It is automatically generated 
	 * by the database.
	 * 
	 * @param userNumber The ID of the user who owns this webcam.
	 * 
	 * @param webcamName The name of this webcam.
	 * 
	 * @param description The description of this webcam.
	 * 
	 * @param purpose The purpose of the webcam (e.g. weather or traffic).
	 * 
	 * @param url The url this webcam is located at.
	 * 
	 * @param latitude The latitude of this webcam.
	 * 
	 * @param longitude The longitude of this webcam.
	 * 
	 * @param city The city this webcam is located in.
	 * 
	 * @param stateProvinceRegion The state, province, or region this webcam is
	 * located in.
	 * 
	 * @param country The country this webcam is located in.
	 * 
	 * @param height The height this webcam is above the ground.
	 * 
	 * @param heightUnits The units the height is in.
	 * 
	 * @param azimuth The angle this camera is pointing clockwise from North
	 * (0°).
	 * 
	 * @param fieldOfView The horizontal field of view of this webcam.
	 * 
	 * @param rotating If this webcam is static, rotating, or controllable.
	 * 
	 * @param rotatingFieldOfView The rotating field of view for this webcam.
	 * 
	 * @param verticalViewAngle The vertical view angle of this webcam.
	 * 
	 * @param verticalFieldOfView The vertical field of view of this webcam.
	 * 
	 * @param minViewRadius The minimum view radius of this webcam.
	 * 
	 * @param minViewRadiusUnits The units the minimum view radius is in.
	 * 
	 * @param maxViewRadius The maximum view radius of this webcam.
	 * 
	 * @param maxViewRadiusUnits The units the maximum view radius is in.
	 * 
	 * @param webcamColor The visible color for this webcam.
	 * 
	 * @param viewshedColor This webcam's viewshed's color.
	 * 
	 * @param active Boolean value for if this webcam is currently active.
	 * 
	 * @param dateSubmitted The date this webcam was submitted.
	 *
	 * @param dateApproved The date this webcam was approved by a SystemAdmin.
	 * 
	 * @param approvalStatus The approval status of this webcam. (submitted, approved, revoked)
	 */
	public Webcam(int webcamID, int userNumber, String webcamName,
			String description, String purpose, String url, double latitude,
			double longitude, String city, String stateProvinceRegion,
			String country, int height, String heightUnits, int azimuth,
			int fieldOfView, String rotating, int rotatingFieldOfView,
			int verticalViewAngle, int verticalFieldOfView, int minViewRadius,
			String minViewRadiusUnits, int maxViewRadius,
			String maxViewRadiusUnits, String webcamColor, String viewshedColor,
			boolean active, LocalDateTime dateSubmitted,
			LocalDateTime dateApproved, String approvalStatus) {
		this.webcamID = webcamID;
		this.userNumber = userNumber;
		this.webcamName = webcamName;
		this.description = description;
		this.purpose = purpose;
		this.url = url;
		this.latitude = latitude;
		this.longitude = longitude;
		this.city = city;
		this.stateProvinceRegion = stateProvinceRegion;
		this.country = country;
		this.height = height;
		this.heightUnits = heightUnits;
		this.azimuth = azimuth;
		this.fieldOfView = fieldOfView;
		this.rotating = rotating;
		this.rotatingFieldOfView = rotatingFieldOfView;
		this.verticalViewAngle = verticalViewAngle;
		this.verticalFieldOfView = verticalFieldOfView;
		this.minViewRadius = minViewRadius;
		this.minViewRadiusUnits = minViewRadiusUnits;
		this.maxViewRadius = maxViewRadius;
		this.maxViewRadiusUnits = maxViewRadiusUnits;
		this.webcamColor = webcamColor;
		this.viewshedColor = viewshedColor;
		this.active = active;
		this.dateSubmitted = dateSubmitted;
		this.dateApproved = dateApproved;
		this.approvalStatus = approvalStatus;
	}

	/**
	 * Gets the ID representing this webcam.
	 * 
	 * @return The ID of this webcam.
	 */
	public int getWebcamID() {
		return webcamID;
	}

	/**
	 * Sets the ID representing this webcam.
	 * 
	 * @param webcamID The new ID for this webcam.
	 */
	public void setWebcamID(int webcamID) {
		this.webcamID = webcamID;
	}

	/**
	 * Gets the ID representing the <code>WebcamUser</code> who owns this
	 * webcam.
	 * 
	 * @return The ID of the <code>WebcamUser</code> who owns this webcam.
	 */
	public int getUserNumber() {
		return userNumber;
	}

	/**
	 * Sets the ID for which <code>WebcamUser</code> owns this webcam.
	 * 
	 * @param userNumber The ID for which <code>WebcamUser</code> owns this
	 * webcam.
	 */
	public void setUserNumber(int userNumber) {
		this.userNumber = userNumber;
	}

	/**
	 * Gets the name of this webcam.
	 * 
	 * @return The name of this webcam.
	 */
	public String getWebcamName() {
		return webcamName;
	}

	/**
	 * Sets the name of this webcam.
	 * 
	 * @param webcamName The name for this webcam.
	 */
	public void setWebcamName(String webcamName) {
		this.webcamName = webcamName;
	}

	/**
	 * Gets the description of this webcam.
	 * 
	 * @return The description of this webcam.
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the description of this webcam.
	 * 
	 * @param description The description for this webcam.
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	/**
	 * Gets the purpose of this webcam (e.g. weather or traffic).
	 * 
	 * @return The purpose of this webcam.
	 */
	public String getPurpose() {
		return purpose;
	}

	/**
	 * Sets the purpose this webcam (e.g. weather or traffic).
	 * 
	 * @param purpose The purpose for this webcam.
	 */
	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}
	
	/**
	 * Gets the URL of this webcam.
	 * 
	 * @return The URL of this webcam.
	 */
	public String getUrl() {	
		return url;
	}

	/**
	 * Sets the URL of this webcam.
	 * 
	 * @param url The URL for this webcam.
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * Gets the latitude of this webcam.
	 * 
	 * @return The latitude of this webcam.
	 */
	public double getLatitude() {
		return latitude;
	}

	/**
	 * Sets the latitude of this webcam.
	 * 
	 * @param latitude The latitude for this webcam.
	 */
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	/**
	 * Gets the longitude of this webcam.
	 * 
	 * @return The longitude of this webcam.
	 */
	public double getLongitude() {
		return longitude;
	}

	/**
	 * Sets the longitude of this webcam.
	 * 
	 * @param longitude The longitude for this webcam.
	 */
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	/**
	 * Gets the city this webcam is located in.
	 * 
	 * @return The city this webcam is located in.
	 */
	public String getCity() {
		return city;
	}

	/**
	 * Sets the city this webcam is located in.
	 * 
	 * @param city The city this webcam is located in.
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * Gets the state, province, or region this webcam is located in.
	 * 
	 * @return The state, province, or region this webcma is located in.
	 */
	public String getStateProvinceRegion() {
		return stateProvinceRegion;
	}

	/**
	 * Sets the state, province, or region this webcam is located in.
	 * 
	 * @param stateProvinceRegion The state, province, or region this webcam is
	 * located in.
	 */
	public void setStateProvinceRegion(String stateProvinceRegion) {
		this.stateProvinceRegion = stateProvinceRegion;
	}

	/**
	 * Gets the country this webcam is located in.
	 * 
	 * @return The country this webcam is located in.
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * Sets the country this webcam is lcated in.
	 * 
	 * @param country The country this webcma is located in.
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * Gets the height above the ground of this webcam.
	 * 
	 * @return The height above the ground of this webcam.
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * Sets the height of this webcam above the ground.
	 * 
	 * @param height The height above the ground for this webcam.
	 */
	public void setHeight(int height) {
		this.height = height;
	}

	/**
	 * Gets the units the height is stored in.
	 * 
	 * @return The units the height is stored in.
	 */
	public String getHeightUnits() {
		return heightUnits;
	}

	/**
	 * Sets the units the height is stored in.
	 * 
	 * @param heightUnits The units for the height.
	 */
	public void setHeightUnits(String heightUnits) {
		this.heightUnits = heightUnits;
	}
	
	/**
	 * Gets the angle clockwise of North (0°) of this webcam.
	 * 
	 * @return The azimuth of this webcam.
	 */
	public int getAzimuth() {
		return azimuth;
	}

	/**
	 * Sets the angle clockwise of North (0°) of this webcam.
	 * 
	 * @param azimuth The azimuth for this webcam.
	 */
	public void setAzimuth(int azimuth) {
		this.azimuth = azimuth;
	}

	/**
	 * Gets the field of view of this webcam.
	 * 
	 * @return The field of view of this webcam. 
	 */
	public int getFieldOfView() {
		return fieldOfView;
	}

	/**
	 * Sets the field of view of this webcam.
	 * 
	 * @param fieldOfView The field of view for this webcam.
	 */
	public void setFieldOfView(int fieldOfView) {
		this.fieldOfView = fieldOfView;
	}

	/**
	 * Returns if this webcam is static, rotating, or controllable.
	 * 
	 * @return The current state of this webcam's motion.
	 */
	public String getRotating() {
		return rotating;
	}

	/**
	 * Sets this webcam to static, rotating, or controllable.
	 * 
	 * @param rotating If this webcam is static, rotating, or controllable
	 */
	public void setRotating(String rotating) {
		this.rotating = rotating;
	}

	/**
	 * Gets the rotating field of view of this webcam.
	 * 
	 * @return The rotating field of view of this webcam.
	 */
	public int getRotatingFieldOfView() {
		return rotatingFieldOfView;
	}

	/**
	 * Sets the rotating field of view of this webcam.
	 * 
	 * @param rotatingFieldOfView The rotating field of view for this webcam.
	 */
	public void setRotatingFieldOfView(int rotatingFieldOfView) {
		this.rotatingFieldOfView = rotatingFieldOfView;
	}

	/**
	 * Gets the vertical view angle of this webcam.
	 * 
	 * @return The vertical view angle of this webcam.
	 */
	public int getVerticalViewAngle() {
		return verticalViewAngle;
	}

	/**
	 * Sets the vertical view angle of this webcam.
	 * 
	 * @param verticalViewAngle The vertical view angle for this webcam.
	 */
	public void setVerticalViewAngle(int verticalViewAngle) {
		this.verticalViewAngle = verticalViewAngle;
	}

	/**
	 * Gets the vertical field of view of this webcam.
	 * 
	 * @return The vertical field of view of this webcam.
	 */
	public int getVerticalFieldOfView() {
		return verticalFieldOfView;
	}

	/**
	 * Sets the vertical field of view of this webcam.
	 * 
	 * @param verticalFieldOfView The vertical field of view for this webcam.
	 */
	public void setVerticalFieldOfView(int verticalFieldOfView) {
		this.verticalFieldOfView = verticalFieldOfView;
	}

	/**
	 * Gets the minimum view radius of this webcam.
	 * 
	 * @return The minimum view radius of this webcam.
	 */
	public int getMinViewRadius() {
		return minViewRadius;
	}

	/**
	 * Sets the minimum view radius of this webcam.
	 * 
	 * @param minViewRadius The minimum view radius for this webcam.
	 */
	public void setMinViewRadius(int minViewRadius) {
		this.minViewRadius = minViewRadius;
	}

	/**
	 * Gets the units of the minimum view radius.
	 * 
	 * @return The units of the minimum view radius.
	 */
	public String getMinViewRadiusUnits() {
		return minViewRadiusUnits;
	}

	/**
	 * Sets the units of the minimum view radius.
	 * 
	 * @param minViewRadiusUnits The units for the minimum view radius.
	 */
	public void setMinViewRadiusUnits(String minViewRadiusUnits) {
		this.minViewRadiusUnits = minViewRadiusUnits;
	}

	/**
	 * Gets the maximum view radius of this webcam.
	 * 
	 * @return The maximum view radius of this webcam.
	 */
	public int getMaxViewRadius() {
		return maxViewRadius;
	}

	/**
	 * Sets the maximum view radius of this webcam.
	 * 
	 * @param maxViewRadius The maximum view radius of this webcam.
	 */
	public void setMaxViewRadius(int maxViewRadius) {
		this.maxViewRadius = maxViewRadius;
	}

	/**
	 * Gets the units of the maximum view radius.
	 * 
	 * @return The units of the maximum view radius.
	 */
	public String getMaxViewRadiusUnits() {
		return maxViewRadiusUnits;
	}

	/**
	 * Sets the units of the maximum view radius.
	 * 
	 * @param maxViewRadiusUnits The units for the maximum view radius.
	 */
	public void setMaxViewRadiusUnits(String maxViewRadiusUnits) {
		this.maxViewRadiusUnits = maxViewRadiusUnits;
	}

	/**
	 * Gets the color of this webcam.
	 * 
	 * @return The color of this webcam.
	 */
	public String getWebcamColor() {
		return webcamColor;
	}

	/**
	 * Sets the color of this webcam.
	 * 
	 * @param webcamColor The color for this webcam.
	 */
	public void setWebcamColor(String webcamColor) {
		this.webcamColor = webcamColor;
	}

	/**
	 * Gets the color of this webcam's viewshed.
	 * 
	 * @return The color of this webcam's viewshed.
	 */
	public String getViewshedColor() {
		return viewshedColor;
	}

	/**
	 * Sets the color of this webcam's viewshed.
	 * 
	 * @param viewshedColor The color for this webcam's viewshed.
	 */
	public void setViewshedColor(String viewshedColor) {
		this.viewshedColor = viewshedColor;
	}
	
	/**
	 * Returns if this webcam is active.
	 * 
	 * @return If this webcam is active.
	 */
	public boolean isActive() {
		return active;
	}

	/**
	 * Sets this webcam to active.
	 * @param active True if the <code>Webcam</code> should be set to active.
	 */
	public void setActive(boolean active) {
		this.active = active;
	}

	/**
	 * Gets the date this webcam was submitted.
	 * 
	 * @return The date this webcam was submitted.
	 */
	public LocalDateTime getDateSubmitted() {
		return dateSubmitted;
	}

	/**
	 * Sets the date this webcam was submitted.
	 * 
	 * @param dateSubmitted The date this webcam was submitted.
	 */
	public void setDateSubmitted(LocalDateTime dateSubmitted) {
		this.dateSubmitted = dateSubmitted;
	}

	/**
	 * Gets the date this webcam was approved by a SystemAdmin.
	 * 
	 * @return The date this webcam was approved by a SystemAdmin.
	 */
	public LocalDateTime getDateApproved() {
		return dateApproved;
	}

	/**
	 * Sets the date this webcam was approved by a SystemAdmin.
	 * 
	 * @param dateApproved The date this webcam was approved by a SystemAdmin.
	 */
	public void setDateApproved(LocalDateTime dateApproved) {
		this.dateApproved = dateApproved;
	}

	/**
	 * Gets the approval status a SystemAdmin set for this webcam.
	 * 
	 * @return This approval status of this webcam.
	 */
	public String getApprovalStatus() {
		return approvalStatus;
	}

	/**
	 * Sets the approval status a SystemAdmin sets this webcam to.
	 * 
	 * @param approvalStatus The approval status for this webcam.
	 */
	public void setApprovalStatus(String approvalStatus) {
		this.approvalStatus = approvalStatus;
	}

	/**
	 * Returns the hash code of this webcam.
	 * 
	 * @return The hash code of this webcam.
	 */
	@Override
	public int hashCode() {
		int hash = 5;
		hash = 59 * hash + this.webcamID;
		return hash;
	}

	/**
	 * Compares this webcam with another object. If the other object is not a
	 * <code>Webcam</code>, null, or has a different ID, returns false;
	 * otherwise, returns true.
	 * 
	 * @param obj The object being compared to this webcam.
	 * 
	 * @return If the object being compared to is equal to this webcam.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Webcam other = (Webcam) obj;
		return this.webcamID == other.webcamID;
	}

	/**
	 * Returns a <code>String</code> representation of this webcam.
	 * 
	 * @return A <code>String</code> represetation of this webcam.
	 */
	@Override
	public String toString() {
		return "Webcam{" + "webcamID=" + webcamID + ", userNumber=" + userNumber
				+ ", webcamName=" + webcamName + ", description=" + description
				+ ", purpose=" + purpose + ", url=" + url + ", latitude="
				+ latitude + ", longitude=" + longitude + ", city=" + city
				+ ", stateProvinceRegion=" + stateProvinceRegion + ", country="
				+ country + ", height=" + height + ", heightUnits="
				+ heightUnits + ", azimuth=" + azimuth + ", fieldOfView="
				+ fieldOfView + ", rotating=" + rotating
				+ ", rotatingFieldOfView=" + rotatingFieldOfView
				+ ", verticalViewAngle=" + verticalViewAngle
				+ ", verticalFieldOfView=" + verticalFieldOfView
				+ ", minViewRadius=" + minViewRadius + ", minViewRadiusUnits="
				+ minViewRadiusUnits + ", maxViewRadius=" + maxViewRadius
				+ ", maxViewRadiusUnits=" + maxViewRadiusUnits
				+ ", webcamColor=" + webcamColor + ", viewshedColor="
				+ viewshedColor + ", active=" + active + ", dateSubmitted="
				+ dateSubmitted + ", dateApproved=" + dateApproved
				+ ", approvalStatus=" + approvalStatus + '}';
	}
	
	/**
	 * Returns a <code>String</code> representation of this webcam using valid
	 * JSON notation.
	 * 
	 * @return A JSON <code>String</code> representing this webcam.
	 */
	public String toJsonString() {
		Gson gson = new Gson();
		return gson.toJson(this, Webcam.class);
	}

}
