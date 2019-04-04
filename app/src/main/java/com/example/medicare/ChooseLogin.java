package com.example.medicare;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ChooseLogin extends AppCompatActivity {

    Button PatLog, DocLog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_login);

        PatLog = findViewById(R.id.patPortal);
        portalPat();

        DocLog = findViewById(R.id.docPortal);
        portalDoc();
    }

    public void portalPat() {
        PatLog.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent launch_patient = new Intent(ChooseLogin.this, MainActivity.class);
                        startActivity(launch_patient);
                    }
                }
        );
    }

    public void portalDoc() {
        DocLog.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent launch_doc = new Intent(ChooseLogin.this, doctor_registration.class);
                        startActivity(launch_doc);
                    }
                }
        );
    }
}
