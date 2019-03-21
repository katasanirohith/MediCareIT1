package com.example.medicare.dummy;

public class item {

    String date;
    String doctor_name;
    String disease;

    public item() {

    }

    public item( String date, String doctor_name, String disease) {

        this.date = date;
        this.doctor_name = doctor_name;
        this.disease = disease;
    }



    public String getDate() {
        return date;
    }

    public String getDoctor_name() {
        return doctor_name;
    }

    public String getDisease() {
        return disease;
    }


    public void setDate(String date) {
        this.date = date;
    }

    public void setDoctor_name(String doctor_name) {
        this.doctor_name = doctor_name;
    }

    public void setDisease(String disease) {
        this.disease = disease;
    }
}
