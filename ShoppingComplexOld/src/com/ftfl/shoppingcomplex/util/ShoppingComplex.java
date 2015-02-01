package com.ftfl.shoppingcomplex.util;

public class ShoppingComplex {

	String mId = "";
	String mName = "";
	String mAddress = "";
	String mServiceDescription = "";
	String mLatitude = "";
	String mLongitude = "";
	String mCloseDay = "";
	String mDailyOpenTime = "";

	public String getId() {
		return mId;
	}

	public void setId(String mId) {
		this.mId = mId;
	}

	public String getName() {
		return mName;
	}

	public void setName(String mName) {
		this.mName = mName;
	}

	public String getAddress() {
		return mAddress;
	}

	public void setAddress(String mAddress) {
		this.mAddress = mAddress;
	}

	public String getServiceDescription() {
		return mServiceDescription;
	}

	public void setServiceDescription(String mServiceDescription) {
		this.mServiceDescription = mServiceDescription;
	}

	public String getLatitude() {
		return mLatitude;
	}

	public void setLatitude(String mLatitude) {
		this.mLatitude = mLatitude;
	}

	public String getLongitude() {
		return mLongitude;
	}

	public void setLongitude(String mLongitude) {
		this.mLongitude = mLongitude;
	}

	public String getCloseDay() {
		return mCloseDay;
	}

	public void setCloseDay(String mCloseDay) {
		this.mCloseDay = mCloseDay;
	}

	public String getDailyOpenTime() {
		return mDailyOpenTime;
	}

	public void setmDailyOpenTime(String mDailyOpenTime) {
		this.mDailyOpenTime = mDailyOpenTime;
	}

	public ShoppingComplex() {

	}

	public ShoppingComplex(String mId, String mName, String mAddress,
			String mLatitude, String mLongitude, String mCloseDay,
			String mDailyOpenTime, String mServiceDescription) {

		this.mId = mId;
		this.mName = mName;
		this.mAddress = mAddress;
		this.mServiceDescription = mServiceDescription;
		this.mLatitude = mLatitude;
		this.mLongitude = mLongitude;
		this.mCloseDay = mCloseDay;
		this.mDailyOpenTime = mDailyOpenTime;
	}

}
