package com.example.accidentdetectionalert.models;

public class User {
    private int userId;
    private String email;
    private String password;
    private String fullName;
    private String phoneNumber;
    public String role;

    public int getUserId(){return userId;}
    public String getEmail(){return email;}
    public String getPassword(){return password;}
    public String getFullName(){return fullName;}
    public String getRole(){return role;}

    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public void setUserId(int _userId)
    {
        userId = _userId;
    }
    public void setEmail(String _email)
    {
        email = _email;
    }
    public void setPassword(String _password)
    {
        password = _password;
    }
    public void setFullName(String _fullName)
    {
        fullName = _fullName;
    }
    public void setRole(String _role) {role = _role;}
    public User() {}
    public User(String _email, String _password)
    {
        email = _email;
        password = _password;
    }
    public User(int id, String _fullName, String _email, String _password, String phonenumber,  String _role)
    {
        userId = id;
        email = _email;
        phoneNumber = phonenumber;
        password = _password;
        fullName = _fullName;
        role = _role;
    }
    public User(int id, String _fullName, String _email, String phonenumber,  String _role)
    {
        userId = id;
        email = _email;
        phoneNumber = phonenumber;
        fullName = _fullName;
        role = _role;
    }
    public User(String _fullName, String _email, String _password, String phonenumber, String _role)
    {
        email = _email;
        phoneNumber = phonenumber;
        password = _password;
        fullName = _fullName;
        role = _role;
    }
}
