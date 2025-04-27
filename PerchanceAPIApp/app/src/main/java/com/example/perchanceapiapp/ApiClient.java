package com.example.perchanceapiapp;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ApiClient {

    private static final String TAG = "ApiClient";
    private String apiUrl;

    public ApiClient(String apiUrl) {
        this.apiUrl = apiUrl;
    }

    public void sendGetRequest(final ApiResponseCallback callback) {
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... voids) {
                try {
                    URL url = new URL(apiUrl);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(5000);
                    connection.setReadTimeout(5000);

                    int responseCode = connection.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                        StringBuilder response = new StringBuilder();
                        String line;
                        while ((line = reader.readLine()) != null) {
                            response.append(line);
                        }
                        reader.close();
                        return response.toString();
                    } else {
                        Log.e(TAG, "GET request failed: " + responseCode);
                        return null;
                    }
                } catch (Exception e) {
                    Log.e(TAG, "Error in GET request", e);
                    return null;
                }
            }

            @Override
            protected void onPostExecute(String result) {
                if (callback != null) {
                    callback.onResponse(result);
                }
            }
        }.execute();
    }

    public void sendPostRequest(final JSONObject jsonRequest, final ApiResponseCallback callback) {
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... voids) {
                try {
                    URL url = new URL(apiUrl);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setConnectTimeout(5000);
                    connection.setReadTimeout(5000);
                    connection.setRequestProperty("Content-Type", "application/json");
                    connection.setDoOutput(true);

                    OutputStream os = connection.getOutputStream();
                    os.write(jsonRequest.toString().getBytes());
                    os.flush();
                    os.close();

                    int responseCode = connection.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                        StringBuilder response = new StringBuilder();
                        String line;
                        while ((line = reader.readLine()) != null) {
                            response.append(line);
                        }
                        reader.close();
                        return response.toString();
                    } else {
                        Log.e(TAG, "POST request failed: " + responseCode);
                        return null;
                    }
                } catch (Exception e) {
                    Log.e(TAG, "Error in POST request", e);
                    return null;
                }
            }

            @Override
            protected void onPostExecute(String result) {
                if (callback != null) {
                    callback.onResponse(result);
                }
            }
        }.execute();
    }

    public interface ApiResponseCallback {
        void onResponse(String response);
    }
}