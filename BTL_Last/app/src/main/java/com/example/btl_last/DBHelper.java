package com.example.btl_last;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DBName = "login.db";


    public DBHelper( Context context) {
        super(context , "login.db" , null ,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE USER(username TEXT PRIMARY KEY , password TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS USER");
    }

    public boolean InSData(String user , String pass ){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("username" , user );
        values.put("password" , pass);

        long result = db.insert("USER" , null , values );
        if (result == -1) return false;
        else              return true;
    }

    public Boolean checkUser (String username){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM USER WHERE username = ? " , new String[] {username});
        if (cursor.getCount() > 0 ){
            return true;
        }else {
            return false;
        }
    }

    public Boolean checkUserPassword (String user , String password){
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM USER WHERE username=? AND password=?" , new String[] {user, password});
        if (cursor.getCount() > 0){
            return true;
        }else {
            return false;
        }

    }
}
