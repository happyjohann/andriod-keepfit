package com.miraclehinn.dietdiary;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.miraclehinn.dietdiary.SharedVariables;
import com.miraclehinn.dietdiary.adapters.DBManager;

public class MainActivity extends Activity {
	private Button mainDiaryButton;
	private Button mainAddButton;
	private Button mainSettingButton;
	private TextView mainTitleAndDaTextView;
	private TextView mainCaloriesTextView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		mainAddButton = (Button) findViewById(R.id.mainAddButton);
		mainDiaryButton = (Button) findViewById(R.id.mainDiaryButton);
		mainSettingButton = (Button) findViewById(R.id.mainSettingButton);
		mainTitleAndDaTextView = (TextView) findViewById(R.id.mainTitleAndDateTextView);
		mainCaloriesTextView = (TextView) findViewById(R.id.mainCaloriesTextView);

		mainAddButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				goAddFoodActivity();
			}
		});

		mainDiaryButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				goFoodHistoryActivity();
			}
		});

		mainSettingButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				goSettingActivity();
			}
		});
		
		// 初始化DBManager
		SharedVariables.dbManager = new DBManager(this);
		//addMeals();
		
	}

	@Override
	protected void onResume() {
		super.onResume();
		
		setTitleForView();
		setCaloriesToday();
	}
	
	// For test
	public void addMeals() {
		ArrayList<Meal> meals = new ArrayList<Meal>();

		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		int minute = calendar.get(Calendar.MINUTE);
		int second = calendar.get(Calendar.SECOND);

		String dateString = ("" + year + "-" + month + "-" + day);
		String timeString = ("" + hour + ":" + minute + ":" + second);

		Meal meal1 = new Meal(Date.valueOf(dateString),
				Time.valueOf(timeString), "早餐", "蛋炒饭", 1000);
		Meal meal2 = new Meal(Date.valueOf(dateString),
				Time.valueOf(timeString), "午餐", "还是蛋炒饭", 2000);
		Meal meal3 = new Meal(Date.valueOf(dateString),
				Time.valueOf(timeString), "晚餐", "不是蛋炒饭", 1500);
		Meal meal4 = new Meal(Date.valueOf(dateString),
				Time.valueOf(timeString), "零食", "薯片", 1000);
		Meal meal5 = new Meal(Date.valueOf("" + year + "-" + month + "-"
				+ (day - 1)), Time.valueOf(timeString), "早餐", "又是蛋炒饭", 1000);
		Meal meal6 = new Meal(Date.valueOf("" + year + "-" + month + "-"
				+ (day - 1)), Time.valueOf(timeString), "午餐", "是蛋炒饭么？", 800);
		Meal meal7 = new Meal(Date.valueOf("" + year + "-" + month + "-"
				+ (day - 1)), Time.valueOf(timeString), "晚餐", "蛋炒饭", 900);
		Meal meal8 = new Meal(Date.valueOf("" + year + "-" + month + "-"
				+ (day - 1)), Time.valueOf(timeString), "早餐", "又是蛋炒饭", 1000);
		Meal meal9 = new Meal(Date.valueOf("" + year + "-" + month + "-"
				+ (day - 1)), Time.valueOf(timeString), "午餐", "是蛋炒饭么？", 800);
		Meal meal10 = new Meal(Date.valueOf("" + year + "-" + month + "-"
				+ (day - 1)), Time.valueOf(timeString), "晚餐", "蛋炒饭", 900);
		
		meals.add(meal1);
		meals.add(meal2);
		meals.add(meal3);
		meals.add(meal4);
		meals.add(meal5);
		meals.add(meal6);
		meals.add(meal7);
		meals.add(meal8);
		meals.add(meal9);
		meals.add(meal10);
		
		SharedVariables.dbManager.addMeals(meals);
	}

	@SuppressLint("DefaultLocale")
	public void setTitleForView() {
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		String dateString = ("" + year + "-" + month + "-" + day);
		String startDateString;
		
		SharedPreferences preferences = getSharedPreferences(
				SharedConstants.SHAREDPREFERENCES_NAME, Context.MODE_PRIVATE);
		startDateString = preferences.getString("STARTDATE", dateString);
		
		int daysBetween = DBManager.daysBetween(Date.valueOf(startDateString), Date.valueOf(dateString));
		String titleString = String.format("减肥 %d天\n%s", daysBetween, dateString);
		mainTitleAndDaTextView.setText(titleString);
	}
	
	public void setCaloriesToday() {
		mainCaloriesTextView.setText(queryForSumCaloriesToday() + "KJ");
	}
	
	private int queryForSumCaloriesToday() {
		
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		String dateString = ("" + year + "-" + month + "-" + day);
		
		List<Meal> meals = SharedVariables.dbManager.queryByDate(dateString);
		
		int sumCaloriesToday = 0;
		for (Meal meal : meals) {
			sumCaloriesToday += meal.getCalory();
		}
		
		return sumCaloriesToday;
	}

	private void goAddFoodActivity() {
		Intent intent = new Intent(MainActivity.this, AddFoodActivity.class);
		this.startActivity(intent);
	}

	private void goSettingActivity() {
		Intent intent = new Intent(MainActivity.this, SettingActivity.class);
		this.startActivity(intent);
	}

	private void goFoodHistoryActivity() {
		Intent intent = new Intent(MainActivity.this, FoodHistoryActivity.class);
		this.startActivity(intent);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("提示");
			builder.setMessage("确定要退出程序?");
			builder.setPositiveButton("确定",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
							MainActivity.this.finish();
							System.exit(0);
						}
					});
			builder.setNeutralButton("取消",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
						}
					});
			builder.show();
			return true;
		}

		return super.onKeyDown(keyCode, event);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		// getMenuInflater().inflate(R.menu.main, menu);
		menu.add(0, 1001, 0, "关于");
		menu.add(0, 1002, 1, "退出");
		return true;
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		if (item.getItemId() == 1001) {
			Intent intent = new Intent(MainActivity.this, AboutActivity.class);
			startActivity(intent);
		} else if (item.getItemId() == 1002) {
			MainActivity.this.finish();
			System.exit(0);
		}
		return super.onMenuItemSelected(featureId, item);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		// 应用的最后一个Activity关闭时应释放DB
		if (SharedVariables.dbManager != null) {
			SharedVariables.dbManager.closeDB();
		}
	}
}
