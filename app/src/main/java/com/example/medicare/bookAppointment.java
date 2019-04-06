package com.example.medicare;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

public class bookAppointment extends AppCompatActivity {

    EditText docId, date, slot;
    Button book;
    TextView myID;
    String patientID;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_appointment);

        Intent i = getIntent();
        patientID = i.getStringExtra(Intent.EXTRA_TEXT);

        Toast.makeText(getApplicationContext(), patientID,Toast.LENGTH_LONG).show();


        docId = findViewById(R.id.Doctorid_entry);
        date = findViewById(R.id.dateText);
        slot = findViewById(R.id.Treatment_slot_entry);
        book = findViewById(R.id.treated_new);

        myID = findViewById(R.id.Pid_entry);
        myID.setText(patientID);

        progressDialog = new ProgressDialog(this);

        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String dateStr, slotStr, uName;

                dateStr = date.getText().toString();
                slotStr = slot.getText().toString();
                uName = docId.getText().toString();

                progressDialog.setMessage("Registering User .. ");
                progressDialog.show();

                StringRequest stringRequest = new StringRequest(Request.Method.POST,
                        Constants.URL_BOOK_APPOINT,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                progressDialog.dismiss();
                                try{
                                    JSONObject jsonObject = new JSONObject(response);
                                    if(!Boolean.parseBoolean(jsonObject.getString("error"))) {
                                        Toast.makeText(getApplicationContext(), jsonObject.getString("message"),Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(bookAppointment.this, after_patient_login_options.class);
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
                        params.put("Pid",patientID);
                        params.put("Username",uName);
                        params.put("Date",dateStr);
                        params.put("Slot",slotStr);


                        return params;
                    }

                };
                MySingleton.getInstance(bookAppointment.this).addToRequestQueue(stringRequest);

                RequestQueue requestQueue = Volley.newRequestQueue(bookAppointment.this);
                requestQueue.add(stringRequest);
            }
        });
    }

}
