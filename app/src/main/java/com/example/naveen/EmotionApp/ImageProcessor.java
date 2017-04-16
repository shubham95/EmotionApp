package com.example.naveen.EmotionApp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.content.ContentValues.TAG;

/**
 * Created by abhishek on 3/28/2017.
 */

public class ImageProcessor {

    HttpClient httpclient = HttpClients.createDefault();
    String imageFilePath;
    public void processImage() {
        // Find our users face in a group of faces using face api

        // Get emotions of the users face using emotion api

        // Print out emotions and save them by date in a database

    }

    public Emotion processEmotions(String imagePath) {
        Emotion result = null;
        imageFilePath = imagePath;
        try
        {
            URIBuilder builder = new URIBuilder("https://westus.api.cognitive.microsoft.com/emotion/v1.0/recognize");


            URI uri = builder.build();
            HttpPost request = new HttpPost(uri);
            request.setHeader("Content-Type", "application/octet-stream");
            request.setHeader("Ocp-Apim-Subscription-Key", "5513070931b8419588dfb9092f6ea10e");


            // Request body
            //StringEntity reqEntity = new StringEntity("{body}");
            //request.setEntity(reqEntity);
            byte[] b  = getCompressedBitmapToFile(new File(imagePath));

//            File file = new File(imagePath);
//            byte[] b = new byte[(int) file.length()];
//            try {
//                FileInputStream fileInputStream = new FileInputStream(file);
//                fileInputStream.read(b);
////                  for (int i = 0; i < b.length; i++) {
////                              System.out.print((char)b[i]);
////                   }
//            } catch (FileNotFoundException e) {
//                System.out.println("File Not Found.");
//                e.printStackTrace();
//            }
//            catch (IOException e1) {
//                System.out.println("Error Reading The File.");
//                e1.printStackTrace();
//            }
            ByteArrayEntity bae = new ByteArrayEntity(b);
            request.setEntity(bae);

            HttpResponse response = httpclient.execute(request);
            HttpEntity entity = response.getEntity();

            //result = entity;
            //Log.d(TAG, "processEmotions: " + EntityUtils.toString(entity));
            if (entity != null)
            {
                //System.out.println(EntityUtils.toString(entity));\
                result = parseEmotionJSON(EntityUtils.toString(entity));
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

        return result;
    }

    public Emotion parseEmotionJSON(String jsonStr) {
        Log.e(TAG, "Response from url: " + jsonStr);
        Emotion emotion = null;

        try {
            Object json = new JSONTokener(jsonStr).nextValue();
            if (json instanceof JSONObject) {
                //you have an object
                JSONObject jsonReader = new JSONObject(jsonStr);
                JSONObject jsonError = jsonReader.getJSONObject("error");
                System.out.println(jsonError.getString("code"));
                System.out.println(jsonError.getString("message"));
            }
            else if (json instanceof JSONArray){
                //you have an array
                JSONArray jsonReader = new JSONArray(jsonStr);
                if (jsonReader != null && jsonReader.length() > 0) {
                    JSONObject faceJson = jsonReader.getJSONObject(0);
                    JSONObject scores = faceJson.getJSONObject("scores");

                    System.out.println(scores.getString("anger"));
                    System.out.println(scores.getString("contempt"));
                    System.out.println(scores.getString("disgust"));
                    System.out.println(scores.getString("fear"));
                    System.out.println(scores.getString("happiness"));
                    System.out.println(scores.getString("neutral"));
                    System.out.println(scores.getString("sadness"));
                    System.out.println(scores.getString("surprise"));

                    emotion = new Emotion((float)scores.getDouble("anger"),
                                        (float)scores.getDouble("contempt"),
                                        (float)scores.getDouble("disgust"),
                                        (float)scores.getDouble("fear"),
                                        (float)scores.getDouble("happiness"),
                                        (float)scores.getDouble("neutral"),
                                        (float)scores.getDouble("sadness"),
                                        (float)scores.getDouble("surprise"),
                                        new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(new Date()).toString(),
                                        imageFilePath);
                } else {
                    System.out.println("No face recognized");
                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return emotion;
    }


    public byte[] getCompressedBitmapToFile(File file){
        try {
            FileInputStream inputStream = new FileInputStream(file);
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = 8;//scale;
            inputStream = new FileInputStream(file);

            Bitmap selectedBitmap = BitmapFactory.decodeStream(inputStream, null, o2);
            inputStream.close();

            // here i override the original image file
            ByteArrayOutputStream stream = new ByteArrayOutputStream();

            selectedBitmap.compress(Bitmap.CompressFormat.JPEG, 100 , stream);

            return stream.toByteArray();
        } catch (Exception e) {
            return null;
        }
    }
}
