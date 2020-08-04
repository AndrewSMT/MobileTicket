package com.andrew.myticketmobile.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.andrew.myticketmobile.R;
import com.andrew.myticketmobile.adapters.EventAdapter;
import com.andrew.myticketmobile.model.Event;
import com.andrew.myticketmobile.model.Ticket;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements EventAdapter.OnItemClickListener {


    public static final String EXTRA_PIC = "imageURL";
    public static final String EXTRA_TITLE = "titleName";
    public static final String EXTRA_DATE = "dateName";
    public static final String EXTRA_PLACE = "placeName";
    public static final String EXTRA_DISC = "discName";
    public static final String EXTRA_CITY = "cityName";
    public static final String EXTRA_POSITION = "0";

    private RecyclerView eventList;
    private EventAdapter eventAdapter;
    private ArrayList<Event> eventItemList;
    private ArrayList<String> titleList  = new ArrayList<>();
    private RequestQueue requestQueue;
    public static ArrayList<Ticket> tickets = new ArrayList<>();
    public static ArrayList<Event> events = new ArrayList<>();
    LoadingDialog loadingMainDialog = new LoadingDialog(MainActivity.this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView (R.layout.activity_main);
        eventList = findViewById(R.id.rv_main);
        eventList.setLayoutManager(new LinearLayoutManager(this));
        eventList.setHasFixedSize(true);


        eventItemList = new ArrayList<>();
        requestQueue = Volley.newRequestQueue(this);

        loadingMainDialog.startLoading();
        parseEventListJSON();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        MenuItem search = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) search.getActionView();
        searchView.setQueryHint("Search here");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                eventAdapter.getFilter().filter(s);
                return true;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.home:
                Intent mainIntent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(mainIntent);
                break;
            case R.id.cart:
                Intent cartIntent = new Intent(this, CartActivity.class);
                startActivity(cartIntent);
                break;
        }
        return true;
    }

    public void parseEventListJSON() {
        String url = "http://3cc8bd7d9f28.ngrok.io/mobile/";
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject it = (JSONObject) response.get(i);

                        Long id = Long.parseLong(it.getString("id"));
                        String title = it.getString("title");
                        String picture = it.getString("picture");
                        String date = it.getString("date");
                        JSONObject place =  it.getJSONObject("place");
                        JSONObject city =  place.getJSONObject("city");
                        String placeTitle = (String) place.get("title");
                        String cityTitle = (String) city.get("title");
                        String description = it.getString("description");

                        eventItemList.add(new Event(id,placeTitle,date,title,description,picture,cityTitle));
                    }
                    for (Event event: eventItemList){
                        titleList.add(event.getTitle());
                    }
                    eventAdapter = new EventAdapter(MainActivity.this,titleList,eventItemList,loadingMainDialog);
                    eventList.setAdapter(eventAdapter);
                    eventAdapter.setOnClickListener(MainActivity.this);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        requestQueue.add(request);
    }

    @Override
    public void onItemClick(int position) {
        Intent eventIntent =new Intent(this, EventActivity.class);
        Event clickEvent = eventItemList.get(position);

        eventIntent.putExtra(EXTRA_PIC, clickEvent.getPicture());
        eventIntent.putExtra(EXTRA_TITLE, clickEvent.getTitle());
        eventIntent.putExtra(EXTRA_DATE, clickEvent.getDate());
        eventIntent.putExtra(EXTRA_PLACE, clickEvent.getPlace());
        eventIntent.putExtra(EXTRA_DISC, clickEvent.getDescription());
        eventIntent.putExtra(EXTRA_CITY, clickEvent.getCityTitle());
        eventIntent.putExtra(EXTRA_POSITION, clickEvent.getId().toString());


        startActivity(eventIntent);
    }
}