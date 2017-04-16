package com.example.naveen.EmotionApp;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

import junit.framework.Test;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class WriteNotesActivity extends AppCompatActivity {
    String capturedImageFilePath = "";
    NestedScrollView nestedScrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_notes);

        boolean isActivityStratedOnClickingListItem = false;
        if(isActivityStratedOnClickingListItem){

        }else {
            capturedImageFilePath = MyCamera.takePictureAndSave(this);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //System.out.println(capturedImageFilePath);
        if(requestCode == MyCamera.REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            //new ImageLoader(null, this).execute(capturedImageFilePath);
            //new DoNetworkTask(this).execute(capturedImageFilePath);
            new ImageLoader(null, this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, capturedImageFilePath);
            new DoNetworkTask(this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, capturedImageFilePath);
        }
        else{
            finish();
        }
    }

    protected void onClickButton(View view) {
        // Add text views dynamically
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.interactBox);

        // Add textview 1
        TextView textView1 = new TextView(this);
        LayoutParams layoutParamsLeft = new LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT);
        layoutParamsLeft.gravity = Gravity.LEFT;
        textView1.setLayoutParams(layoutParamsLeft);
        textView1.setText("programmatically created TextView1");
        textView1.setBackgroundResource(R.drawable.bubble1);
        //textView1.setBackgroundColor(0xff66ff66); // hex color 0xAARRGGBB
        //textView1.setPadding(20, 20, 20, 20);// in pixels (left, top, right, bottom)
        linearLayout.addView(textView1);

//        // Add textview 2
//        TextView textView2 = new TextView(this);
//        LayoutParams layoutParamsRight = new LayoutParams(LayoutParams.WRAP_CONTENT,
//                LayoutParams.WRAP_CONTENT);
//        layoutParamsRight.gravity = Gravity.RIGHT;
//        //layoutParams.setMargins(10, 10, 10, 10); // (left, top, right, bottom)
//        textView2.setLayoutParams(layoutParamsRight);
//        textView2.setText("programmatically created TextView2");
//        textView2.setBackgroundResource(R.drawable.bubble2);
//        //textView2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
//        //textView2.setBackgroundColor(0xffffdbdb); // hex color 0xAARRGGBB
//        linearLayout.addView(textView2);

        nestedScrollView = (NestedScrollView) findViewById(R.id.scroll);
        try {
            nestedScrollView.post(new Runnable() {
                @Override
                public void run() {
                    nestedScrollView.fullScroll(nestedScrollView.FOCUS_DOWN);
                }
            });
        }catch (Exception e){
            Log.d("Excep", e.getMessage());
        }

        Log.d("TAG", "onClickButton: After Click");
    }

    class ImageLoader extends AsyncTask<String, Void, Bitmap> {

        ImageView imageView;
        Activity callingActivity;

        public ImageLoader(ImageView imageView , Activity callingActivity) {
            this.imageView = imageView;
            this.callingActivity = callingActivity;
        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            System.out.println("in first async task background");
            if(strings[0].isEmpty())
                return null;
            //do image decoding task
            Bitmap bitmap = BitmapFactory.decodeFile(strings[0]);
            System.out.println("exiting first async task background");
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            System.out.println("in first async task post");
            if(bitmap != null){
                //callingActivity.setContentView(R.layout.activity_write_notes);
                imageView = (ImageView)callingActivity.findViewById(R.id.imageView);
                imageView.setImageBitmap(bitmap);
            }else {
                //callingActivity.finish();
            }
            System.out.println("exiting first async task post");
        }
    }

    class DoNetworkTask extends AsyncTask<String, Void, Emotion>{
        Activity callingActivity;

        public DoNetworkTask(Activity callingActivity) {
            this.callingActivity = callingActivity;
        }

        @Override
        protected Emotion doInBackground(String... strings) {
            System.out.println("in second async task background");
            if(strings[0].isEmpty())
                return null;
            //do network api task here
            // get emotions of picture from api
            System.out.println("exiting second async task background");
            return new ImageProcessor().processEmotions(strings[0]);
        }

        protected void onPostExecute(Emotion emotion) {
            System.out.println("in second async task post");

            if (emotion != null) {
                System.out.println("anger : " + Math.round(emotion.anger*100));
                System.out.println("contempt : " + Math.round(emotion.contempt*100));
                System.out.println("disgust : " + Math.round(emotion.disgust*100));
                System.out.println("fear : " + Math.round(emotion.fear*100));
                System.out.println("happiness : " + Math.round(emotion.happiness*100));
                System.out.println("neutral : " + Math.round(emotion.neutral*100));
                System.out.println("sadness : " + Math.round(emotion.sadness*100));
                System.out.println("surprise : " + Math.round(emotion.surprise*100));
                System.out.println("date: "+ emotion.date);
                System.out.println("file: "+ emotion.fileName);
                // Fill progress bars here
                //private ProgressBar progreso;
                ObjectAnimator progressAnimator;
                ProgressBar happinessBar = (ProgressBar) callingActivity.findViewById(R.id.progressBar);
                //happinessBar.setProgress(Math.round( (emotion.happiness+emotion.surprise) * 100));
                progressAnimator = ObjectAnimator.ofInt(happinessBar, "progress", 0, Math.round((emotion.happiness+emotion.surprise) * 100));
                progressAnimator.setDuration(1500);
                progressAnimator.start();
                ProgressBar sadBar = (ProgressBar) findViewById(R.id.progressBar2);
                //sadBar.setProgress(Math.round( (emotion.sadness+emotion.anger+emotion.contempt+emotion.disgust+emotion.fear) * 100));
                progressAnimator = ObjectAnimator.ofInt(sadBar, "progress", 0, Math.round((emotion.sadness+emotion.anger+emotion.contempt+emotion.disgust+emotion.fear) * 100));
                progressAnimator.setDuration(1500);
                progressAnimator.start();
                ProgressBar NeutralBar = (ProgressBar) findViewById(R.id.progressBar3);
                //NeutralBar.setProgress(Math.round(emotion.neutral * 100));
                progressAnimator = ObjectAnimator.ofInt(NeutralBar, "progress", 0, Math.round(emotion.neutral * 100));
                progressAnimator.setDuration(1500);
                progressAnimator.start();

                //Save emotion to database
                emotion.save(getApplicationContext());
            }
            System.out.println("exiting second async task post");
        }
    }
}
