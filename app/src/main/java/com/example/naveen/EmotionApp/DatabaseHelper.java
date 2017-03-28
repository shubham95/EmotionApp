package com.example.naveen.EmotionApp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Naveen on 3/25/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    private final static int DB_VERSION = 2;
    private final static String DB_NAME = "emotionDB";

    private final static String CREATE_TABLE = "CREATE TABLE  emotion " +
            "(anger nvarchar(20), " +
            "contempt nvarchar(20), " +
            "disgust nvarchar(20), " +
            "fear nvarchar(20), " +
            "happiness nvarchar(20), " +
            "neutral nvarchar(20), " +
            "sadness nvarchar(20), " +
            "surprise nvarchar(20), " +
            "date nvarchar(20))" ;


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
