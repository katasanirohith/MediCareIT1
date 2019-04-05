package com.example.medicare;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class doctor_doing_treatment extends AppCompatActivity {

    EditText textTreatmentDate, textSlot, textSymptoms, textdiagnosis, textPrescription, textRemarks;
    Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_doing_treatment);
    }
}
