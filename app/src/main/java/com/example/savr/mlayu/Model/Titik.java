package com.example.savr.mlayu.Model;

/**
 * Created by SAVR on 05/09/2017.
 */

public class Titik {

    private String id_lari;
    private double latitude;
    private double longitude;

    public Titik(String id_lari, double latitude, double longitude) {
        this.id_lari = id_lari;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Titik() {
    }

    public String getId_lari() {
        return id_lari;
    }

    public void setId_lari(String id_lari) {
        this.id_lari = id_lari;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
