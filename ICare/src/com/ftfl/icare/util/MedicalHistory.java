package com.ftfl.icare.util;

public class MedicalHistory {

	String mId = "";
	String mDate = "";
	String mDoctorName = "";
	String mPurpose = "";
	String mSuggestion = "";
	String mProfileId = "";
	String mPrescription = "";

	/*
	 * Setter Getter Method
	 */
	public String getId() {
		return mId;
	}

	public void setId(String mId) {
		this.mId = mId;
	}

	public String getDate() {
		return mDate;
	}

	public void setDate(String mDate) {
		this.mDate = mDate;
	}

	public String getDoctorName() {
		return mDoctorName;
	}

	public void setDoctorName(String mDoctorName) {
		this.mDoctorName = mDoctorName;
	}

	public String getPurpose() {
		return mPurpose;
	}

	public void setPurpose(String mPurpose) {
		this.mPurpose = mPurpose;
	}

	public String getSuggestion() {
		return mSuggestion;
	}

	public void setSuggestion(String mSuggestion) {
		this.mSuggestion = mSuggestion;
	}

	public String getProfileId() {
		return mProfileId;
	}

	public void setProfileId(String mProfileId) {
		this.mProfileId = mProfileId;
	}

	public String getPrescription() {
		return mPrescription;
	}

	public void setPrescription(String mPrescription) {
		this.mPrescription = mPrescription;
	}

	/*
	 * constructor
	 */
	public MedicalHistory(String mId, String mDate, String mDoctorName,
			String mPurpose, String mSuggestion, String mPrescription,
			String mProfileId) {
		this.mId = mId;
		this.mDate = mDate;
		this.mDoctorName = mDoctorName;
		this.mPurpose = mPurpose;
		this.mSuggestion = mSuggestion;
		this.mPrescription = mPrescription;
		this.mProfileId = mProfileId;
	}

	/*
	 * constructor
	 */
	public MedicalHistory(String mDate, String mDoctorName, String mPurpose,
			String mSuggestion, String mPrescription, String mProfileId) {
		this.mDate = mDate;
		this.mDoctorName = mDoctorName;
		this.mPurpose = mPurpose;
		this.mSuggestion = mSuggestion;
		this.mPrescription = mPrescription;
		this.mProfileId = mProfileId;
	}
}
