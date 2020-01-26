package com.example.user.weathertoday;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class ActivityLearnCity extends AppCompatActivity {

    ImageView imageWeather;

    TextView textViewTemperature;

    TextView textViewMainWeather;

    TextView textViewWeatherDescription;

    TextView textViewAreaName;

    private LocationManager locationManager;
    private LocationListener locationListener;

    boolean hasBeenChecktedBefore;

    private RequestQueue myRequestQueue;



    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn_city);

        hasBeenChecktedBefore = false;

        myRequestQueue = Volley.newRequestQueue(this);

        // Find all the UI Components
        imageWeather = (ImageView) findViewById(R.id.imageWeather);
        textViewAreaName = (TextView) findViewById(R.id.textViewAreaName);
        textViewMainWeather = (TextView) findViewById(R.id.textViewMainWeather);
        textViewWeatherDescription = (TextView) findViewById(R.id.textViewWeatherDescription);
        textViewTemperature = (TextView) findViewById(R.id.textViewTemperature);

        /* LEARN THE COORDINATES OF THE USER */

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                if(!hasBeenChecktedBefore){
                    /* USE THIS INFORMATION TO CONNECT TO THE SERVER */
                    hasBeenChecktedBefore = true; // do not get the coordinates again
                    // only one time

                    JsonObjectRequest myRequest = CreateTheRequest(location); // create the request of the weather data
                    // based on the location

                    // and now, make the request to the server
                    myRequestQueue.add(myRequest);


                }

            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

            }
        };

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
    }

    private JsonObjectRequest CreateTheRequest(Location theLocation){

        // Create the URL based on the location

        // http://api.openweathermap.org/data/2.5/weather?lat=39.937776&lon=32.830899&APPID=9b0b85dc9743db90725c224668ad2047

        String baseURL = "http://api.openweathermap.org/data/2.5/weather?";
        String latitude = "lat=" + Double.toString(theLocation.getLatitude());
        String longitude = "lon=" + Double.toString(theLocation.getLongitude());
        String apiKey = "APPID=9b0b85dc9743db90725c224668ad2047";
        final String finalURL = baseURL + latitude + "&" + longitude + "&" + apiKey;

        // Create the positive answer listener

        Response.Listener<JSONObject> positiveResponseListener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    // Get the name of the county / area
                    String areaName = response.getString("name");
                    // Get the country name
                    JSONObject system = response.getJSONObject("sys");
                    // then
                    String countryName = system.getString("country");
                    // then concatenate AREA, COUNTRY
                    String locationOfUser = areaName + "," + countryName;
                    textViewAreaName.setText(locationOfUser);



                    // Get the temperature
                    JSONObject main = response.getJSONObject("main");
                    Double temperatureKelvin = main.getDouble("temp");
                    int temperature = FromKelvinToCelcius(temperatureKelvin);
                    textViewTemperature.setText(String.valueOf(temperature) + "°C");


                    // Get the weather information
                    JSONArray weatherInformatioArray = response.getJSONArray("weather");
                    JSONObject weather = (JSONObject) weatherInformatioArray.get(0);
                    String mainWeather = weather.getString("main");
                    String weatherDescription = weather.getString("description");

                    textViewMainWeather.setText(mainWeather);
                    textViewWeatherDescription.setText(weatherDescription);

                    switch(mainWeather){
                        case "Clouds":
                            imageWeather.setImageResource(R.drawable.clouds);
                            break;
                        case "Rain":
                            imageWeather.setImageResource(R.drawable.rain);
                            break;
                        case "Haze":
                            imageWeather.setImageResource(R.drawable.haze);
                            break;
                        case "Mist":
                            imageWeather.setImageResource(R.drawable.mist);
                            break;
                        case "Clear":
                            imageWeather.setImageResource(R.drawable.clear_sky);
                            break;
                        case "Fog":
                            imageWeather.setImageResource(R.drawable.fog);
                            break;
                    }





                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        };

        // Create the error listener

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        };

        JsonObjectRequest theRequest = new JsonObjectRequest(Request.Method.GET, finalURL, null, positiveResponseListener, errorListener);

        return theRequest;
    }

    private int FromKelvinToCelcius(double temperatureInKelvin){

        int Celcius = 0;

        Celcius = (int) Math.floor(temperatureInKelvin - 273.15);

        return Celcius;
    }

}
