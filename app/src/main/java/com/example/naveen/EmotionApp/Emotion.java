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

    float anger;
    float contempt;
    float disgust;
    float fear;
    float happiness;
    float neutral;
    float sadness;
    float surprise;
    String date;
    String fileName;

    public Emotion() {}

    public Emotion(float anger, float contempt, float disgust, float fear, float happiness, float neutral, float sadness, float surprise, String date, String fileName) {
        this.anger = anger;
        this.contempt = contempt;
        this.disgust = disgust;
        this.fear = fear;
        this.happiness = happiness;
        this.neutral = neutral;
        this.sadness = sadness;
        this.surprise = surprise;
        this.date = date;
        this.fileName = fileName;
    }

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
        contentValues.put(TableInof.COL_DATE, this.date);
        contentValues.put(TableInof.COL_FILE, this.fileName);

        //insert return -1 if there is error
        return database.insert(TableInof.TABLE, null, contentValues)== -1 ? false : true ;
    }

    public static List<Emotion> listAll(Context context){
        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        SQLiteDatabase database = databaseHelper.getWritableDatabase();

        List<Emotion> list = new ArrayList<Emotion>();
        Cursor cursor = database.rawQuery("Select * from " + TableInof.TABLE , null);


//        id integer primary key autoincrement, " +  0
//        "anger real, " +   1
//                "contempt real, " + 2
//                "disgust real, " + 3
//                "fear real, " + 4
//                "happiness real, " + 5
//                "neutral real, " + 6
//                "sadness real, " + 7
//                "surprise real, " + 8
//                "date nvarchar(20), " + 9
//                "fileName nvarchar(200))"; 10

        //moveToFirst() check if there is atleast one row
        if(cursor.moveToFirst()) {
            do {
                Emotion emotion = new Emotion();
                emotion.anger = cursor.getFloat(1);
                emotion.contempt = cursor.getFloat(2);
                emotion.disgust = cursor.getFloat(3);
                emotion.fear = cursor.getFloat(4);
                emotion.happiness = cursor.getFloat(5);
                emotion.neutral = cursor.getFloat(6);
                emotion.sadness = cursor.getFloat(7);
                emotion.surprise = cursor.getFloat(8);
                emotion.date = cursor.getString(9);
                emotion.fileName = cursor.getString(10);
                list.add(emotion);

            } while (cursor.moveToNext());
        }

        return list;
    }

//    public static List<Emotion> listBySelectionCriteria(Context context, boolean distinct,
//                                                        String[] columns,
//                                                        String selection,
//                                                        String[] selectionArgs,
//                                                        String groupBy,
//                                                        String having,
//                                                        String orderBy,
//                                                        String limit)
//    {
//        DatabaseHelper databaseHelper = new DatabaseHelper(context);
//        SQLiteDatabase database = databaseHelper.getWritableDatabase();
//        List<Emotion> list = new ArrayList<Emotion>();
//        Cursor cursor = database.query(distinct, TableInof.TABLE, columns, selection , selectionArgs, groupBy, having,orderBy, limit);
//
//        // check if there is atleast one row
//        if(cursor.moveToFirst()) {
//            do {
//                Emotion emotion = new Emotion();
////                emotion.anger = cursor.getFloat(0);
////                emotion.contempt = cursor.getFloat(1);
////                emotion.disgust = cursor.getFloat(2);
////                emotion.fear = cursor.getFloat(3);
////                emotion.happiness = cursor.getFloat(4);
////                emotion.neutral = cursor.getFloat(5);
////                emotion.sadness = cursor.getFloat(6);
////                emotion.surprise = cursor.getFloat(7);
//                emotion.date = cursor.getString(0);
//                emotion.fileName = cursor.getString(1);
//                list.add(emotion);
//
//            } while (cursor.moveToNext());
//        }
//
//        return list;
//    }

    public static Cursor getCursorlistBySelectionCriteria(Context context, boolean distinct,
                                                 String[] columns,
                                                 String selection,
                                                 String[] selectionArgs,
                                                 String groupBy,
                                                 String having,
                                                 String orderBy,
                                                 String limit){
        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        SQLiteDatabase database = databaseHelper.getWritableDatabase();
        List<Emotion> list = new ArrayList<Emotion>();
        Cursor cursor = database.query(distinct, TableInof.TABLE, columns, selection , selectionArgs, groupBy, having,orderBy, limit);
        return  cursor;
    }


    public static List<Emotion> getEmotionsListForGraphView(Context context){
        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        SQLiteDatabase database = databaseHelper.getWritableDatabase();

        List<Emotion> list = new ArrayList<Emotion>();
        String sqlQuery = "Select  " +
                "  avg(anger) as anger" +
                ", avg(contempt) as contempt" +
                ", avg(disgust) as disgust" +
                ", avg(fear) as fear" +
                ", avg(happiness) as happiness" +
                ", avg(neutral) as neutral" +
                ", avg(sadness) as sadness" +
                ", avg(surprise) as surprise" +
                ",  substr(date,1,10) as dateOnly " +
                " from emotion group by dateOnly " +
                " order by dateOnly desc" +
                " limit 15" ;
        Cursor cursor = database.rawQuery(sqlQuery , null);


//        id integer primary key autoincrement, " +  0
//        "anger real, " +   1
//                "contempt real, " + 2
//                "disgust real, " + 3
//                "fear real, " + 4
//                "happiness real, " + 5
//                "neutral real, " + 6
//                "sadness real, " + 7
//                "surprise real, " + 8
//                "date nvarchar(20), " + 9
//                "fileName nvarchar(200))"; 10

        //moveToFirst() check if there is atleast one row
        if(cursor.moveToFirst()) {
            do {
                Emotion emotion = new Emotion();
                emotion.anger = cursor.getFloat(0);
                emotion.contempt = cursor.getFloat(1);
                emotion.disgust = cursor.getFloat(2);
                emotion.fear = cursor.getFloat(3);
                emotion.happiness = cursor.getFloat(4);
                emotion.neutral = cursor.getFloat(5);
                emotion.sadness = cursor.getFloat(6);
                emotion.surprise = cursor.getFloat(7);
                emotion.date = cursor.getString(8);
                //emotion.fileName = cursor.getString(10);
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
        final static String COL_FILE = "fileName";
    }
}
