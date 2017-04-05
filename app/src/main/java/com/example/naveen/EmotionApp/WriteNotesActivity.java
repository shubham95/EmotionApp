package com.example.naveen.EmotionApp;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;
import java.io.File;

import static android.content.ContentValues.TAG;

public class WriteNotesActivity extends AppCompatActivity {
    String capturedImageFilePath = "";
    NestedScrollView nestedScrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_write_notes);
        capturedImageFilePath  = MyCamera.takePictureAndSave(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //System.out.println(capturedImageFilePath);
        if(requestCode == MyCamera.REQUEST_TAKE_PHOTO && resultCode == RESULT_OK)
            new ImageLoader(null, this).execute(capturedImageFilePath);
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

        // Add textview 2
        TextView textView2 = new TextView(this);
        LayoutParams layoutParamsRight = new LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT);
        layoutParamsRight.gravity = Gravity.RIGHT;
        //layoutParams.setMargins(10, 10, 10, 10); // (left, top, right, bottom)
        textView2.setLayoutParams(layoutParamsRight);
        textView2.setText("programmatically created TextView2");
        textView2.setBackgroundResource(R.drawable.bubble2);
        //textView2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        //textView2.setBackgroundColor(0xffffdbdb); // hex color 0xAARRGGBB
        linearLayout.addView(textView2);

        nestedScrollView = (NestedScrollView) this.findViewById(R.id.scroll);
        //nestedScrollView.fullScroll(nestedScrollView.FOCUS_DOWN);
        nestedScrollView.post(new Runnable() {
            @Override
            public void run() {
                nestedScrollView.fullScroll(nestedScrollView.FOCUS_DOWN);
            }
        });
        Log.d(TAG, "onClickButton: After Click");
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
            if(strings[0].isEmpty())
                return null;

            //new ImageProcessor().processEmotions(strings[0]);
            Bitmap bitmap = BitmapFactory.decodeFile(strings[0]);
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {

            if(bitmap != null){
                callingActivity.setContentView(R.layout.activity_write_notes);
                imageView = (ImageView)callingActivity.findViewById(R.id.imageView);
                imageView.setImageBitmap(bitmap);
            }else {
                //callingActivity.finish();
            }
        }
    }
}
