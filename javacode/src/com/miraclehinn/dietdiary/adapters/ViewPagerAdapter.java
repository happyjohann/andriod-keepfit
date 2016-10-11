package com.miraclehinn.dietdiary.adapters;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.miraclehinn.dietdiary.SharedConstants;
import com.miraclehinn.dietdiary.InitialWeightActivity;

public class ViewPagerAdapter extends PagerAdapter {
	
	private List<View> views;
	private Activity activity;
	
	public ViewPagerAdapter(List<View> views, Activity activity) {
		this.views = views;
		this.activity = activity;
	}
	
	private void goInitialWeightActivity() {
		Intent intent = new Intent(activity, InitialWeightActivity.class);
		activity.startActivity(intent);
		activity.finish();
	}
	
	@Override
	public void destroyItem(View container, int position, Object object) {
		((ViewPager) container).removeView(views.get(position));
	}

	@Override
	public int getCount() {
		if (views != null) {
			return views.size();
		}
		return 0;
	}

	@Override
	public Object instantiateItem(View container, int position) {
		((ViewPager) container).addView(views.get(position), 0);
		if (position == views.size() - 1) {
			ImageView startImageButton = (ImageView) container
					.findViewById(com.miraclehinn.dietdiary.R.id.StartButtonImageView);
			startImageButton.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View view) {
					
					SharedPreferences preferences = activity.getSharedPreferences(
							SharedConstants.SHAREDPREFERENCES_NAME, Context.MODE_PRIVATE);
					Editor editor = preferences.edit();
					editor.putBoolean("FIRSTSTART", false);
					editor.commit();
					
					goInitialWeightActivity();
				}

			});
		}
		
		return views.get(position);
	}
	
	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return (arg0 == arg1);
	}

}
