package com.andrew.myticketmobile.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.andrew.myticketmobile.R;
import com.andrew.myticketmobile.model.Event;
import com.andrew.myticketmobile.model.FullTicket;
import com.andrew.myticketmobile.model.Ticket;
import com.android.volley.RequestQueue;

import java.util.ArrayList;
import java.util.List;

import static com.andrew.myticketmobile.activities.MainActivity.events;
import static com.andrew.myticketmobile.adapters.OrderAdapter.order;

public class BuyTicketAdapter extends RecyclerView.Adapter<BuyTicketAdapter.BuyTicketViewHolder> {
    private Context mContext;
    private List<FullTicket> mCartList;
    private ArrayList<Event> mEventList;
    private RequestQueue requestQueue;
    private Button cartButton;

    public BuyTicketAdapter(Context context, List<FullTicket> ticketList) {
        mContext = context;
        mCartList = ticketList;
    }

    @NonNull
    @Override
    public BuyTicketAdapter.BuyTicketViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.buy_tickets_layout, parent, false);
        return new BuyTicketAdapter.BuyTicketViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final BuyTicketAdapter.BuyTicketViewHolder holder, final int position) {
        FullTicket currentItem = mCartList.get(position);
        Integer row = currentItem.getRow();
        String date = currentItem.getDate();
        String place = currentItem.getPlace();
        String city = currentItem.getCity();
        String eventTitle = currentItem.getTitle();
        Integer number = currentItem.getNumber();
        Integer price = currentItem.getPrice();
        holder.ticketOrder.setText("Order № "+currentItem.getOrderNumber());
        holder.rowPlace.setText("Row: " + row.toString() + " Place: " + number.toString());
        holder.datePlace.setText(date + ", " + place + ", " + city);
        holder.title.setText(eventTitle);
        holder.price.setText("Price: " + price.toString() + "UAH");
    }


    @Override
    public int getItemCount() {
        return mCartList.size();
    }

    class BuyTicketViewHolder extends RecyclerView.ViewHolder {
        public TextView rowPlace;
        public TextView title;
        public TextView datePlace;
        public TextView price;
        public TextView ticketOrder;

        public BuyTicketViewHolder(@NonNull View itemView) {
            super(itemView);
            rowPlace = itemView.findViewById(R.id.row_place);
            title = itemView.findViewById(R.id.ticket_title);
            datePlace = itemView.findViewById(R.id.date_place);
            price = itemView.findViewById(R.id.ticket_price);
            ticketOrder = itemView.findViewById(R.id.ticket_order);
        }

    }
}
