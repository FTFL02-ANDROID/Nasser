package com.ftfl.icare.util;

public class ImportantNotes {

	String mId = "";
	String mTitle = "";
	String mSubject = "";
	String mDescription = "";
	String mProfileId = "";

	/*
	 * Setter Getter method
	 */
	public String getId() {
		return mId;
	}

	public void setId(String mId) {
		this.mId = mId;
	}

	public String getTitle() {
		return mTitle;
	}

	public void setTitle(String mTitle) {
		this.mTitle = mTitle;
	}

	public String getSubject() {
		return mSubject;
	}

	public void setSubject(String mSubject) {
		this.mSubject = mSubject;
	}

	public String getDescription() {
		return mDescription;
	}

	public void setDescription(String mDescription) {
		this.mDescription = mDescription;
	}

	public String getProfileId() {
		return mProfileId;
	}

	public void setProfileId(String mProfileId) {
		this.mProfileId = mProfileId;
	}

	/*
	 * constructor
	 */
	public ImportantNotes(String eId, String eTitle, String eSubject,
			String eDescription, String eProfileId) {

		this.mId = eId;
		this.mTitle = eTitle;
		this.mSubject = eSubject;
		this.mDescription = eDescription;
		this.mProfileId = eProfileId;
	}

	/*
	 * constructor
	 */
	public ImportantNotes(String eTitle, String eSubject, String eDescription,
			String eProfileId) {

		this.mTitle = eTitle;
		this.mSubject = eSubject;
		this.mDescription = eDescription;
		this.mProfileId = eProfileId;
	}

}
