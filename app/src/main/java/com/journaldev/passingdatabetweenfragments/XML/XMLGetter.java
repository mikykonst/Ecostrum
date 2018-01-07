package com.journaldev.passingdatabetweenfragments.XML;

import android.content.Context;
import android.net.Uri;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Евгений on 28.09.2017.
 */

public class XMLGetter {
    public byte[] getUrlBytes(String urlSpec) throws IOException {
        URL url = new URL(urlSpec);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            InputStream in = connection.getInputStream();
            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new IOException(connection.getResponseMessage() +
                        ": with " +
                        urlSpec);
            }
            int bytesRead = 0;
            byte[] buffer = new byte[1024];
            while ((bytesRead = in.read(buffer)) > 0) {
                out.write(buffer, 0, bytesRead);
            }
            out.close();
            return out.toByteArray();
        } finally {
            connection.disconnect();
        }
    }

    public String getUrlString(String urlSpec) throws IOException {
        return new String(getUrlBytes(urlSpec));
    }

    public String fetchItems(Context context) {

        //List<GalleryItem> items = new ArrayList<>();
        String xmlString = "";

        try {
            String url = Uri.parse("https://back-ecostrum.herokuapp.com/getxmlastext").toString();
            xmlString = getUrlString(url);
            FileOutputStream fos = context.openFileOutput("test.xml", Context.MODE_PRIVATE);
            fos.write(xmlString.getBytes());
            fos.close();


        } catch (IOException e) {
            e.printStackTrace();
        }

        return xmlString;
    }


}
