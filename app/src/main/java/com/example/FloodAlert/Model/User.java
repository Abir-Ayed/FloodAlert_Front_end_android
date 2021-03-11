package com.example.FloodAlert.Model;

import android.widget.Spinner;

public class User {




    private String email;


    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    private String region;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    private String phone;


    public User(String name,String email) {
        this.name = name;
        this.email = email;
    }
    public User(String email,String phone, String name) {
        this.email = email;
        this.phone = phone;

        this.name = name;


    }

    public User(String email,String phone, String password, String name, String region) {
        this.email = email;
        this.phone = phone;
        Password = password;
        this.name = name;
        this.region = region;

    }
    public User(String email,String phone, String name, String region) {
        this.email = email;
        this.phone = phone;

        this.name = name;
        this.region = region;

    }
    public User() {}



    private String Password;
    private String name;





    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}