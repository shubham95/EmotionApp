package com.example.naveen.EmotionApp;

import android.content.Context;
import android.graphics.Color;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Naveen on 3/26/2017.
 */

public class GraphHelper {

    /***
     * This method showStatistics in the given graphView.
     * @param list list of emotions to be showed on the graph. list must be sorted
     * @param graphView graphview where to show the graph-statistics
     * @param context application context
     */
    public static void showStatistics(List<Emotion> list , GraphView graphView, Context context){
        try {

            DataPoint[] anger = new DataPoint[list.size()];
            DataPoint[] happy = new DataPoint[list.size()];

            for (int i = list.size() -1 ; i >= 0 ; i--) {
                Date X =  new SimpleDateFormat("yyyy-MM-dd").parse(list.get(i).date);
                double Y = list.get(i).anger;
                anger[i] = new DataPoint(X, Y);

                Y = list.get(i).happiness;
                happy[i] = new DataPoint(X, Y);
            }


            LineGraphSeries<DataPoint> angerSeries = new LineGraphSeries<DataPoint>(anger);
            angerSeries.setTitle("anger");
            angerSeries.setDataPointsRadius(5);
            angerSeries.setDrawDataPoints(true);
            angerSeries.setColor(Color.RED);
//        LineGraphSeries<DataPoint> contemptSeries = new LineGraphSeries<>();
//        LineGraphSeries<DataPoint> disgustSeries = new LineGraphSeries<>();
//        LineGraphSeries<DataPoint> fearSeries = new LineGraphSeries<>();
            LineGraphSeries<DataPoint> happinessSeries = new LineGraphSeries<>(happy);
            happinessSeries.setTitle("happiness");
            happinessSeries.setDrawDataPoints(true);
            happinessSeries.setDataPointsRadius(5);
            happinessSeries.setColor(Color.BLUE);
//        LineGraphSeries<DataPoint> neutralSeries = new LineGraphSeries<>();
//        LineGraphSeries<DataPoint> sadSeries = new LineGraphSeries<>();
//        LineGraphSeries<DataPoint> surpriseSeries = new LineGraphSeries<>();

            graphView.getViewport().setXAxisBoundsManual(true);
            Date minX = new SimpleDateFormat("yyyy-MM-dd").parse(list.get(list.size() - 1).date);
            Date maxX = new SimpleDateFormat("yyyy-MM-dd").parse(list.get(0).date);
            graphView.getViewport().setMinX(minX.getTime());
            graphView.getViewport().setMaxX(maxX.getTime());
            graphView.getGridLabelRenderer().setNumHorizontalLabels(list.size());

            graphView.getViewport().setYAxisBoundsManual(true);
            graphView.getViewport().setMinY(0);
            graphView.getViewport().setMaxY(1);

            graphView.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(graphView.getContext()));
            graphView.getGridLabelRenderer().setHorizontalAxisTitle("Day");
            graphView.getGridLabelRenderer().setHorizontalLabelsAngle(110);
            graphView.getGridLabelRenderer().setVerticalAxisTitle("Socres");

            //to show legends at the top
            graphView.getLegendRenderer().setVisible(true);
            graphView.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);

            graphView.addSeries(angerSeries);
            graphView.addSeries(happinessSeries);
        }catch (Exception e){

        }

    }

//    //date format yyyy-MM-dd'T'HH:mm:ss
//    public static String getDay(String date) {
//        int startIndex = date.lastIndexOf('-') + 1;
//        return date.substring(startIndex, startIndex + 2);
//    }
}
