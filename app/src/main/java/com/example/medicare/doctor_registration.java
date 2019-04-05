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

public class doctor_registration extends AppCompatActivity {

    EditText username, emailText, full_name, passwordText, confirm_pass, special, shift_type, mob_no, sex, DOB;
    Button button_register;

    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_registration);

        /*if(SharedPrefManager.getInstance(this).isLoggedIn()){
            finish();
            startActivity(new Intent(this, cardsOverView.class));
            return;
        }*/

        username = findViewById(R.id.docId);
        emailText = findViewById(R.id.Email_entry);
        full_name = findViewById(R.id.Name_entry);
        passwordText = findViewById(R.id.password_entry);
        confirm_pass = findViewById(R.id.Confirm_Password_entry);
        special = findViewById(R.id.specializationText);
        shift_type = findViewById(R.id.editText16);
        mob_no = findViewById(R.id.editText18);
        sex = findViewById(R.id.editText17);
        DOB = findViewById(R.id.editText5);

        button_register = findViewById(R.id.docRegister);
        progressDialog = new ProgressDialog(this);

        button_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String doctorId, name, email, password, confirm_password, Sex, phone, specialisation, shiftType,dob;

                doctorId = username.getText().toString();
                name = full_name.getText().toString();
                dob = DOB.getText().toString();
                email = emailText.getText().toString();
                password = passwordText.getText().toString();
                Sex = sex.getText().toString();
                phone = mob_no.getText().toString();
                specialisation = special.getText().toString();
                shiftType = shift_type.getText().toString();
                confirm_password = confirm_pass.getText().toString();

                progressDialog.setMessage("Registering User .. ");
                progressDialog.show();

                StringRequest stringRequest = new StringRequest(Request.Method.POST,
                        Constants.URL_REGISTER_DOC,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                progressDialog.dismiss();
                                try{
                                    JSONObject jsonObject = new JSONObject(response);
                                    if(!Boolean.parseBoolean(jsonObject.getString("error"))) {
                                        Toast.makeText(getApplicationContext(), jsonObject.getString("message"),Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(doctor_registration.this, doctorLoginMain.class);
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
                        params.put("Username",doctorId);
                        params.put("email",email);
                        params.put("FullName",name);
                        params.put("Password",password);
                        params.put("ConfirmPass",confirm_password);
                        params.put("specialization",specialisation);
                        params.put("shift_type",shiftType);
                        params.put("Mob_no",phone);
                        params.put("Sex",Sex);
                        params.put("DOB",dob);

                        return params;
                    }

                };
                MySingleton.getInstance(doctor_registration.this).addToRequestQueue(stringRequest);

                RequestQueue requestQueue = Volley.newRequestQueue(doctor_registration.this);
                requestQueue.add(stringRequest);
            }
        });

    }
}
