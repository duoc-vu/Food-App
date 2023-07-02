package com.example.btl_last;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.os.Build;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.time.LocalDateTime;
import java.util.ArrayList;

@RequiresApi(api = Build.VERSION_CODES.O)
public class Database extends SQLiteOpenHelper {

    final static String TABLE_NAME = "monAn";
    final static String COLUMN_NAME_1 = "tenMonAn";
    final static String COLUMN_NAME_2 = "chuThich";
    final static String COLUMN_NAME_3 = "diaChi";



    public Database(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public void QueryData (String sql){
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql);

    }

    public Cursor getData(String sql){
        SQLiteDatabase database = getWritableDatabase();
        return database.rawQuery(sql , null);
    }

    public void InsertMonAn(String tenMonAn , String chuThich , String diaChi , byte[] hinh ){
        SQLiteDatabase database = getWritableDatabase();
        String sql = "INSERT INTO monAn VALUES ( null , ? , ?, ? , ? )";
        SQLiteStatement sqLiteStatement = database.compileStatement(sql);
        sqLiteStatement.clearBindings();
        sqLiteStatement.bindString(1 , tenMonAn );
        sqLiteStatement.bindString(2 , chuThich);
        sqLiteStatement.bindString(3 , diaChi);

        sqLiteStatement.bindBlob(4 , hinh);

        sqLiteStatement.executeInsert();

    }



    public void drop (){
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL("DROP TABLE monAn");

    }

    public void delAll (){
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL("DELETE FROM monAn");

    }



    public void UpDate( int id ,  String tenMonAn , String chuThich , String diaChi, byte[] hinh ){
        SQLiteDatabase database = getWritableDatabase();
        String sql = "UPDATE monAn SET tenMonAn = ? , chuThich = ? , diaChi = ? , hinh = ? WHERE ID = ? ";
//        database.execSQL("UPDATE " + TABLE_NAME + " SET " + COLUMN_NAME_1
//                + " = '" + tenMonAn  + "' , " + COLUMN_NAME_2
//                + " = '"  + chuThich + "' , " + COLUMN_NAME_3 + "= '" + diaDiem + "' WHERE ID = " + id);
        SQLiteStatement sqLiteStatement = database.compileStatement(sql);
        sqLiteStatement.clearBindings();
        sqLiteStatement.bindString(1 , tenMonAn );
        sqLiteStatement.bindString(2 , chuThich);
        sqLiteStatement.bindString(3 , diaChi);
        sqLiteStatement.bindBlob(4 , hinh);
        sqLiteStatement.bindLong(5 , id);

        sqLiteStatement.executeUpdateDelete();
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }



    public ArrayList<String> getAllMonAn(){
        ArrayList<String> lsMonAn = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cs = db.rawQuery("Select * from " + TABLE_NAME, null);
        while (cs.moveToNext()){
            int id = cs.getInt(0);
            String ten = cs.getString(1);
            String chuThich = cs.getString(2);
            String diaDiem = cs.getString(3);

            byte[] hinh = cs.getBlob(5);
            lsMonAn.add(new monAn(id , ten , chuThich , diaDiem , hinh ).toString());
        }
        return  lsMonAn;
    }
}

/*String sql = "select * from " + tableName + "where column1 = " + param1 + "and column2 = " + param2;
Cursor cur = _db.rawQuery( sql, new String[0] );

if(cur.getCount() == 0)
{
    //upload
}





















/*
public void timMonAn( String diaChi){
        SQLiteDatabase database = getWritableDatabase();
        String sql = "SELECT * FROM monAn WHERE diaChi = ? ";
        SQLiteStatement sqLiteStatement = database.compileStatement(sql);
        sqLiteStatement.clearBindings();
        sqLiteStatement.bindString(1 , diaChi);
    }
 */