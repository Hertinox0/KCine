/**
 * Projet: R411 - Projet filrouge - Equipe A
 * Auteurs: BONJOUR Corentin, FARCHETTO Lilian, HAMBLI Kenzo
 */
package edu.equipe_a.kcine;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

import edu.equipe_a.kcine.adapters.CinemaAdapter;
import edu.equipe_a.kcine.entries.Cinema;
import edu.equipe_a.kcine.tools.HttpAsyncGet;
import edu.equipe_a.kcine.tools.NavigationBar;
import edu.equipe_a.kcine.tools.PostExecuteActivity;
import nl.joery.animatedbottombar.AnimatedBottomBar;

/**
 * Activité principale de l'application
 */
public class MainActivity extends AppCompatActivity implements PostExecuteActivity<Cinema> {

    private ArrayList<Cinema> cinemaList; // Liste des cinémas
    private int selectedDistance = 9999999; // Distance en km (valeurs par défaut)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Demande de permission pour la localisation
        ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION}, 101);

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Bouton pour mettre à jour la liste des cinémas
        final Button btn = findViewById(R.id.updateList);
        btn.setOnClickListener(v -> initListCinema());

        // Initialisation de la barre de navigation
        final AnimatedBottomBar bar = findViewById(R.id.top_bar);
        new NavigationBar(bar, this.getApplicationContext(), this, 0).registerListeners();

        this.createNotificationChannel(); // Création du canal de notification
        initListCinema(); // Initialisation de la liste des cinémas
        initSeekBar(); // Initialisation de la barre de recherche
    }

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(0, 0); // Suppression de l'animation de transition entre les activités
    }

    /**
     * Création du canal de notification
     */
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            final NotificationManager notificationManager = getSystemService(NotificationManager.class);
            final NotificationChannel channel = new NotificationChannel("channel_id", "KCiné Notifications", NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription("Channel description");
            // Enregistrement du canal de notification
            notificationManager.createNotificationChannel(channel);
        }
    }

    /**
     * Initialisation de la liste des cinémas
     */
    public void initListCinema() {
        final LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

            return;
        }

        final Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        final double longitude = location.getLongitude();
        final double latitude = location.getLatitude();
        Log.d("MainActivity", "initListCinema: " + location.getLatitude() + " " + location.getLongitude());
        final String url = "http://localhost:9090/api/closest?latitude=" + location.getLatitude() + "&longitude=" + location.getLongitude() + "&n=19";
        Log.d("MainActivity", "initListCinema: " + url);
        new HttpAsyncGet<>(url, Cinema.class, this, new ProgressDialog(MainActivity.this));
    }

    /**
     * Méthode appelée après la récupération des données
     * @param itemList Liste des cinémas
     */
    @Override
    public void onPostExecute(List<Cinema> itemList) {
        final ListView listView = findViewById(R.id.cinemaList);
        cinemaList = new ArrayList<>();
        cinemaList.addAll(itemList);
        final ArrayList<String> cinemaNames = new ArrayList<>();
        for (Cinema cinema : cinemaList)
            cinemaNames.add(cinema.getNom());
        final ListAdapter adapter = new CinemaAdapter(this, cinemaList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            // Lance l'activité de détail du cinéma
            final Intent intent = new Intent(MainActivity.this, CinemaSingleActivity.class);
            intent.putExtra("cinema", cinemaList.get(position).getCinemaParselable());
            intent.putExtra("cinemaNum", position);
            startActivity(intent);
        });
    }

    /**
     * Initialisation de la barre de recherche
     */
    public void initSeekBar(){
        final SeekBar seekBar = findViewById(R.id.distanceBar);
        final TextView textBar = findViewById(R.id.distanceText);
        seekBar.setProgress(50);
        textBar.setText("∞ km");

        // On change la distance en fonction de la valeur de la barre
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (progress > 49){
                    textBar.setText("∞" + " km");
                    selectedDistance = 9999999;
                } else {
                    textBar.setText(progress + " km");
                    selectedDistance = progress;
                }
                final ListView listView = findViewById(R.id.cinemaList);
                // On filtre les cinémas en fonction de la distance
                final ArrayList<Cinema> cinemaListFiltered = new ArrayList<>();
                for (Cinema cinema : cinemaList)
                    if (cinema.getDistance() <= selectedDistance)
                        cinemaListFiltered.add(cinema);
                final ListAdapter adapter = new CinemaAdapter(MainActivity.this, cinemaListFiltered);
                listView.setAdapter(adapter);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }

}
