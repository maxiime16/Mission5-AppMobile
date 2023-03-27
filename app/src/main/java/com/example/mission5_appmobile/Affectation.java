package com.example.mission5_appmobile;

import com.google.gson.annotations.SerializedName;

public class Affectation {
    private String id;
    @SerializedName("datemission")
    private String date;
    @SerializedName("motif")
    private String libelle;

    public Affectation(String id, String date, String libelle) {
        this.id = id;
        this.date = date;
        this.libelle = libelle;
    }

    public String getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public String getLibelle() {
        return libelle;
    }
}