package com.andrew.myticketmobile.JSONparsers;

import com.andrew.myticketmobile.model.Event;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class EventInfoParser {

    ArrayList<Integer> eventItemList = new ArrayList<>();

    public ArrayList<Integer> parseEventInfoListJSON(Long id) {
        String url = "http://6c85103883d2.ngrok.io/mobile/event/inf/"+id;
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    for (int i = 0; i < response.length(); i++) {
                        response.get(i);
                        int item = (int) response.get(i);
                        eventItemList.add(item);
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
        return eventItemList;
    }
}
