package com.ftfl.icare.utill;


public class ICareProfile {

	public String name;

	public String age;
	public String bloodGroup;
	public String weight;
	public String height;
	public String dateOfBirth;
	public String specialNotes;
	String id;


	/*
	 * set id of the profile
	 */
	public void setID(String iID) {
		id = iID;
	}
/*
 * get id of the activity
 */
	public String getID() {
		return id;
	}

	/*
	 * Set a name for icare profile. parameter iName return name
	 */
	public void setName(String iName) {
		name = iName;
	}
	/*
	 * get name of the icare profile.
	 */
	public String getName() {
		return name;
	}
	
	/*
	 * set age of the person
	 */
	public void setAge(String iAge) {
		age = iAge;
	}
	/*
	 * get age of the person
	 */
	public String getAge() {
		return age;
	}
	/*
	 * set eye color of the person
	 */
	public void setBloodGroup(String eBloodGroup) {
		bloodGroup = eBloodGroup;
	}
	/*
	 * get eye color of the person
	 */
	public String getBlooGroup() {
		return bloodGroup;
		
	}
/*
 * set weight
 */
	public void setWeight(String iWeight) {
		weight = iWeight;
	}
/*
 * get weight
 */
	public String getWeight() {
		return weight;
	}
/*
 * set height
 */
	public void setHeight(String iHeight) {
		height = iHeight;
	}
/*
 * get height
 */
	public String getHeight() {
		return height;
	}
	/*
	 * set date of birth
	 */
	public void setDateOfBirth(String iDateOfBirth) {
		dateOfBirth = iDateOfBirth;
	}
	/*
	 * get date of birth
	 */
	public String getDateOfBirth() {
		return dateOfBirth;
	}

	/*
	 * set special notes
	 */
	public void setSpecialNotes(String iSpecialNotes) {
		specialNotes = iSpecialNotes;
	}
	/*
	 * get special notes
	 */
	public String getSpecialNotes() {
		return specialNotes;
	}



	
	/*
	 * set empty constructor of this class
	 */
	public ICareProfile() {

	}
	/*
	 * constructor for set value
	 */
	public ICareProfile(String mId, String icName, String icAge, String eBloodGroup,
			String icWeight, String icHeight, String icDateOfBirth,
			 String icSpecialNotes) {
		id = mId;
		name = icName;
		
		age = icAge;
		bloodGroup = eBloodGroup;
		weight = icWeight;
		height = icHeight;
		dateOfBirth = icDateOfBirth;
		
		specialNotes = icSpecialNotes;

	}
	


}
