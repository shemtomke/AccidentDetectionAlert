package com.example.accidentdetectionalert.models;

public class Hospital {
    private int hospitalId;
    private User user;
    private String location;
    public int getHospitalId(){return hospitalId;}
    public User getUser(){return user;}
    public String getLocation(){return location;}
    public void setUser(User user) {
        this.user = user;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    public void setHospitalId(int hospitalId) {
        this.hospitalId = hospitalId;
    }
    public Hospital(int _hospitalId, User _user, String _location)
    {
        hospitalId = _hospitalId;
        user = _user;
        location = _location;
    }
}
