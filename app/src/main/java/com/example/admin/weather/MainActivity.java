package com.example.admin.weather;

import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Locale;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private URL url;
    private WeatherRequestAsyncTask weatherRequestAsyncTask;
    private String city = Constants.DEFAULT_CITY;
    private Button update;
    private TextView value;
    private EditText editCity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUI();

        requestCityWeather(city);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            requestCityWeather(city);
        }
    };


    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            city =  editCity.getText().toString().toLowerCase(Locale.getDefault());
        }
    };

    private WeatherRequestAsyncTask.Callback callback = new WeatherRequestAsyncTask.Callback() {
        @Override
        public void onData(String data) {
            String jsonData = data;
            value.setText(jsonData);
        }
    };

    private void requestCityWeather(String city){

        Uri.Builder builder = new Uri.Builder();
        builder.scheme("http")
                .authority(Constants.HOST)
                .appendPath(Constants.OUTH_PASS)
                .appendQueryParameter("q", city)
                .appendQueryParameter("units", Constants.UNITS)
                .appendQueryParameter("mode",  Constants.MODE)
                .appendQueryParameter("APPID", Constants.CLIENT_ID)
                .build();

        weatherRequestAsyncTask = new WeatherRequestAsyncTask(callback);
        weatherRequestAsyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, builder.toString());

    }

    private void initUI(){
        update = (Button) findViewById(R.id.btnUpdate);
        value = (TextView) findViewById(R.id.txtValue);
        editCity = (EditText) findViewById(R.id.editCity);
        update.setOnClickListener(onClickListener);
        editCity.addTextChangedListener(textWatcher);
    }

}
