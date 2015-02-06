package com.ftfl.imagedatabase.util;

public class ImageInformation {
	String mId = "";
	String mLatitude = "";
	String mLongitude = "";
	String mDescription = "";
	String mFileName = "";
	String mDate = "";
	String mTime = "";

	public String getId() {
		return mId;
	}

	public void setId(String eId) {
		this.mId = eId;
	}

	public String getLatitude() {
		return mLatitude;
	}

	public void setLatitude(String eLatitude) {
		this.mLatitude = eLatitude;
	}

	public String getLongitude() {
		return mLongitude;
	}

	public void setLongitude(String eLongitude) {
		this.mLongitude = eLongitude;
	}

	public String getDescription() {
		return mDescription;
	}

	public void setDescription(String eDescription) {
		this.mDescription = eDescription;
	}

	public String getFileName() {
		return mFileName;
	}

	public void setFileName(String eFileName) {
		this.mFileName = eFileName;
	}

	public String getDate() {
		return mDate;
	}

	public void setDate(String eDate) {
		this.mDate = eDate;
	}

	public String getTime() {
		return mTime;
	}

	public void setTime(String eTime) {
		this.mTime = eTime;
	}

	public ImageInformation() {

	}

	public ImageInformation(String eId, String eLatitude, String eLongitude,
			String eDescription, String eFileName, String eDate, String eTime) {

		this.mId = eId;
		this.mLatitude = eLatitude;
		this.mLongitude = eLongitude;
		this.mDescription = eDescription;
		this.mFileName = eFileName;
		this.mDate = eDate;
		this.mTime = eTime;
	}
}
