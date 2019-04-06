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

    String Pid;
    Button book_button;
    EditText docId, dateText, slotText;
    TextView pidText;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_appointment);

        Intent i = getIntent();
        Pid = i.getStringExtra(Intent.EXTRA_TEXT);

        pidText = findViewById(R.id.Pid_entry);
        pidText.setText(Pid);

        docId = findViewById(R.id.Doctorid_entry);
        dateText = findViewById(R.id.date__entry);
        slotText = findViewById(R.id.Treatment_slot_entry);

        book_button = findViewById(R.id.booked);
        progressDialog = new ProgressDialog(this);

        book_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String date, slot, DocId;

                DocId = docId.getText().toString();
                date = dateText.getText().toString();
                slot = slotText.getText().toString();

                progressDialog.setMessage("Registering User .. ");
                progressDialog.show();

                StringRequest stringRequest = new StringRequest(Request.Method.POST,
                        Constants.URL_BOOK_APPOINTMENT,
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
                        params.put("Pid",Pid);
                        params.put("Username",DocId);
                        params.put("Date",date);
                        params.put("Slot",slot);

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
