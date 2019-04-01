package com.example.medicare;

import android.text.format.Time;

import java.util.Date;

public class item {

    String pid;
    String doctorId;
    Date date;
    Time slot;
    String diagnosis;

    public void item(String pid, String doctorId, Date date, Time slot, String diagnosis) {

        this.pid = pid;
        this.doctorId = doctorId;
        this.date = date;
        this.slot = slot;
        this.diagnosis = diagnosis;
    }



    public Date getDate() {
        return date;
    }

    public String getDoctor_name() {
        return doctorId;
    }

    public String getDisease() {
        return pid;
    }

    public Time getSlot() {
        return slot;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

/*
    public void setDate(String date) {
        this.date = date;
    }

    public void setDoctor_name(String doctor_name) {
        this.doctor_name = doctor_name;
    }

    public void setDisease(String disease) {
        this.disease = disease;
    }
*/
}
