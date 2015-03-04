package com.ftfl.icarewebservice.util;

public class Profile {
	
	String mId = "";
	String mName = "";
	String mDateOfBirth = "";
	String mHeight = "";
	String mWeight = "";
	String mGender = "";
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
	public String getDateOfBirth() {
		return mDateOfBirth;
	}
	public void setDateOfBirth(String mDateOfBirth) {
		this.mDateOfBirth = mDateOfBirth;
	}
	public String getHeight() {
		return mHeight;
	}
	public void setHeight(String mHeight) {
		this.mHeight = mHeight;
	}
	public String getmWeight() {
		return mWeight;
	}
	public void setWeight(String mWeight) {
		this.mWeight = mWeight;
	}
	public String getGender() {
		return mGender;
	}
	public void setGender(String mGender) {
		this.mGender = mGender;
	}
	public Profile(String mId, String mName, String mDateOfBirth,
			String mHeight, String mWeight, String mGender) {
		super();
		this.mId = mId;
		this.mName = mName;
		this.mDateOfBirth = mDateOfBirth;
		this.mHeight = mHeight;
		this.mWeight = mWeight;
		this.mGender = mGender;
	}
	
	

}
