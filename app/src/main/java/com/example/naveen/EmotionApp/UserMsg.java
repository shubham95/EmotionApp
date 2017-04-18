package com.example.naveen.EmotionApp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Naveen on 4/17/2017.
 */

public class UserMsg {
//    String fileName;
//    int ansNo;
//    String answer;
//    String date;


//    public  boolean saveAns(Context context){
//        DatabaseHelper databaseHelper = new DatabaseHelper(context);
//        SQLiteDatabase database = databaseHelper.getWritableDatabase();
//
//        ContentValues contentValues = new ContentValues();
//        contentValues.put("fileName", this.fileName);
//        contentValues.put("postNo", this.ansNo);
//        contentValues.put("msg", this.answer);
//        contentValues.put("date", this.date);
//
//        return database.insert("UserPostMsg",null, contentValues) == -1 ? false : true;
//    }

    public static Cursor executeRawSql( Context context,String sql){
        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        SQLiteDatabase database = databaseHelper.getWritableDatabase();

        Cursor cursor = database.rawQuery(sql, null);
        return  cursor;
    }

    public static void InserMsg(Context context, String fileName,String msg){
        String sql = "insert into UserPostMsg ( postNo,fileName, msg, date) " +
                      "select " +
                        "case when max(postNo) is not null then max(postNo)+1 else 1 end ," +
                        "'" + fileName + "'"+ ", " +
                        "'"+ msg +"'" + "," +
                        "'"+ new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(new Date()).toString()+ "'" +
                        "from UserPostMsg " +
                "       where fileName=" + "'"+fileName +"'";
        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        SQLiteDatabase database = databaseHelper.getWritableDatabase();
        try {
            database.execSQL(sql, new String[]{});
        }catch(Exception e){
            System.out.print(e.getMessage());
        }

    }
}
