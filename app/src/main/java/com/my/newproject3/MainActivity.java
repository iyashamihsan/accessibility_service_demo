package com.my.newproject3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends Activity {
	
	private Timer _timer = new Timer();
	
	private Intent start = new Intent();
	private TimerTask delay;

	Button openWhatsapp;
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		//setContentView(R.layout.main);

		Toast.makeText(this, "Opening WhatsApp", Toast.LENGTH_SHORT).show();

		Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.whatsapp");
		launchIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(launchIntent);
		Log.v("Whatsapp intent after", "opening whatsapp");

		finish();
		//openWhatsapp = (Button) findViewById(R.id.open_wt);

		//openWhatsapp.setOnClickListener(openWhatsappClick);


		//initialize();
		//initializeLogic();
	}

	View.OnClickListener openWhatsappClick = new View.OnClickListener() {
		@Override
		public void onClick(View view) {


		}
	};
	
	private void initialize() {
		
	}
	private void initializeLogic() {
		delay = new TimerTask() {
			@Override
			public void run() {
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						Intent start = getPackageManager().getLaunchIntentForPackage("com.whatsapp"); startActivity(start);
					}
				});
			}
		};
		_timer.schedule(delay, (int)(5000));
	}
	
	@Override
	protected void onActivityResult(int _requestCode, int _resultCode, Intent _data) {
		super.onActivityResult(_requestCode, _resultCode, _data);
		
		switch (_requestCode) {
			
			default:
			break;
		}
	}
	
	@Deprecated
	public void showMessage(String _s) {
		Toast.makeText(getApplicationContext(), _s, Toast.LENGTH_SHORT).show();
	}
	
	@Deprecated
	public int getLocationX(View _v) {
		int _location[] = new int[2];
		_v.getLocationInWindow(_location);
		return _location[0];
	}
	
	@Deprecated
	public int getLocationY(View _v) {
		int _location[] = new int[2];
		_v.getLocationInWindow(_location);
		return _location[1];
	}
	
	@Deprecated
	public int getRandom(int _min, int _max) {
		Random random = new Random();
		return random.nextInt(_max - _min + 1) + _min;
	}
	
	@Deprecated
	public ArrayList<Double> getCheckedItemPositionsToArray(ListView _list) {
		ArrayList<Double> _result = new ArrayList<Double>();
		SparseBooleanArray _arr = _list.getCheckedItemPositions();
		for (int _iIdx = 0; _iIdx < _arr.size(); _iIdx++) {
			if (_arr.valueAt(_iIdx))
			_result.add((double)_arr.keyAt(_iIdx));
		}
		return _result;
	}
	
	@Deprecated
	public float getDip(int _input){
		return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, _input, getResources().getDisplayMetrics());
	}
	
	@Deprecated
	public int getDisplayWidthPixels(){
		return getResources().getDisplayMetrics().widthPixels;
	}
	
	@Deprecated
	public int getDisplayHeightPixels(){
		return getResources().getDisplayMetrics().heightPixels;
	}
	
}
