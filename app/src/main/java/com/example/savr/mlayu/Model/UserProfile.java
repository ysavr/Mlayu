package com.example.savr.mlayu.Model;

import android.widget.TextView;

/**
 * Created by SAVR on 05/09/2017.
 */

public class UserProfile {
    private String id;
    private String name;
    private String email;
    private String gender;
    private Integer berat;
    private Integer umur;
    private Integer tinggi;

    public UserProfile(String id, String name, String email,String gender, Integer umur, Integer berat, Integer tinggi) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.gender = gender;
        this.berat = berat;
        this.umur = umur;
        this.tinggi = tinggi;
    }

    public Integer getBerat() {
        return berat;
    }

    public void setBerat(Integer berat) {
        this.berat = berat;
    }

    public Integer getUmur() {
        return umur;
    }

    public void setUmur(Integer umur) {
        this.umur = umur;
    }

    public Integer getTinggi() {
        return tinggi;
    }

    public void setTinggi(Integer tinggi) {
        this.tinggi = tinggi;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserProfile() {
    }
}
