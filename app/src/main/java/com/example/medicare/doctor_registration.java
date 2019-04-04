package com.example.medicare;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class doctor_registration extends AppCompatActivity {

    private EditText username, email, full_name, password, confirm_pass, specialization, shift_type, mob_no, sex, DOB;
    Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_registration);
    }
}
