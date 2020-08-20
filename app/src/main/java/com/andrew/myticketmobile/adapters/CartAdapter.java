package com.andrew.myticketmobile.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.andrew.myticketmobile.R;
import com.andrew.myticketmobile.activities.CartActivity;
import com.andrew.myticketmobile.model.Event;
import com.andrew.myticketmobile.model.Ticket;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;

import static com.andrew.myticketmobile.activities.MainActivity.events;
import static com.andrew.myticketmobile.adapters.OrderAdapter.order;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private Context mContext;
    private ArrayList<Ticket> mCartList;
    private Button cartButton;
    private TextView orderText;
    private RequestQueue requestQueue;

    public CartAdapter(Context context, ArrayList<Ticket> ticketList, Button cartButton,TextView orderText) {
        mContext = context;
        mCartList = ticketList;
        this.orderText = orderText;
        this.cartButton = cartButton;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.cart_layout, parent, false);
        return new CartViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final CartViewHolder holder, final int position) {
        String city = "";
        String date = "";
        String place = "";
        String eventTitle = "";
        final Ticket currentItem = mCartList.get(position);
        Integer row = currentItem.getRow();
        Integer number = currentItem.getNumber();
        Integer price = currentItem.getPrice();
        for(Event event: events){
            if(event.getId().equals(currentItem.getEvent())){
                 city = event.getCityTitle();
                 date = event.getDate();
                 place = event.getPlace();
                 eventTitle = event.getTitle();
            }
        }
        orderText.setText("Order № "+order.getOrderId());
        holder.rowPlace.setText("Row: " + row.toString()+" Place: "+number.toString());
        holder.datePlace.setText(date+", "+place+", "+city);
        holder.title.setText(eventTitle);
        holder.price.setText("Price: "+ price.toString()+"UAH");
        cartButton.setVisibility(View.VISIBLE);
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestQueue = Volley.newRequestQueue(view.getContext());
                mCartList.remove(position);
                deleteTicketFromCart(currentItem.getId());
                Toast.makeText(view.getContext(),"Ticket was delete",Toast.LENGTH_LONG).show();
                Intent cartIntent = new Intent(view.getContext(), CartActivity.class);
                mContext.startActivity(cartIntent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return mCartList.size();
    }

    class CartViewHolder extends RecyclerView.ViewHolder {
        public TextView rowPlace;
        public TextView title;
        public TextView datePlace;
        public TextView price;
        public ImageButton delete;
        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            rowPlace = itemView.findViewById(R.id.row_place);
            title = itemView.findViewById(R.id.ticket_title);
            datePlace = itemView.findViewById(R.id.date_place);
            price = itemView.findViewById(R.id.ticket_price);
            delete = itemView.findViewById(R.id.delete);
            if(mCartList.isEmpty()){
                orderText.setText("Cart is empty");
            }
        }
    }

    public void deleteTicketFromCart(Long id) {
        String url = "http://3361bdd5b40a.ngrok.io/mobile/tickets/delete/"+id;
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
