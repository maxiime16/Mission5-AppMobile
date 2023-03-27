package com.example.mission5_appmobile;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ListeChauffeur implements Serializable {
    @SerializedName("code")
    private int id;

    @SerializedName("chauffeur")
    private String name;

    public ListeChauffeur(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

}