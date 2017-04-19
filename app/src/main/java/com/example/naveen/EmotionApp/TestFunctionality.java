package com.example.naveen.EmotionApp;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;

import com.jjoe64.graphview.GraphView;

import java.util.List;

/**
 * Created by Naveen on 3/27/2017.
 */

public class TestFunctionality {

    public static  void testSql(Context context){
        Emotion emotion = new Emotion();
        emotion.anger = 1.0f;
        emotion.contempt = 2.0f;
        emotion.disgust = 4.3f;

        emotion.save(context);
        List<Emotion> list = Emotion.listAll(context);

        for(Emotion e : list)
            System.out.println("Tag"+ e.anger + e.contempt);
    }

    public static void testGraph(GraphView graphView, Activity currentActivity){
        //List<Emotion> list = new ArrayList<Emotion>();

//        String day = GraphHelper.getDay(new SimpleDateFormat("yyyy/MM/dd").format(new Date()).toString());
//        double dayD = Double.parseDouble(String.valueOf("1"));
//        Log.d("Tag ","Day is " + dayD);
        //currentActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
//        Calendar calendar = Calendar.getInstance();
//        calendar.set(2017,1,1);
//
//        for (int i = 0; i < 30; i++) {
//            Emotion e = new Emotion();
//            e.anger = i%10;
//            Date d = calendar.getTime();
//            calendar.add(Calendar.DATE, 1);
//            e.date = new SimpleDateFormat("yyyy/MM/dd").format(d).toString();
//            list.add(e);
//        }

        List<Emotion> list = Emotion.getEmotionsListForGraphView(currentActivity);

        GraphHelper.showStatistics(list,graphView,currentActivity);

//
////        Calendar calendar = Calendar.getInstance();
////        Date d1 = calendar.getTime();
////        calendar.add(Calendar.DATE, 1);
////        Date d2 = calendar.getTime();
////        calendar.add(Calendar.DATE, 1);
////        Date d3 = calendar.getTime();

    }

    public static void testUserPostMsgTable(Context context, String fileName, String msg){
        UserMsg.InserMsg(context,fileName,msg);


        String sql = "select fileName,postNo,msg from UserPostMsg order by fileName asc, postNo asc";
        Cursor cursor = UserMsg.executeRawSql(context,sql);
        while (cursor.moveToNext()){
            String file = cursor.getString(0);
            int postNo = cursor.getInt(1);
            String msgdb = cursor.getString(2);
            String result = file.substring(file.lastIndexOf("/")) +"," + postNo+ "," + msgdb ;
            System.out.println(result);
         }
    }
}
