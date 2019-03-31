package com.example.medicare;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.tasks.OnSuccessListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class MainActivity extends AppCompatActivity
        /*implements NavigationView.OnNavigationItemSelectedListener*/ {

    private Button btn_newUser, login, getLocation;
    private EditText editTextId, editTextPassword;
    private FusedLocationProviderClient client;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);


       Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        final RecyclerView recyclerView = findViewById(R.id.rv_list);

        final List<item> mlist = new ArrayList<>();


        recyclerView.setLayoutManager(new LinearLayoutManager(this));



     /*   Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    */
        editTextId = (EditText) findViewById(R.id.editText3);
        editTextPassword = (EditText) findViewById(R.id.editText4);
        login = (Button) findViewById(R.id.button);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait ... ");

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String medical_id,password;
                medical_id = editTextId.getText().toString();
                password = editTextPassword.getText().toString();

                StringRequest stringRequest = new StringRequest(Request.Method.POST,
                        Constants.URL_LOGIN,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                progressDialog.dismiss();
                                try{
                                    JSONArray jsonArray = new JSONArray(response);

                                    JSONObject initial = jsonArray.getJSONObject(0);
                                    String error = initial.getString("error");
                                    String message = initial.getString("message");

                                    if(!Boolean.parseBoolean(error)){
                                        //SharedPrefManager.getInstance(getApplicationContext()).userLogin(
                                        //  jsonObject.getString("PatientID")
                                        //);

                                        for(int i=1;i<jsonArray.length();i++){

                                            JSONObject productObject = jsonArray.getJSONObject(i);
                                            String Pid = productObject.getString("PatientID");
                                            String Did = productObject.getString("DocID" );
                                            String Slot = productObject.getString("Slot");                                                                                       String Diagnosis = productObject.getString("Diagnosis");
                                            String Date = productObject.getString("Date");

                                            item Item = new item(Date, Did, Diagnosis, Pid , Slot);
                                            mlist.add(Item);
                                        }
                                        com.example.medicare.Adapter adapter = new com.example.medicare.Adapter(MainActivity.this,mlist);
                                        recyclerView.setAdapter(adapter);
                                        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(MainActivity.this, cardsOverView.class);
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
                        }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("Pid",medical_id);
                        params.put("password",password);

                        return params;
                    }

                };
                MySingleton.getInstance(MainActivity.this).addToRequestQueue(stringRequest);
            }
        });

        btn_newUser = (Button) findViewById(R.id.button1);
        btn_newUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PatientDetails.class);
                startActivity(intent);
            }

        });
       /* getLocation = (Button) findViewById(R.id.location);
        getLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(MainActivity.this, ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                    return;
                }
                client.getLastLocation().addOnSuccessListener(MainActivity.this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if(location != null){
                            TextView textView = (TextView) findViewById(R.id.editText3);
                            textView.setText(location.toString());
                        }
                    }
                });
          }
      });*/
    }
    private void requestPermission(){

            ActivityCompat.requestPermissions(this, new String[] {ACCESS_FINE_LOCATION}, 1);

    }
/*
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
*/

}
