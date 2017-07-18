package com.example.admin.weather;

import android.os.AsyncTask;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class WeatherRequestAsyncTask extends AsyncTask<String, String, String> {

    private Callback callback;

    public WeatherRequestAsyncTask(Callback callback) {
        this.callback = callback;
    }

    @Override
    protected String doInBackground(String... objects) {

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(objects[0])
                .build();

        String data = null;
        try {
            Response response = client.newCall(request).execute();
            data = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return data;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        callback.onData(s);
    }

    interface Callback {
        void onData(String data);
    }
}