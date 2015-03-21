package com.ftfl.icare.util;

public class DoctorProfile {

	String mId = "";
	String mName = "";
	String mSpecialization = "";
	String mPhone = "";
	String mEmail = "";
	String mAddress = "";
	String mProfileId = "";

	/*
	 * return id of the doctor profile
	 */
	public String getId() {
		return mId;
	}

	/*
	 * set id of the doctor profile
	 */
	public void setId(String mId) {
		this.mId = mId;
	}

	/*
	 * return name of the doctor profile
	 */
	public String getName() {
		return mName;
	}

	/*
	 * set name of the doctor profile
	 */
	public void setName(String mName) {
		this.mName = mName;
	}

	/*
	 * return specialization of the doctor profile
	 */

	public String getSpecialization() {
		return mSpecialization;
	}

	/*
	 * set specialization of the doctor profile
	 */
	public void setSpecialization(String mSpecialization) {
		this.mSpecialization = mSpecialization;
	}

	/*
	 * return phone number of the doctor profile
	 */
	public String getPhone() {
		return mPhone;
	}

	/*
	 * set phone of the doctor profile
	 */
	public void setPhone(String mPhone) {
		this.mPhone = mPhone;
	}

	/*
	 * return email of the doctor profile
	 */
	public String getEmail() {
		return mEmail;
	}

	/*
	 * set email of the doctor profile
	 */
	public void setEmail(String mEmail) {
		this.mEmail = mEmail;
	}

	/*
	 * return address of the doctor profile
	 */
	public String getAddress() {
		return mAddress;
	}

	/*
	 * set address of the doctor profile
	 */
	public void setAddress(String mAddress) {
		this.mAddress = mAddress;
	}

	/*
	 * return user profile id of the doctor profile
	 */
	public String getProfileId() {
		return mProfileId;
	}

	/*
	 * set user profile id of the doctor profile
	 */
	public void setProfileId(String mProfileId) {
		this.mProfileId = mProfileId;
	}

	/*
	 * constructor using all field
	 */

	public DoctorProfile(String eId, String eName, String eSpecialization,
			String ePhone, String eEmail, String eAddress, String eProfileId) {

		this.mId = eId;
		this.mName = eName;
		this.mSpecialization = eSpecialization;
		this.mPhone = ePhone;
		this.mEmail = eEmail;
		this.mAddress = eAddress;
		this.mProfileId = eProfileId;
	}

	/*
	 * constructor without id field
	 */

	public DoctorProfile(String eName, String eSpecialization, String ePhone,
			String eEmail, String eAddress, String eProfileId) {

		this.mName = eName;
		this.mSpecialization = eSpecialization;
		this.mPhone = ePhone;
		this.mEmail = eEmail;
		this.mAddress = eAddress;
		this.mProfileId = eProfileId;
	}
}
