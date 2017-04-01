package com.example.naveen.EmotionApp;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;

import com.jjoe64.graphview.GraphView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Naveen on 3/27/2017.
 */

public class TestFunctionality {

    public static  void testSql(Context context){
        Emotion emotion = new Emotion();
        emotion.anger = "1.0";
        emotion.contempt = "2.0";
        emotion.disgust = "4.3";

        emotion.save(context);
        List<Emotion> list = Emotion.listAll(context);

        for(Emotion e : list)
            System.out.println("Tag"+ e.anger + e.contempt);
    }

    public static void testGraph(GraphView graphView, Activity currentActivity){
        List<Emotion> list = new ArrayList<Emotion>();

//        String day = GraphHelper.getDay(new SimpleDateFormat("yyyy/MM/dd").format(new Date()).toString());
//        double dayD = Double.parseDouble(String.valueOf("1"));
//        Log.d("Tag ","Day is " + dayD);
        //currentActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        Calendar calendar = Calendar.getInstance();
        calendar.set(2017,1,1);

        for (int i = 0; i < 30; i++) {
            Emotion e = new Emotion();
            e.anger = String.valueOf(i%10);
            Date d = calendar.getTime();
            calendar.add(Calendar.DATE, 1);
            e.date = new SimpleDateFormat("yyyy/MM/dd").format(d).toString();
            list.add(e);
        }

        GraphHelper.showStatistics(list,graphView,currentActivity);

//
////        Calendar calendar = Calendar.getInstance();
////        Date d1 = calendar.getTime();
////        calendar.add(Calendar.DATE, 1);
////        Date d2 = calendar.getTime();
////        calendar.add(Calendar.DATE, 1);
////        Date d3 = calendar.getTime();

    }
}
