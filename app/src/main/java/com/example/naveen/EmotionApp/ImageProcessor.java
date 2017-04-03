package com.example.naveen.EmotionApp;

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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;

import static android.R.attr.data;
import static android.content.ContentValues.TAG;

/**
 * Created by abhishek on 3/28/2017.
 */

public class ImageProcessor {

    Emotion emotion;
    HttpClient httpclient = HttpClients.createDefault();

    public void processImage() {
        // Find our users face in a group of faces using face api

        // Get emotions of the users face using emotion api

        // Print out emotions and save them by date in a database

    }

    public HttpEntity processEmotions(String ImagePath) {
        HttpEntity result = null;
        try
        {
            URIBuilder builder = new URIBuilder("https://westus.api.cognitive.microsoft.com/emotion/v1.0/recognize");


            URI uri = builder.build();
            HttpPost request = new HttpPost(uri);
            request.setHeader("Content-Type", "application/octet-stream");
            request.setHeader("Ocp-Apim-Subscription-Key", "a549eae280444460b6bc42542dd82b9e");


            // Request body
            //StringEntity reqEntity = new StringEntity("{body}");
            //request.setEntity(reqEntity);
            File file = new File(ImagePath);

            byte[] b = new byte[(int) file.length()];
            try {
                FileInputStream fileInputStream = new FileInputStream(file);
                fileInputStream.read(b);
//                  for (int i = 0; i < b.length; i++) {
//                              System.out.print((char)b[i]);
//                   }
            } catch (FileNotFoundException e) {
                System.out.println("File Not Found.");
                e.printStackTrace();
            }
            catch (IOException e1) {
                System.out.println("Error Reading The File.");
                e1.printStackTrace();
            }
            ByteArrayEntity bae = new ByteArrayEntity(b);
            request.setEntity(bae);

            HttpResponse response = httpclient.execute(request);
            HttpEntity entity = response.getEntity();

            result = entity;
            //Log.d(TAG, "processEmotions: " + EntityUtils.toString(entity));
            if (entity != null)
            {
                //System.out.println(EntityUtils.toString(entity));\
                parseEmotionJSON(EntityUtils.toString(entity));
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

        return  result;
    }

    public void parseEmotionJSON(String jsonStr) {
        Log.e(TAG, "Response from url: " + jsonStr);

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

                    emotion = new Emotion(scores.getString("anger"),
                                        scores.getString("contempt"),
                                        scores.getString("disgust"),
                                        scores.getString("fear"),
                                        scores.getString("happiness"),
                                        scores.getString("neutral"),
                                        scores.getString("sadness"),
                                        scores.getString("surprise"));
                }
                else {
                    System.out.println("No face recognized");
                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
