package com.andrew.myticketmobile.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.andrew.myticketmobile.R;
import com.andrew.myticketmobile.adapters.CartAdapter;
import com.andrew.myticketmobile.model.Ticket;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;

import static com.andrew.myticketmobile.activities.MainActivity.tickets;
import static com.andrew.myticketmobile.adapters.OrderAdapter.order;

public class CartActivity extends AppCompatActivity {
    public static String email = null;
    private RecyclerView cartList;
    private CartAdapter cartAdapter;
    private RequestQueue requestQueue;
    private Button cartButton;
    private TextView orderText;
    private String ticketList = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        cartList = findViewById(R.id.rv_cart);
        cartList.setLayoutManager(new LinearLayoutManager(this));
        cartList.setHasFixedSize(true);

        cartButton = findViewById(R.id.cart_button);
        orderText = findViewById(R.id.order);

        if(tickets.isEmpty()){
            orderText.setText("Cart is empty");
        }
        cartAdapter = new CartAdapter(CartActivity.this, tickets,cartButton,orderText);
        cartList.setAdapter(cartAdapter);
        for (Ticket ticket : tickets) {
            ticketList += ticket.getId() + ",";
        }
        requestQueue = Volley.newRequestQueue(this);
        cartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(email == null) {
                    EmailDialog emailDialog = new EmailDialog(CartActivity.this);
                    emailDialog.show();
                }else {
                    parseMakeOrderJSON(email,order.getOrderId());
                    BuyTicketsActivity.buyTickets = tickets;
                    tickets = new ArrayList<>();
                    cartButton.setVisibility(View.INVISIBLE);
                    Toast.makeText(CartActivity.this,"Order was finished",Toast.LENGTH_LONG).show();
                    Intent cartIntent = new Intent(CartActivity.this, CartActivity.class);
                    startActivity(cartIntent);
                }
            }
        });
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

    public void parseMakeOrderJSON(String email, Long orderId) {
        email = "vewebi1206@mailvk.net";
        String url = "http://3361bdd5b40a.ngrok.io/mobile/tickets?tickets=" + ticketList+"&email="+email+"&order="+orderId;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String flag = response;
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        requestQueue.add(stringRequest);
    }

}
