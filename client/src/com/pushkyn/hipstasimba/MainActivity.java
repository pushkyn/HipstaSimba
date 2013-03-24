package com.pushkyn.hipstasimba;

import java.util.ArrayList;
import java.util.List;

import com.pushkyn.hipstasimba.ImagesAsyncTask.ImagesResultListener;
import com.pushkyn.hipstasimba.model.HipstaImage;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.GridView;

public class MainActivity extends Activity implements ImagesResultListener {

	private static final String LOG_TAG = "HipstaSimba";
	ImagesAdapter adapter = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		GridView grid = (GridView) findViewById(R.id.grid_item);
		adapter = new ImagesAdapter(this, new ArrayList<HipstaImage>());
		grid.setAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	protected void onResume() {
		super.onResume();
		//
		Log.v(LOG_TAG, "onResume");
		refreshImages();
	}

	@Override
	public void onImagesLoaded(List<HipstaImage> images) {
		hideLoading();
		adapter.addAll(images);
		adapter.notifyDataSetChanged();
	}

	private void refreshImages() {
		Log.v(LOG_TAG, "refresh images");
		showLoading();
		new ImagesAsyncTask(this, this).execute();
	}

	private void showLoading() {
		if (findViewById(R.id.grid) != null) {
			findViewById(R.id.grid).setVisibility(View.INVISIBLE);
		}

		if (findViewById(R.id.pb) != null) {
			findViewById(R.id.pb).setVisibility(View.VISIBLE);
		}
	}

	private void hideLoading() {
		if (findViewById(R.id.grid) != null) {
			findViewById(R.id.grid).setVisibility(View.VISIBLE);
		}

		if (findViewById(R.id.pb) != null) {
			findViewById(R.id.pb).setVisibility(View.GONE);
		}
	}

}
