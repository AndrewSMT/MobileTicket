package com.andrew.myticketmobile.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.andrew.myticketmobile.R;
import com.andrew.myticketmobile.adapters.EventAdapter;
import com.andrew.myticketmobile.adapters.OrderAdapter;
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
import java.util.HashSet;
import java.util.Set;

import static com.andrew.myticketmobile.activities.EventActivity.EXTRA_EVENT;
import static com.andrew.myticketmobile.activities.MainActivity.EXTRA_POSITION;

public class OrderActivity extends AppCompatActivity {

    private RecyclerView eventList;
    private OrderAdapter orderAdapter;
    private ArrayList<Ticket> ticketList;
    private ArrayList<Integer> sizeList;
    private RequestQueue requestQueue;
    private String eventId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        Intent intent = getIntent();
        eventId = intent.getStringExtra(EXTRA_EVENT);

        eventList = findViewById(R.id.rv_order);
        eventList.setLayoutManager(new GridLayoutManager(this,10));
        eventList.setHasFixedSize(true);

        ticketList = new ArrayList<>();
        requestQueue = Volley.newRequestQueue(this);

        parseTicketListJSON(eventId);
    }
    public void parseTicketListJSON(String eventId) {
        String url = "http://05ba165a86d8.ngrok.io/mobile/tickets/"+eventId;
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject it = (JSONObject) response.get(i);

                        Long id = Long.parseLong(it.getString("id_ticket"));
                        JSONObject event =  it.getJSONObject("event");
                        Long eventId =  event.getLong("id");
                        Integer row = it.getInt("row");
                        Integer place = it.getInt("number");
                        Integer price = it.getInt("price");
                        Set<String> status = new HashSet();
                        status.add(it.getString("ticketStatus"));
                        Long orderNumber = null;
                        if(it.getString("orderNumber") != "null" ) {
                           orderNumber = Long.parseLong(it.getString("orderNumber"));
                        }
                        ticketList.add(new Ticket(id,eventId,row,place,price,status,orderNumber));
                    }
                    orderAdapter = new OrderAdapter(OrderActivity.this,ticketList);
                    eventList.setAdapter(orderAdapter);
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
