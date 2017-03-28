package com.example.naveen.EmotionApp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Naveen on 3/25/2017.
 */

public class Emotion {

    String anger;
    String contempt;
    String disgust;
    String fear;
    String happiness;
    String neutral;
    String sadness;
    String surprise;
    String date;


    public boolean save(Context context){
        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        SQLiteDatabase database = databaseHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TableInof.COL_ANGER, this.anger);
        contentValues.put(TableInof.COL_CONTEMTP, this.contempt);
        contentValues.put(TableInof.COL_DISGUST, this.disgust);
        contentValues.put(TableInof.COL_FEAR, this.fear);
        contentValues.put(TableInof.COL_HAPPY, this.happiness);
        contentValues.put(TableInof.COL_NEUTRAL, this.neutral);
        contentValues.put(TableInof.COL_SAD, this.sadness);
        contentValues.put(TableInof.COL_SURPRISE, this.surprise);
        contentValues.put(TableInof.COL_DATE, new SimpleDateFormat("yyyy/MM/dd").format(new Date()).toString());

        //insert return -1 if there is error
        return database.insert(TableInof.TABLE, null, contentValues)== -1 ? false : true ;
    }

    public static List<Emotion> listAll(Context context){
        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        SQLiteDatabase database = databaseHelper.getWritableDatabase();

        List<Emotion> list = new ArrayList<Emotion>();
        Cursor cursor = database.rawQuery("Select * from " + TableInof.TABLE , null);

        //moveToFirst() check if there is atleast one row
        if(cursor.moveToFirst()) {
            do {
                Emotion emotion = new Emotion();
                emotion.anger = cursor.getString(0);
                emotion.contempt = cursor.getString(1);
                emotion.disgust = cursor.getString(2);
                emotion.fear = cursor.getString(3);
                emotion.happiness = cursor.getString(4);
                emotion.neutral = cursor.getString(5);
                emotion.sadness = cursor.getString(6);
                emotion.surprise = cursor.getString(7);
                emotion.date = cursor.getString(8);
                list.add(emotion);

            } while (cursor.moveToNext());
        }

        return list;
    }

    public static List<Emotion> listBySelectionCriteria(Context context, String selectionCriteria, String[] selectionArgs ){
        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        SQLiteDatabase database = databaseHelper.getWritableDatabase();
        List<Emotion> list = new ArrayList<Emotion>();
        Cursor cursor = database.query(false, TableInof.TABLE, new String[]{}, selectionCriteria , selectionArgs, null, null,null, null);

        // check if there is atleast one row
        if(cursor.moveToFirst()) {
            do {
                Emotion emotion = new Emotion();
                emotion.anger = cursor.getString(0);
                emotion.contempt = cursor.getString(1);
                emotion.disgust = cursor.getString(2);
                emotion.fear = cursor.getString(3);
                emotion.happiness = cursor.getString(4);
                emotion.neutral = cursor.getString(5);
                emotion.sadness = cursor.getString(6);
                emotion.surprise = cursor.getString(7);
                emotion.date = cursor.getString(8);
                list.add(emotion);

            } while (cursor.moveToNext());
        }

        return list;
    }



    final static class TableInof{
        final static String TABLE = "emotion";
        final static String COL_ANGER = "anger";
        final static String COL_CONTEMTP = "contempt";
        final static String COL_DISGUST = "disgust";
        final static String COL_FEAR = "fear";
        final static String COL_HAPPY = "happiness";
        final static String COL_NEUTRAL= "neutral";
        final static String COL_SAD = "sadness";
        final static String COL_SURPRISE = "surprise";
        final static String COL_DATE = "date";
    }
}
