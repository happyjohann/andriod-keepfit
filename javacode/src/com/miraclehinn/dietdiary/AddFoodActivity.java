package com.miraclehinn.dietdiary;

import java.sql.Date;
import java.sql.Time;
import java.util.Calendar;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddFoodActivity extends Activity implements OnKeyListener {
	private EditText addFoodNoteEditText;
	private EditText addFoodNameEditText;
	private EditText addFoodCaloriesEditText;
	private Button addFoodSaveButton;
	private Button addFoodCancelButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_food);
		
		addFoodCaloriesEditText = (EditText)findViewById(R.id.addFoodCaloriesEditText);
		addFoodNameEditText = (EditText)findViewById(R.id.addFoodNameEditText);
		addFoodNoteEditText = (EditText)findViewById(R.id.addFoodNoteEditText);
		addFoodSaveButton = (Button)findViewById(R.id.addFoodSaveButton);
		addFoodCancelButton = (Button)findViewById(R.id.addFoodCancelButton);
		
		addFoodCaloriesEditText.setOnKeyListener(this);
		addFoodNameEditText.setOnKeyListener(this);
		addFoodNoteEditText.setOnKeyListener(this);
		
		addFoodSaveButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Calendar calendar = Calendar.getInstance();
				int year = calendar.get(Calendar.YEAR);
				int month = calendar.get(Calendar.MONTH) + 1;
				int day = calendar.get(Calendar.DAY_OF_MONTH);
				int hour = calendar.get(Calendar.HOUR_OF_DAY);
				int minute = calendar.get(Calendar.MINUTE);
				int second = calendar.get(Calendar.SECOND);

				String dateString = ("" + year + "-" + month + "-" + day);
				String timeString = ("" + hour + ":" + minute + ":" + second);
				
				Meal meal = new Meal(Date.valueOf(dateString),
						Time.valueOf(timeString), addFoodNoteEditText.getText().toString(), addFoodNameEditText.getText().toString(), Integer.parseInt(addFoodCaloriesEditText.getText().toString()));
				if (SharedVariables.dbManager.addMeal(meal)) {
					Toast.makeText(AddFoodActivity.this, "添加成功！", Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(AddFoodActivity.this, "添加失败！", Toast.LENGTH_SHORT).show();
				}
				AddFoodActivity.this.finish();
			}
		});
		
		addFoodCancelButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				AddFoodActivity.this.finish();
			}
		});
	}

	@Override
	public boolean onKey(View v, int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		return false;
	}
}
