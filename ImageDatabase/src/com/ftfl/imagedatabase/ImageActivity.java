package com.ftfl.imagedatabase;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;

public class ImageActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image);
	}

	public void performRegister(View v) {
		startActivity(new Intent(ImageActivity.this, ImageCaptureActivity.class));
	}

	public void performRetreave(View v) {
		startActivity(new Intent(ImageActivity.this, ImageListActivity.class));
	}
}
