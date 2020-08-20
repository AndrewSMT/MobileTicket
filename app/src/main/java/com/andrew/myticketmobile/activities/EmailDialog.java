package com.andrew.myticketmobile.activities;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.andrew.myticketmobile.R;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.UUID;

public class EmailDialog  extends Dialog {
    Activity activity;
    EditText editText,editCode;
    Button emailButton,codeButton;
    private RequestQueue requestQueue;
    private  String code;
    private TextView error_text;
    private String emailFromEdit;

    public EmailDialog(Activity activity) {
        super(activity);
        this.activity = activity;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.email_dialog);
        editText = findViewById(R.id.edit_email);
        editCode = findViewById(R.id.edit_code);
        emailButton = findViewById(R.id.edit_email_button);
        error_text = findViewById(R.id.error_text);
        codeButton = findViewById(R.id.edit_code_button);
        requestQueue = Volley.newRequestQueue(activity);
        emailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                emailFromEdit = editText.getText().toString();
                code = UUID.randomUUID().toString().substring(0,8).toUpperCase();
                sendEmailOrderJSON(emailFromEdit,code);
            }
        });
        codeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(code.equals(editCode.getText().toString())){
                    EmailDialog.this.dismiss();
                    Toast.makeText(activity,"Confirmed success",Toast.LENGTH_LONG).show();
                    CartActivity.email = emailFromEdit;
                }else{
                    error_text.setVisibility(View.VISIBLE);
                }
            }
        });
    }
    public void sendEmailOrderJSON(String email,String code) {
        String url = "http://3361bdd5b40a.ngrok.io/mobile/tickets/confirm?email=" + email+"&code="+code;
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
