package com.andrew.myticketmobile.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.andrew.myticketmobile.R;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import static com.andrew.myticketmobile.activities.MainActivity.EXTRA_CITY;
import static com.andrew.myticketmobile.activities.MainActivity.EXTRA_DATE;
import static com.andrew.myticketmobile.activities.MainActivity.EXTRA_DISC;

import static com.andrew.myticketmobile.activities.MainActivity.EXTRA_PIC;
import static com.andrew.myticketmobile.activities.MainActivity.EXTRA_PLACE;

import static com.andrew.myticketmobile.activities.MainActivity.EXTRA_POSITION;
import static com.andrew.myticketmobile.activities.MainActivity.EXTRA_TITLE;


public class EventActivity extends AppCompatActivity {
    public static final String EXTRA_EVENT = "-1";
    private ArrayList<Integer> sizeList;
    private RequestQueue requestQueue;
    private Button orderButton;
    private String eventId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);


        Intent intent = getIntent();
        String picture = intent.getStringExtra(EXTRA_PIC);
        String title = intent.getStringExtra(EXTRA_TITLE);
        String date = intent.getStringExtra(EXTRA_DATE);
        String place = intent.getStringExtra(EXTRA_PLACE);
        String disc = intent.getStringExtra(EXTRA_DISC);
        String city = intent.getStringExtra(EXTRA_CITY);
        eventId = intent.getStringExtra(EXTRA_POSITION);

        ImageView imageView = findViewById(R.id.event_image);
        TextView titleView = findViewById(R.id.event_title);
        TextView datePlaceView = findViewById(R.id.event_date_place);
        TextView discView = findViewById(R.id.event_disc);
        orderButton = findViewById(R.id.order_button);


        sizeList = new ArrayList();
        requestQueue = Volley.newRequestQueue(this);
        parseEventInfoListJSON(Long.parseLong(eventId));


        Picasso.with(this).load(picture).fit().centerCrop().into(imageView);
        titleView.setText(title);
        datePlaceView.setText(city + ", " + place + ", " + date);
        discView.setText(disc);

        orderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent orderIntent = new Intent(EventActivity.this, OrderActivity.class);
                orderIntent.putExtra(EXTRA_EVENT, eventId);
                startActivity(orderIntent);
            }
        });

     /*   ImageButton homeButton = findViewById(R.id.home);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent eventIntent = new Intent(EventActivity.this, EventActivity.class);
                startActivity(eventIntent);
            }
        });*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.home:
                Intent mainIntent = new Intent(this, MainActivity.class);
                startActivity(mainIntent);
                break;
            case R.id.cart:
                Intent cartIntent = new Intent(this, CartActivity.class);
                startActivity(cartIntent);
                break;
            case R.id.tickets:
                Intent ticketsIntent = new Intent(this, BuyTicketsActivity.class);
                startActivity(ticketsIntent);
                break;
        }
        return true;
    }
    public void parseEventInfoListJSON(long id) {
        String url = "http://fe41b8d8e05c.ngrok.io/mobile/event/inf/" + id;
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    for (int i = 0; i < response.length(); i++) {
                        response.get(i);
                        int item = (int) response.get(i);
                        sizeList.add(item);
                    }
                    TextView priceView = findViewById(R.id.event_price);
                    priceView.setText(sizeList.get(0) + "-" + sizeList.get(1) + " UAH (<" + sizeList.get(2) + "psc.)");
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
}