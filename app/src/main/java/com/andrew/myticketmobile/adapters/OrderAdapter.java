package com.andrew.myticketmobile.adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.andrew.myticketmobile.R;
import com.andrew.myticketmobile.model.Event;
import com.andrew.myticketmobile.model.Ticket;
import com.squareup.picasso.Picasso;



import java.util.ArrayList;


public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {

    private Context mContext;
    private ArrayList<Ticket> mTicketList;
    private OnItemClickListener mListener;

    public  interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnClickListener(OnItemClickListener listener){
        mListener = listener;
    }


    public OrderAdapter(Context context, ArrayList<Ticket> ticketList) {
        mContext = context;
        mTicketList = ticketList;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.order_layout, parent, false);
        return new OrderViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
       /* Event currentItem = mEventItemList.get(position);*/
    }

    @Override
    public int getItemCount() {
        return mTicketList.size();
    }

    class OrderViewHolder extends RecyclerView.ViewHolder {

        public Button place;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            place = itemView.findViewById(R.id.place);
           /* itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(mListener != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            mListener.onItemClick(position);
                        }
                    }
                }
            });*/
        }

    }


}
