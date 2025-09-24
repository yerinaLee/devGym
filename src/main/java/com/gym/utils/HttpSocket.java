package com.gym.utils;

import netscape.javascript.JSObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.http.HttpRequest;

public class HttpSocket {

    public static String getApiRequest(String strUrl, HttpRequest request) throws IOException {
        String result = null;

        BufferedReader in = null;
        HttpURLConnection conn = null;

        try{
            URL url = new URL(strUrl);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setDoOutput(true);

            // Response
            in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            StringBuilder sb = new StringBuilder();
            String line;

            while ((line = in.readLine()) != null){
                sb.append(line);
            }

            result = sb.toString();
            in.close();

        } catch (Exception e){
            e.printStackTrace();
        } finally {
            if (conn != null){
                conn.disconnect();
            }
        }

        return result;
    }
}
