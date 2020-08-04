package com.andrew.myticketmobile.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.andrew.myticketmobile.R;
import com.andrew.myticketmobile.adapters.CartAdapter;
import com.andrew.myticketmobile.adapters.OrderAdapter;
import com.andrew.myticketmobile.model.Ticket;
import com.android.volley.RequestQueue;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static com.andrew.myticketmobile.activities.MainActivity.tickets;

public class CartActivity extends AppCompatActivity {
    private RecyclerView cartList;
    private CartAdapter cartAdapter;
    private RequestQueue requestQueue;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);


        cartList = findViewById(R.id.rv_cart);
        cartList.setLayoutManager(new LinearLayoutManager(this));
        cartList.setHasFixedSize(true);




        cartAdapter = new CartAdapter(CartActivity.this,tickets);
        cartList.setAdapter(cartAdapter);
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
        }
        return true;
    }
}
