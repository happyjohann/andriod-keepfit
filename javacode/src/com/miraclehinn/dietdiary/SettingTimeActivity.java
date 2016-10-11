package com.miraclehinn.dietdiary;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

public class SettingTimeActivity extends Activity {
	
	private TimePicker settingTimeTimePicker;
	private Button settingTimeConfirmButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting_time_timepicker);
		
		settingTimeTimePicker = (TimePicker) findViewById(R.id.SettingTimeTimePicker);
		settingTimeConfirmButton = (Button) findViewById(R.id.SettingTimeConfirmButton);
		
		settingTimeTimePicker.setIs24HourView(true);
		settingTimeTimePicker.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				int hour = settingTimeTimePicker.getCurrentHour();
				int minute = settingTimeTimePicker.getCurrentMinute();
				int second = 0;
				Toast.makeText(SettingTimeActivity.this, "设置时间为" + hour + ":" + minute + ":" + second, Toast.LENGTH_SHORT).show();
			}
		});
		//settingTimeTimePicker.setOnTimeChangedListener();
		
		settingTimeConfirmButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				int hour = settingTimeTimePicker.getCurrentHour();
				int minute = settingTimeTimePicker.getCurrentMinute();
				int second = 0;
				
				SharedPreferences preferences = getSharedPreferences(
						SharedConstants.SHAREDPREFERENCES_NAME, Context.MODE_PRIVATE);
				Editor editor = preferences.edit();
				editor.putString("TIME", hour + ":" + minute + ":" + second);
				editor.commit();
				
                Intent intent = new Intent();
                intent.putExtra("Time", hour + ":" + minute + ":" + second);
                SettingTimeActivity.this.setResult(RESULT_OK, intent);
				SettingTimeActivity.this.finish();
			}
		});
	}
}
