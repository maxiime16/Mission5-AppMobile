package com.example.mission5_appmobile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserDetailsActivity extends AppCompatActivity {

    private Button btnRetour;
    ListView superListView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);

        superListView = findViewById(R.id.superListView);
        btnRetour = findViewById(R.id.btnRetour);

        // Récupération de l'utilisateur sélectionné depuis l'intent
        ListeChauffeur selectedListeChauffeur = (ListeChauffeur) getIntent().getSerializableExtra("selectedUser");

        // Affichage des informations de l'utilisateur dans les TextView correspondants

        getAffectations(selectedListeChauffeur.getId());

        btnRetour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(UserDetailsActivity.this, MainActivity.class); // Crée une intention pour ouvrir l'activité UserDetailsActivity
                startActivity(intent); // Démarre l'activité UserDetailsActivity
            }
        });
    }

    private void getAffectations(int chauffeurId) {

        Call<List<Affectation>> call = RetrofitClient.getInstance().getMyApi().getAffectation(chauffeurId); // Appelle la méthode getAffectationsByChauffeur() du service web via Retrofit en passant l'id du chauffeur sélectionné
        call.enqueue(new Callback<List<Affectation>>() { // Enregistre un Callback pour être notifié du résultat de l'appel asynchrone
            @Override
            public void onResponse(@NonNull Call<List<Affectation>> call, @NonNull Response<List<Affectation>> response) {
                if (response.isSuccessful()) { // Vérifie si la requête a réussi

                    List<Affectation> affectationList = response.body(); // Récupère la liste des affectations depuis la réponse
                    assert affectationList != null;
                    ArrayList<String> list = new ArrayList<>();
                    for (Affectation user : affectationList) {
                        String temp = "";
                        temp += "Date: " + user.getDate() + "\n";
                        temp += "Motif: " + user.getLibelle() + "\n";
                        list.add(temp);
                    }
                    superListView.setAdapter(new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, list));

                } else {
                    Toast.makeText(UserDetailsActivity.this, "Failed to get affectations", Toast.LENGTH_SHORT).show(); // Affiche un message d'erreur si la requête a échoué
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Affectation>> call, @NonNull Throwable t) {
                t.printStackTrace(); // Affiche la pile d'erreurs dans la console
                Toast.makeText(UserDetailsActivity.this, "Failed to get affectations", Toast.LENGTH_SHORT).show(); // Affiche un message d'erreur si la requête a échoué
            }
        });
    }
}