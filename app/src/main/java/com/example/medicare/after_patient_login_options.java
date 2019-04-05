package com.example.medicare;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class after_patient_login_options extends AppCompatActivity {

    String Pid;
    Button bookAppointment, myHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_patient_login_options);

        Pid = getIntent().getStringExtra("pid");

        bookAppointment = findViewById(R.id.bookAppointment);
        book_appointment();

        myHistory = findViewById(R.id.patient_history);
        myHistory();

    }

    public void book_appointment() {
        bookAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent bookApp = new Intent(after_patient_login_options.this, bookAppointment.class);
                bookApp.putExtra("pid",Pid);
                startActivity(bookApp);
            }
        });
    }

    public void myHistory() {
        myHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent fetch_history = new Intent(after_patient_login_options.this, cardsOverView.class);
                fetch_history.putExtra("pid",Pid);
                startActivity(fetch_history);
            }
        });
    }

}
