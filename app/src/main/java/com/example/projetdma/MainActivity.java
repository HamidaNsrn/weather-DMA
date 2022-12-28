package com.example.projetdma;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;


import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private static final String API_KEY = "9929a919b48711c974462191883e3b33";
    Button btnSearch;
    EditText etCityName;
    ImageView iconWeather;
    TextView tvTemp,tvCity;

    ListView lvDailyWeather;
    Button btnplan;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

        btnSearch = findViewById(R.id.btnSearch);
        etCityName = findViewById(R.id.etCityName);
        iconWeather = findViewById(R.id.iconWeather);
        tvTemp = findViewById(R.id.tvTemp);
        tvCity = findViewById(R.id.tvCity);
        lvDailyWeather = findViewById(R.id.lvDailyWeather);
        btnplan =  findViewById(R.id.btnplan);

        Ion.with(this)
                .load("https://api.openweathermap.org/data/2.5/weather?q=alger&appid="+API_KEY+"&units=metric")
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        // do stuff with the result or error
                        //  Log.d("result", result.toString());
                        if(e != null){       //si kayen kach problem
                            e.printStackTrace();
                            Toast.makeText(MainActivity.this, "Server error", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            //convert json response to java
                            JsonObject main = result.get("main").getAsJsonObject();
                            double temp = main.get("temp").getAsDouble();
                            tvTemp.setText(temp+"°C");



                            JsonObject sys = result.get("sys").getAsJsonObject();
                            String country = sys.get("country").getAsString();
                            tvCity.setText("Algeria, "+country);

                            //load icon
                            JsonArray weather = result.get("weather").getAsJsonArray();
                            String icon = weather.get(0).getAsJsonObject().get("icon").getAsString();
                            loadIcon(icon);







                        }

                    }
                });
        Ion.with(this)
                .load("https://api.openweathermap.org/data/2.5/forecast?lat=36.7525&lon=3.042&exclude=hourly,minutely,current&appid=9929a919b48711c974462191883e3b33&units=metric")
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        // do stuff with the result or error

                        if(e != null){       //si kayen kach problem
                            e.printStackTrace();
                            Toast.makeText(MainActivity.this, "Server error", Toast.LENGTH_SHORT).show();
                        }
                        else {

                            Log.d("result", result.toString());
                            List<Weather> weatherList = new ArrayList<>();
                            // String timeZone =result.get("timezone").getAsString();
                            JsonArray daily = result.get("list").getAsJsonArray();
                            //bach na5do 7days ,lazm nabdaw boucle man i=1 not 0 bach manadoch day li rana fih
                            for (int i=2;i< daily.size();i++){
                                String sp = daily.get(i).getAsJsonObject().get("dt_txt").getAsString();
                                char sp1 = sp.charAt(11);
                                char sp2 = sp.charAt(12);
                                if (sp1=='1' && sp2=='2'){
                                    Long date = daily.get(i).getAsJsonObject().get("dt").getAsLong();
                                    //Double temp = daily.get(i).getAsJsonObject().get("temp").getAsDouble();
                                    Double temp = daily.get(i).getAsJsonObject().get("main").getAsJsonObject().get("temp").getAsDouble();
                                    String icon = daily.get(i).getAsJsonObject().get("weather").getAsJsonArray().get(0).getAsJsonObject().get("icon").getAsString();
                                    weatherList.add(new Weather(date, temp, icon));
                                }



                            }
                            DailyWeatherAdapter dailyWeatherAdapter = new DailyWeatherAdapter(MainActivity.this, weatherList);
                            lvDailyWeather.setAdapter(dailyWeatherAdapter);

                        }
                    }
                });



        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String city = etCityName.getText().toString();  // li ydakhlo l'user
                if (city.isEmpty())
                    Toast.makeText(MainActivity.this, "Please enter city name", Toast.LENGTH_SHORT).show();
                else {
                    // load weather by city name
                    loeadWeatherByCityName(city);
                }

            }

        });

        btnplan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openPlanActivity();
            }
        });



    }



    private void loeadWeatherByCityName(String city) {
        Ion.with(this)
                .load("https://api.openweathermap.org/data/2.5/weather?q="+city+"&appid="+API_KEY+"&units=metric")
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        // do stuff with the result or error
                      //  Log.d("result", result.toString());
                        if(e != null){       //si kayen kach problem
                            e.printStackTrace();
                            Toast.makeText(MainActivity.this, "Server error", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            //convert json response to java
                            JsonObject main = result.get("main").getAsJsonObject();
                            double temp = main.get("temp").getAsDouble();
                            tvTemp.setText(temp+"°C");



                            JsonObject sys = result.get("sys").getAsJsonObject();
                            String country = sys.get("country").getAsString();
                            tvCity.setText(city+", "+country);

                            //load icon
                            JsonArray weather = result.get("weather").getAsJsonArray();
                            String icon = weather.get(0).getAsJsonObject().get("icon").getAsString();
                            loadIcon(icon);


                            // get lon and lat to use it for the next api cs bach ndiroh yjib 7 next days maykhdemch b asem lblad
                            JsonObject coord = result.get("coord").getAsJsonObject();
                            double lon = coord.get("lon").getAsDouble();
                            double lat = coord.get("lat").getAsDouble();
                            loadDailyForecast(lon, lat);






                        }

                    }
                });
    }



    private void loadDailyForecast(double lon, double lat) {

        Ion.with(this)
                .load("https://api.openweathermap.org/data/2.5/forecast?lat="+lat+"&lon="+lon+"&exclude=hourly,minutely,current&appid=9929a919b48711c974462191883e3b33&units=metric")
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        // do stuff with the result or error

                        if(e != null){       //si kayen kach problem
                            e.printStackTrace();
                            Toast.makeText(MainActivity.this, "Server error", Toast.LENGTH_SHORT).show();
                        }
                        else {

                            Log.d("result", result.toString());
                            List<Weather> weatherList = new ArrayList<>();
                            // String timeZone =result.get("timezone").getAsString();
                            JsonArray daily = result.get("list").getAsJsonArray();
                            //bach na5do 7days ,lazm nabdaw boucle man i=1 not 0 bach manadoch day li rana fih
                            for (int i=2;i< daily.size();i++){
                                String sp = daily.get(i).getAsJsonObject().get("dt_txt").getAsString();
                                char sp1 = sp.charAt(11);
                                char sp2 = sp.charAt(12);
                                if (sp1=='1' && sp2=='2'){
                                    Long date = daily.get(i).getAsJsonObject().get("dt").getAsLong();
                                    //Double temp = daily.get(i).getAsJsonObject().get("temp").getAsDouble();
                                    Double temp = daily.get(i).getAsJsonObject().get("main").getAsJsonObject().get("temp").getAsDouble();
                                    String icon = daily.get(i).getAsJsonObject().get("weather").getAsJsonArray().get(0).getAsJsonObject().get("icon").getAsString();
                                    weatherList.add(new Weather(date, temp, icon));
                                }



                        }
                            DailyWeatherAdapter dailyWeatherAdapter = new DailyWeatherAdapter(MainActivity.this, weatherList);
                            lvDailyWeather.setAdapter(dailyWeatherAdapter);

                    }
                    }
                    });
                }


    private void loadIcon(String icon) {
        Ion.with(this)
                .load("https://openweathermap.org/img/w/"+icon+".png")
                .intoImageView(iconWeather);

    }

    public void openPlanActivity(){
        Intent intent = new Intent(this, PlanActivity.class);
        startActivity(intent);
    }
}

