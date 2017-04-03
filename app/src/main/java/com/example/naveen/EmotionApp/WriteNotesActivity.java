package com.example.naveen.EmotionApp;

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
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import java.io.File;

public class WriteNotesActivity extends AppCompatActivity {
    String capturedImageFilePath = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
