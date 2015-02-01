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
	String mImagePath = "";

	/*
	 * provide Shopping Complex id
	 */
	public String getId() {
		return mId;
	}

	/*
	 * set Shopping Complex id
	 */

	public void setId(String eId) {
		this.mId = eId;
	}

	/*
	 * provide Shopping Complex name
	 */

	public String getName() {
		return mName;
	}

	/*
	 * set Shopping Complex name
	 */

	public void setName(String eName) {
		this.mName = eName;
	}

	/*
	 * provide Shopping Complex address
	 */

	public String getAddress() {
		return mAddress;
	}

	/*
	 * set Shopping Complex address
	 */

	public void setAddress(String eAddress) {
		this.mAddress = eAddress;
	}

	/*
	 * provide Shopping Complex service description
	 */

	public String getServiceDescription() {
		return mServiceDescription;
	}

	/*
	 * set Shopping Complex service description
	 */
	public void setServiceDescription(String eServiceDescription) {
		this.mServiceDescription = eServiceDescription;
	}

	/*
	 * provide Shopping Complex latitude
	 */

	public String getLatitude() {
		return mLatitude;
	}

	/*
	 * set Shopping Complex latitude
	 */
	public void setLatitude(String eLatitude) {
		this.mLatitude = eLatitude;
	}

	/*
	 * provide Shopping Complex longitude
	 */

	public String getLongitude() {
		return mLongitude;
	}

	/*
	 * set Shopping Complex longitude
	 */

	public void setLongitude(String eLongitude) {
		this.mLongitude = eLongitude;
	}

	/*
	 * provide Shopping Complex close day
	 */

	public String getCloseDay() {
		return mCloseDay;
	}

	/*
	 * set Shopping Complex close day
	 */

	public void setCloseDay(String eCloseDay) {
		this.mCloseDay = eCloseDay;
	}

	/*
	 * provide Shopping Complex opening time
	 */

	public String getDailyOpenTime() {
		return mDailyOpenTime;
	}

	/*
	 * set Shopping Complex opening time
	 */

	public void setmDailyOpenTime(String eDailyOpenTime) {
		this.mDailyOpenTime = eDailyOpenTime;
	}

	/*
	 * provide Shopping Complex image path
	 */

	public String getImagePath() {
		return mImagePath;
	}

	/*
	 * set Shopping Complex image path
	 */

	public void setImagePath(String eImagePath) {
		this.mImagePath = eImagePath;
	}

	/*
	 * empty constructor
	 */

	public ShoppingComplex() {

	}

	/*
	 * Constructor for setting the member variable.
	 */

	public ShoppingComplex(String eId, String eName, String eAddress,
			String eLatitude, String eLongitude, String eCloseDay,
			String eDailyOpenTime, String eServiceDescription, String eImagePath) {

		this.mId = eId;
		this.mName = eName;
		this.mAddress = eAddress;
		this.mServiceDescription = eServiceDescription;
		this.mLatitude = eLatitude;
		this.mLongitude = eLongitude;
		this.mCloseDay = eCloseDay;
		this.mDailyOpenTime = eDailyOpenTime;
		this.mImagePath = eImagePath;
	}

}
