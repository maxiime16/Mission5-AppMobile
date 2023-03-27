package com.example.mission5_appmobile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private Spinner userSpinner; // Déclare une instance de Spinner
    private List<ListeChauffeur> listeChauffeurList; // Déclare une liste de User
    private Button button; // Déclare une instance de Button

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Définit le layout de l'activité

        userSpinner = findViewById(R.id.user_spinner); // Récupère le Spinner depuis le layout
        getChauffeur(); // Appelle la méthode pour récupérer la liste des utilisateurs
    }

    private void getChauffeur() {
        Call<List<ListeChauffeur>> call = RetrofitClient.getInstance().getMyApi().getChauffeur(); // Appelle la méthode getUsers() du service web via Retrofit
        call.enqueue(new Callback<List<ListeChauffeur>>() { // Enregistre un Callback pour être notifié du résultat de l'appel asynchrone
            @Override
            public void onResponse(@NonNull Call<List<ListeChauffeur>> call, @NonNull Response<List<ListeChauffeur>> response) {
                if (response.isSuccessful()) { // Vérifie si la requête a réussi
                    listeChauffeurList = response.body(); // Récupère la liste des utilisateurs depuis la réponse

                    List<String> userNameList = new ArrayList<>();
                    for (ListeChauffeur listeChauffeur : listeChauffeurList) {
                        userNameList.add(listeChauffeur.getName()); // Ajoute le nom de chaque utilisateur à la liste des noms d'utilisateur
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_spinner_item, userNameList); // Crée un ArrayAdapter pour afficher la liste des noms d'utilisateur dans le Spinner
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // Définit le layout utilisé pour chaque élément de la liste déroulante
                    userSpinner.setAdapter(adapter); // Associe l'adapter au Spinner

                    Button goToUserDetailsButton = findViewById(R.id.goToUserDetailsButton); // Récupère le bouton depuis le layout
                    goToUserDetailsButton.setOnClickListener(new View.OnClickListener() { // Définit un listener pour le bouton
                        @Override
                        public void onClick(View v) {

                            String selectedUserName = (String) userSpinner.getSelectedItem(); // Récupère le nom de l'utilisateur sélectionné dans le Spinner

                            for (ListeChauffeur listeChauffeur : listeChauffeurList) { // Parcourt la liste des utilisateurs pour trouver celui qui correspond au nom sélectionné
                                if (listeChauffeur.getName().equals(selectedUserName)) {
                                    Intent intent = new Intent(MainActivity.this, UserDetailsActivity.class); // Crée une intention pour ouvrir l'activité UserDetailsActivity
                                    intent.putExtra("selectedUser", listeChauffeur); // Ajoute l'utilisateur sélectionné en tant qu'extra
                                    startActivity(intent); // Démarre l'activité UserDetailsActivity
                                    break; // Sort de la boucle
                                }
                            }
                        }
                    });

                } else {
                    Toast.makeText(MainActivity.this, "Failed to get users", Toast.LENGTH_SHORT).show(); // Affiche un message d'erreur si la requête a échoué
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<ListeChauffeur>> call, @NonNull Throwable t) {
                t.printStackTrace(); // Affiche la pile d'erreurs dans la console
                Toast.makeText(MainActivity.this, "Failed to get users", Toast.LENGTH_SHORT).show(); // Affiche un message d'erreur si la requête a échoué
            }
        });
    }
}