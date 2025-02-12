/**
 * Projet: R411 - Projet filrouge - Equipe A
 * Auteurs: BONJOUR Corentin, FARCHETTO Lilian, HAMBLI Kenzo
 */
package edu.equipe_a.kcine;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import edu.equipe_a.kcine.adapters.FilmAdapter;
import edu.equipe_a.kcine.parcelables.CinemaParcelable;
import edu.equipe_a.kcine.entries.Film;
import edu.equipe_a.kcine.tools.HttpAsyncGet;
import edu.equipe_a.kcine.tools.PostExecuteActivity;

public class CinemaSingleActivity extends AppCompatActivity implements PostExecuteActivity<Film> {

    private ListView filmList;
    private int cinemaPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cinema);

        // Récupération des informations du cinéma
        final CinemaParcelable cinema = getIntent().getParcelableExtra("cinema");
        cinemaPosition = getIntent().getIntExtra("cinemaNum", -1);

        // Affichage du titre du cinéma
        final TextView cinemaTitle = findViewById(R.id.title);
        cinemaTitle.setText(cinema.getNom());

        filmList = findViewById(R.id.filmList); // Récupération de la ListView des films
        final List<String> films = new ArrayList<>(); // Création de la liste des films
        final ArrayAdapter<String> filmAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, films); // Création de l'adapter
        filmList.setAdapter(filmAdapter); // Association de l'adapter à la ListView

        initFilmList(); // Initialisation de la liste des films
    }

    /**
     * Initialisation de la liste des films
     */
    public void initFilmList() {
        String url = "http://localhost:9090/api/filmsbycinema?cinemaId=" + cinemaPosition;
        Log.d("CinemaSingleActivity", "initFilmList: " + url);
        new HttpAsyncGet<>(url, Film.class, this, new ProgressDialog(CinemaSingleActivity.this) );
    }

    /**
     * Méthode appelée après l'exécution de la requête HTTP
     * @param itemList Liste des films
     */
    @Override
    public void onPostExecute(List<Film> itemList) {
        final ListAdapter filmAdapter = new FilmAdapter(this, itemList, cinemaPosition);
        filmList.setAdapter(filmAdapter);
    }

}
