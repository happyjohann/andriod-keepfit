package com.miraclehinn.dietdiary;

import java.lang.ref.WeakReference;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.miraclehinn.dietdiary.SharedConstants;

public class SplashActivity extends Activity {
	boolean isFirstIn = false;
	boolean isWeightSetted = false;
	
	private static final int GO_HOME = 1000;
	private static final int GO_WEIGHTSET = 1001;
	private static final int GO_GUIDE = 1002;
	private static final long SPLASH_DELAY_MILLIS = 1000;
	
	static class MyHandler extends Handler {
		WeakReference<SplashActivity> mActivity;
		
		MyHandler(SplashActivity activity) {
			mActivity = new WeakReference<SplashActivity>(activity);
		}

		@Override
		public void handleMessage(Message msg) {
			SplashActivity theActivity = mActivity.get();
			super.handleMessage(msg);
			
			switch (msg.what) {
			case GO_HOME:
				theActivity.goHome();
				break;
			case GO_WEIGHTSET:
				theActivity.goWeightSet();
				break;
			case GO_GUIDE:
				theActivity.goGuide();
				break;
			}
		}
	};
	
	MyHandler mHandler = new MyHandler(this);
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);

		init();
	}
	
	private void init() {
		SharedPreferences preferences = getSharedPreferences(
				SharedConstants.SHAREDPREFERENCES_NAME, Context.MODE_PRIVATE);

		isFirstIn = preferences.getBoolean("FIRSTSTART", true);
		isWeightSetted = preferences.getInt("WEIGHT", 0) == 0? false : true;
		
		if (!isFirstIn) {
			if (isWeightSetted) {
				mHandler.sendEmptyMessageDelayed(GO_HOME, SPLASH_DELAY_MILLIS);
			} else {
				mHandler.sendEmptyMessageDelayed(GO_WEIGHTSET, SPLASH_DELAY_MILLIS);
			}
		} else {
			mHandler.sendEmptyMessageDelayed(GO_GUIDE, SPLASH_DELAY_MILLIS);
		}
	}
	
	private void goWeightSet() {
		Intent intent = new Intent(SplashActivity.this, InitialWeightActivity.class);
		SplashActivity.this.startActivity(intent);
		SplashActivity.this.finish();
	}
	
	private void goHome() {
		Intent intent = new Intent(SplashActivity.this, MainActivity.class);
		SplashActivity.this.startActivity(intent);
		SplashActivity.this.finish();
	}

	private void goGuide() {
		Intent intent = new Intent(SplashActivity.this, GuideActivity.class);
		SplashActivity.this.startActivity(intent);
		SplashActivity.this.finish();
	}

}
