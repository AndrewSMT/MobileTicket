package com.andrew.myticketmobile.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.andrew.myticketmobile.R;
import com.andrew.myticketmobile.adapters.BuyTicketAdapter;
import com.andrew.myticketmobile.model.FullTicket;
import com.andrew.myticketmobile.model.Ticket;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class BuyTicketsActivity extends AppCompatActivity {
    public static ArrayList<Ticket> buyTickets = new ArrayList<>();
    private RecyclerView ticketBuyList;
    private BuyTicketAdapter buyTicketAdapter;
    private SharedPreferences myPreferences = null;
    private TextView empty;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_tickets);


        myPreferences = PreferenceManager.getDefaultSharedPreferences(BuyTicketsActivity.this);
        SharedPreferences.Editor edit = myPreferences.edit();
        Set<String> ticketsFromPer = myPreferences.getStringSet("tickets", new HashSet<String>());
        edit.clear().apply();

        for (Ticket ticket : buyTickets) {
            String stringTicket = String.valueOf(Ticket.stringTicket(ticket));
            ticketsFromPer.add(stringTicket);
            edit.putStringSet("tickets", ticketsFromPer);
            edit.apply();
        }

        ticketBuyList = findViewById(R.id.rv_buy_tickets);
        ticketBuyList.setLayoutManager(new LinearLayoutManager(this));
        ticketBuyList.setHasFixedSize(true);

        empty = findViewById(R.id.empty);
        if( FullTicket.ticketFromPer(ticketsFromPer).isEmpty()){
            empty.setText("You don't have tickets");
        }
        buyTicketAdapter = new BuyTicketAdapter(BuyTicketsActivity.this, FullTicket.ticketFromPer(ticketsFromPer));
        ticketBuyList.setAdapter(buyTicketAdapter);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
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
}