package com.example.FloodAlert.Model;

import java.util.Date;

public class Notifications {


    private String titre;
    private String body;
    private Date date;
    public Notifications(String titre, String body) {
        this.titre = titre;
        this.body = body;

    }

    public Notifications() {

    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }



}
