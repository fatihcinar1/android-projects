package com.example.user.imdbinterface;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<Movie> theTop50Movies;

    Gson GSON;

    // Create a request queue for volley
    RequestQueue myRequestQueue;

    RecyclerView theRecyclerView;

    MovieAdapter theMovieAdapter;

    GridLayoutManager myGridLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        theRecyclerView = (RecyclerView) findViewById(R.id.Recyclerview);

        int NUMBER_COLUMNS = 2;
        myGridLayoutManager = new GridLayoutManager(MainActivity.this, NUMBER_COLUMNS);


        theTop50Movies = new ArrayList<Movie>();

        GSON = new Gson(); // For JSON operations

        // Initiliaze Request Queue
        myRequestQueue = Volley.newRequestQueue(MainActivity.this);

        // CREATE THE HTTP REQUEST TO THE SERVER

        String theURL = "https://api.myjson.com/bins/nyv5q";

        Response.Listener<JSONObject> positiveResponseListener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                // IT GIVES US JSON OBJECT HERE

                try {
                    final JSONArray theMovies = response.getJSONArray("top50");

                    for(int itr = 0; itr < theMovies.length(); itr++){
                        // Convert all JSON Objects to movies
                        String oneMovie = theMovies.get(itr).toString();
                        // get one movie JSON

                        Movie convertedToMovie = GSON.fromJson(oneMovie,Movie.class);
                        theTop50Movies.add(convertedToMovie);
                    }

                    // Create the adapter here
                    theMovieAdapter = new MovieAdapter(theTop50Movies, MainActivity.this);

                    // CONNECT THE ADAPTER AND LAYOUT MANAGER TO THE RECYCLER VIEW

                    theRecyclerView.setAdapter(theMovieAdapter);
                    theRecyclerView.setLayoutManager(myGridLayoutManager);

                    theRecyclerView.setHasFixedSize(true); // Hızlandırıcı


                    // Set THE LISTENER
                    MovieAdapter.ItemClickListener theListener = new MovieAdapter.ItemClickListener() {
                        @Override
                        public void onItemClick(int position) {
                            Log.i("CLICKED TO", "INDEX " + String.valueOf(position)+ " " + theTop50Movies.get(position).getTitle());
                            Toast.makeText(MainActivity.this,theTop50Movies.get(position).getTitle(),Toast.LENGTH_SHORT).show();
                        }
                    };

                    theMovieAdapter.setItemClickListener(theListener);

                    

                } catch (JSONException e) {

                    e.printStackTrace();
                    Log.i("ERROR","Cannot get the json");
                }


            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("ERROR","Failure to connect to the server!");
            }
        };

        JsonObjectRequest myRequestIMDB = new JsonObjectRequest(Request.Method.GET, theURL, null, positiveResponseListener, errorListener);

        // Make the request
        myRequestQueue.add(myRequestIMDB);

    }


}
