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
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PatientDetails extends AppCompatActivity {

    private EditText editText_medicalId, editText__name, editText_dob, editText_emailId, editText_bloodGroup, editText_hostel,
    editText_weight, editText_roomNumber, editText_password, editText_sex, editText_phone, editText_latitude,
    editText_longitude,editText_confirmPassword;
    private Button btn_register;
    /*String server_url = "http://172.17.3.188/MediCare/includes/registerUser.php";
    AlertDialog.Builder builder;
   */
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_details);

        if(SharedPrefManager.getInstance(this).isLoggedIn()){
            finish();
            startActivity(new Intent(this, cardsOverView.class));
            return;
        }

        editText_medicalId = (EditText) findViewById(R.id.editText11);
        editText__name = (EditText) findViewById(R.id.Full_name_entry);
        editText_dob = (EditText) findViewById(R.id.editText5);
        editText_emailId = (EditText) findViewById(R.id.editText6);
        editText_bloodGroup = (EditText) findViewById(R.id.editText9);
        editText_hostel = (EditText) findViewById(R.id.editText7);
        editText_weight = (EditText) findViewById(R.id.editText13);
        editText_roomNumber = (EditText) findViewById(R.id.editText14);
        editText_password = (EditText) findViewById(R.id.editText12);
        editText_sex = (EditText) findViewById(R.id.editText19);
        editText_phone = (EditText) findViewById(R.id.editText20);
        editText_latitude = (EditText) findViewById(R.id.editText10);
        editText_longitude = (EditText) findViewById(R.id.editText21);
        editText_confirmPassword = (EditText) findViewById(R.id.editText22);
        btn_register = (Button) findViewById(R.id.treated_new);
        progressDialog = new ProgressDialog(this);
       // builder = new AlertDialog.Builder(PatientDetails.this);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String medical_id,name,dob,email,bloodGroup,hostel,weight,roomNumber,password,sex,phone,latitude,longitude,confirmPassword;
                medical_id = editText_medicalId.getText().toString();
                name = editText_medicalId.getText().toString();
                dob = editText_dob.getText().toString();
                email = editText_emailId.getText().toString();
                bloodGroup = editText_bloodGroup.getText().toString();
                hostel = editText_hostel.getText().toString();
                weight = editText_weight.getText().toString();
                roomNumber = editText_roomNumber.getText().toString();
                password = editText_password.getText().toString();
                sex = editText_sex.getText().toString();
                phone = editText_phone.getText().toString();
                latitude = editText_latitude.getText().toString();
                longitude = editText_longitude.getText().toString();
                confirmPassword = editText_confirmPassword.getText().toString();

                progressDialog.setMessage("Registering User .. ");
                progressDialog.show();

                StringRequest stringRequest = new StringRequest(Request.Method.POST,
                        Constants.URL_REGISTER,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                progressDialog.dismiss();
                                try{
                                    JSONObject jsonObject = new JSONObject(response);
                                    if(!Boolean.parseBoolean(jsonObject.getString("error"))) {
                                        Toast.makeText(getApplicationContext(), jsonObject.getString("message"),Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(PatientDetails.this, patientLoginMain.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                    else {
                                        Toast.makeText(getApplicationContext(), jsonObject.getString("message"),Toast.LENGTH_LONG).show();
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
                        }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("Pid",medical_id);
                        params.put("Name",name);
                        params.put("DOB",dob);
                        params.put("email",email);
                        params.put("Bloodgrp",bloodGroup);
                        params.put("Hostel",hostel);
                        params.put("weight",weight);
                        params.put("Roomno",roomNumber);
                        params.put("Password",password);
                        params.put("Sex",sex);
                        params.put("Mob_no",phone);
                        params.put("lati",latitude);
                        params.put("longi",longitude);
                        params.put("ConfirmPass",confirmPassword);

                        return params;
                    }

                };
                MySingleton.getInstance(PatientDetails.this).addToRequestQueue(stringRequest);

                RequestQueue requestQueue = (RequestQueue) Volley.newRequestQueue(PatientDetails.this);
                requestQueue.add(stringRequest);
            }
        });
    }

}
