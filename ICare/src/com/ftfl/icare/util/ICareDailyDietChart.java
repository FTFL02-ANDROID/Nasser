package com.ftfl.icare.util;

public class ICareDailyDietChart {

	String mId = "";
	String mDate = "";
	String mTime = "";
	String mEventName = "";
	String mFoodMenu = "";
	String mAlarm = "";
	String mProfileId = "";

	/*
	 * set id of the diet chart
	 */

	public void setId(String eId) {
		mId = eId;
	}

	/*
	 * get id of diet chart
	 */
	public String getId() {
		return mId;
	}

	/*
	 * set Date of the diet chart
	 */
	public void setDate(String eDate) {
		mDate = eDate;
	}

	/*
	 * get Date of the diet chart
	 */
	public String getDate() {
		return mDate;
	}

	/*
	 * set time for the diet chart
	 */
	public void setTime(String eTime) {
		mTime = eTime;
	}

	/*
	 * get time of the diet chart
	 */
	public String getTime() {
		return mTime;
	}

	/*
	 * set food menu
	 */
	public void setFoodMenu(String eFoodMenu) {
		mFoodMenu = eFoodMenu;
	}

	/*
	 * get food menu
	 */
	public String getFoodMenu() {
		return mFoodMenu;
	}

	/*
	 * set the event name
	 */
	public void setEventName(String eEventName) {
		mEventName = eEventName;
	}

	/*
	 * get event name
	 */
	public String getEventName() {
		return mEventName;
	}

	/*
	 * set alarm for the diet chart
	 */
	public void setAlarm(String eAlarm) {
		mAlarm = eAlarm;
	}

	/*
	 * get alarm of the diet chart
	 */
	public String getAlarm() {
		return mAlarm;
	}

	/*
	 * empty constructor of this class
	 */
	public ICareDailyDietChart() {

	}

	/*
	 * get user profile id
	 */
	public String getProfileId() {
		return mProfileId;
	}

	/*
	 * set user profile id
	 */
	public void setProfileId(String mProfileId) {
		this.mProfileId = mProfileId;
	}

	/*
	 * constructor for set value
	 */
	public ICareDailyDietChart(String eId, String eDate, String eTime,
			String eFoodMenu, String eEventName, String eAlarm, String mProfile) {
		mId = eId;
		mDate = eDate;
		mTime = eTime;
		mFoodMenu = eFoodMenu;
		mEventName = eEventName;
		mAlarm = eAlarm;
		this.mProfileId = mProfile;
	}

	/*
	 * construction for set value
	 */

	public ICareDailyDietChart(String mDate, String mTime, String mFoodMenu,
			String mEventName, String mAlarm, String mProfile) {
		this.mDate = mDate;
		this.mTime = mTime;
		this.mEventName = mEventName;
		this.mFoodMenu = mFoodMenu;
		this.mAlarm = mAlarm;
		this.mProfileId = mProfile;
	}

}
