package com.example.naveen.EmotionApp;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Naveen on 4/25/2017.
 */

public class Helper {

    public static String formatDate(String dateFormat, String dateString){
        SimpleDateFormat customFormatter = null;
        Date date = null;
        try {
             date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(dateString);

            if(dateFormat == null || dateFormat.isEmpty())
                customFormatter = new SimpleDateFormat("d MMM, ''yy");
            else
                customFormatter = new SimpleDateFormat(dateFormat);


        }catch (Exception e){

        }

        return customFormatter.format(date);
    }

    public static String formatTime(String timeFormat, String dateString){
        SimpleDateFormat customFormatter = null;
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(dateString);

            if(timeFormat == null || timeFormat.isEmpty())
                customFormatter = new SimpleDateFormat("K:mm:ss aaa");
            else
                customFormatter = new SimpleDateFormat(timeFormat);


        }catch (Exception e){

        }

        return customFormatter.format(date);
    }
}
