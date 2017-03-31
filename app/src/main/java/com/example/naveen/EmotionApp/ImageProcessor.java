package com.example.naveen.EmotionApp;

import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;

import ch.boye.httpclientandroidlib.HttpEntity;
import ch.boye.httpclientandroidlib.HttpResponse;
import ch.boye.httpclientandroidlib.client.HttpClient;
import ch.boye.httpclientandroidlib.client.methods.HttpPost;
import ch.boye.httpclientandroidlib.client.utils.URIBuilder;
import ch.boye.httpclientandroidlib.entity.ByteArrayEntity;
import ch.boye.httpclientandroidlib.entity.StringEntity;
import ch.boye.httpclientandroidlib.impl.client.HttpClients;
import ch.boye.httpclientandroidlib.util.EntityUtils;

/**
 * Created by abhishek on 3/28/2017.
 */

public class ImageProcessor {

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

            if (entity != null)
            {

                System.out.println(EntityUtils.toString(entity));
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

        return  result;
    }

}
