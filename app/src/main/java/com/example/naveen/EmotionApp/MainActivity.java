package com.example.naveen.EmotionApp;

import android.app.Activity;
import android.hardware.Camera;
//import android.graphics.Camera;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.SurfaceView;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.jjoe64.graphview.GraphView;

public class MainActivity extends AppCompatActivity {
    private Activity currentActivity;
    private Camera camera ;
    private SurfaceView cameraPreviewSurface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        currentActivity = this;

        //tested sql
        //TestFunctionality.testSql(getApplicationContext());

        //TestFunctionality.testGraph((GraphView)findViewById(R.id.graph), currentActivity);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //on pressing capture button
    public void capturePhotoClick(View view) {

        //take photo and save it assigning unique name to image file.
        //String capturedImageFilePath = MyCamera.takePictureAndSave(this);

        //to allow gallery app to visible images captured using our app.
        //MyCamera.galleryAddPic(this, capturedImageFilePath);

        String capturedImageFilePath = ;
        new ImageProcessor().processEmotions(capturedImageFilePath);
    }

}


