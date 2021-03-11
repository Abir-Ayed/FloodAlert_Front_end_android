package com.example.FloodAlert.Model;

import java.util.Date;

public class Conseil {




    private String periode;
    private String objet;



    private Date date;
    private String description;





   public Conseil( String objet,String periode, String description) {

        this.objet = objet;

        this.periode = periode;
        this.description = description;
    }
    public Conseil() {

    }

    public Date  getDate() {
        return date;
    }
    public String getPeriode() {
        return periode;
    }

    public void setPeriode(String periode) {
        this.periode = periode;
    }

    public String getObjet() {
        return objet;
    }

    public void setObjet(String objet) {
        this.objet = objet;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
