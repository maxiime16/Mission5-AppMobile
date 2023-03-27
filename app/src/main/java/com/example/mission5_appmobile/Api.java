package com.example.mission5_appmobile;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Api {
    String BASE_URL = "http://10.0.2.2:8888/APIconnexion/Fonctions/";

    @GET("AfficherChauffeur.php")
    Call<List<ListeChauffeur>> getChauffeur();

    @GET("AfficherAffectation.php")
    Call<List<Affectation>> getAffectation(@Query("chauffeur_id") int chauffeurId);
}