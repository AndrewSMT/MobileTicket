package com.andrew.myticketmobile.adapters;


import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.andrew.myticketmobile.activities.LoadingDialog;
import com.andrew.myticketmobile.R;
import com.andrew.myticketmobile.activities.MainActivity;
import com.andrew.myticketmobile.model.Ticket;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Set;


public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {

    private Context mContext;
    private ArrayList<Ticket> mTicketList;
    private RequestQueue requestQueue;
    private LoadingDialog loadingMainDialog;
    private static int viewHolderCounter;
    private ImageView stage;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }



    public OrderAdapter(Context context, ArrayList<Ticket> ticketList, LoadingDialog loadingOrderDialog, ImageView stage) {
        mContext = context;
        mTicketList = ticketList;
        this.loadingMainDialog = loadingOrderDialog;
        this.stage = stage;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.order_layout, parent, false);
        return new OrderViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final OrderViewHolder holder, final int position) {

        requestQueue = Volley.newRequestQueue(mContext);
        final Ticket currentItem = mTicketList.get(position);
        final int place = currentItem.getNumber();
        Set<String> status = currentItem.getTicketStatus();
        holder.place.setText(String.valueOf(place));
        if (status.contains("ACTIVE")) {
            holder.place.setBackgroundColor(Color.WHITE);
            holder.place.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    String url = "http://3cc8bd7d9f28.ngrok.io/mobile/tickets/order/" + currentItem.getId();
                    JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            error.printStackTrace();
                        }
                    });
                    requestQueue.add(request);

                    holder.place.setBackgroundColor(Color.GRAY);

                    MainActivity.tickets.add(currentItem);

                    Toast.makeText(mContext,"Ticket was added to cart",Toast.LENGTH_LONG).show();
                }
            });
        } else {
            holder.place.setBackgroundColor(Color.GRAY);
        }
        viewHolderCounter++;
        if(viewHolderCounter == mTicketList.size()-15){
            loadingMainDialog.dismissDialog();
            stage.setVisibility(View.VISIBLE);

        }
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
        }

    }


}
