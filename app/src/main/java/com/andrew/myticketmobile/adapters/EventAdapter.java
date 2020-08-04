package com.andrew.myticketmobile.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.andrew.myticketmobile.activities.LoadingDialog;
import com.andrew.myticketmobile.R;
import com.andrew.myticketmobile.activities.MainActivity;
import com.andrew.myticketmobile.model.Event;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder> {

    private int counter = 0;
    private ArrayList<Event> originalData = null;
    private ArrayList<Event> filteredData = null;
    private Context mContext;
    private ArrayList<Event> mEventItemList;
    private OnItemClickListener mListener;
    private ItemFilter mFilter = new ItemFilter();
    private Map<String,Integer> savePos = new HashMap<>();
    private LoadingDialog loadingMainDialog;
    private static int viewHolderCounter;

    public EventAdapter(Context context, ArrayList<String> eventTitleList, ArrayList<Event> eventItemList, LoadingDialog loadingMainDialog) {
        mContext = context;
        mEventItemList = eventItemList;
        MainActivity.events = mEventItemList;
        for (Event event: mEventItemList){
            savePos.put(event.getTitle(),counter);
            counter++;
        }
        this.filteredData = eventItemList ;
        this.originalData = eventItemList ;
        this.loadingMainDialog = loadingMainDialog;
        viewHolderCounter = 0;
    }

    public Filter getFilter() {
        return mFilter;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public int getCount() {
        return filteredData.size();
    }

    public Object getItem(int position) {
        return originalData.get(position);
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
        holder.placeTitle.setText(city + ", " + place);
        Picasso.with(mContext).load(picture).fit().centerInside().into(holder.imageView);
        viewHolderCounter++;
        if(viewHolderCounter == 2){
            loadingMainDialog.dismissDialog();
        }
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

        public EventViewHolder(@NonNull final View itemView) {
            super(itemView);
            eventTitle = itemView.findViewById(R.id.event_title);
            imageView = itemView.findViewById(R.id.event_image);
            dateTitle = itemView.findViewById(R.id.event_date);
            placeTitle = itemView.findViewById(R.id.event_place);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mListener != null) {
                        Integer position = savePos.get(eventTitle.getText());
                        if (position != RecyclerView.NO_POSITION) {
                            mListener.onItemClick(position);
                        }
                    }
                }
            });
        }

    }

    private class ItemFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            String filterString = constraint.toString().toLowerCase();

            List<Event> list = originalData;
            int count = list.size();
            final ArrayList<Event> resultList = new ArrayList<Event>(count);

            if("".equals(filterString)){
                resultList.addAll(list);
            }else {
                Event filterableString;

                for (int i = 0; i < count; i++) {
                    filterableString = list.get(i);
                    if (filterableString.getTitle().toLowerCase().contains(filterString)) {
                        resultList.add(filterableString);
                    }
                }
            }
            results.values = resultList;
            results.count = resultList.size();

            return results;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mEventItemList = (ArrayList<Event>) results.values;
            notifyDataSetChanged();
        }

    }

}
