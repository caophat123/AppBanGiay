// DBUser.java
package com.example.doan_1.mydatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class DBUser extends SQLiteOpenHelper {
    public static final String DBName = "user.db";

    public DBUser(Context context) {
        super(context, DBName, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table users(username TEXT primary key, numberphone TEXT, password TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists users");
    }

    public boolean insertData(String username, String numberphone, String password) {
        SQLiteDatabase myDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", username);
        contentValues.put("numberphone", numberphone);
        contentValues.put("password", password);

        myDB.beginTransaction();
        try {
            long result = myDB.insert("users", null, contentValues);
            if (result != -1) {
                // Thêm lệnh Log khi insert thành công
                Log.d("DBUser", "Inserted user with username: " + username);
            } else {
                // Thêm lệnh Log khi insert lỗi
                Log.e("DBUser", "Error inserting data for user: " + username);
            }
            myDB.setTransactionSuccessful();
            return result != -1;
        } catch (Exception e) {
            Log.e("DBUser", "Error inserting data: " + e.getMessage());
            return false;
        } finally {
            myDB.endTransaction();
        }
    }

    public boolean checkUsername(String username) {
        SQLiteDatabase myDB = this.getWritableDatabase();
        Cursor cursor = myDB.rawQuery("select * from users where username = ?", new String[]{username});
        boolean exists = cursor.getCount() > 0; // Kiểm tra kết quả và đóng cursor
        cursor.close();
        return exists;
    }

    public boolean checkUser(String username, String pwd) {
        SQLiteDatabase myDB = this.getWritableDatabase();
        Cursor cursor = myDB.rawQuery("select * from users where username = ? and password = ?", new String[]{username, pwd});
        boolean valid = cursor.getCount() > 0; // Kiểm tra kết quả và đóng cursor
        cursor.close();
        return valid;
    }
}
