package com.example.medicare;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.location.FusedLocationProviderClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class doctorLoginMain extends AppCompatActivity {

    Button registration, login, getLocation;
    EditText Id, Password;
    //private FusedLocationProviderClient client;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_login_main);

        Id = findViewById(R.id.userId);
        Password = findViewById(R.id.pass);
        login = findViewById(R.id.buttonLogin);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait ... ");

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String ID, password;
                ID = Id.getText().toString();
                password = Password.getText().toString();

                StringRequest stringRequest = new StringRequest(Request.Method.POST,
                        Constants.URL_LOGIN,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                progressDialog.dismiss();

                                try {
                                    JSONObject jsonObj = new JSONObject(response);
                                    String error = jsonObj.getString("error");
                                    String message = jsonObj.getString("message");

                                    if(!Boolean.parseBoolean(error)) {
                                        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(doctorLoginMain.this, after_doctor_login_options.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                    else {
                                        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
                                    }
                                }
                                catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                progressDialog.hide();
                                Toast.makeText(getApplicationContext(),error.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("Username",ID);
                        params.put("Password",password);

                        return params;
                    }
                };
                MySingleton.getInstance(doctorLoginMain.this).addToRequestQueue(stringRequest);
            }
        }  );

        registration = findViewById(R.id.buttonReg);
        registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotoReg = new Intent(doctorLoginMain.this, doctor_registration.class);
                startActivity(gotoReg);
            }
        });

    }




}