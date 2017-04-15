package com.example.naveen.EmotionApp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Naveen on 3/25/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    private final static int DB_VERSION = 5;
    private final static String DB_NAME = "emotionDB";

    private final static String CREATE_TABLE = "CREATE TABLE  emotion " +
            "( _id integer primary key autoincrement, " +
            "anger real, " +
            "contempt real, " +
            "disgust real, " +
            "fear real, " +
            "happiness real, " +
            "neutral real, " +
            "sadness real, " +
            "surprise real, " +
            "date nvarchar(20), " +
            "fileName nvarchar(200))";


    public DatabaseHelper(Context context){
        super(context,DB_NAME,null,DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("Drop table emotion");
        onCreate(sqLiteDatabase);
    }

}
