package com.example.naveen.EmotionApp;

import android.content.Context;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

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
        DataPoint[] anger = new DataPoint[list.size()];

        for (int i = 0; i < list.size(); i++) {
            double Y = Double.parseDouble(list.get(i).anger);
            int X = Integer.parseInt(getDay(list.get(i).date));
            anger[i] = new DataPoint( X, Y );
        }

        LineGraphSeries<DataPoint> angerSeries = new LineGraphSeries<DataPoint>(anger);
//        LineGraphSeries<DataPoint> contemptSeries = new LineGraphSeries<>();
//        LineGraphSeries<DataPoint> disgustSeries = new LineGraphSeries<>();
//        LineGraphSeries<DataPoint> fearSeries = new LineGraphSeries<>();
//        LineGraphSeries<DataPoint> happinessSeries = new LineGraphSeries<>();
//        LineGraphSeries<DataPoint> neutralSeries = new LineGraphSeries<>();
//        LineGraphSeries<DataPoint> sadSeries = new LineGraphSeries<>();
//        LineGraphSeries<DataPoint> surpriseSeries = new LineGraphSeries<>();
        graphView.getViewport().setXAxisBoundsManual(true);
        graphView.getViewport().setMinX(1);
        graphView.getViewport().setMaxX(31);

        graphView.getViewport().setYAxisBoundsManual(true);
        graphView.getViewport().setMinY(-10);
        graphView.getViewport().setMaxY(10);

        graphView.getGridLabelRenderer().setHorizontalAxisTitle("Day");
        graphView.getGridLabelRenderer().setVerticalAxisTitle("Socres");

        //graphView.getGridLabelRenderer().setNumVerticalLabels(10);
        //graphView.getGridLabelRenderer().setNumHorizontalLabels(10);

//        graphView.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter(){
//            @Override
//            public String formatLabel(double value, boolean isValueX) {
//                if (isValueX) {
//                    // show normal x values
//                    String Xlabel = super.formatLabel(value, isValueX);
//                    return Xlabel.substring(0, Xlabel.indexOf('.')-1);
//                } else {
//                    // show currency for y values
//                    return super.formatLabel(value, isValueX);
//                }
//            }
//        });

        //graphView.getViewport().setScrollable(true);

        graphView.addSeries(angerSeries);

    }

    //date format yyyy/MM/dd
    public static String getDay(String date) {
        return date.substring(date.lastIndexOf('/') + 1);
    }
}
