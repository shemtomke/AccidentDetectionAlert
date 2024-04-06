package com.example.accidentdetectionalert.models;

public class Hospital {
    private String hospitalId;
    private User user;
    private String location;
    public String getHospitalId(){return hospitalId;}
    public User getUser(){return user;}
    public String getLocation(){return location;}
    public void setUser(User user) {
        this.user = user;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
    }
    public Hospital(String _hospitalId, User _user, String _location)
    {
        hospitalId = _hospitalId;
        user = _user;
        location = _location;
    }
}
