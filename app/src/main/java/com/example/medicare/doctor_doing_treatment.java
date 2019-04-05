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

public class doctor_doing_treatment extends AppCompatActivity {

    String docID;
    EditText textTreatmentDate, textSlot, textSymptoms, textdiagnosis, textPrescription, textRemarks, pid;
    Button submitButton;
    ProgressDialog progressDialog;
    TextView Did;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_doing_treatment);
        Intent i = getIntent();
        docID = i.getStringExtra(Intent.EXTRA_TEXT);


        Did = findViewById(R.id.DID);
        Did.setText(docID);

        textTreatmentDate = findViewById(R.id.Full_name_entry);
        textSlot = findViewById(R.id.Treatment_slot_entry);
        textSymptoms = findViewById(R.id.symptoms_entry);
        textdiagnosis = findViewById(R.id.Diagnosis_entry);
        textPrescription = findViewById(R.id.Prescription_entry);
        textRemarks = findViewById(R.id.Remarks_entry);
        pid = findViewById(R.id.pid_entry);

        submitButton = findViewById(R.id.treated_new);
        progressDialog = new ProgressDialog(this);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String date, slot, symptoms, diagnosis, prescription, remarks, Pid;

                Pid = pid.getText().toString();
                date = textTreatmentDate.getText().toString();
                slot = textSlot.getText().toString();
                symptoms = textSymptoms.getText().toString();
                diagnosis = textdiagnosis.getText().toString();
                prescription = textPrescription.getText().toString();
                remarks = textRemarks.getText().toString();

                progressDialog.setMessage("Registering User .. ");
                progressDialog.show();

                StringRequest stringRequest = new StringRequest(Request.Method.POST,
                        Constants.URL_TREAT_NEW,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                progressDialog.dismiss();
                                try{
                                    JSONObject jsonObject = new JSONObject(response);
                                    if(!Boolean.parseBoolean(jsonObject.getString("error"))) {
                                        Toast.makeText(getApplicationContext(), jsonObject.getString("message"),Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(doctor_doing_treatment.this, after_doctor_login_options.class);
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
                        params.put("Hospital_name","");
                        params.put("Username",docID);
                        params.put("treatment_date",date);
                        params.put("slot",slot);
                        params.put("symptoms",symptoms);
                        params.put("diagnosis",diagnosis);
                        params.put("prescription",prescription);
                        params.put("remarks",remarks);

                        return params;
                    }

                };
                MySingleton.getInstance(doctor_doing_treatment.this).addToRequestQueue(stringRequest);

                RequestQueue requestQueue = Volley.newRequestQueue(doctor_doing_treatment.this);
                requestQueue.add(stringRequest);
            }
        });
    }
}
