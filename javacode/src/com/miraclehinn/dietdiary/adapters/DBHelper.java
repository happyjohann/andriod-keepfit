package com.miraclehinn.dietdiary.adapters;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "diet.db";  
    private static final int DATABASE_VERSION = 1;  
	
	public DBHelper(Context context) {  
        //CursorFactory设置为null,使用默认值  
        super(context, DATABASE_NAME, null, DATABASE_VERSION);  
    }
	
	//数据库第一次被创建时onCreate会被调用
	@Override
	public void onCreate(SQLiteDatabase arg0) {
		arg0.execSQL("CREATE TABLE IF NOT EXISTS meals" +  
                "(_id INTEGER PRIMARY KEY AUTOINCREMENT, date DATE, time TIME, type VARCHAR(4), name VAECHAR(32), calory INTEGER)");
	}

	//如果DATABASE_VERSION值被改为2,系统发现现有数据库版本不同,即会调用onUpgrade
	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		//arg0.execSQL("ALTER TABLE meals MODIFY COLUMN calory REAL");  
		arg0.execSQL("ALTER TABLE meals ADD COLUMN photo BLOB"); 
	}

}
