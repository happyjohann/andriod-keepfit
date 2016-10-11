package com.miraclehinn.dietdiary;

import java.sql.Date;
import java.util.List;

import com.miraclehinn.dietdiary.adapters.DBManager;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

public class FoodHistoryOneDayActivity extends Activity {
	private String dateString;
	private TextView dayCountTitleTextView;
	private TextView detailTextView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.food_history_one_day);
		
		dayCountTitleTextView = (TextView)findViewById(R.id.DayCountTitleTextView);
		detailTextView = (TextView)findViewById(R.id.DetailTextView);
		
		Intent intent = getIntent();
        dateString = intent.getStringExtra("date");
        
        setTitleForView();
        setDetailTexView();
	}
	
	@SuppressLint("DefaultLocale")
	public void setTitleForView() {
		String startDateString;
		SharedPreferences preferences = getSharedPreferences(
				SharedConstants.SHAREDPREFERENCES_NAME, Context.MODE_PRIVATE);
		startDateString = preferences.getString("STARTDATE", dateString);
		int daysBetween = DBManager.daysBetween(Date.valueOf(startDateString), Date.valueOf(dateString));
		String titleString = String.format("减肥第 %d天\n%s", daysBetween, dateString);
		dayCountTitleTextView.setText(titleString);
	}
	
	public void setDetailTexView() {
		detailTextView.setText(constractDetailText());
	}
	
	private String constractDetailText() {
		List<Meal> meals = SharedVariables.dbManager.queryByDate(dateString);
		
		StringBuilder abstractInfoStringBuilder = new StringBuilder(dateString + "的能量补充详情为：\n");
		
		for (Meal meal : meals) {
			abstractInfoStringBuilder.append(meal.getTime().toString()).append("添加:").append(meal.getType()).append(" ").append(meal.getName()).append(" 能量为").append(String.valueOf(meal.getCalory())).append("KJ").append("\n");
		}
		
		return abstractInfoStringBuilder.toString();
	}
}
