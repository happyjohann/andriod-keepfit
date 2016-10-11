package com.miraclehinn.dietdiary.adapters;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "diet.db";  
    private static final int DATABASE_VERSION = 1;  
	
	public DBHelper(Context context) {  
        //CursorFactory����Ϊnull,ʹ��Ĭ��ֵ  
        super(context, DATABASE_NAME, null, DATABASE_VERSION);  
    }
	
	//���ݿ��һ�α�����ʱonCreate�ᱻ����
	@Override
	public void onCreate(SQLiteDatabase arg0) {
		arg0.execSQL("CREATE TABLE IF NOT EXISTS meals" +  
                "(_id INTEGER PRIMARY KEY AUTOINCREMENT, date DATE, time TIME, type VARCHAR(4), name VAECHAR(32), calory INTEGER)");
	}

	//���DATABASE_VERSIONֵ����Ϊ2,ϵͳ�����������ݿ�汾��ͬ,�������onUpgrade
	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		//arg0.execSQL("ALTER TABLE meals MODIFY COLUMN calory REAL");  
		arg0.execSQL("ALTER TABLE meals ADD COLUMN photo BLOB"); 
	}

}
