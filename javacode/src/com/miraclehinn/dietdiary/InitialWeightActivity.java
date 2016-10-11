package com.miraclehinn.dietdiary;

import java.util.Calendar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.miraclehinn.dietdiary.SharedConstants;

public class InitialWeightActivity extends Activity implements OnKeyListener {
	
	private EditText initialWeightInputEditText;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.initial_weight);
		
		initialWeightInputEditText = (EditText)findViewById(R.id.InitialWeightInputEditText);
		initialWeightInputEditText.setOnKeyListener(this);
		
		Handler handler = new Handler();
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT,0);
			}
		},200);
		
	}

	@Override
	public boolean onKey(View v, int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_ENTER) {
			if (initialWeightInputEditText.getText().length() == 0) {
				Toast.makeText(this, "体重值不能为空", Toast.LENGTH_SHORT).show();
			}else if (Integer.parseInt(initialWeightInputEditText.getText().toString()) <= 0 || Integer.parseInt(initialWeightInputEditText.getText().toString()) >= 500) {
				Toast.makeText(this, "体重值" + initialWeightInputEditText.getText() + "无效", Toast.LENGTH_SHORT).show();
			} else {
				//TODO: save the weight to the database
				InputMethodManager imm = (InputMethodManager)v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);  
				if(imm.isActive()) {
					imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0 );   
				} 
				
				SharedPreferences preferences = getSharedPreferences(SharedConstants.SHAREDPREFERENCES_NAME, Context.MODE_PRIVATE);
				Editor editor = preferences.edit();
				editor.putInt("WEIGHT", Integer.parseInt(initialWeightInputEditText.getText().toString()));
				Calendar calendar = Calendar.getInstance();
				int year = calendar.get(Calendar.YEAR);
				int month = calendar.get(Calendar.MONTH) + 1;
				int day = calendar.get(Calendar.DAY_OF_MONTH);
				String dateString = ("" + year + "-" + month + "-" + day);
				editor.putString("STARTDATE", dateString);
				editor.commit();
				
				Intent intent = new Intent(InitialWeightActivity.this, MainActivity.class);
				startActivity(intent);
				this.finish();
			}
			return true; 
		}
		
		return false; 
	}
}
