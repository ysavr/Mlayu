package com.example.savr.mlayu.Model;

/**
 * Created by SAVR on 05/09/2017.
 */

public class Titik {

    private double latitude;
    private double longitude;

    public Titik(double latitude, double longitude) {

        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Titik() {
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
