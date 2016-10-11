package com.miraclehinn.dietdiary.adapters;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.miraclehinn.dietdiary.Meal;

public class DBManager {
	private DBHelper dbHelper;  
    private SQLiteDatabase db;  
    
    public static final int daysBetween(Date early, Date late) {

		java.util.Calendar calst = java.util.Calendar.getInstance();
		java.util.Calendar caled = java.util.Calendar.getInstance();
		calst.setTime(early);
		caled.setTime(late);
		// 设置时间为0时
		calst.set(java.util.Calendar.HOUR_OF_DAY, 0);
		calst.set(java.util.Calendar.MINUTE, 0);
		calst.set(java.util.Calendar.SECOND, 0);
		caled.set(java.util.Calendar.HOUR_OF_DAY, 0);
		caled.set(java.util.Calendar.MINUTE, 0);
		caled.set(java.util.Calendar.SECOND, 0);
		// 得到两个日期相差的天数
		int days = ((int) (caled.getTime().getTime() / 1000) - (int) (calst
				.getTime().getTime() / 1000)) / 3600 / 24;

		return days;
	}
    
    public DBManager(Context context) {  
    	dbHelper = new DBHelper(context);  
        //因为getWritableDatabase内部调用了mContext.openOrCreateDatabase(mName, 0, mFactory);  
        //所以要确保context已初始化,我们可以把实例化DBManager的步骤放在调用的Activity的onCreate里  
        db = dbHelper.getWritableDatabase();  
    }  
    
    /** 
     * add meal 
     * @param meal 
     */  
    public boolean addMeal(Meal meal) {   
    	try {
    		db.execSQL("INSERT INTO meals VALUES(null, ?, ?, ?, ?, ?)", new Object[]{meal.getDate(), meal.getTime(), meal.getType(), meal.getName(), meal.getCalory()});  
			return true;
		} catch (Exception e) {
			return false;
		}
} 
    
    /** 
     * add meals 
     * @param meals 
     */  
    public void addMeals(List<Meal> meals) {  
        db.beginTransaction();  //开始事务  
        try {  
            for (Meal meal : meals) {  
                db.execSQL("INSERT INTO meals VALUES(null, ?, ?, ?, ?, ?)", new Object[]{meal.getDate(), meal.getTime(), meal.getType(), meal.getName(), meal.getCalory()});  
            }  
            db.setTransactionSuccessful();  //设置事务成功完成  
        } finally {  
            db.endTransaction();    //结束事务  
        }  
    }  
      
    /** 
     * update meal
     * @param meal 
     */  
    public void updateMeal(Meal meal) {  
        ContentValues cv = new ContentValues();  
        cv.put("type", meal.getType());
        cv.put("name", meal.getName());
        cv.put("calory", meal.getCalory());
        db.update("meals", cv, "_id = ?", new String[]{String.valueOf(meal.get_id())});  
    }  
     
    /** 
     * delete meals 
     * @param date 
     */  
    public boolean deleteMealByDate(String date) {  
		try {
			db.delete("meals", "date = ?", new String[] { date });
			return true;
		} catch (Exception e) {
			return false;
		}	
    }
    
    /** 
     * delete meal 
     * @param id 
     */  
    public boolean deleteMealByID(int id) {  
		try {
			db.delete("meals", "_id = ?", new String[] { String.valueOf(id) });
			return true;
		} catch (Exception e) {
			return false;
		}	
    }  
    
    /** 
     * query meals by date, return list 
     * @param 
     * @return List<Meal> 
     */  
    public List<Meal> queryByDate(String dateString) {  
        ArrayList<Meal> meals = new ArrayList<Meal>();  
        Cursor c = queryTheCursorByDate(dateString);  
        while (c.moveToNext()) {  
        	Meal meal = new Meal();  
        	meal.set_id(c.getInt(c.getColumnIndex("_id")));  
        	meal.setDate(Date.valueOf(c.getString(c.getColumnIndex("date")))); 
        	meal.setTime(Time.valueOf(c.getString(c.getColumnIndex("time")))); 
        	meal.setType(c.getString(c.getColumnIndex("type")));
        	meal.setName(c.getString(c.getColumnIndex("name")));
        	meal.setCalory(c.getInt(c.getColumnIndex("calory")));
        	meals.add(meal);  
        }  
        c.close();  
        return meals;  
    }  
    
    /** 
     * query all meals, return list 
     * @return List<Meal> 
     */  
    public List<Meal> queryAll() {  
        ArrayList<Meal> meals = new ArrayList<Meal>();  
        Cursor c = queryTheCursor();  
        while (c.moveToNext()) {  
        	Meal meal = new Meal();  
        	meal.set_id(c.getInt(c.getColumnIndex("_id")));  
        	meal.setDate(Date.valueOf(c.getString(c.getColumnIndex("date")))); 
        	meal.setTime(Time.valueOf(c.getString(c.getColumnIndex("time")))); 
        	meal.setType(c.getString(c.getColumnIndex("type")));
        	meal.setName(c.getString(c.getColumnIndex("name")));
        	meal.setCalory(c.getInt(c.getColumnIndex("calory")));
        	meals.add(meal);  
        }  
        c.close();  
        return meals;  
    }  
    
    /** 
     * query meals by date, return cursor 
     * @param String dateString
     * @return Cursor 
     */
    public Cursor queryTheCursorByDate(String dateString) {
    	Cursor c = db.rawQuery("SELECT * FROM meals where date = ?", new String[]{dateString});  
        return c; 
    }
    
    /** 
     * query all meals, return cursor 
     * @return  Cursor 
     */  
    public Cursor queryTheCursor() {  
        Cursor c = db.rawQuery("SELECT * FROM meals ORDER BY date", null);  
        return c;  
    }  
      
    /** 
     * close database 
     */  
    public void closeDB() {  
        db.close();  
    }  
}
