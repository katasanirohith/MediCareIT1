package com.example.medicare;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class cardsOverView extends AppCompatActivity {
    private List<item> mlist = new ArrayList<>();

    RecyclerView recyclerView ;
    ArrayList<item> mList;
    private  String ID;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.card_all);

        recyclerView = findViewById(R.id.rv_list);

        mList = new ArrayList<>();
        ID = getIntent().getStringExtra("pid");
        /*
        mList.add(new item("charan", "doc","2019-08-08"));
        mlist.add(new item("anil", "gajji", "2018-09-09"));
        mList.add(new item("chikda", "doc3","2016-08-08"));
        mlist.add(new item("pantha", "ache", "2013-09-09"));
*/

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constants.URL_FETCH_CARDVIEWS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();

                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            JSONObject status = jsonArray.getJSONObject(0);
                            String error = status.getString("error");
                            String message = status.getString("message");

                            if(!Boolean.parseBoolean(error)) {
                                for (int i = 1; i < jsonArray.length(); i++) {

                                    JSONObject productObject = jsonArray.getJSONObject(i);
                                    String Pid = productObject.getString("PatientID");
                                    String Hospital = productObject.getString("Hospital");
                                    String Did = productObject.getString("DocID");
                                    String Date = productObject.getString("Date");
                                    String Slot = productObject.getString("Slot");
                                    String symptoms = productObject.getString("Symptoms");
                                    String diagnosis = productObject.getString("Diagnosis");
                                    String prescription = productObject.getString("Prescription");
                                    String remarks = productObject.getString("Remarks");

                                    item Item = new item(Pid, Hospital, Did, Date, Slot, symptoms, diagnosis, prescription, remarks);
                                    mlist.add(Item);
                                }
                            }
                            else{
                                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
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
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Pid",ID);
                params.put("pid",ID);

                return params;
            }
        };
        MySingleton.getInstance(cardsOverView.this).addToRequestQueue(stringRequest);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        RecyclerView.LayoutManager rvLayoutManager = linearLayoutManager;

        recyclerView.setLayoutManager(rvLayoutManager);

        Adapter adapter = new Adapter(this, mList);

        recyclerView.setAdapter(adapter);
    }

}