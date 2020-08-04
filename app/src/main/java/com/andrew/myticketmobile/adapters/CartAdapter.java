package com.andrew.myticketmobile.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.andrew.myticketmobile.R;
import com.andrew.myticketmobile.model.Event;
import com.andrew.myticketmobile.model.Ticket;
import com.android.volley.RequestQueue;

import java.util.ArrayList;

import static com.andrew.myticketmobile.activities.MainActivity.events;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private Context mContext;
    private ArrayList<Ticket> mCartList;
    private ArrayList<Event> mEventList;
    private OnItemClickListener mListener;
    private RequestQueue requestQueue;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnClickListener(OnItemClickListener listener) {
        mListener = listener;
    }


    public CartAdapter(Context context, ArrayList<Ticket> ticketList) {
        mContext = context;
        mCartList = ticketList;
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
        Ticket currentItem = mCartList.get(position);
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
        holder.rowPlace.setText("Row: " + row.toString()+" Place: "+number.toString());
        holder.datePlace.setText(date+", "+place+", "+city);
        holder.title.setText(eventTitle);
        holder.price.setText("Price: "+ price.toString()+"UAH");
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
        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            rowPlace = itemView.findViewById(R.id.row_place);
            title = itemView.findViewById(R.id.ticket_title);
            datePlace = itemView.findViewById(R.id.date_place);
            price = itemView.findViewById(R.id.ticket_price);
        }

    }
}
