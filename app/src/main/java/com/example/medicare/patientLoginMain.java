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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class patientLoginMain extends AppCompatActivity {

    Button patReg, PatLogin, getLocation;
    EditText Pid, patPassword;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_login_main);

        Pid = findViewById(R.id.PatientID);
        patPassword = findViewById(R.id.patientPass);
        PatLogin = findViewById(R.id.patientLogin);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait ... ");

        PatLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String ID, password;
                ID = Pid.getText().toString();
                password = patPassword.getText().toString();

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
                                        Intent intent = new Intent(patientLoginMain.this, after_patient_login_options.class);
                                        intent.putExtra(Intent.EXTRA_TEXT,ID);
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
                        params.put("Pid",ID);
                        params.put("password",password);

                        return params;
                    }
                };
                MySingleton.getInstance(patientLoginMain.this).addToRequestQueue(stringRequest);
            }
        }  );

        patReg = findViewById(R.id.treated_new);
        patReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotoReg = new Intent(patientLoginMain.this, PatientDetails.class);
                startActivity(gotoReg);
            }
        });

    }




}