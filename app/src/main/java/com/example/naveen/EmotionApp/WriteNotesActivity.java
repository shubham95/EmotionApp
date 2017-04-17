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
import android.widget.EditText;
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
    int systemMessageCount = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_notes);
        capturedImageFilePath  = MyCamera.takePictureAndSave(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //System.out.println(capturedImageFilePath);
        if(requestCode == MyCamera.REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            //new ImageLoader(null, this).execute(capturedImageFilePath);
            //new DoNetworkTask(this).execute(capturedImageFilePath);
            createMessage("system", "Diary: Hi, How are you today ?");
            new ImageLoader(null, this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, capturedImageFilePath);
            new DoNetworkTask(this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, capturedImageFilePath);
        }
        else{
            finish();
        }
    }
    protected void createMessage(String who, String text) {
        // Add text views dynamically
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.interactBox);
        // Add textview 1
        TextView textView1 = new TextView(this);
        LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        if (who == "system") {
            layoutParams.gravity = Gravity.LEFT;
            textView1.setBackgroundResource(R.drawable.bubble1);
            systemMessageCount++;
        }
        else if (who == "user") {
            layoutParams.gravity = Gravity.RIGHT;
            textView1.setBackgroundResource(R.drawable.bubble2);
        }
        else {
            System.out.println("Error in creating the message because of wrong who");
            return;
        }
        textView1.setLayoutParams(layoutParams);
        textView1.setText(text);
        linearLayout.addView(textView1);
        //textView1.setBackgroundColor(0xff66ff66); // hex color 0xAARRGGBB
        //textView1.setPadding(20, 20, 20, 20);// in pixels (left, top, right, bottom)

        nestedScrollView = (NestedScrollView) findViewById(R.id.scroll);
        try {
            nestedScrollView.post(new Runnable() {
                @Override
                public void run() {
                    nestedScrollView.fullScroll(nestedScrollView.FOCUS_DOWN);
                }
            });
        }catch (Exception e){
            Log.d("Exception", e.getMessage());
        }
    }

    protected void onClickButton(View view) {
        EditText messageText = (EditText) findViewById(R.id.messageText);
        String message = messageText.getText().toString();
        if ( message != null && !message.isEmpty() ) {
            createMessage("user", "Me: " + message);
            messageText.setText("");
        }
        Log.d("TAG", "onClickButton: After Click");
    }

    protected int maxOfThree(float x, float y, float z) {
        if ( x > y && x > z )
            return 1;
        else if ( y > x && y > z )
            return 2;
        else if ( z > x && z > y )
            return 3;
        else
            return 0;
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

                // Categorize emotions in 3
                float firstCategory = emotion.happiness + emotion.surprise;
                float secondCategory = emotion.sadness + emotion.anger + emotion.contempt + emotion.disgust + emotion.fear;
                float thirdCategory = emotion.neutral;

                // Fill progress bars here
                ObjectAnimator progressAnimator;
                ProgressBar firstBar = (ProgressBar) callingActivity.findViewById(R.id.progressBar);
                //happinessBar.setProgress(Math.round( (emotion.happiness+emotion.surprise) * 100));
                progressAnimator = ObjectAnimator.ofInt(firstBar, "progress", 0, Math.round(firstCategory * 100));
                progressAnimator.setDuration(1500);
                progressAnimator.start();

                ProgressBar secondBar = (ProgressBar) findViewById(R.id.progressBar2);
                //sadBar.setProgress(Math.round( (emotion.sadness+emotion.anger+emotion.contempt+emotion.disgust+emotion.fear) * 100));
                progressAnimator = ObjectAnimator.ofInt(secondBar, "progress", 0, Math.round(secondCategory * 100));
                progressAnimator.setDuration(1500);
                progressAnimator.start();

                ProgressBar thirdBar = (ProgressBar) findViewById(R.id.progressBar3);
                //NeutralBar.setProgress(Math.round(emotion.neutral * 100));
                progressAnimator = ObjectAnimator.ofInt(thirdBar, "progress", 0, Math.round(thirdCategory * 100));
                progressAnimator.setDuration(1500);
                progressAnimator.start();

                // Create Message
                if(systemMessageCount == 2) {
                    int max = maxOfThree(firstCategory, secondCategory, thirdCategory);
                    String e;
                    if (max == 1) {
                        e = "Wow you seem happy today, what's going on ?";
                    }
                    else if (max == 2) {
                        e = "You look sad, what happened ?";
                    }
                    else if (max == 3) {
                        e = "Are you tired today ?";
                    }
                    else {
                        e = "Mixed feelings ?";
                    }

                    createMessage("system", "Diary: " + e);
                }

                //Save emotion to database
                emotion.save(getApplicationContext());
            }
            else {
                createMessage("system", "Diary: I can't seem to find any emotions right now, you can still write to me :)");
            }
            System.out.println("exiting second async task post");
        }
    }
}
