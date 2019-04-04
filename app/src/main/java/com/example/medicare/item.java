package com.example.medicare;

public class item {

    private String date,pid,did;

    public item(String date, String pid, String did) {
        this.date = date;
        this.pid = pid;
        this.did = did;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getDid() {
        return did;
    }

    public void setDid(String did) {
        this.did = did;
    }
}