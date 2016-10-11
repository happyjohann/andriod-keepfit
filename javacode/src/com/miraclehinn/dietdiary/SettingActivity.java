package com.miraclehinn.dietdiary;

import java.util.Calendar;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class SettingActivity extends Activity {
	SharedPreferences preferences;
	
	private TextView settingWeightTextView;
	private TextView settingRemaindTextView;
	private Button settingWeightButton;
	private Button settingRemaindButton;
	private Button settingAccountsButton;
	private Button settingFadebackButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting);
		
		settingWeightTextView = (TextView)findViewById(R.id.SettingWeightTextView);
		settingRemaindTextView = (TextView)findViewById(R.id.SettingRemaindTextView);
		settingWeightButton = (Button)findViewById(R.id.SettingWeightButton);
		settingRemaindButton = (Button)findViewById(R.id.SettingRemaindButton);
		settingAccountsButton = (Button)findViewById(R.id.SettingAccountsButton);
		settingFadebackButton = (Button)findViewById(R.id.SettingFadebackButton);
		
		preferences = getSharedPreferences(
				SharedConstants.SHAREDPREFERENCES_NAME, Context.MODE_PRIVATE);
		
		settingWeightButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				LayoutInflater inflater = getLayoutInflater();
				final View layout = inflater.inflate(R.layout.setting_weight_dialog, (ViewGroup) findViewById(R.id.DialogLinearLayout));
				new AlertDialog.Builder(SettingActivity.this).setTitle(R.string.setting_weight_update)
				.setIcon(android.R.drawable.ic_dialog_info).setView(layout)
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Editor editor = preferences.edit();
						editor.putInt("WEIGHT", Integer.parseInt(((EditText) layout.findViewById(R.id.SettingNewWeightEditText)).getText().toString()));
						editor.commit();
						updatWeightForView();
					}
				})
				.setNegativeButton("取消", null).show();	
			}
		});
		
		settingRemaindButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
//				Intent intent = new Intent(SettingActivity.this, SettingTimeActivity.class);
//				startActivityForResult(intent, 1010);
				Calendar canlendar = Calendar.getInstance();
				
				new TimePickerDialog(SettingActivity.this, new TimePickerDialog.OnTimeSetListener() {

					@Override
					public void onTimeSet(TimePicker view, int hourOfDay,
							int minute) {
						Editor editor = preferences.edit();
						editor.putString("TIME", hourOfDay + ":" + minute);
						editor.commit();
						
						updateTimeForView();
					}
			           }, canlendar.get(Calendar.HOUR_OF_DAY), canlendar.get(Calendar.HOUR_OF_DAY), true).show();

			}
		});
		
		settingFadebackButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(SettingActivity.this, SendMailActivity.class);
				startActivity(intent);
			}
		});
		
		settingAccountsButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(SettingActivity.this, "还没有绑定账户", Toast.LENGTH_LONG).show();	
			}
		});
		
		updateTimeForView();
		updatWeightForView();
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 1010 && resultCode == RESULT_OK) {
			//updateTimeForView();
		}
	}
	
	private void updatWeightForView() {
		
		int weight = preferences.getInt("WEIGHT", 0);
		settingWeightTextView.setText("体重 " + weight + "KG");
	}
	
	private void updateTimeForView() {
		String timeString = preferences.getString("TIME", "未设定");
		settingRemaindTextView.setText("提醒时间" + timeString);
	}
}
