package com.miraclehinn.dietdiary;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnCreateContextMenuListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class FoodHistoryActivity extends Activity implements OnItemClickListener{
	
	private ListView foodHistoryListView;
	private ArrayList<Map<String, Object>> dataArrayList;
	private MyAdapter myAdapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.food_history);
		
		foodHistoryListView = (ListView) findViewById(R.id.FoodHistoryListView);
		foodHistoryListView.setOnItemClickListener(this);
		foodHistoryListView.setOnCreateContextMenuListener(listviewLongPress);
		
		dataArrayList = loadDateFromDatabase();
		if (dataArrayList != null && dataArrayList.size() > 0) {
			myAdapter = new MyAdapter(this);
			foodHistoryListView.setAdapter(myAdapter);
		}
		
	}  
  
	private ArrayList<Map<String, Object>> loadDateFromDatabase() {
		List<Meal> meals = SharedVariables.dbManager.queryAll();  
        ArrayList<Map<String, Object>> list = new ArrayList<Map<String, Object>>();  
        int totalRecord = meals.size();
        if (totalRecord == 0) {
			return null;
		}
        
        int number = 0;
        for (int i = totalRecord - 1; i >= 0; i--) {
        	HashMap<String, Object> map = new HashMap<String, Object>();
        	
        	int calories = 0;
        	StringBuilder abstractInfoStringBuilder = new StringBuilder();
        	
        	Meal meal = null;
        	Date date = meals.get(i).getDate();
        	do {
        		meal = meals.get(i);
        		calories += meal.getCalory(); 
        		abstractInfoStringBuilder.append(meal.getType()).append(":").append(meal.getName()).append("\n");
        		i--;
			} while (i>=0 && date.toString().equals(meals.get(i).getDate().toString()));
        	i++;
        	
        	map.put("number", ++number);
            map.put("date", date);
            map.put("abstractInfp", abstractInfoStringBuilder); 
            map.put("calories", calories);
            list.add(map); 
		}
        
        return list;
	}

	public void showInfo() {
		new AlertDialog.Builder(this).setTitle("����")
				.setMessage("��������")
				.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Toast.makeText(FoodHistoryActivity.this, "ȷ������", Toast.LENGTH_SHORT).show();
					}
				}).show();
	}
	
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {
		Intent intent = new Intent(FoodHistoryActivity.this, FoodHistoryOneDayActivity.class);
		intent.putExtra("date", dataArrayList.get(position).get("date").toString());
		this.startActivity(intent);
	}
	
	public final class ViewHolder{
		public TextView numberTextView;
		public TextView dateTextView;
		public TextView caloriesTextView;
		public TextView abstractInfoTextView;
		public Button shareButton;
	}
	
	public class MyAdapter extends BaseAdapter {

		private LayoutInflater mInflater;

		public MyAdapter(Context context) {
			this.mInflater = LayoutInflater.from(context);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return dataArrayList.size();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			ViewHolder holder = null;
			if (convertView == null) {
				holder = new ViewHolder();

				convertView = mInflater.inflate(R.layout.food_history_item, null);
				holder.numberTextView = (TextView) convertView.findViewById(R.id.NumberTextView);
				holder.dateTextView = (TextView) convertView.findViewById(R.id.DateTextView);
				holder.caloriesTextView = (TextView) convertView.findViewById(R.id.CaloriesTextView);
				holder.abstractInfoTextView = (TextView) convertView.findViewById(R.id.AbstractInfoTextView);
				holder.shareButton = (Button) convertView.findViewById(R.id.ShareButton);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			holder.numberTextView.setText(dataArrayList.get(position).get("number").toString());
			holder.dateTextView.setText(dataArrayList.get(position).get("date").toString());
			holder.caloriesTextView.setText(dataArrayList.get(position).get("calories").toString() + "KJ");
			holder.abstractInfoTextView.setText(dataArrayList.get(position).get("abstractInfp").toString());
			
			holder.shareButton.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					showInfo();
				}
			});

			return convertView;
		}
	}
	
	// �����¼���Ӧ
	OnCreateContextMenuListener listviewLongPress = new OnCreateContextMenuListener() {
		@Override
		public void onCreateContextMenu(ContextMenu menu, View v,
				ContextMenuInfo menuInfo) {
			final AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
			new AlertDialog.Builder(FoodHistoryActivity.this)
					/* �������ڵ�����ͷ���� */
					.setTitle("ɾ����ǰ����")
					/* ���õ������ڵ�ͼʽ */
					.setIcon(android.R.drawable.ic_dialog_info)
					/* ���õ������ڵ���Ϣ */
					.setMessage("ȷ��ɾ����ǰ��¼")
					.setPositiveButton("��",
							new DialogInterface.OnClickListener() {
								public void onClick(
										DialogInterface dialoginterface, int i) {
									// ��ȡλ������
									int mListPos = info.position;
									// ��ȡ��ӦHashMap��������
									Map<String, Object> map = dataArrayList.get(mListPos);
									// ��ȡid
									//int id = Integer.valueOf((map.get("_id").toString()));
									//��ȡdate
									String dateString = map.get("date").toString();
									// ��ȡ�������ֵ��,���Զ����ݽ�����صĲ���,�����������
									if (SharedVariables.dbManager.deleteMealByDate(dateString)) {
										// �Ƴ�listData������
										dataArrayList.remove(mListPos);
										myAdapter.notifyDataSetChanged();
									}
								}
							})
					.setNegativeButton("��",
							new DialogInterface.OnClickListener() {
								public void onClick(
										DialogInterface dialoginterface, int i) {
									// ʲôҲû��
								}
							}).show();
		}
	};

}
