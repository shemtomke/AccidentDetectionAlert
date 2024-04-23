package com.example.accidentdetectionalert.models;

public class Accident {
    private int accidentId;
    private User user;
    private String location;
    private String dateTime;
    private String status;
    public int getAccidentId(){return accidentId;}
    public User getUser(){return user;}
    public String getLocation(){return location;}
    public String getDateTime(){return dateTime;}
    public String getStatus(){return status;}
    public void setAccidentId(int accidentId) {
        this.accidentId = accidentId;
    }
    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public Accident(int _accidentId, User _user, String _dateTime, String _location, String _status)
    {
        accidentId = _accidentId;
        user = _user;
        dateTime = _dateTime;
        location = _location;
        status = _status;
    }
    public Accident(User _user, String _dateTime, String _location)
    {
        user = _user;
        dateTime = _dateTime;
        location = _location;
    }
    public Accident(User _user, String _dateTime, String _location, String _status)
    {
        user = _user;
        dateTime = _dateTime;
        location = _location;
        status = _status;
    }
    public Accident(int _accidentId, String _dateTime, String _location, String _status)
    {
        accidentId = _accidentId;
        dateTime = _dateTime;
        location = _location;
        status = _status;
    }
}
