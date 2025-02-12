/**
 * Projet: R411 - Projet filrouge - Equipe A
 * Auteurs: BONJOUR Corentin, FARCHETTO Lilian, HAMBLI Kenzo
 */
package edu.equipe_a.kcine.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import java.text.Normalizer;
import java.util.ArrayList;

import edu.equipe_a.kcine.R;
import edu.equipe_a.kcine.entries.Cinema;

/**
 * Adapter for the cinema list
 */
public class CinemaAdapter extends BaseAdapter {

    private final Context context; // Context
    private final ArrayList<Cinema> cinemas; // Liste des cinemas

    private final LayoutInflater inflater; // Inflater

    /**
     * Constructor
     *
     * @param context Context
     * @param cinemas List of cinemas
     */
    public CinemaAdapter(Context context, ArrayList<Cinema> cinemas) {
        this.context = context;
        this.cinemas = cinemas;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return cinemas.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final View view = inflater.inflate(R.layout.adapter_cinema, null);
        final TextView name = view.findViewById(R.id.cinema_name);
        final TextView address = view.findViewById(R.id.address);
        final TextView distance = view.findViewById(R.id.distance);
        final ImageView imageView = view.findViewById(R.id.imageView);
        name.setText(cinemas.get(position).getNom());
        address.setText(cinemas.get(position).getAdresse());
        distance.setText((double) Math.round(cinemas.get(position).getDistance() * 100) / 100 + " km");

        final String nameToCheck = Normalizer.normalize(name.getText().toString().toLowerCase(), Normalizer.Form.NFD);
        if (nameToCheck.contains("gaumont")) {
            imageView.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.gaumont_logo));
        } else if (nameToCheck.contains("pathe")) {
            imageView.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.pathe_logo));
            Log.d("CinemaAdapter", "getView: pathe trouvé");
        } else if (nameToCheck.contains("ugc")) {
            imageView.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ugc_logo));
        } else if (nameToCheck.contains("megarama")) {
            imageView.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.megarama_logo));
        } else {
            imageView.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.kcine_nobg));
        }
        Log.d("CinemaAdapter", "getView: " + nameToCheck);

        return view;
    }

}
