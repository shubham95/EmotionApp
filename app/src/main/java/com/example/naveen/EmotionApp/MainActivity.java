package com.example.naveen.EmotionApp;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.hardware.Camera;
//import android.graphics.Camera;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.SurfaceView;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;

import ch.boye.httpclientandroidlib.HttpEntity;

public class MainActivity extends AppCompatActivity {
    private Activity currentActivity;
    private Camera camera ;
    private SurfaceView cameraPreviewSurface;


    final int MY_PERMISSIONS_REQUEST_ACCESS_CAMERA = 4;
    private boolean isCameraGranted = false;
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

        if (ContextCompat.checkSelfPermission(currentActivity,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(currentActivity,
                        new String[]{Manifest.permission.CAMERA},
                        MY_PERMISSIONS_REQUEST_ACCESS_CAMERA);


        }else{
            isCameraGranted = true;
        }

        if(isCameraGranted){
            String capturedImageFilePath = new MyCamera().takePictureAndSave(this);

            //to allow gallery app to visible images captured using our app.
            MyCamera.galleryAddPic(this, capturedImageFilePath);
        }else{
            Toast.makeText(currentActivity, "Access to camera is not granted", Toast.LENGTH_SHORT).show();
        }

//        new AsyncTask<String, Void, HttpEntity>() {
//            @Override
//            protected HttpEntity doInBackground(String... strings) {
//                HttpEntity result = new ImageProcessor().processEmotions(strings[0]);
//                return result;
//            }
//
//            @Override
//            protected void onPostExecute(HttpEntity entity){
//
//            }
//        }.execute(capturedImageFilePath);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_ACCESS_CAMERA: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    isCameraGranted = true;


                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    isCameraGranted = false;

                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

}


