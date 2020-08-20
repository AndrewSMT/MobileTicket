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

import com.andrew.myticketmobile.activities.CartActivity;
import com.andrew.myticketmobile.activities.LoadingDialog;
import com.andrew.myticketmobile.R;
import com.andrew.myticketmobile.activities.MainActivity;
import com.andrew.myticketmobile.model.Order;
import com.andrew.myticketmobile.model.OrderStatus;
import com.andrew.myticketmobile.model.Ticket;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;


public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {

    private Context mContext;
    private ArrayList<Ticket> mTicketList;
    private RequestQueue requestQueue;
    private LoadingDialog loadingMainDialog;
    private static int viewHolderCounter;
    private ImageView stage;
    public static Order order = new Order();

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
                    if(MainActivity.tickets.isEmpty()) {
                        order.setOrderId(0L);
                    }
                    String url = "http://3361bdd5b40a.ngrok.io/mobile/tickets/order/" + currentItem.getId()+"?order="+order.getOrderId();
                    JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            JSONObject orderResponse = (JSONObject) response;
                            try {
                                order.setOrderId(Long.parseLong(orderResponse.getString("id_order")));
                                Set<String> status = new HashSet();
                                status.add(orderResponse.getString("orderStatus").replaceAll("\\W", ""));
                                order.setOrderStatus(status);
                                JSONArray ticketResponse = orderResponse.getJSONArray("ticket");
                                for (int i = 0; i < ticketResponse.length(); i++) {
                                    JSONObject it = (JSONObject) ticketResponse.get(i);
                                    Long id = Long.parseLong(it.getString("id_ticket"));
                                    JSONObject event = it.getJSONObject("event");
                                    Long eventId = event.getLong("id");
                                    Integer row = it.getInt("row");
                                    Integer place = it.getInt("number");
                                    Integer price = it.getInt("price");
                                    Set<String> ticketStatus = new HashSet();
                                    ticketStatus.add(it.getString("ticketStatus").replaceAll("\\W", ""));
                                    Long orderNumber = order.getOrderId();
                                    if (it.getString("orderNumber") != "null") {
                                        orderNumber = Long.parseLong(it.getString("orderNumber"));
                                    }
                                    order.getTicket().add(new Ticket(id, eventId, row, place, price, ticketStatus, orderNumber));
                                    order.setTicket(order.getTicket());
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
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
            viewHolderCounter = 0;
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
