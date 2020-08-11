package com.andrew.myticketmobile.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.andrew.myticketmobile.R;
import com.andrew.myticketmobile.adapters.OrderAdapter;
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

public class OrderActivity extends AppCompatActivity {

    private RecyclerView orderList;
    private OrderAdapter orderAdapter;
    private ArrayList<Ticket> ticketList;
    private RequestQueue requestQueue;
    private String eventId;
    public LoadingDialog loadingOrderDialog = new LoadingDialog(OrderActivity.this);
    public ImageView stage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        Intent intent = getIntent();
        eventId = intent.getStringExtra(EXTRA_EVENT);

        orderList = findViewById(R.id.rv_order);
        orderList.setLayoutManager(new GridLayoutManager(this, 10));
        orderList.setHasFixedSize(true);

        ticketList = new ArrayList<>();
        requestQueue = Volley.newRequestQueue(this);

        stage = findViewById(R.id.stage);
        loadingOrderDialog.startLoading();
        parseTicketListJSON(eventId);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
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

    public void parseTicketListJSON(String eventId) {
        String url = "http://fe41b8d8e05c.ngrok.io/mobile/tickets/" + eventId;
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject it = (JSONObject) response.get(i);

                        Long id = Long.parseLong(it.getString("id_ticket"));
                        JSONObject event = it.getJSONObject("event");
                        Long eventId = event.getLong("id");
                        Integer row = it.getInt("row");
                        Integer place = it.getInt("number");
                        Integer price = it.getInt("price");
                        Set<String> status = new HashSet();

                        status.add(it.getString("ticketStatus").replaceAll("\\W", ""));
                        Long orderNumber = null;
                        if (it.getString("orderNumber") != "null") {
                            orderNumber = Long.parseLong(it.getString("orderNumber"));
                        }
                        ticketList.add(new Ticket(id, eventId, row, place, price, status, orderNumber));
                    }
                    orderAdapter = new OrderAdapter(OrderActivity.this, ticketList, loadingOrderDialog, stage);
                    orderList.setAdapter(orderAdapter);
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
