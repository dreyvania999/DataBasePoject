package com.example.databasepoject;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    public static final int DBVersion =2;
    public static final String DBName = "contactDb";
    public static final String Name = "name";
    public static final String KEY_ID = "_id";
    public static final String Phone = "phone";
    public static final String Surname = "surname";
    public static final String Address = "address";
    public static final String Сontacts = "contacts";
    public static final String Login = "login";
    public static final String Password = "password";
    public static final String Registration = "registration";





    public DBHelper(Context context) {
        super(context, DBName, null, DBVersion);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table " + Сontacts + "(" + KEY_ID
                + " integer primary key," + Name + " text," + Phone + " text," + Surname + " text," + Address + " text" + ")");
        sqLiteDatabase.execSQL("create table " + Registration + "(" + KEY_ID
                + " integer primary key," + Login + " text," + Password + " text" + ")");
        sqLiteDatabase.execSQL("insert into " + Registration + "("  + Login + ", " + Password  + ")"+ " values"+"("  + "'admin' ,  'admin'"  + ")");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists " + Сontacts);
        sqLiteDatabase.execSQL("drop table if exists " + Registration);
        onCreate(sqLiteDatabase);
    }
}
