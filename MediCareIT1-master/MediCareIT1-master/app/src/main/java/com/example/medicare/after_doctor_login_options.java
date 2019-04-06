package com.example.medicare;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class after_doctor_login_options extends AppCompatActivity {

    String Username;
    Button myHistory, treatNewpatient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_doctor_login_options);

        Intent i = getIntent();
        Username = i.getStringExtra(Intent.EXTRA_TEXT);

        treatNewpatient = findViewById(R.id.treatNewButton);
            treatNewpat();
        myHistory = findViewById(R.id.myHistoryButton);
            myHistory();
    }

    public void treatNewpat() {
        treatNewpatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent treat_new = new Intent(after_doctor_login_options.this, doctor_doing_treatment.class);
                treat_new.putExtra(Intent.EXTRA_TEXT,Username);
                startActivity(treat_new);
            }
        });
    }

    public void myHistory() {
        myHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent history = new Intent(after_doctor_login_options.this, doctorHistory.class);
                history.putExtra(Intent.EXTRA_TEXT,Username);
                startActivity(history);
            }
        });
    }

}
