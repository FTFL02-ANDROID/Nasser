package com.ftfl.icare;

import java.util.ArrayList;
import java.util.List;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ftfl.icare.adapter.CustomDrawerAdapter;
import com.ftfl.icare.database.DoctorProfileDataSource;
import com.ftfl.icare.database.ICareProfileDataSource;
import com.ftfl.icare.database.ImportantNoteDataSource;
import com.ftfl.icare.database.MedicalHistoryDataSource;
import com.ftfl.icare.database.VaccineDataSource;
import com.ftfl.icare.fragment.FragmentAbout;
import com.ftfl.icare.fragment.FragmentCareCenter;
import com.ftfl.icare.fragment.FragmentDietChart;
import com.ftfl.icare.fragment.FragmentDoctor;
import com.ftfl.icare.fragment.FragmentEmergencyCall;
import com.ftfl.icare.fragment.FragmentGallery;
import com.ftfl.icare.fragment.FragmentHealthInformation;
import com.ftfl.icare.fragment.FragmentHome;
import com.ftfl.icare.fragment.FragmentMedicalHistory;
import com.ftfl.icare.fragment.FragmentNewDailyDiet;
import com.ftfl.icare.fragment.FragmentNewDoctor;
import com.ftfl.icare.fragment.FragmentNewMedicalHistory;
import com.ftfl.icare.fragment.FragmentNewNote;
import com.ftfl.icare.fragment.FragmentNewVaccine;
import com.ftfl.icare.fragment.FragmentNote;
import com.ftfl.icare.fragment.FragmentUpdateProfile;
import com.ftfl.icare.fragment.FragmentVaccine;
import com.ftfl.icare.fragment.FragmentViewDiet;
import com.ftfl.icare.fragment.FragmentViewProfile;
import com.ftfl.icare.util.DrawerItem;
import com.ftfl.icare.util.ICareConstants;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class HomeActivity extends android.support.v4.app.FragmentActivity {

	DrawerLayout mDrawerLayout = null;
	ListView mDrawerList = null;
	ActionBarDrawerToggle mDrawerToggle = null;

	CharSequence mDrawerTitle = null;
	CharSequence mTitle = null;
	CustomDrawerAdapter adapter = null;

	List<DrawerItem> dataList = null;
	ICareProfileDataSource mDataSource = null;
	SharedPreferences sharedpreferences = null;

	public String mUpdatePageName = "";
	public String mId = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_ACTION_BAR);
		setContentView(R.layout.activity_home);

		DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
				.cacheInMemory(true).cacheOnDisk(true).build();
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				getApplicationContext()).defaultDisplayImageOptions(
				defaultOptions).build();
		ImageLoader.getInstance().init(config);

		sharedpreferences = getSharedPreferences(ICareConstants.MY_PREFERENCES,
				Context.MODE_PRIVATE);
		if (sharedpreferences.contains(ICareConstants.POS)) {
			ICareConstants.POSITION = sharedpreferences.getInt(
					ICareConstants.POS, 0);

			ICareProfileDataSource mDataSource = new ICareProfileDataSource(
					this);
			List<String> profileId = mDataSource.profilesId();

			ICareConstants.SELECTED_PROFILE_ID = profileId
					.get(ICareConstants.POSITION);
		}

		// Initializing
		dataList = new ArrayList<DrawerItem>();
		mTitle = mDrawerTitle = getTitle();
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.left_drawer);

		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
				GravityCompat.START);

		// Add Drawer Item to dataList
		dataList.add(new DrawerItem(true)); // adding a spinner to the list
		dataList.add(new DrawerItem("Divider")); // adding a divider to the list
													// // list
		dataList.add(new DrawerItem("View Profile", R.drawable.ic_action_person));
		dataList.add(new DrawerItem("Create Profile",
				R.drawable.ic_action_add_person));
		dataList.add(new DrawerItem("Divider"));
		dataList.add(new DrawerItem("Home", R.drawable.android_home));
		dataList.add(new DrawerItem("Diet List", R.drawable.drawer_diet));
		dataList.add(new DrawerItem("Doctor", R.drawable.drawer_doctor));
		dataList.add(new DrawerItem("Vaccine", R.drawable.drawer_vaccine));
		dataList.add(new DrawerItem("Gallery", R.drawable.gallery_drawer));
		dataList.add(new DrawerItem("Notes", R.drawable.note_drawer));
		dataList.add(new DrawerItem("Medical History",
				R.drawable.drawer_history));
		dataList.add(new DrawerItem("Divider"));
		dataList.add(new DrawerItem("Health Information",
				R.drawable.android_information));
		dataList.add(new DrawerItem("Care Center",
				R.drawable.android_care_center));
		dataList.add(new DrawerItem("Emergency Number",
				R.drawable.ic_action_call));
		dataList.add(new DrawerItem("Divider"));
		dataList.add(new DrawerItem("About", R.drawable.ic_action_about));
		adapter = new CustomDrawerAdapter(this, R.layout.custom_drawer_item,
				dataList);

		mDrawerList.setAdapter(adapter);

		mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

		getActionBar().setHomeButtonEnabled(true);
		getActionBar().setLogo(R.drawable.ic_drawer);

		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_drawer, R.string.drawer_open,
				R.string.drawer_close) {
			public void onDrawerClosed(View view) {
				getActionBar().setTitle(mTitle);
				invalidateOptionsMenu();
			}

			public void onDrawerOpened(View drawerView) {
				getActionBar().setTitle(mDrawerTitle);
				invalidateOptionsMenu();
			}
		};

		mDrawerLayout.setDrawerListener(mDrawerToggle);

		if (savedInstanceState == null) {
			if (dataList.get(0).isSpinner()
					& dataList.get(1).getTitle() != null) {
				SelectItem(5);
			} else if (dataList.get(0).getTitle() != null) {
				SelectItem(1);
			} else {
				SelectItem(0);
			}
		}

		centerActionBarTitle();
		ActionBar bar = getActionBar();
		bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#666666")));
	}

	private void centerActionBarTitle() {

		int titleId = 0;
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			titleId = getResources().getIdentifier("action_bar_title", "id",
					"android");
		} else {
			// This is the id is from your app's generated R class when
			// ActionBarActivity is used
			// for SupportActionBar
			titleId = R.id.action_bar_title;
		}

		// Final check for non-zero invalid id
		if (titleId > 0) {
			TextView titleTextView = (TextView) findViewById(titleId);
			LinearLayout.LayoutParams txvPars = (android.widget.LinearLayout.LayoutParams) titleTextView
					.getLayoutParams();
			txvPars.gravity = Gravity.CENTER_HORIZONTAL;
			titleTextView.setTextColor(Color.WHITE);
			titleTextView.setLayoutParams(txvPars);
			titleTextView.setTextSize(25);
		}
	}

	/*
	 * if user click a item in the drawer then in this function perform the
	 * specific task.
	 */

	public void SelectItem(int position) {

		Fragment fragment = null;
		Bundle args = new Bundle();
		switch (position) {

		case 2:
			fragment = new FragmentViewProfile();
			break;

		case 3:
			mDataSource = new ICareProfileDataSource(HomeActivity.this);

			if (mDataSource.profileNumber() >= 2) {
				mDrawerLayout.closeDrawer(mDrawerList);

				Toast.makeText(getApplicationContext(),
						"You can not open more then two profile.",
						Toast.LENGTH_LONG).show();
			} else {
				mDrawerLayout.closeDrawer(mDrawerList);
				Intent intentCreateProfile = new Intent(HomeActivity.this,
						CreateProfileActivity.class);

				HomeActivity.this.startActivity(intentCreateProfile);
			}
			break;

		case 5:
			fragment = new FragmentHome();
			break;

		case 6:
			fragment = new FragmentDietChart();
			break;

		case 7:
			fragment = new FragmentDoctor();
			break;

		case 8:
			fragment = new FragmentVaccine();
			break;

		case 9:
			fragment = new FragmentGallery();
			break;

		case 10:
			fragment = new FragmentNote();
			break;

		case 11:
			fragment = new FragmentMedicalHistory();
			break;

		case 13:
			fragment = new FragmentHealthInformation();
			break;

		case 14:
			fragment = new FragmentCareCenter();
			break;

		case 15:
			fragment = new FragmentEmergencyCall();
			break;

		case 17:
			fragment = new FragmentAbout();
			break;

		case 30:
			fragment = new FragmentNewNote();
			break;

		case 31:
			fragment = new FragmentNewDoctor();
			break;

		case 32:
			fragment = new FragmentNewVaccine();
			break;

		case 33:
			fragment = new FragmentNewMedicalHistory();
			break;

		case 34:
			fragment = new FragmentNewDailyDiet();
			break;

		case 35:
			fragment = new FragmentViewDiet();
			break;

		default:
			break;
		}

		if (!(position == 3)) {
			fragment.setArguments(args);
			FragmentManager frgManager = getSupportFragmentManager();
			frgManager.beginTransaction().replace(R.id.content_frame, fragment)
					.commit();
			if (!(position == 30 || position == 31 || position == 32
					|| position == 33 || position == 34 || position == 35)) {

				mDrawerList.setItemChecked(position, true);
				setTitle(dataList.get(position).getItemName());
			}
			mDrawerLayout.closeDrawer(mDrawerList);
		}
	}

	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		getActionBar().setTitle(mTitle);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggles
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	private class DrawerItemClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {

			if (dataList.get(position).getTitle() == null) {
				SelectItem(position);
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.

		if (ICareConstants.VIEW_MENU) {
			getMenuInflater().inflate(R.menu.home_update, menu);
		}

		else
			getMenuInflater().inflate(R.menu.home, menu);

		return true;

	}

	/*
	 * This function is use for enable menu option
	 */

	public void enableMenu() {
		ICareConstants.VIEW_MENU = true;
		this.invalidateOptionsMenu();
	}

	/*
	 * This function is use for disable menu option
	 */

	public void disableMenu() {
		ICareConstants.VIEW_MENU = false;
		this.invalidateOptionsMenu();
	}

	/*
	 * This function is used for find the current visible fragment. This
	 * function return current visible fragment.
	 */

	public Fragment getVisibleFragment() {
		FragmentManager fragmentManager = this.getSupportFragmentManager();
		List<Fragment> fragments = fragmentManager.getFragments();
		for (Fragment fragment : fragments) {
			if (fragment != null && fragment.isVisible()) {
				return fragment;
			}
		}
		return null;
	}

	/*
	 * this function reload the current fragment view by the user
	 */

	public void reloadCurrentFragment() {
		Fragment currentFragment = getVisibleFragment();

		FragmentManager fragmentManager = getSupportFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager
				.beginTransaction();
		fragmentTransaction.detach(currentFragment);
		fragmentTransaction.attach(currentFragment);
		fragmentTransaction.commit();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}

		else {

			Bundle bundle = new Bundle();
			switch (item.getItemId()) {
			case R.id.update:

				if (mUpdatePageName == ICareConstants.UPDATE_NOTE) {
					FragmentNewNote fragmentNewNote = new FragmentNewNote();
					bundle.putString(ICareConstants.NOTE_ID, mId);
					fragmentNewNote.setArguments(bundle);

					FragmentManager fragmentManager = getSupportFragmentManager();
					FragmentTransaction fragmentTransaction = fragmentManager
							.beginTransaction();
					fragmentTransaction.replace(R.id.content_frame,
							fragmentNewNote);
					fragmentTransaction.addToBackStack(null);
					fragmentTransaction.commit();
				}

				else if (mUpdatePageName == ICareConstants.UPDATE_DOCTOR_PROFILE) {
					FragmentNewDoctor fragmentNewDoctor = new FragmentNewDoctor();
					bundle.putString(ICareConstants.DOCTOR_ID, mId);
					fragmentNewDoctor.setArguments(bundle);

					FragmentManager fragmentManager = getSupportFragmentManager();
					FragmentTransaction fragmentTransaction = fragmentManager
							.beginTransaction();
					fragmentTransaction.replace(R.id.content_frame,
							fragmentNewDoctor);
					fragmentTransaction.addToBackStack(null);
					fragmentTransaction.commit();
				}

				else if (mUpdatePageName == ICareConstants.UPDATE_VACCINE) {
					FragmentNewVaccine fragmentNewVaccine = new FragmentNewVaccine();
					bundle.putString(ICareConstants.VACCINE_ID, mId);
					fragmentNewVaccine.setArguments(bundle);

					FragmentManager fragmentManager = getSupportFragmentManager();
					FragmentTransaction fragmentTransaction = fragmentManager
							.beginTransaction();
					fragmentTransaction.replace(R.id.content_frame,
							fragmentNewVaccine);
					fragmentTransaction.addToBackStack(null);
					fragmentTransaction.commit();
				}

				else if (mUpdatePageName == ICareConstants.UPDATE_MEDICAL_HISTORY) {
					FragmentNewMedicalHistory fragmentNewMedicalHistory = new FragmentNewMedicalHistory();
					bundle.putString(ICareConstants.MEDICAL_HISTORY_ID, mId);
					fragmentNewMedicalHistory.setArguments(bundle);

					FragmentManager fragmentManager = getSupportFragmentManager();
					FragmentTransaction fragmentTransaction = fragmentManager
							.beginTransaction();
					fragmentTransaction.replace(R.id.content_frame,
							fragmentNewMedicalHistory);
					fragmentTransaction.addToBackStack(null);
					fragmentTransaction.commit();
				}

				else if (mUpdatePageName == ICareConstants.UPDATE_DIET) {

					ICareConstants.DIET_UPDATE_STATE = "update";
					Fragment currentFragment = getVisibleFragment();

					FragmentManager fragmentManager = getSupportFragmentManager();
					FragmentTransaction fragmentTransaction = fragmentManager
							.beginTransaction();
					fragmentTransaction.detach(currentFragment);
					fragmentTransaction.attach(currentFragment);
					fragmentTransaction.commit();
				}

				else if (mUpdatePageName == ICareConstants.UPDATE_PROFILE_TAG) {

					FragmentUpdateProfile fragmentUpdateProfile = new FragmentUpdateProfile();

					FragmentManager fragmentManager = getSupportFragmentManager();
					FragmentTransaction fragmentTransaction = fragmentManager
							.beginTransaction();
					fragmentTransaction.replace(R.id.content_frame,
							fragmentUpdateProfile);
					fragmentTransaction.addToBackStack(null);
					fragmentTransaction.commit();
				}

				return true;
			case R.id.delete:
				if (mUpdatePageName == ICareConstants.UPDATE_NOTE) {
					new AlertDialog.Builder(this)
							.setTitle("Delete Note ")
							.setMessage(
									"Are you sure you want to delete this entry?")
							.setPositiveButton(android.R.string.yes,
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int which) {
											ImportantNoteDataSource noteDS = new ImportantNoteDataSource(
													HomeActivity.this);
											int id = Integer.parseInt(mId);
											noteDS.deleteData(id);
											getSupportFragmentManager()
													.popBackStack();
										}
									})
							.setNegativeButton(android.R.string.no,
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int which) {
											ICareConstants.DIET_UPDATE_STATE = "";

										}
									})
							.setIcon(android.R.drawable.ic_dialog_alert).show();

				}

				else if (mUpdatePageName == ICareConstants.UPDATE_DOCTOR_PROFILE) {

					new AlertDialog.Builder(this)
							.setTitle("Delete Doctor Profile ")
							.setMessage(
									"Are you sure you want to delete this entry?")
							.setPositiveButton(android.R.string.yes,
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int which) {
											DoctorProfileDataSource doctorDS = new DoctorProfileDataSource(
													HomeActivity.this);
											int id = Integer.parseInt(mId);
											doctorDS.deleteData(id);
											getSupportFragmentManager()
													.popBackStack();
										}
									})
							.setNegativeButton(android.R.string.no,
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int which) {
											ICareConstants.DIET_UPDATE_STATE = "";

										}
									})
							.setIcon(android.R.drawable.ic_dialog_alert).show();
				}

				else if (mUpdatePageName == ICareConstants.UPDATE_VACCINE) {

					new AlertDialog.Builder(this)
							.setTitle("Delete Vaccine ")
							.setMessage(
									"Are you sure you want to delete this entry?")
							.setPositiveButton(android.R.string.yes,
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int which) {
											VaccineDataSource vaccineDS = new VaccineDataSource(
													HomeActivity.this);
											int id = Integer.parseInt(mId);
											vaccineDS.deleteData(id);
											getSupportFragmentManager()
													.popBackStack();
										}
									})
							.setNegativeButton(android.R.string.no,
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int which) {
											ICareConstants.DIET_UPDATE_STATE = "";

										}
									})
							.setIcon(android.R.drawable.ic_dialog_alert).show();

				}

				else if (mUpdatePageName == ICareConstants.UPDATE_MEDICAL_HISTORY) {

					new AlertDialog.Builder(this)
							.setTitle("Delete Medical History ")
							.setMessage(
									"Are you sure you want to delete this entry?")
							.setPositiveButton(android.R.string.yes,
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int which) {

											MedicalHistoryDataSource medicalHistoryDS = new MedicalHistoryDataSource(
													HomeActivity.this);
											int id = Integer.parseInt(mId);
											medicalHistoryDS.deleteData(id);
											getSupportFragmentManager()
													.popBackStack();
										}
									})
							.setNegativeButton(android.R.string.no,
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int which) {
											ICareConstants.DIET_UPDATE_STATE = "";

										}
									})
							.setIcon(android.R.drawable.ic_dialog_alert).show();

				}

				else if (mUpdatePageName == ICareConstants.UPDATE_DIET) {

					ICareConstants.DIET_UPDATE_STATE = "delete";
					Fragment currentFragment = getVisibleFragment();

					FragmentManager fragmentManager = getSupportFragmentManager();
					FragmentTransaction fragmentTransaction = fragmentManager
							.beginTransaction();
					fragmentTransaction.detach(currentFragment);
					fragmentTransaction.attach(currentFragment);
					fragmentTransaction.commit();
				}

				return true;

			default:
				return super.onOptionsItemSelected(item);

			}
		}
	}
}
