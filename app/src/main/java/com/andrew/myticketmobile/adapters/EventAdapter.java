package com.andrew.myticketmobile.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.andrew.myticketmobile.R;
import com.andrew.myticketmobile.model.Event;
import com.squareup.picasso.Picasso;



import java.util.ArrayList;


public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder> {

    private Context mContext;
    private ArrayList<Event> mEventItemList;
    private OnItemClickListener mListener;

    public  interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnClickListener(OnItemClickListener listener){
        mListener = listener;
    }


    public EventAdapter(Context context, ArrayList<Event> eventItemList) {
        mContext = context;
        mEventItemList = eventItemList;
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.event_layout, parent, false);
        return new EventViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
        Event currentItem = mEventItemList.get(position);
        String title = currentItem.getTitle();
        String picture = currentItem.getPicture();
        String date = currentItem.getDate();
        String place = currentItem.getPlace();
        String city = currentItem.getCityTitle();
        holder.eventTitle.setText(title);
        holder.dateTitle.setText(date);
        holder.placeTitle.setText(city+", "+place);
       Picasso.with(mContext).load(picture).fit().centerInside().into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return mEventItemList.size();
    }

    class EventViewHolder extends RecyclerView.ViewHolder {

        public TextView eventTitle;
        public TextView dateTitle;
        public TextView placeTitle;
        public ImageView imageView;

        public EventViewHolder(@NonNull View itemView) {
            super(itemView);
            eventTitle = itemView.findViewById(R.id.event_title);
            imageView = itemView.findViewById(R.id.event_image);
            dateTitle = itemView.findViewById(R.id.event_date);
            placeTitle = itemView.findViewById(R.id.event_place);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(mListener != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            mListener.onItemClick(position);
                        }
                    }
                }
            });
        }

    }


}
