package com.example.FloodAlert.Model;

import android.graphics.Bitmap;

import com.google.firebase.database.Exclude;

public class ImageUpload {



    private String name;
 @Exclude
    public String getkey() {
        return mkey;
    }
@Exclude
    public void setkey(String key) {
      mkey = mkey;
    }

    private  String mkey;


    private String url;



    private String etat ="en cours";
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private String description;
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }
    public ImageUpload(String name, String url,String description, String etat) {
        this.name = name;
        this.url = url;
        this.description = description;
        this.etat = etat;
    }
    public ImageUpload(String name, String url, String etat) {
        this.name = name;
        this.url = url;

        this.etat = etat;
    }
    public ImageUpload() {

    }

}
