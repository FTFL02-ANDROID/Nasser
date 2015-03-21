package com.ftfl.icare.util;

public class DrawerItem {

	String ItemName;
	int imgResID = 0;
	String title;
	boolean isSpinner;
	
	/*
	 * construction for item name and image id
	 */
	public DrawerItem(String itemName, int imgResID) {
		ItemName = itemName;
		this.imgResID = imgResID;
	}

	/*
	 * construction for spinner
	 */
	
	public DrawerItem(boolean isSpinner) {
		this(null, 0);
		this.isSpinner = isSpinner;
	}

	/*
	 * construction for divider
	 */
	
	public DrawerItem(String divider) {
		this(null, 0);
		this.title = divider;
	}

	/*
	 * Setter getter method
	 */
	
	public String getItemName() {
		return ItemName;
	}

	public void setItemName(String itemName) {
		ItemName = itemName;
	}

	public int getImgResID() {
		return imgResID;
	}

	public void setImgResID(int imgResID) {
		this.imgResID = imgResID;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public boolean isSpinner() {
		return isSpinner;
	}

}
