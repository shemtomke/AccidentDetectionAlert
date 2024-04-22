package com.example.accidentdetectionalert.models;

public class HistoryAlert {
    private int id;
    User user;
    private String dateTime;
    private String status;
    public int getId() {
        return id;
    }
    public String getDateTime() {
        return dateTime;
    }
    public User getUser(){
        return user;
    }
    public String getStatus() {
        return status;
    }
    public void setDateTime(String dateTime1){
        this.dateTime = dateTime1;
    }
    public void setStatus(String status1){
        this.status = status1;
    }
    public HistoryAlert(int id, User user, String dateTime, String status) {
        this.id = id;
        this.user = user;
        this.dateTime = dateTime;
        this.status = status;
    }
    public HistoryAlert(int id, String dateTime, String status) {
        this.id = id;
        this.dateTime = dateTime;
        this.status = status;
    }
    public HistoryAlert(User user, String dateTime, String status) {
        this.user = user;
        this.dateTime = dateTime;
        this.status = status;
    }
}

