package com.example.naveen.EmotionApp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Naveen on 3/20/2017.
 */

public class MyCamera {

    /**
     * This method triggers others installed camera-apps  in the phone to take picture for our app and
     * @return filepath where successfully taken picture is saved. else returns null( null --> error)
     */
    public static String takePictureAndSave(Activity callingActivity) {
        final int REQUEST_TAKE_PHOTO = 1;
        String savedImageFilePath = null;
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (takePictureIntent.resolveActivity(callingActivity.getPackageManager()) != null) {
            // Create the Unique File where the photo should go
            File photoFile = null;
            try {
                photoFile = createUniqueFileToSaveImage(callingActivity.getApplicationContext());
            } catch (IOException ex) {
                // Error occurred while creating the File
                //...
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(callingActivity.getApplicationContext(),
                        "com.example.naveen.EmotionApp",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);

                //this line triggers other installed camera app to take photo and save it to photoURI
                callingActivity.startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);

                //get saved image file absolute path
                savedImageFilePath = photoFile.getAbsolutePath();
            }
        }

        return savedImageFilePath;
    }

    /**
     * This creates unique image file, where other camera app stores the captured image
     * @param context is used to getExternalFilesDir path for the application.
     * @return File created
     * @throws IOException
     */
    private static File createUniqueFileToSaveImage(Context context) throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents

        return image;
    }

    public static void galleryAddPic(Activity callingActivity, String mCurrentPhotoPath) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        callingActivity.sendBroadcast(mediaScanIntent);
    }
}
