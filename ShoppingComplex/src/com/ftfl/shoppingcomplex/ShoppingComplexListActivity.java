package com.ftfl.shoppingcomplex;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.ftfl.shoppingcomplex.database.ShoppingComplexDataSource;
import com.ftfl.shoppingcomplex.util.ShoppingComplex;
import com.ftfl.shoppingcomplex.util.ShoppingComplexConstants;

public class ShoppingComplexListActivity extends ActionBarActivity {
	ShoppingComplexDataSource mShoppingComplexDS = null;
	List<ShoppingComplex> mShoppingComplexList = new ArrayList<ShoppingComplex>();
	List<String> mNamesList = new ArrayList<String>();
	List<String> mIdList = new ArrayList<String>();
	ListView mListView = null;
	Integer mPosition = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shopping_complex_list);

		final String[] option = new String[] { getString(R.string.viewData),
				getString(R.string.editData), getString(R.string.googleMap),
				getString(R.string.delete) };
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.select_dialog_item, option);
		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		builder.setTitle(getString(R.string.selectOption));
		builder.setAdapter(adapter, new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {

				if (which == 0) {
					viewData(mPosition);
				}
				if (which == 1) {
					editData(mPosition);
				}
				if (which == 2) {
					googleMap(mPosition);
				}
				if (which == 3) {
					deleteData(mPosition);
				}
			}

		});
		final AlertDialog alertDialog = builder.create();

		setListData();
		mListView = (ListView) findViewById(R.id.shoppingComplexList);

		ArrayAdapter<String> mShoppingComplexAdapter = new ArrayAdapter<String>(
				this, R.layout.view_row, mNamesList);

		mListView.setAdapter(mShoppingComplexAdapter);

		mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				mPosition = position;
				alertDialog.show();
			}
		});
	}

	private void setListData() {
		ShoppingComplexDataSource shoppingComplexData = new ShoppingComplexDataSource(
				this);
		mShoppingComplexList = shoppingComplexData.shoppingComplexData();
		for (int i = 0; i < mShoppingComplexList.size(); i++) {
			ShoppingComplex mShoppingComplex = mShoppingComplexList.get(i);
			mIdList.add(mShoppingComplex.getId());
			mNamesList.add(mShoppingComplex.getName());
		}
	}

	public void viewData(Integer ePosition) {
		Intent mEditIntent = new Intent(getApplicationContext(),
				ShoppingComplexViewActivity.class);
		Long id = Long.parseLong(mIdList.get(ePosition));
		mEditIntent.putExtra(ShoppingComplexConstants.ID, id.toString());
		startActivity(mEditIntent);
	}

	public void editData(Integer ePosition) {
		Intent mEditIntent = new Intent(getApplicationContext(),
				AddShoppingComplexActivity.class);
		Long id = Long.parseLong(mIdList.get(ePosition));
		mEditIntent.putExtra(ShoppingComplexConstants.ID, id.toString());
		startActivity(mEditIntent);
		// startActivityForResult(mEditIntent, 2);
	}

	public void googleMap(Integer ePosition) {
		Intent mEditIntent = new Intent(getApplicationContext(),
				GoogleMapActivity.class);
		Long id = Long.parseLong(mIdList.get(ePosition));
		mEditIntent.putExtra(ShoppingComplexConstants.ID, id.toString());
		startActivity(mEditIntent);
	}

	public void deleteData(Integer ePosition) {
		mShoppingComplexDS = new ShoppingComplexDataSource(this);
		Long id = Long.parseLong(mIdList.get(ePosition));
		mShoppingComplexDS.deleteData(id);
		this.recreate();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.shopping_complex_list, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.addShoppingComplex:
			startActivity(new Intent(ShoppingComplexListActivity.this,
					AddShoppingComplexActivity.class));
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
	}
}
