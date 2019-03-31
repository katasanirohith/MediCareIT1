package com.example.medicare;

public class item {

    String date;
    String doctor_id;
    String diagnosis;
    String pid,slot;


    public item(String date, String doctor_id, String diagnosis, String pid , String slot) {

        this.date = date;
        this.doctor_id = doctor_id;
        this.diagnosis = diagnosis;
        this.slot = slot;
        this.pid = pid;
    }

    public String getDate() {
        return date;
    }

    public String getDoctor_id() {
        return doctor_id;
    }

    public String getDiagnosis() {
        return diagnosis;
    }
    public String getSlot() {
        return slot;
    }
    public String getPid() {
        return pid;
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
