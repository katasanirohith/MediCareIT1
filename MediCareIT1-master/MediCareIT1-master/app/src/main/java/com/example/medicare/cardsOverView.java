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

import static android.text.TextUtils.isEmpty;

public class cardsOverView extends AppCompatActivity {
    //private List<item> mList = new ArrayList<>();

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
        Intent i = getIntent();
        ID = i.getStringExtra(Intent.EXTRA_TEXT);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        RecyclerView.LayoutManager rvLayoutManager = linearLayoutManager;

        recyclerView.setLayoutManager(rvLayoutManager);

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constants.URL_FETCH_CARDVIEWS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            JSONObject obj = jsonArray.getJSONObject(0);
                            String error = obj.getString("error");
                            String message = obj.getString("message");
                            if(!Boolean.parseBoolean(error)){

                                for(int i=1;i<jsonArray.length();i++){
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    String Pid = object.getString("PatientID");
                                    String Hospital = object.getString("Hospital");
                                    String DocID = object.getString("DocID");
                                    String Date = object.getString("Date");
                                    String Slot = object.getString("Slot");
                                    String Symptoms = object.getString("Symptoms");
                                    String Diagnosis = object.getString("Diagnosis");
                                    String Prescription = object.getString("Prescription");
                                    String Remarks = object.getString("Remarks");

                                    item items = new item(Pid,Hospital, DocID, Date, Slot, Symptoms, Diagnosis, Prescription, Remarks);
                                    mList.add(items);
                                }
                                Adapter adapter = new Adapter(cardsOverView.this, mList);

                                recyclerView.setAdapter(adapter);

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.hide();
                        Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String , String> params = new HashMap<>();
                params.put("Pid",ID);
                return params;
            }
        };

        MySingleton.getInstance(cardsOverView.this).addToRequestQueue(stringRequest);

//        mList.add(new item("akra143", "chettinad","DOC001" , "1998-09-09", "morning" , "headache" , "migrane" , "dolo", "nil"));



    }

}
