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

public class EventListParser {

    ArrayList<Event> eventItemList = new ArrayList<>();

   /* public ArrayList<Event> parseEventListJSON(RequestQueue requestQueue) {
        String url = "http://6c85103883d2.ngrok.io/mobile/";
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject it = (JSONObject) response.get(i);
                        Long id = Long.parseLong(it.getString("id"));
                        String title = it.getString("title");
                        String picture = it.getString("picture");
                        String date = it.getString("date");
                        JSONObject place =  it.getJSONObject("place");
                        JSONObject city =  place.getJSONObject("city");
                        String placeTitle = (String) place.get("title");
                        String cityTitle = (String) city.get("title");
                        String description = it.getString("description");

                        eventItemList.add(new Event(placeTitle,date,title,description,picture,cityTitle));
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
        return eventItemList;
    }*/
}
